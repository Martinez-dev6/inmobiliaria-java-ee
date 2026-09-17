package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.dao.InmobiliariaDAO;
import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.util.Flash;

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
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response);
    }

    /** POST-Redirect-GET para que recargar no vuelva a cambiar el estado de la cita. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        int idCita = Integer.parseInt(request.getParameter("idCita"));
        String accion = request.getParameter("accion");
        String respuesta = request.getParameter("respuesta");

        String nuevoEstado;
        String confirmacion;
        switch (accion == null ? "" : accion) {
            case "confirmar":
                nuevoEstado = "confirmada";
                confirmacion = "Cita confirmada. El cliente verá tu respuesta en su panel.";
                break;
            case "cancelar":
                nuevoEstado = "cancelada";
                confirmacion = "Cita cancelada.";
                break;
            case "realizada":
                nuevoEstado = "realizada";
                confirmacion = "Cita marcada como realizada.";
                break;
            default:
                nuevoEstado = null;
                confirmacion = null;
        }

        try {
            if (idInmobiliaria == null || nuevoEstado == null) {
                Flash.error(request, "No se pudo procesar la cita.");
            } else {
                citaDAO.cambiarEstado(idCita, idInmobiliaria, nuevoEstado, respuesta);

                HttpSession sesion = request.getSession(false);
                int idUsuario = (int) sesion.getAttribute("idUsuario");
                usuarioDAO.registrarAuditoria(idUsuario, "cambio_estado_cita",
                        "Cita id " + idCita + " -> " + nuevoEstado);

                Flash.exito(request, confirmacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "No se pudo actualizar la cita. Intenta más tarde.");
        }

        response.sendRedirect(request.getContextPath() + "/agente/citas");
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        request.setAttribute("sinInmobiliaria", idInmobiliaria == null);

        try {
            if (idInmobiliaria != null) {
                request.setAttribute("citas", citaDAO.listarPorInmobiliaria(idInmobiliaria));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorCitas", "No se pudieron cargar las citas.");
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
