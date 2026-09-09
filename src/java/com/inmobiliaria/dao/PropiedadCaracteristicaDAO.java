package com.inmobiliaria.dao;

import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropiedadCaracteristicaDAO {

        public List<Integer> listarIdsPorPropiedad(int idPropiedad) throws SQLException {

        String sql = "SELECT id_caracteristica FROM propiedad_caracteristica WHERE id_propiedad = ?";

        List<Integer> ids = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropiedad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id_caracteristica"));
                }
            }
        }

        return ids;
    }

    public List<String> listarNombresPorPropiedad(int idPropiedad) throws SQLException {

        String sql = "SELECT c.nombre_caracteristica " +
                     "FROM propiedad_caracteristica pc " +
                     "JOIN caracteristica c ON c.id_caracteristica = pc.id_caracteristica " +
                     "WHERE pc.id_propiedad = ? " +
                     "ORDER BY c.nombre_caracteristica";

        List<String> nombres = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropiedad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nombres.add(rs.getString("nombre_caracteristica"));
                }
            }
        }

        return nombres;
    }

    public void asignarCaracteristicas(int idPropiedad, List<Integer> idsCaracteristicas) throws SQLException {

        String sqlBorrar = "DELETE FROM propiedad_caracteristica WHERE id_propiedad = ?";
        String sqlInsertar = "INSERT INTO propiedad_caracteristica (id_propiedad, id_caracteristica) VALUES (?, ?)";

        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            con.setAutoCommit(false);

            try (PreparedStatement psBorrar = con.prepareStatement(sqlBorrar)) {
                psBorrar.setInt(1, idPropiedad);
                psBorrar.executeUpdate();
            }

            try (PreparedStatement psInsertar = con.prepareStatement(sqlInsertar)) {
                for (int idCaracteristica : idsCaracteristicas) {
                    psInsertar.setInt(1, idPropiedad);
                    psInsertar.setInt(2, idCaracteristica);
                    psInsertar.addBatch();
                }
                if (!idsCaracteristicas.isEmpty()) {
                    psInsertar.executeBatch();
                }
            }

            con.commit();

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
}