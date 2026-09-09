package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.InmobiliariaDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.modelo.Propiedad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AgentePanelController", urlPatterns = {"/agente/panel"})
public class AgentePanelController extends HttpServlet {

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final InmobiliariaDAO inmobiliariaDAO = new InmobiliariaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        try {
            Integer idInmobiliaria = inmobiliariaDAO.buscarIdInmobiliariaPorUsuario(idUsuario);

            if (idInmobiliaria != null) {
                List<Propiedad> propiedades = propiedadDAO.listarPorInmobiliaria(idInmobiliaria);

                long disponibles = propiedades.stream().filter(p -> "disponible".equals(p.getEstado())).count();
                long inactivas = propiedades.stream().filter(p -> "inactiva".equals(p.getEstado())).count();
                long destacadas = propiedades.stream().filter(Propiedad::isDestacada).count();

                request.setAttribute("totalPropiedades", propiedades.size());
                request.setAttribute("totalDisponibles", disponibles);
                request.setAttribute("totalInactivas", inactivas);
                request.setAttribute("totalDestacadas", destacadas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/agente/panel.jsp").forward(request, response);
    }
}