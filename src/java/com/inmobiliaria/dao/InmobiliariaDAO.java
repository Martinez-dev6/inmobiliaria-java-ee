package com.inmobiliaria.dao;

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
}