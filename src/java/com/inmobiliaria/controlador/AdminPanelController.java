package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.dao.ReporteDAO;
import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.modelo.ReporteCiudad;
import com.inmobiliaria.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminPanelController", urlPatterns = {"/admin/panel"})
public class AdminPanelController extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final ReporteDAO reporteDAO = new ReporteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuariosConRoles();

            long totalClientes = usuarios.stream().filter(u -> u.getRoles().contains("Cliente")).count();
            long totalAgentes = usuarios.stream().filter(u -> u.getRoles().contains("Inmobiliaria")).count();
            long totalAdmins = usuarios.stream().filter(u -> u.getRoles().contains("Administrador")).count();

            request.setAttribute("totalUsuarios", usuarios.size());
            request.setAttribute("totalClientes", totalClientes);
            request.setAttribute("totalAgentes", totalAgentes);
            request.setAttribute("totalAdmins", totalAdmins);
            request.setAttribute("totalPropiedades", propiedadDAO.contarTodas());

            // Resumen por ciudad para el panel: es el mismo reporte agregado,
            // aquí sólo para dar contexto sin salir del panel.
            request.setAttribute("ciudades",
                    ReporteCiudad.agrupar(reporteDAO.propiedadesPorCiudadYEstado()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/admin/panel.jsp").forward(request, response);
    }
}