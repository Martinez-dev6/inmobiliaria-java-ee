package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Solicitud;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAO {

    public int crear(Solicitud s) throws SQLException {

        String sql = "INSERT INTO solicitud (id_propiedad, id_cliente, tipo_solicitud, observaciones) " +
                     "VALUES (?, ?, ?, ?) RETURNING id_solicitud";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getIdPropiedad());
            ps.setInt(2, s.getIdCliente());
            ps.setString(3, s.getTipoSolicitud());
            ps.setString(4, s.getObservaciones());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("id_solicitud");
            }
        }
    }

    public List<Solicitud> listarPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT s.id_solicitud, s.id_propiedad, s.id_cliente, s.tipo_solicitud, s.estado, " +
                     "       s.fecha_solicitud, s.observaciones, s.respuesta_agente, s.fecha_respuesta, " +
                     "       p.titulo AS titulo_propiedad " +
                     "FROM solicitud s " +
                     "JOIN propiedad p ON p.id_propiedad = s.id_propiedad " +
                     "WHERE s.id_cliente = ? " +
                     "ORDER BY s.fecha_solicitud DESC";

        List<Solicitud> lista = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearFila(rs, false));
                }
            }
        }

        return lista;
    }

    public List<Solicitud> listarPorInmobiliaria(int idInmobiliaria) throws SQLException {

        String sql = "SELECT s.id_solicitud, s.id_propiedad, s.id_cliente, s.tipo_solicitud, s.estado, " +
                     "       s.fecha_solicitud, s.observaciones, s.respuesta_agente, s.fecha_respuesta, " +
                     "       p.titulo AS titulo_propiedad, u.correo AS correo_cliente, " +
                     "       pf.nombres AS nombres_cliente, pf.apellidos AS apellidos_cliente, " +
                     "       pf.telefono AS telefono_cliente " +
                     "FROM solicitud s " +
                     "JOIN propiedad p ON p.id_propiedad = s.id_propiedad " +
                     "JOIN usuario u ON u.id_usuario = s.id_cliente " +
                     "LEFT JOIN perfil pf ON pf.id_usuario = s.id_cliente " +
                     "WHERE p.id_inmobiliaria = ? " +
                     "ORDER BY s.fecha_solicitud DESC";

        List<Solicitud> lista = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idInmobiliaria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearFila(rs, true));
                }
            }
        }

        return lista;
    }

    /**
     * Cambia el estado de una solicitud (y registra el mensaje del agente para
     * el cliente), verificando que la propiedad relacionada pertenezca a la
     * inmobiliaria que hace la peticion (proteccion IDOR).
     */
    public int cambiarEstado(int idSolicitud, int idInmobiliaria, String nuevoEstado, String respuesta)
            throws SQLException {

        String sql = "UPDATE solicitud SET estado = ?, respuesta_agente = ?, fecha_respuesta = NOW() " +
                     "WHERE id_solicitud = ? " +
                     "AND id_propiedad IN (SELECT id_propiedad FROM propiedad WHERE id_inmobiliaria = ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setString(2, respuesta);
            ps.setInt(3, idSolicitud);
            ps.setInt(4, idInmobiliaria);

            return ps.executeUpdate();
        }
    }

    private Solicitud mapearFila(ResultSet rs, boolean conCliente) throws SQLException {
        Solicitud s = new Solicitud();
        s.setIdSolicitud(rs.getInt("id_solicitud"));
        s.setIdPropiedad(rs.getInt("id_propiedad"));
        s.setIdCliente(rs.getInt("id_cliente"));
        s.setTipoSolicitud(rs.getString("tipo_solicitud"));
        s.setEstado(rs.getString("estado"));
        s.setFechaSolicitud(rs.getTimestamp("fecha_solicitud"));
        s.setObservaciones(rs.getString("observaciones"));
        s.setRespuestaAgente(rs.getString("respuesta_agente"));
        s.setFechaRespuesta(rs.getTimestamp("fecha_respuesta"));
        s.setTituloPropiedad(rs.getString("titulo_propiedad"));
        if (conCliente) {
            s.setCorreoCliente(rs.getString("correo_cliente"));
            s.setNombresCliente(rs.getString("nombres_cliente"));
            s.setApellidosCliente(rs.getString("apellidos_cliente"));
            s.setTelefonoCliente(rs.getString("telefono_cliente"));
        }
        return s;
    }
}
