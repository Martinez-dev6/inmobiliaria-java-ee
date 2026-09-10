package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.dao.InmobiliariaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CitaAgenteController", urlPatterns = {"/agente/citas"})
public class CitaAgenteController extends HttpServlet {

    private final CitaDAO citaDAO = new CitaDAO();
    private final InmobiliariaDAO inmobiliariaDAO = new InmobiliariaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        int idCita = Integer.parseInt(request.getParameter("idCita"));
        String accion = request.getParameter("accion");

        String nuevoEstado;
        switch (accion) {
            case "confirmar":
                nuevoEstado = "confirmada";
                break;
            case "cancelar":
                nuevoEstado = "cancelada";
                break;
            case "realizada":
                nuevoEstado = "realizada";
                break;
            default:
                nuevoEstado = null;
        }

        try {
            if (idInmobiliaria != null && nuevoEstado != null) {
                citaDAO.cambiarEstado(idCita, idInmobiliaria, nuevoEstado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mostrar(request, response, null);
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);

        try {
            if (idInmobiliaria != null) {
                request.setAttribute("citas", citaDAO.listarPorInmobiliaria(idInmobiliaria));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            error = "No se pudieron cargar las citas.";
        }

        if (error != null) {
            request.setAttribute("errorCitas", error);
        }

        request.getRequestDispatcher("/agente/citas.jsp").forward(request, response);
    }

    private Integer resolverIdInmobiliaria(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");
        try {
            return inmobiliariaDAO.buscarIdInmobiliariaPorUsuario(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
