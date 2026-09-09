package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Caracteristica;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaracteristicaDAO {

    public List<Caracteristica> listarTodas() throws SQLException {

        String sql = "SELECT id_caracteristica, nombre_caracteristica FROM caracteristica ORDER BY nombre_caracteristica";

        List<Caracteristica> caracteristicas = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                caracteristicas.add(new Caracteristica(rs.getInt("id_caracteristica"), rs.getString("nombre_caracteristica")));
            }
        }

        return caracteristicas;
    }
}