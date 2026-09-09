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
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "LandingController", urlPatterns = {""})
public class LandingController extends HttpServlet {

    private static final int CANTIDAD_DESTACADAS = 3;

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final CiudadDAO ciudadDAO = new CiudadDAO();
    private final TipoPropiedadDAO tipoPropiedadDAO = new TipoPropiedadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("destacadas", propiedadDAO.listarDestacadas(CANTIDAD_DESTACADAS));
            request.setAttribute("ciudades", ciudadDAO.listarTodas());
            request.setAttribute("tipos", tipoPropiedadDAO.listarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("destacadas", Collections.emptyList());
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}