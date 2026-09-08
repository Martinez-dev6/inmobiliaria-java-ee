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
import java.util.regex.Pattern;

@WebServlet(name = "PerfilController", urlPatterns = {"/cliente/perfil"})
public class PerfilController extends HttpServlet {

    private static final Pattern PATRON_TELEFONO = Pattern.compile("^[0-9+()\\-\\s]{7,20}$");

    private final PerfilDAO dao = new PerfilDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        Perfil perfil;
        try {
            perfil = dao.buscarPorIdUsuario(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
            perfil = null;
            request.setAttribute("errorPerfil", "No se pudo cargar el perfil.");
        }

        mostrarFormulario(request, response, perfil);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String documento = request.getParameter("documento");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        Perfil perfilEscrito = new Perfil(idUsuario, nombres, apellidos, documento);
        perfilEscrito.setTelefono(telefono);
        perfilEscrito.setDireccion(direccion);

        String error = validar(nombres, apellidos, documento, telefono);
        if (error != null) {
            request.setAttribute("errorPerfil", error);
            mostrarFormulario(request, response, perfilEscrito);
            return;
        }

        perfilEscrito.setNombres(nombres.trim());
        perfilEscrito.setApellidos(apellidos.trim());
        perfilEscrito.setDocumento(documento.trim());
        perfilEscrito.setTelefono(telefono != null ? telefono.trim() : null);
        perfilEscrito.setDireccion(direccion != null ? direccion.trim() : null);

        try {
            dao.guardar(perfilEscrito);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorPerfil", "No se pudo guardar el perfil. Intenta más tarde.");
            mostrarFormulario(request, response, perfilEscrito);
            return;
        }

        request.setAttribute("mensajeExito", "Perfil actualizado correctamente.");
        mostrarFormulario(request, response, perfilEscrito);
    }

    private String validar(String nombres, String apellidos, String documento, String telefono) {
        if (nombres == null || nombres.trim().isEmpty()
                || apellidos == null || apellidos.trim().isEmpty()
                || documento == null || documento.trim().isEmpty()) {
            return "Nombres, apellidos y documento son obligatorios.";
        }
        if (telefono != null && !telefono.trim().isEmpty()
                && !PATRON_TELEFONO.matcher(telefono.trim()).matches()) {
            return "El formato del teléfono no es válido.";
        }
        return null;
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Perfil perfil)
            throws ServletException, IOException {
        request.setAttribute("perfil", perfil);
        request.getRequestDispatcher("/cliente/perfil.jsp").forward(request, response);
    }
}