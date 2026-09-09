package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CiudadDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.dao.TipoPropiedadDAO;
import com.inmobiliaria.modelo.Propiedad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CatalogoController", urlPatterns = {"/catalogo"})
public class CatalogoController extends HttpServlet {

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final CiudadDAO ciudadDAO = new CiudadDAO();
    private final TipoPropiedadDAO tipoPropiedadDAO = new TipoPropiedadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idCiudad = parsearEntero(request.getParameter("idCiudad"));
        Integer idTipoPropiedad = parsearEntero(request.getParameter("idTipoPropiedad"));
        BigDecimal precioMin = parsearDecimal(request.getParameter("precioMin"));
        BigDecimal precioMax = parsearDecimal(request.getParameter("precioMax"));

        try {
            List<Propiedad> propiedades = propiedadDAO.buscarConFiltros(idCiudad, idTipoPropiedad, precioMin, precioMax);
            request.setAttribute("propiedades", propiedades);
            request.setAttribute("ciudades", ciudadDAO.listarTodas());
            request.setAttribute("tipos", tipoPropiedadDAO.listarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorCatalogo", "No se pudo cargar el catálogo. Intenta más tarde.");
        }

        // se reenvían los filtros ya elegidos para que el formulario los recuerde
        request.setAttribute("idCiudadSeleccionada", idCiudad);
        request.setAttribute("idTipoSeleccionado", idTipoPropiedad);
        request.setAttribute("precioMinTexto", request.getParameter("precioMin"));
        request.setAttribute("precioMaxTexto", request.getParameter("precioMax"));

        request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
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

    private BigDecimal parsearDecimal(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}