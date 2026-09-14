package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.dao.UsuarioDAO;
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
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response, null, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("cancelar".equals(request.getParameter("accion"))) {
            procesarCancelar(request, response);
            return;
        }

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
            int idGenerado = citaDAO.crear(cita);
            usuarioDAO.registrarAuditoria(idCliente, "agendar_cita",
                    "Agendó la cita id " + idGenerado + " para la propiedad id " + idPropiedad);
            mostrar(request, response, null, "Cita agendada correctamente.");
        } catch (HorarioOcupadoException e) {
            mostrar(request, response, e.getMessage(), null);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrar(request, response, "No se pudo agendar la cita. Intenta más tarde.", null);
        }
    }

    private void procesarCancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");
        int idCita = Integer.parseInt(request.getParameter("idCita"));

        try {
            int filas = citaDAO.cancelarPorCliente(idCita, idCliente);
            if (filas == 0) {
                mostrar(request, response, "Esa cita no se puede cancelar (ya pasó o no es tuya).", null);
                return;
            }
            usuarioDAO.registrarAuditoria(idCliente, "cancelar_cita_cliente", "Canceló su cita id " + idCita);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrar(request, response, "No se pudo cancelar la cita. Intenta más tarde.", null);
            return;
        }

        mostrar(request, response, null, "Cita cancelada.");
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
