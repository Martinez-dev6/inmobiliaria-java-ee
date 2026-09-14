package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.FavoritoDAO;
import com.inmobiliaria.dao.ImagenPropiedadDAO;
import com.inmobiliaria.dao.PropiedadCaracteristicaDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.modelo.Propiedad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PropiedadDetalleController", urlPatterns = {"/propiedad"})
public class PropiedadDetalleController extends HttpServlet {

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final ImagenPropiedadDAO imagenPropiedadDAO = new ImagenPropiedadDAO();
    private final PropiedadCaracteristicaDAO propiedadCaracteristicaDAO = new PropiedadCaracteristicaDAO();
    private final FavoritoDAO favoritoDAO = new FavoritoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idPropiedad = parsearEntero(request.getParameter("id"));
        if (idPropiedad == null) {
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }

        try {
            Propiedad propiedad = propiedadDAO.buscarPorId(idPropiedad);

            HttpSession sesion = request.getSession(false);
            boolean esAdmin = false;
            if (sesion != null) {
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) sesion.getAttribute("roles");
                esAdmin = roles != null && roles.contains("Administrador");
            }

            // El catálogo público solo muestra propiedades disponibles, pero el
            // administrador necesita poder revisar el detalle de cualquiera
            // (incluida una que él mismo dio de baja) desde su panel de moderación.
            if (propiedad == null || (!"disponible".equals(propiedad.getEstado()) && !esAdmin)) {
                response.sendRedirect(request.getContextPath() + "/catalogo");
                return;
            }

            request.setAttribute("propiedad", propiedad);
            request.setAttribute("imagenes", imagenPropiedadDAO.listarPorPropiedad(idPropiedad));
            request.setAttribute("nombresCaracteristicas", propiedadCaracteristicaDAO.listarNombresPorPropiedad(idPropiedad));

            if (sesion != null && sesion.getAttribute("idUsuario") != null) {
                int idUsuario = (int) sesion.getAttribute("idUsuario");
                request.setAttribute("esFavorita", favoritoDAO.existe(idUsuario, idPropiedad));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }

        request.getRequestDispatcher("/propiedad-detalle.jsp").forward(request, response);
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