package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.InmobiliariaDAO;
import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.modelo.Inmobiliaria;
import com.inmobiliaria.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Ficha comercial de la agencia.
 *
 * Tener el ROL "Inmobiliaria" no basta para publicar: las propiedades apuntan a
 * una fila de la tabla 'inmobiliaria' (nombre comercial, NIT, telefono) y esa
 * fila no existe hasta que el agente la crea aqui. Antes no habia ninguna
 * pantalla para hacerlo, asi que cualquier cuenta a la que un administrador le
 * diera el rol quedaba bloqueada con "Tu cuenta no esta asociada a ninguna
 * inmobiliaria" y sin forma de salir de ahi.
 */
@WebServlet(name = "InmobiliariaPerfilController", urlPatterns = {"/agente/perfil"})
public class InmobiliariaPerfilController extends HttpServlet {

    private static final Pattern PATRON_TELEFONO = Pattern.compile("^[0-9+()\\-\\s]{7,20}$");

    private final InmobiliariaDAO inmobiliariaDAO = new InmobiliariaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = idUsuario(request);

        try {
            request.setAttribute("inmobiliaria", inmobiliariaDAO.buscarPorUsuario(idUsuario));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorInmobiliaria", "No se pudo cargar la ficha de la inmobiliaria.");
        }

        request.getRequestDispatcher("/agente/perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = idUsuario(request);

        Inmobiliaria ficha = new Inmobiliaria();
        ficha.setIdUsuario(idUsuario);
        ficha.setNombreComercial(limpiar(request.getParameter("nombreComercial")));
        ficha.setNit(limpiar(request.getParameter("nit")));
        ficha.setTelefonoContacto(limpiar(request.getParameter("telefonoContacto")));

        String error = validar(ficha);
        if (error != null) {
            request.setAttribute("errorInmobiliaria", error);
            request.setAttribute("inmobiliaria", ficha);
            request.getRequestDispatcher("/agente/perfil.jsp").forward(request, response);
            return;
        }

        try {
            boolean esNueva = inmobiliariaDAO.buscarIdInmobiliariaPorUsuario(idUsuario) == null;
            int idInmobiliaria = inmobiliariaDAO.guardar(ficha);

            usuarioDAO.registrarAuditoria(idUsuario,
                    esNueva ? "crear_inmobiliaria" : "actualizar_inmobiliaria",
                    (esNueva ? "Creó" : "Actualizó") + " la ficha de la inmobiliaria id " + idInmobiliaria
                            + " (" + ficha.getNombreComercial() + ")");

            Flash.exito(request, esNueva
                    ? "Listo: tu inmobiliaria quedó registrada y ya puedes publicar propiedades."
                    : "Datos de la inmobiliaria actualizados.");
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "No se pudo guardar la ficha de la inmobiliaria. Intenta más tarde.");
        }

        response.sendRedirect(request.getContextPath() + "/agente/perfil");
    }

    private String validar(Inmobiliaria ficha) {
        if (ficha.getNombreComercial() == null || ficha.getNombreComercial().isEmpty()) {
            return "El nombre comercial es obligatorio.";
        }
        if (ficha.getNombreComercial().length() > 150) {
            return "El nombre comercial no puede superar los 150 caracteres.";
        }
        if (ficha.getNit() != null && ficha.getNit().length() > 30) {
            return "El NIT no puede superar los 30 caracteres.";
        }
        if (ficha.getTelefonoContacto() != null && !ficha.getTelefonoContacto().isEmpty()
                && !PATRON_TELEFONO.matcher(ficha.getTelefonoContacto()).matches()) {
            return "El formato del teléfono de contacto no es válido.";
        }
        return null;
    }

    private String limpiar(String texto) {
        if (texto == null) {
            return null;
        }
        String limpio = texto.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    private int idUsuario(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        return (int) sesion.getAttribute("idUsuario");
    }
}
