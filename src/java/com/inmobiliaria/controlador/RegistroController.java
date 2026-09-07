package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.excepcion.CorreoDuplicadoException;
import com.inmobiliaria.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

@WebServlet(name = "RegistroController", urlPatterns = {"/registro"})
public class RegistroController extends HttpServlet {

    private static final Pattern PATRON_CORREO =
        Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nadie debería "visitar" /registro directamente, solo llegar por el form
        response.sendRedirect("acceso.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String confirmarContrasena = request.getParameter("confirmarContrasena");

        request.setAttribute("panelActivo", "registro");
        request.setAttribute("correoPrevio", correo);

        String error = validar(correo, contrasena, confirmarContrasena);
        if (error != null) {
            request.setAttribute("errorRegistro", error);
            forward(request, response);
            return;
        }

        String correoNormalizado = correo.trim().toLowerCase();
        String hash = BCrypt.hashpw(contrasena, BCrypt.gensalt(12));
        Usuario nuevo = new Usuario(correoNormalizado, hash);

        UsuarioDAO dao = new UsuarioDAO();
        try {
            dao.registrarUsuario(nuevo);
        } catch (CorreoDuplicadoException e) {
            request.setAttribute("errorRegistro", e.getMessage());
            forward(request, response);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorRegistro", "No se pudo completar el registro. Intenta más tarde.");
            forward(request, response);
    return;
        }

        // Registro exitoso: se muestra el panel de login con un mensaje de éxito
            request.setAttribute("panelActivo", "login");
            request.setAttribute("animarRegreso", true);
            request.setAttribute("mensajeExito", "Cuenta creada con éxito. Ahora inicia sesión.");
            forward(request, response);
    }

    private String validar(String correo, String contrasena, String confirmarContrasena) {
        if (correo == null || correo.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()
                || confirmarContrasena == null || confirmarContrasena.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }
        if (!PATRON_CORREO.matcher(correo.trim()).matches()) {
            return "El formato del correo no es válido.";
        }
        if (contrasena.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres.";
        }
        if (!contrasena.equals(confirmarContrasena)) {
            return "Las contraseñas no coinciden.";
        }
        return null;
    }

    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("acceso.jsp").forward(request, response);
    }
}