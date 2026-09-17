package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CiudadDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.modelo.Ciudad;
import com.inmobiliaria.modelo.Propiedad;
import com.inmobiliaria.util.Flash;
import com.inmobiliaria.util.Paginacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminPropiedadController", urlPatterns = {"/admin/propiedades"})
public class AdminPropiedadController extends HttpServlet {

    private static final int POR_PAGINA = 15;

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final CiudadDAO ciudadDAO = new CiudadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response);
    }

    /**
     * POST-Redirect-GET: se aplica el cambio, se deja el mensaje en sesión y se
     * redirige al mismo listado (con sus filtros y su página). Así un F5 no
     * repite la baja ni la reactivación.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
        String accion = request.getParameter("accion");
        boolean esBaja = "baja".equals(accion);
        String nuevoEstado = esBaja ? "inactiva" : "disponible";

        HttpSession sesion = request.getSession(false);
        int idAdmin = (int) sesion.getAttribute("idUsuario");

        try {
            Propiedad propiedad = propiedadDAO.buscarPorId(idPropiedad);
            propiedadDAO.cambiarEstadoAdmin(idPropiedad, nuevoEstado);

            if (propiedad != null) {
                String verbo = esBaja ? "Dio de baja" : "Reactivó";
                usuarioDAO.registrarAuditoria(idAdmin,
                        esBaja ? "baja_propiedad_admin" : "reactivar_propiedad_admin",
                        verbo + " la propiedad id " + idPropiedad + " (" + propiedad.getMatriculaInmobiliaria()
                                + " — " + propiedad.getTitulo() + ")");

                Flash.exito(request, esBaja
                        ? "Se dio de baja la propiedad " + propiedad.getTitulo() + "."
                        : "Se reactivó la propiedad " + propiedad.getTitulo() + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "No se pudo actualizar el estado de la propiedad.");
        }

        response.sendRedirect(urlListado(request));
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Filtros opcionales: llegan desde el reporte de propiedades por ciudad
        // y estado, para ver exactamente las publicaciones de esa fila.
        Integer idCiudad = parsearEntero(request.getParameter("idCiudad"));
        String estado = request.getParameter("estado");
        if (estado != null && estado.trim().isEmpty()) {
            estado = null;
        }

        try {
            int total = propiedadDAO.contarTodas(idCiudad, estado);
            Paginacion paginacion = new Paginacion(request.getParameter("pagina"), POR_PAGINA, total);

            request.setAttribute("propiedades", propiedadDAO.listarTodas(
                    idCiudad, estado, paginacion.getLimite(), paginacion.getDesplazamiento()));

            paginacion.publicar(request, urlBaseConFiltros(request));

            if (idCiudad != null) {
                request.setAttribute("nombreCiudadFiltro", nombreDeCiudad(idCiudad));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorPropiedades", "No se pudieron cargar las propiedades.");
        }

        request.setAttribute("idCiudadFiltro", idCiudad);
        request.setAttribute("estadoFiltro", estado);

        request.getRequestDispatcher("/admin/propiedades.jsp").forward(request, response);
    }

    /** URL base del listado con los filtros, terminada en "?" o en "&". */
    private String urlBaseConFiltros(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(request.getContextPath()).append("/admin/propiedades?");
        agregarParametro(url, request, "idCiudad");
        agregarParametro(url, request, "estado");
        return url.toString();
    }

    /** La misma URL más la página, para volver a donde estaba el administrador. */
    private String urlListado(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(urlBaseConFiltros(request));
        agregarParametro(url, request, "pagina");
        return url.toString();
    }

    private void agregarParametro(StringBuilder url, HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        if (valor == null || valor.trim().isEmpty()) {
            return;
        }
        try {
            url.append(nombre).append("=").append(URLEncoder.encode(valor.trim(), "UTF-8")).append("&");
        } catch (UnsupportedEncodingException e) {
            // UTF-8 siempre está disponible; si fallara, se omite el filtro.
        }
    }

    private String nombreDeCiudad(int idCiudad) throws SQLException {
        List<Ciudad> ciudades = ciudadDAO.listarTodas();
        for (Ciudad c : ciudades) {
            if (c.getIdCiudad() == idCiudad) {
                return c.getNombreCiudad();
            }
        }
        return null;
    }

    private Integer parsearEntero(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
