package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.dao.UsuarioDAO;
import com.inmobiliaria.excepcion.HorarioOcupadoException;
import com.inmobiliaria.modelo.Cita;
import com.inmobiliaria.util.Flash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet(name = "CitaController", urlPatterns = {"/cliente/citas"})
public class CitaController extends HttpServlet {

    private final CitaDAO citaDAO = new CitaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrar(request, response);
    }

    /**
     * POST-Redirect-GET: agendar o cancelar deja un mensaje flash y redirige al
     * listado, de modo que recargar la página no vuelva a enviar el formulario.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("cancelar".equals(request.getParameter("accion"))) {
            procesarCancelar(request);
        } else {
            procesarAgendar(request);
        }

        response.sendRedirect(request.getContextPath() + "/cliente/citas");
    }

    private void procesarAgendar(HttpServletRequest request) {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");

        int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
        String fechaTexto = request.getParameter("fechaHora"); // formato de <input type="datetime-local">: 2026-09-20T15:30

        Timestamp fechaHora = parsearFechaHora(fechaTexto);
        if (fechaHora == null) {
            Flash.error(request, "La fecha y hora no son válidas.");
            return;
        }
        // El navegador acepta fechas pasadas en datetime-local, así que la regla
        // se valida también aquí, que es donde de verdad cuenta.
        if (fechaHora.before(new Timestamp(System.currentTimeMillis()))) {
            Flash.error(request, "No puedes agendar una visita en una fecha que ya pasó.");
            return;
        }

        Cita cita = new Cita(idPropiedad, idCliente, fechaHora);

        try {
            int idGenerado = citaDAO.crear(cita);
            usuarioDAO.registrarAuditoria(idCliente, "agendar_cita",
                    "Agendó la cita id " + idGenerado + " para la propiedad id " + idPropiedad);
            Flash.exito(request, "Cita agendada correctamente. La inmobiliaria debe confirmarla; "
                    + "abajo tienes sus datos de contacto.");
        } catch (HorarioOcupadoException e) {
            Flash.error(request, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "No se pudo agendar la cita. Intenta más tarde.");
        }
    }

    private void procesarCancelar(HttpServletRequest request) {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");
        int idCita = Integer.parseInt(request.getParameter("idCita"));

        try {
            int filas = citaDAO.cancelarPorCliente(idCita, idCliente);
            if (filas == 0) {
                Flash.error(request, "Esa cita no se puede cancelar (ya pasó o no es tuya).");
                return;
            }
            usuarioDAO.registrarAuditoria(idCliente, "cancelar_cita_cliente", "Canceló su cita id " + idCita);
            Flash.exito(request, "Cita cancelada.");
        } catch (SQLException e) {
            e.printStackTrace();
            Flash.error(request, "No se pudo cancelar la cita. Intenta más tarde.");
        }
    }

    private Timestamp parsearFechaHora(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            // "2026-09-20T15:30" -> "2026-09-20 15:30:00"
            String normalizado = texto.replace("T", " ") + ":00";
            return Timestamp.valueOf(normalizado);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idCliente = (int) sesion.getAttribute("idUsuario");

        try {
            request.setAttribute("citas", citaDAO.listarPorCliente(idCliente));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorCitas", "No se pudieron cargar tus citas.");
        }

        request.getRequestDispatcher("/cliente/citas.jsp").forward(request, response);
    }
}
