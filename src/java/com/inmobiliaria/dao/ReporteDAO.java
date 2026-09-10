package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.ReportePropiedadesPorCiudad;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    /**
     * Historia 12: consulta de agregacion obligatoria del PDF (GROUP BY + HAVING).
     * HAVING COUNT(*) > 0 es redundante en la practica (un GROUP BY nunca produce
     * grupos vacios), pero se deja explicito porque el PDF pide demostrar el uso
     * de HAVING, no solo de GROUP BY.
     */
    public List<ReportePropiedadesPorCiudad> propiedadesPorCiudadYEstado() throws SQLException {

        String sql = "SELECT c.nombre_ciudad, p.estado, COUNT(*) AS total " +
                     "FROM propiedad p " +
                     "JOIN ciudad c ON c.id_ciudad = p.id_ciudad " +
                     "GROUP BY c.nombre_ciudad, p.estado " +
                     "HAVING COUNT(*) > 0 " +
                     "ORDER BY c.nombre_ciudad, p.estado";

        List<ReportePropiedadesPorCiudad> lista = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReportePropiedadesPorCiudad r = new ReportePropiedadesPorCiudad();
                r.setNombreCiudad(rs.getString("nombre_ciudad"));
                r.setEstado(rs.getString("estado"));
                r.setTotal(rs.getInt("total"));
                lista.add(r);
            }
        }

        return lista;
    }
}
