package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.ImagenPropiedad;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImagenPropiedadDAO {

    public List<ImagenPropiedad> listarPorPropiedad(int idPropiedad) throws SQLException {

        String sql = "SELECT id_imagen, id_propiedad, url_imagen, orden " +
                     "FROM imagen_propiedad WHERE id_propiedad = ? ORDER BY orden";

        List<ImagenPropiedad> imagenes = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropiedad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImagenPropiedad img = new ImagenPropiedad();
                    img.setIdImagen(rs.getInt("id_imagen"));
                    img.setIdPropiedad(rs.getInt("id_propiedad"));
                    img.setUrlImagen(rs.getString("url_imagen"));
                    img.setOrden(rs.getInt("orden"));
                    imagenes.add(img);
                }
            }
        }

        return imagenes;
    }

    public void agregarImagen(int idPropiedad, String urlImagen) throws SQLException {

        String sql = "INSERT INTO imagen_propiedad (id_propiedad, url_imagen, orden) " +
                     "VALUES (?, ?, (SELECT COALESCE(MAX(orden), -1) + 1 " +
                     "               FROM imagen_propiedad WHERE id_propiedad = ?))";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropiedad);
            ps.setString(2, urlImagen);
            ps.setInt(3, idPropiedad);
            ps.executeUpdate();
        }
    }

    public String eliminarImagen(int idImagen, int idPropiedad) throws SQLException {

        String sql = "DELETE FROM imagen_propiedad WHERE id_imagen = ? AND id_propiedad = ? RETURNING url_imagen";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idImagen);
            ps.setInt(2, idPropiedad);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("url_imagen") : null;
            }
        }
    }
}