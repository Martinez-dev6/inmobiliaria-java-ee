package com.inmobiliaria.dao;

import com.inmobiliaria.excepcion.HorarioOcupadoException;
import com.inmobiliaria.modelo.Cita;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    public int crear(Cita cita) throws SQLException, HorarioOcupadoException {

        String sql = "INSERT INTO cita (id_propiedad, id_cliente, fecha_hora) VALUES (?, ?, ?) RETURNING id_cita";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdPropiedad());
            ps.setInt(2, cita.getIdCliente());
            ps.setTimestamp(3, cita.getFechaHora());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("id_cita");
            }

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new HorarioOcupadoException(
                        "Ya existe una cita agendada para esa propiedad en ese horario. Elige otro momento.");
            }
            throw e;
        }
    }

    public List<Cita> listarPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT c.id_cita, c.id_propiedad, c.id_cliente, c.fecha_hora, c.estado, " +
                     "       c.respuesta_agente, c.fecha_respuesta, p.titulo AS titulo_propiedad " +
                     "FROM cita c " +
                     "JOIN propiedad p ON p.id_propiedad = c.id_propiedad " +
                     "WHERE c.id_cliente = ? " +
                     "ORDER BY c.fecha_hora DESC";

        List<Cita> citas = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapearFila(rs, false));
                }
            }
        }

        return citas;
    }

    public List<Cita> listarPorInmobiliaria(int idInmobiliaria) throws SQLException {

        String sql = "SELECT c.id_cita, c.id_propiedad, c.id_cliente, c.fecha_hora, c.estado, " +
                     "       c.respuesta_agente, c.fecha_respuesta, " +
                     "       p.titulo AS titulo_propiedad, u.correo AS correo_cliente, " +
                     "       pf.nombres AS nombres_cliente, pf.apellidos AS apellidos_cliente, " +
                     "       pf.telefono AS telefono_cliente " +
                     "FROM cita c " +
                     "JOIN propiedad p ON p.id_propiedad = c.id_propiedad " +
                     "JOIN usuario u ON u.id_usuario = c.id_cliente " +
                     "LEFT JOIN perfil pf ON pf.id_usuario = c.id_cliente " +
                     "WHERE p.id_inmobiliaria = ? " +
                     "ORDER BY c.fecha_hora ASC";

        List<Cita> citas = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idInmobiliaria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapearFila(rs, true));
                }
            }
        }

        return citas;
    }

    /**
     * Cambia el estado de una cita (y registra el mensaje del agente para el
     * cliente), verificando que la propiedad de esa cita pertenezca a la
     * inmobiliaria que hace la peticion (proteccion IDOR).
     */
    public int cambiarEstado(int idCita, int idInmobiliaria, String nuevoEstado, String respuesta)
            throws SQLException {

        String sql = "UPDATE cita SET estado = ?, respuesta_agente = ?, fecha_respuesta = NOW() " +
                     "WHERE id_cita = ? " +
                     "AND id_propiedad IN (SELECT id_propiedad FROM propiedad WHERE id_inmobiliaria = ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setString(2, respuesta);
            ps.setInt(3, idCita);
            ps.setInt(4, idInmobiliaria);

            return ps.executeUpdate();
        }
    }

    /**
     * Cancela una cita a pedido del propio cliente (no del agente): verifica
     * que la cita sea de ese cliente (proteccion IDOR) y que todavia no haya
     * pasado (no se puede "cancelar" una cita ya realizada o ya cancelada).
     */
    public int cancelarPorCliente(int idCita, int idCliente) throws SQLException {

        String sql = "UPDATE cita SET estado = 'cancelada' WHERE id_cita = ? AND id_cliente = ? " +
                     "AND estado IN ('pendiente', 'confirmada')";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCita);
            ps.setInt(2, idCliente);

            return ps.executeUpdate();
        }
    }

    private Cita mapearFila(ResultSet rs, boolean conCliente) throws SQLException {
        Cita c = new Cita();
        c.setIdCita(rs.getInt("id_cita"));
        c.setIdPropiedad(rs.getInt("id_propiedad"));
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setFechaHora(rs.getTimestamp("fecha_hora"));
        c.setEstado(rs.getString("estado"));
        c.setRespuestaAgente(rs.getString("respuesta_agente"));
        c.setFechaRespuesta(rs.getTimestamp("fecha_respuesta"));
        c.setTituloPropiedad(rs.getString("titulo_propiedad"));
        if (conCliente) {
            c.setCorreoCliente(rs.getString("correo_cliente"));
            c.setNombresCliente(rs.getString("nombres_cliente"));
            c.setApellidosCliente(rs.getString("apellidos_cliente"));
            c.setTelefonoCliente(rs.getString("telefono_cliente"));
        }
        return c;
    }
}
