package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GestionRolesController", urlPatterns = {"/admin/roles"})
public class GestionRolesController extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarYMostrar(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String rol = request.getParameter("rol");

        HttpSession sesion = request.getSession(false);
        int idAdmin = (int) sesion.getAttribute("idUsuario");

        String mensaje;
        try {
            if ("asignar".equals(accion)) {
                dao.asignarRol(idUsuario, rol);
                dao.registrarAuditoria(idAdmin, "cambio_rol",
                        "Asignó rol " + rol + " al usuario id " + idUsuario);
                mensaje = "Rol '" + rol + "' asignado correctamente.";

            } else if ("revocar".equals(accion)) {
                if (dao.contarRoles(idUsuario) <= 1) {
                    mensaje = "No se puede quitar: el usuario debe conservar al menos un rol.";
                } else {
                    dao.revocarRol(idUsuario, rol);
                    dao.registrarAuditoria(idAdmin, "cambio_rol",
                            "Revocó rol " + rol + " al usuario id " + idUsuario);
                    mensaje = "Rol '" + rol + "' revocado correctamente.";
                }
            } else {
                mensaje = "Acción no reconocida.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mensaje = "Ocurrió un error al procesar la solicitud.";
        }

        cargarYMostrar(request, response, mensaje);
    }

    private void cargarYMostrar(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        try {
            List<Usuario> usuarios = dao.listarUsuariosConRoles();
            List<String> todosLosRoles = dao.listarNombresDeRoles();
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("todosLosRoles", todosLosRoles);
        } catch (SQLException e) {
            e.printStackTrace();
            mensaje = "No se pudo cargar la lista de usuarios.";
        }
        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
        }
        request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);
    }
}