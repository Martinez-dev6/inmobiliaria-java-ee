package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.FavoritoDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.modelo.Propiedad;
import com.inmobiliaria.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FavoritoController", urlPatterns = {"/cliente/favoritos"})
public class FavoritoController extends HttpServlet {

    private final FavoritoDAO favoritoDAO = new FavoritoDAO();
    private final PropiedadDAO propiedadDAO = new PropiedadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        try {
            request.setAttribute("favoritas", propiedadDAO.listarFavoritasDeUsuario(idUsuario));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorFavoritos", "No se pudieron cargar tus favoritos.");
        }

        request.getRequestDispatcher("/cliente/favoritos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");
        int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
        String accion = request.getParameter("accion");

        try {
            if ("marcar".equals(accion)) {
                favoritoDAO.marcar(idUsuario, idPropiedad);
                Flash.exito(request, "Propiedad añadida a tus favoritos.");
            } else if ("desmarcar".equals(accion)) {
                favoritoDAO.desmarcar(idUsuario, idPropiedad);
                Flash.exito(request, "Propiedad quitada de tus favoritos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "No se pudieron actualizar tus favoritos.");
        }

        response.sendRedirect(request.getContextPath() + "/propiedad?id=" + idPropiedad);
    }
}
