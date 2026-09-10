package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.excepcion.HorarioOcupadoException;
import com.inmobiliaria.modelo.Cita;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet(name = "CitaController", urlPatterns = {"/cliente/citas"})
public class CitaController extends HttpServlet {

    private final CitaDAO citaDAO = new CitaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response, null, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");

        int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
        String fechaTexto = request.getParameter("fechaHora"); // formato de <input type="datetime-local">: 2026-09-20T15:30

        Timestamp fechaHora = parsearFechaHora(fechaTexto);
        if (fechaHora == null) {
            mostrar(request, response, "La fecha y hora no son válidas.", null);
            return;
        }

        Cita cita = new Cita(idPropiedad, idCliente, fechaHora);

        try {
            citaDAO.crear(cita);
            mostrar(request, response, null, "Cita agendada correctamente.");
        } catch (HorarioOcupadoException e) {
            mostrar(request, response, e.getMessage(), null);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrar(request, response, "No se pudo agendar la cita. Intenta más tarde.", null);
        }
    }

    private Timestamp parsearFechaHora(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            // "2026-09-20T15:30" -> "2026-09-20 15:30:00"
            String normalizado = texto.replace("T", " ") + ":00";
            return Timestamp.valueOf(normalizado);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response,
                          String error, String mensaje)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");

        try {
            request.setAttribute("citas", citaDAO.listarPorCliente(idCliente));
        } catch (SQLException e) {
            e.printStackTrace();
            error = "No se pudieron cargar tus citas.";
        }

        if (error != null) {
            request.setAttribute("errorCitas", error);
        }
        if (mensaje != null) {
            request.setAttribute("mensajeCitas", mensaje);
        }

        request.getRequestDispatcher("/cliente/citas.jsp").forward(request, response);
    }
}
