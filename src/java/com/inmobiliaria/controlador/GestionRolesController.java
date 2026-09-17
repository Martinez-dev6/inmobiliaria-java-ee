package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.modelo.Usuario;
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

@WebServlet(name = "GestionRolesController", urlPatterns = {"/admin/roles"})
public class GestionRolesController extends HttpServlet {

    private static final String ROL_ADMIN = "Administrador";

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarYMostrar(request, response);
    }

    /**
     * POST-Redirect-GET: el resultado viaja como mensaje flash en sesión y la
     * respuesta es una redirección, para que recargar no repita el cambio de rol.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String rol = request.getParameter("rol");

        HttpSession sesion = request.getSession(false);
        int idAdmin = (int) sesion.getAttribute("idUsuario");

        try {
            if (esDeOtroAdministrador(accion, idAdmin, idUsuario)) {
                Flash.error(request, "Esa es la cuenta de otro administrador: no puedes cambiarle los roles "
                        + "ni el estado. Cada administrador gestiona la suya.");
            } else if ("asignar".equals(accion)) {
                asignar(request, idAdmin, idUsuario, rol);
            } else if ("revocar".equals(accion)) {
                revocar(request, idAdmin, idUsuario, rol);
            } else if ("activar".equals(accion)) {
                cambiarEstado(request, idAdmin, idUsuario, true);
            } else if ("desactivar".equals(accion)) {
                cambiarEstado(request, idAdmin, idUsuario, false);
            } else {
                Flash.error(request, "Acción no reconocida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "Ocurrió un error al procesar la solicitud.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/roles");
    }

    /**
     * Regla general de esta pantalla: un administrador no se mete con la cuenta
     * de otro administrador. No es solo el rol Administrador: tampoco puede
     * quitarle el de Cliente ni el de Inmobiliaria, ni desactivarla. Blindar
     * unicamente el rol Administrador dejaba media puerta abierta.
     *
     * La unica excepcion es reactivar, que no le quita nada a nadie: es la
     * salida para una cuenta que ya estaba inactiva cuando recibio el rol, que
     * de otro modo quedaria encerrada fuera del sistema para siempre.
     *
     * Se valida aqui y no solo en la vista: la pantalla puede esconder el boton,
     * pero un POST a mano no.
     */
    private boolean esDeOtroAdministrador(String accion, int idAdmin, int idUsuario) throws SQLException {
        if ("activar".equals(accion) || idUsuario == idAdmin) {
            return false;
        }
        return dao.tieneRolAdministrador(idUsuario);
    }

    private void asignar(HttpServletRequest request, int idAdmin, int idUsuario, String rol)
            throws SQLException {

        dao.asignarRol(idUsuario, rol);
        dao.registrarAuditoria(idAdmin, "cambio_rol",
                "Asignó rol " + rol + " al usuario id " + idUsuario);
        Flash.exito(request, "Rol " + rol + " asignado correctamente.");
    }

    /**
     * Que no se toquen los roles de OTRO administrador ya lo garantiza el filtro
     * de doPost, asi que aqui solo quedan las dos reglas propias de revocar:
     *   - solo puedes renunciar a tu rol Administrador si queda otro;
     *   - nadie puede quedarse sin ningun rol.
     */
    private void revocar(HttpServletRequest request, int idAdmin, int idUsuario, String rol)
            throws SQLException {

        if (ROL_ADMIN.equals(rol) && dao.contarAdministradores() <= 1) {
            Flash.error(request, "No puedes renunciar al rol Administrador: eres el único que queda activo.");
            return;
        }

        if (dao.contarRoles(idUsuario) <= 1) {
            Flash.error(request, "No se puede quitar: el usuario debe conservar al menos un rol.");
            return;
        }

        dao.revocarRol(idUsuario, rol);
        dao.registrarAuditoria(idAdmin, "cambio_rol",
                "Revocó rol " + rol + " al usuario id " + idUsuario);

        if (ROL_ADMIN.equals(rol) && idUsuario == idAdmin) {
            // La sesión conserva la lista de roles del login; hay que refrescarla
            // o el ex-administrador seguiría entrando a /admin/* hasta cerrar sesión.
            refrescarRolesEnSesion(request, idUsuario);
            Flash.exito(request, "Renunciaste al rol Administrador.");
            return;
        }

        Flash.exito(request, "Rol " + rol + " revocado correctamente.");
    }

    /**
     * Activa o desactiva una cuenta. Una cuenta inactiva conserva sus datos y
     * sus roles, pero no puede iniciar sesion (lo comprueba LoginController).
     *
     * Que no se desactive la cuenta de OTRO administrador ya lo garantiza el
     * filtro de doPost; aqui solo queda impedir que alguien se desactive a si
     * mismo y se deje fuera del sistema. Reactivar no tiene candados.
     */
    private void cambiarEstado(HttpServletRequest request, int idAdmin, int idUsuario, boolean activar)
            throws SQLException {

        if (!activar && idUsuario == idAdmin) {
            Flash.error(request, "No puedes desactivar tu propia cuenta: perderías el acceso al sistema.");
            return;
        }

        if (dao.cambiarEstadoActivo(idUsuario, activar) == 0) {
            Flash.error(request, "No se encontró la cuenta que intentas modificar.");
            return;
        }

        dao.registrarAuditoria(idAdmin, activar ? "activar_usuario" : "desactivar_usuario",
                (activar ? "Activó" : "Desactivó") + " la cuenta id " + idUsuario);

        Flash.exito(request, activar
                ? "Cuenta activada: ya puede iniciar sesión."
                : "Cuenta desactivada: no podrá iniciar sesión hasta que la reactives.");
    }

    private void refrescarRolesEnSesion(HttpServletRequest request, int idUsuario) throws SQLException {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return;
        }
        for (Usuario u : dao.listarUsuariosConRoles()) {
            if (u.getIdUsuario() == idUsuario) {
                sesion.setAttribute("roles", u.getRoles());
                return;
            }
        }
    }

    private void cargarYMostrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Usuario> usuarios = dao.listarUsuariosConRoles();
            List<String> todosLosRoles = dao.listarNombresDeRoles();
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("todosLosRoles", todosLosRoles);
            request.setAttribute("totalAdministradores", dao.contarAdministradores());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorRoles", "No se pudo cargar la lista de usuarios.");
        }
        request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);
    }
}
