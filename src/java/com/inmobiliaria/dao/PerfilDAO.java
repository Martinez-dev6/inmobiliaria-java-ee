package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Perfil;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfilDAO {

    public Perfil buscarPorIdUsuario(int idUsuario) throws SQLException {

        String sql = "SELECT id_perfil, id_usuario, nombres, apellidos, documento, telefono, direccion, foto_url " +
                     "FROM perfil WHERE id_usuario = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Perfil perfil = new Perfil();
                perfil.setIdPerfil(rs.getInt("id_perfil"));
                perfil.setIdUsuario(rs.getInt("id_usuario"));
                perfil.setNombres(rs.getString("nombres"));
                perfil.setApellidos(rs.getString("apellidos"));
                perfil.setDocumento(rs.getString("documento"));
                perfil.setTelefono(rs.getString("telefono"));
                perfil.setDireccion(rs.getString("direccion"));
                perfil.setFotoUrl(rs.getString("foto_url"));
                return perfil;
            }
        }
    }

    public void guardar(Perfil perfil) throws SQLException {

        String sql =
            "INSERT INTO perfil (id_usuario, nombres, apellidos, documento, telefono, direccion, foto_url) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
            "ON CONFLICT (id_usuario) DO UPDATE SET " +
            "    nombres = EXCLUDED.nombres, " +
            "    apellidos = EXCLUDED.apellidos, " +
            "    documento = EXCLUDED.documento, " +
            "    telefono = EXCLUDED.telefono, " +
            "    direccion = EXCLUDED.direccion, " +
            "    foto_url = EXCLUDED.foto_url";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, perfil.getIdUsuario());
            ps.setString(2, perfil.getNombres());
            ps.setString(3, perfil.getApellidos());
            ps.setString(4, perfil.getDocumento());
            ps.setString(5, perfil.getTelefono());
            ps.setString(6, perfil.getDireccion());
            ps.setString(7, perfil.getFotoUrl());

            ps.executeUpdate();
        }
    }
}