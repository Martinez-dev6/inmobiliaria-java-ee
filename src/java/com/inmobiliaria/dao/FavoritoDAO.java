package com.inmobiliaria.dao;

import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoritoDAO {

    public boolean existe(int idUsuario, int idPropiedad) throws SQLException {

        String sql = "SELECT 1 FROM favorito WHERE id_usuario = ? AND id_propiedad = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPropiedad);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void marcar(int idUsuario, int idPropiedad) throws SQLException {

        String sql = "INSERT INTO favorito (id_usuario, id_propiedad) VALUES (?, ?) " +
                     "ON CONFLICT (id_usuario, id_propiedad) DO NOTHING";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPropiedad);
            ps.executeUpdate();
        }
    }

    public void desmarcar(int idUsuario, int idPropiedad) throws SQLException {

        String sql = "DELETE FROM favorito WHERE id_usuario = ? AND id_propiedad = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPropiedad);
            ps.executeUpdate();
        }
    }
}