package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.DocumentoSolicitud;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentoSolicitudDAO {

    public void agregarDocumento(int idSolicitud, String tipoDocumento, String urlArchivo) throws SQLException {

        String sql = "INSERT INTO documento_solicitud (id_solicitud, tipo_documento, url_archivo) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud);
            ps.setString(2, tipoDocumento);
            ps.setString(3, urlArchivo);
            ps.executeUpdate();
        }
    }

    public List<DocumentoSolicitud> listarPorSolicitud(int idSolicitud) throws SQLException {

        String sql = "SELECT id_documento, id_solicitud, tipo_documento, url_archivo, fecha_carga " +
                     "FROM documento_solicitud WHERE id_solicitud = ? ORDER BY fecha_carga";

        List<DocumentoSolicitud> lista = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DocumentoSolicitud d = new DocumentoSolicitud();
                    d.setIdDocumento(rs.getInt("id_documento"));
                    d.setIdSolicitud(rs.getInt("id_solicitud"));
                    d.setTipoDocumento(rs.getString("tipo_documento"));
                    d.setUrlArchivo(rs.getString("url_archivo"));
                    d.setFechaCarga(rs.getTimestamp("fecha_carga"));
                    lista.add(d);
                }
            }
        }

        return lista;
    }
}
