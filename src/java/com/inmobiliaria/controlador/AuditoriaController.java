package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.AuditoriaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AuditoriaController", urlPatterns = {"/admin/auditoria"})
public class AuditoriaController extends HttpServlet {

    private static final int LIMITE_REGISTROS = 200;

    private final AuditoriaDAO auditoriaDAO = new AuditoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("registros", auditoriaDAO.listarRecientes(LIMITE_REGISTROS));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorAuditoria", "No se pudo cargar la auditoría.");
        }

        request.getRequestDispatcher("/admin/auditoria.jsp").forward(request, response);
    }
}
