package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.DocumentoSolicitudDAO;
import com.inmobiliaria.dao.SolicitudDAO;
import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.modelo.Solicitud;
import com.inmobiliaria.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(name = "SolicitudController", urlPatterns = {"/cliente/solicitudes"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 25 * 1024 * 1024
)
public class SolicitudController extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final DocumentoSolicitudDAO documentoDAO = new DocumentoSolicitudDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response);
    }

    /** POST-Redirect-GET: radicar deja un mensaje flash y redirige al listado. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");

        int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
        String tipoSolicitud = request.getParameter("tipoSolicitud");
        String observaciones = request.getParameter("observaciones");

        if (tipoSolicitud == null || (!tipoSolicitud.equals("compra") && !tipoSolicitud.equals("arriendo"))) {
            Flash.error(request, "Selecciona un tipo de solicitud válido (compra o arriendo).");
            response.sendRedirect(request.getContextPath() + "/cliente/solicitudes");
            return;
        }

        Solicitud solicitud = new Solicitud(idPropiedad, idCliente, tipoSolicitud, observaciones);

        try {
            int idGenerado = solicitudDAO.crear(solicitud);
            guardarDocumentos(request, idGenerado);
            usuarioDAO.registrarAuditoria(idCliente, "radicar_solicitud",
                    "Radicó una solicitud de " + tipoSolicitud + " (id " + idGenerado
                            + ") para la propiedad id " + idPropiedad);
            Flash.exito(request, "Solicitud radicada correctamente. La inmobiliaria la revisará pronto.");
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "No se pudo radicar la solicitud. Intenta más tarde.");
        }

        response.sendRedirect(request.getContextPath() + "/cliente/solicitudes");
    }

    private void guardarDocumentos(HttpServletRequest request, int idSolicitud)
            throws IOException, ServletException, SQLException {

        String directorioReal = getServletContext().getRealPath("/documentos/solicitudes");
        File directorio = new File(directorioReal);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        for (Part parte : request.getParts()) {
            if (!"documentos".equals(parte.getName())) {
                continue;
            }
            String nombreOriginal = parte.getSubmittedFileName();
            if (nombreOriginal == null || nombreOriginal.trim().isEmpty() || parte.getSize() == 0) {
                continue;
            }

            String extension = "";
            int punto = nombreOriginal.lastIndexOf('.');
            if (punto >= 0) {
                extension = nombreOriginal.substring(punto);
            }
            String nombreUnico = UUID.randomUUID().toString() + extension;

            parte.write(directorioReal + File.separator + nombreUnico);

            documentoDAO.agregarDocumento(idSolicitud, "soporte", "documentos/solicitudes/" + nombreUnico);
        }
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");

        try {
            request.setAttribute("solicitudes", solicitudDAO.listarPorCliente(idCliente));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorSolicitudes", "No se pudieron cargar tus solicitudes.");
        }

        request.getRequestDispatcher("/cliente/solicitudes.jsp").forward(request, response);
    }
}
