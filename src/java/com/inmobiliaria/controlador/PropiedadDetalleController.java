package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.ImagenPropiedadDAO;
import com.inmobiliaria.dao.PropiedadCaracteristicaDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.modelo.Propiedad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PropiedadDetalleController", urlPatterns = {"/propiedad"})
public class PropiedadDetalleController extends HttpServlet {

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final ImagenPropiedadDAO imagenPropiedadDAO = new ImagenPropiedadDAO();
    private final PropiedadCaracteristicaDAO propiedadCaracteristicaDAO = new PropiedadCaracteristicaDAO();

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

            if (propiedad == null || !"disponible".equals(propiedad.getEstado())) {
                response.sendRedirect(request.getContextPath() + "/catalogo");
                return;
            }

            request.setAttribute("propiedad", propiedad);
            request.setAttribute("imagenes", imagenPropiedadDAO.listarPorPropiedad(idPropiedad));
            request.setAttribute("nombresCaracteristicas", propiedadCaracteristicaDAO.listarNombresPorPropiedad(idPropiedad));

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