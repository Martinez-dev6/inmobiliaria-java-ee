package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.ReporteDAO;
import com.inmobiliaria.modelo.ReporteCiudad;
import com.inmobiliaria.modelo.ReportePropiedadesPorCiudad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ReporteController", urlPatterns = {"/admin/reportes"})
public class ReporteController extends HttpServlet {

    private final ReporteDAO reporteDAO = new ReporteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<ReportePropiedadesPorCiudad> filas = reporteDAO.propiedadesPorCiudadYEstado();
            // Una fila por ciudad, con el desglose por estado dentro: antes la
            // misma ciudad se repetía una vez por cada estado.
            request.setAttribute("ciudades", ReporteCiudad.agrupar(filas));
            request.setAttribute("totalGeneral", sumar(filas));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorReporte", "No se pudo generar el reporte.");
        }

        request.getRequestDispatcher("/admin/reportes.jsp").forward(request, response);
    }

    private int sumar(List<ReportePropiedadesPorCiudad> filas) {
        int total = 0;
        for (ReportePropiedadesPorCiudad fila : filas) {
            total += fila.getTotal();
        }
        return total;
    }
}
