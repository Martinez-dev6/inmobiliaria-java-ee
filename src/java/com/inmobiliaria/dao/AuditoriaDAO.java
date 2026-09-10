package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Auditoria;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAO {

    /**
     * LEFT JOIN a proposito: un registro de auditoria debe seguir siendo visible
     * aunque el usuario que lo genero ya no exista (FK auditoria.id_usuario es
     * ON DELETE SET NULL). Con un INNER JOIN, esos registros desaparecerian.
     */
    public List<Auditoria> listarRecientes(int limite) throws SQLException {

        String sql = "SELECT a.id_auditoria, a.id_usuario, a.accion, a.descripcion, a.fecha_evento, " +
                     "       u.correo " +
                     "FROM auditoria a " +
                     "LEFT JOIN usuario u ON u.id_usuario = a.id_usuario " +
                     "ORDER BY a.fecha_evento DESC " +
                     "LIMIT ?";

        List<Auditoria> lista = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Auditoria a = new Auditoria();
                    a.setIdAuditoria(rs.getInt("id_auditoria"));
                    a.setIdUsuario((Integer) rs.getObject("id_usuario"));
                    a.setAccion(rs.getString("accion"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setFechaEvento(rs.getTimestamp("fecha_evento"));
                    a.setCorreoUsuario(rs.getString("correo"));
                    lista.add(a);
                }
            }
        }

        return lista;
    }
}
