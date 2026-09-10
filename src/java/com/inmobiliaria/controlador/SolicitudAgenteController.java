package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.DocumentoSolicitudDAO;
import com.inmobiliaria.dao.InmobiliariaDAO;
import com.inmobiliaria.dao.SolicitudDAO;
import com.inmobiliaria.modelo.DocumentoSolicitud;
import com.inmobiliaria.modelo.Solicitud;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SolicitudAgenteController", urlPatterns = {"/agente/solicitudes"})
public class SolicitudAgenteController extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final DocumentoSolicitudDAO documentoDAO = new DocumentoSolicitudDAO();
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
        int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
        String accion = request.getParameter("accion");
        String nuevoEstado = "aprobar".equals(accion) ? "aprobada" : "rechazada";

        try {
            if (idInmobiliaria != null) {
                solicitudDAO.cambiarEstado(idSolicitud, idInmobiliaria, nuevoEstado);
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
                List<Solicitud> solicitudes = solicitudDAO.listarPorInmobiliaria(idInmobiliaria);
                request.setAttribute("solicitudes", solicitudes);

                // Documentos de cada solicitud, indexados por id_solicitud para consultarlos facil en el JSP.
                Map<Integer, List<DocumentoSolicitud>> documentosPorSolicitud = new HashMap<>();
                for (Solicitud s : solicitudes) {
                    documentosPorSolicitud.put(s.getIdSolicitud(), documentoDAO.listarPorSolicitud(s.getIdSolicitud()));
                }
                request.setAttribute("documentosPorSolicitud", documentosPorSolicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            error = "No se pudieron cargar las solicitudes.";
        }

        if (error != null) {
            request.setAttribute("errorSolicitudes", error);
        }

        request.getRequestDispatcher("/agente/solicitudes.jsp").forward(request, response);
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
