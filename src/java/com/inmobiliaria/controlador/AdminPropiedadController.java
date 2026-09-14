package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.dao.UsuarioDAO;
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

@WebServlet(name = "AdminPropiedadController", urlPatterns = {"/admin/propiedades"})
public class AdminPropiedadController extends HttpServlet {

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
        String accion = request.getParameter("accion");
        String nuevoEstado = "baja".equals(accion) ? "inactiva" : "disponible";

        HttpSession sesion = request.getSession(false);
        int idAdmin = (int) sesion.getAttribute("idUsuario");

        try {
            Propiedad propiedad = propiedadDAO.buscarPorId(idPropiedad);
            propiedadDAO.cambiarEstadoAdmin(idPropiedad, nuevoEstado);

            if (propiedad != null) {
                String verbo = "baja".equals(accion) ? "Dio de baja" : "Reactivó";
                usuarioDAO.registrarAuditoria(idAdmin,
                        "baja".equals(accion) ? "baja_propiedad_admin" : "reactivar_propiedad_admin",
                        verbo + " la propiedad id " + idPropiedad + " (" + propiedad.getMatriculaInmobiliaria()
                                + " — " + propiedad.getTitulo() + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mostrar(request, response, null);
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {

        try {
            request.setAttribute("propiedades", propiedadDAO.listarTodas());
        } catch (SQLException e) {
            e.printStackTrace();
            error = "No se pudieron cargar las propiedades.";
        }

        if (error != null) {
            request.setAttribute("errorPropiedades", error);
        }

        request.getRequestDispatcher("/admin/propiedades.jsp").forward(request, response);
    }
}
