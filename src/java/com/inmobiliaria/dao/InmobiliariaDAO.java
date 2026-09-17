package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Inmobiliaria;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InmobiliariaDAO {

    public Integer buscarIdInmobiliariaPorUsuario(int idUsuario) throws SQLException {

        String sql = "SELECT id_inmobiliaria FROM inmobiliaria WHERE id_usuario = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id_inmobiliaria") : null;
            }
        }
    }

    /** Ficha completa de la agencia de ese usuario, o null si todavia no la creo. */
    public Inmobiliaria buscarPorUsuario(int idUsuario) throws SQLException {

        String sql = "SELECT id_inmobiliaria, id_usuario, nombre_comercial, nit, telefono_contacto " +
                     "FROM inmobiliaria WHERE id_usuario = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Inmobiliaria i = new Inmobiliaria();
                i.setIdInmobiliaria(rs.getInt("id_inmobiliaria"));
                i.setIdUsuario(rs.getInt("id_usuario"));
                i.setNombreComercial(rs.getString("nombre_comercial"));
                i.setNit(rs.getString("nit"));
                i.setTelefonoContacto(rs.getString("telefono_contacto"));
                return i;
            }
        }
    }

    /**
     * Crea la ficha si el usuario todavia no tiene, o actualiza la que ya tiene.
     *
     * Se resuelve con un UPSERT sobre la restriccion UNIQUE de id_usuario en vez
     * de "consultar y luego decidir": asi dos peticiones simultaneas no pueden
     * crear dos fichas para la misma cuenta.
     */
    public int guardar(Inmobiliaria inmobiliaria) throws SQLException {

        String sql = "INSERT INTO inmobiliaria (id_usuario, nombre_comercial, nit, telefono_contacto) " +
                     "VALUES (?, ?, ?, ?) " +
                     "ON CONFLICT (id_usuario) DO UPDATE SET " +
                     "    nombre_comercial = EXCLUDED.nombre_comercial, " +
                     "    nit = EXCLUDED.nit, " +
                     "    telefono_contacto = EXCLUDED.telefono_contacto " +
                     "RETURNING id_inmobiliaria";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, inmobiliaria.getIdUsuario());
            ps.setString(2, inmobiliaria.getNombreComercial());
            ps.setString(3, inmobiliaria.getNit());
            ps.setString(4, inmobiliaria.getTelefonoContacto());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("id_inmobiliaria");
            }
        }
    }
}
