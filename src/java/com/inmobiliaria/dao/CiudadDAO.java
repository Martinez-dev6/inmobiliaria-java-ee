package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Ciudad;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CiudadDAO {

    public List<Ciudad> listarTodas() throws SQLException {

        String sql = "SELECT id_ciudad, nombre_ciudad FROM ciudad ORDER BY nombre_ciudad";

        List<Ciudad> ciudades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ciudades.add(new Ciudad(rs.getInt("id_ciudad"), rs.getString("nombre_ciudad")));
            }
        }

        return ciudades;
    }
}