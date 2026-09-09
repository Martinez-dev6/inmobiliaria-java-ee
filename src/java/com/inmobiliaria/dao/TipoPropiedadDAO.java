package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.TipoPropiedad;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPropiedadDAO {

    public List<TipoPropiedad> listarTodos() throws SQLException {

        String sql = "SELECT id_tipo_propiedad, nombre_tipo FROM tipo_propiedad ORDER BY nombre_tipo";

        List<TipoPropiedad> tipos = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tipos.add(new TipoPropiedad(rs.getInt("id_tipo_propiedad"), rs.getString("nombre_tipo")));
            }
        }

        return tipos;
    }
}