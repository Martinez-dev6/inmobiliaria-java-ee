package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.ReporteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ReporteController", urlPatterns = {"/admin/reportes"})
public class ReporteController extends HttpServlet {

    private final ReporteDAO reporteDAO = new ReporteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("filas", reporteDAO.propiedadesPorCiudadYEstado());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorReporte", "No se pudo generar el reporte.");
        }

        request.getRequestDispatcher("/admin/reportes.jsp").forward(request, response);
    }
}
