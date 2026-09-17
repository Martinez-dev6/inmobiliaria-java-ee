package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.AuditoriaDAO;
import com.inmobiliaria.util.Paginacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AuditoriaController", urlPatterns = {"/admin/auditoria"})
public class AuditoriaController extends HttpServlet {

    private static final int POR_PAGINA = 25;

    private final AuditoriaDAO auditoriaDAO = new AuditoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int total = auditoriaDAO.contar();
            Paginacion paginacion = new Paginacion(request.getParameter("pagina"), POR_PAGINA, total);

            request.setAttribute("registros",
                    auditoriaDAO.listarPagina(paginacion.getLimite(), paginacion.getDesplazamiento()));

            paginacion.publicar(request, request.getContextPath() + "/admin/auditoria?");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorAuditoria", "No se pudo cargar la auditoría.");
        }

        request.getRequestDispatcher("/admin/auditoria.jsp").forward(request, response);
    }
}
