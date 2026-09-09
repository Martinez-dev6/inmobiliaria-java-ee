package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.PerfilDAO;
import com.inmobiliaria.modelo.Perfil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ClientePanelController", urlPatterns = {"/cliente/panel"})
public class ClientePanelController extends HttpServlet {

    private final PerfilDAO perfilDAO = new PerfilDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        try {
            Perfil perfil = perfilDAO.buscarPorIdUsuario(idUsuario);
            request.setAttribute("perfil", perfil);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/cliente/panel.jsp").forward(request, response);
    }
}