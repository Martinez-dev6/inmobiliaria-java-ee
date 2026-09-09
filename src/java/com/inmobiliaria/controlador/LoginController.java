package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private static final String MENSAJE_ERROR_GENERICO = "Correo o contraseña incorrectos.";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("acceso.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        if (correo == null || correo.trim().isEmpty() || contrasena == null || contrasena.isEmpty()) {
            mostrarError(request, response, correo, "Todos los campos son obligatorios.");
            return;
        }

        String correoNormalizado = correo.trim().toLowerCase();

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario;
        try {
            usuario = dao.buscarPorCorreo(correoNormalizado);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError(request, response, correo, "No se pudo validar el acceso. Intenta más tarde.");
            return;
        }

        if (usuario == null || !BCrypt.checkpw(contrasena, usuario.getContrasenaHash())) {
            mostrarError(request, response, correo, MENSAJE_ERROR_GENERICO);
            return;
        }

        if (!usuario.isActivo()) {
            mostrarError(request, response, correo, "Esta cuenta se encuentra inactiva. Contacta al administrador.");
            return;
        }

        // Credenciales válidas: creamos la sesión
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idUsuario", usuario.getIdUsuario());
        sesion.setAttribute("correo", usuario.getCorreo());
        sesion.setAttribute("roles", usuario.getRoles());

        response.sendRedirect(redirigirSegunRol(usuario.getRoles()));
    }

    private void mostrarError(HttpServletRequest request, HttpServletResponse response,
                               String correoEscrito, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("errorLogin", mensaje);
        request.setAttribute("correoPrevio", correoEscrito);
        request.getRequestDispatcher("acceso.jsp").forward(request, response);
    }

   private String redirigirSegunRol(java.util.List<String> roles) {
        if (roles.contains("Administrador")) {
            return "admin/panel";
        } else if (roles.contains("Inmobiliaria")) {
            return "agente/panel";
        } else {
            return "cliente/panel";
        }
    }
}