package com.inmobiliaria.dao;

import com.inmobiliaria.excepcion.MatriculaDuplicadaException;
import com.inmobiliaria.modelo.Propiedad;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PropiedadDAO {

    private static final String SELECT_BASE =
            "SELECT p.id_propiedad, p.id_inmobiliaria, p.id_ciudad, p.id_tipo_propiedad, " +
            "       p.matricula_inmobiliaria, p.titulo, p.descripcion, p.direccion, " +
            "       p.precio, p.area_m2, p.estado, p.destacada, p.fecha_publicacion, " +
            "       c.nombre_ciudad, t.nombre_tipo, i.nombre_comercial, i.telefono_contacto, " +
            "       (SELECT ip.url_imagen FROM imagen_propiedad ip " +
            "        WHERE ip.id_propiedad = p.id_propiedad ORDER BY ip.orden LIMIT 1) AS url_miniatura " +
            "FROM propiedad p " +
            "JOIN ciudad c ON c.id_ciudad = p.id_ciudad " +
            "JOIN tipo_propiedad t ON t.id_tipo_propiedad = p.id_tipo_propiedad " +
            "JOIN inmobiliaria i ON i.id_inmobiliaria = p.id_inmobiliaria ";

    public List<Propiedad> listarPorInmobiliaria(int idInmobiliaria) throws SQLException {

        String sql = SELECT_BASE + "WHERE p.id_inmobiliaria = ? ORDER BY p.fecha_publicacion DESC";

        List<Propiedad> propiedades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idInmobiliaria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propiedades.add(mapearFila(rs));
                }
            }
        }

        return propiedades;
    }

    public Propiedad buscarPorId(int idPropiedad) throws SQLException {

        String sql = SELECT_BASE + "WHERE p.id_propiedad = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropiedad);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearFila(rs) : null;
            }
        }
    }

    public int crear(Propiedad p) throws SQLException, MatriculaDuplicadaException {

        String sql = "INSERT INTO propiedad " +
                     "(id_inmobiliaria, id_ciudad, id_tipo_propiedad, matricula_inmobiliaria, " +
                     " titulo, descripcion, direccion, precio, area_m2, destacada) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id_propiedad";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdInmobiliaria());
            ps.setInt(2, p.getIdCiudad());
            ps.setInt(3, p.getIdTipoPropiedad());
            ps.setString(4, p.getMatriculaInmobiliaria());
            ps.setString(5, p.getTitulo());
            ps.setString(6, p.getDescripcion());
            ps.setString(7, p.getDireccion());
            ps.setBigDecimal(8, p.getPrecio());
            ps.setBigDecimal(9, p.getAreaM2());
            ps.setBoolean(10, p.isDestacada());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("id_propiedad");
            }

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new MatriculaDuplicadaException(
                        "Ya existe una propiedad registrada con la matrícula " + p.getMatriculaInmobiliaria());
            }
            throw e;
        }
    }

    public int actualizar(Propiedad p) throws SQLException {

        String sql = "UPDATE propiedad SET " +
                     "id_ciudad = ?, id_tipo_propiedad = ?, titulo = ?, descripcion = ?, " +
                     "direccion = ?, precio = ?, area_m2 = ?, destacada = ? " +
                     "WHERE id_propiedad = ? AND id_inmobiliaria = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdCiudad());
            ps.setInt(2, p.getIdTipoPropiedad());
            ps.setString(3, p.getTitulo());
            ps.setString(4, p.getDescripcion());
            ps.setString(5, p.getDireccion());
            ps.setBigDecimal(6, p.getPrecio());
            ps.setBigDecimal(7, p.getAreaM2());
            ps.setBoolean(8, p.isDestacada());
            ps.setInt(9, p.getIdPropiedad());
            ps.setInt(10, p.getIdInmobiliaria());

            return ps.executeUpdate();
        }
    }

    public int cambiarEstado(int idPropiedad, int idInmobiliaria, String nuevoEstado) throws SQLException {

        String sql = "UPDATE propiedad SET estado = ? WHERE id_propiedad = ? AND id_inmobiliaria = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPropiedad);
            ps.setInt(3, idInmobiliaria);

            return ps.executeUpdate();
        }
    }

    public int contarTodas() throws SQLException {

        String sql = "SELECT COUNT(*) FROM propiedad";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    public List<Propiedad> buscarConFiltros(Integer idCiudad, Integer idTipoPropiedad,
                                             BigDecimal precioMin, BigDecimal precioMax) throws SQLException {

        StringBuilder sql = new StringBuilder(SELECT_BASE);
        sql.append("WHERE p.estado = 'disponible' ");

        List<Object> parametros = new ArrayList<>();

        if (idCiudad != null) {
            sql.append("AND p.id_ciudad = ? ");
            parametros.add(idCiudad);
        }
        if (idTipoPropiedad != null) {
            sql.append("AND p.id_tipo_propiedad = ? ");
            parametros.add(idTipoPropiedad);
        }
        if (precioMin != null) {
            sql.append("AND p.precio >= ? ");
            parametros.add(precioMin);
        }
        if (precioMax != null) {
            sql.append("AND p.precio <= ? ");
            parametros.add(precioMax);
        }

        sql.append("ORDER BY p.fecha_publicacion DESC");

        List<Propiedad> propiedades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propiedades.add(mapearFila(rs));
                }
            }
        }

        return propiedades;
    }

    public List<Propiedad> listarDestacadas(int limite) throws SQLException {

        String sql = SELECT_BASE + "WHERE p.estado = 'disponible' AND p.destacada = TRUE " +
                     "ORDER BY p.fecha_publicacion DESC LIMIT ?";

        List<Propiedad> propiedades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propiedades.add(mapearFila(rs));
                }
            }
        }

        return propiedades;
    }

    public List<Propiedad> listarFavoritasDeUsuario(int idUsuario) throws SQLException {

        String sql = SELECT_BASE + "JOIN favorito f ON f.id_propiedad = p.id_propiedad " +
                     "WHERE f.id_usuario = ? ORDER BY f.fecha_marcado DESC";

        List<Propiedad> propiedades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propiedades.add(mapearFila(rs));
                }
            }
        }

        return propiedades;
    }

    /**
     * Historia/consulta obligatoria del PDF: LEFT JOIN — propiedades disponibles
     * de una inmobiliaria que todavia NO tienen ninguna cita agendada.
     * El LEFT JOIN es necesario (no un INNER JOIN): sin el, una propiedad sin
     * ninguna fila en 'cita' desaparecería del resultado en vez de aparecer con
     * id_cita = NULL, que es justo la condicion que se filtra despues en el WHERE.
     */
    public List<Propiedad> listarSinCitas(int idInmobiliaria) throws SQLException {

        String sql = "SELECT p.id_propiedad, p.id_inmobiliaria, p.id_ciudad, p.id_tipo_propiedad, " +
                     "       p.matricula_inmobiliaria, p.titulo, p.descripcion, p.direccion, " +
                     "       p.precio, p.area_m2, p.estado, p.destacada, p.fecha_publicacion, " +
                     "       c.nombre_ciudad, t.nombre_tipo, i.nombre_comercial, i.telefono_contacto, " +
                     "       NULL AS url_miniatura " +
                     "FROM propiedad p " +
                     "JOIN ciudad c ON c.id_ciudad = p.id_ciudad " +
                     "JOIN tipo_propiedad t ON t.id_tipo_propiedad = p.id_tipo_propiedad " +
                     "JOIN inmobiliaria i ON i.id_inmobiliaria = p.id_inmobiliaria " +
                     "LEFT JOIN cita ci ON ci.id_propiedad = p.id_propiedad " +
                     "WHERE p.id_inmobiliaria = ? AND ci.id_cita IS NULL " +
                     "ORDER BY p.fecha_publicacion DESC";

        List<Propiedad> propiedades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idInmobiliaria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propiedades.add(mapearFila(rs));
                }
            }
        }

        return propiedades;
    }

    private Propiedad mapearFila(ResultSet rs) throws SQLException {
        Propiedad p = new Propiedad();
        p.setIdPropiedad(rs.getInt("id_propiedad"));
        p.setIdInmobiliaria(rs.getInt("id_inmobiliaria"));
        p.setIdCiudad(rs.getInt("id_ciudad"));
        p.setIdTipoPropiedad(rs.getInt("id_tipo_propiedad"));
        p.setMatriculaInmobiliaria(rs.getString("matricula_inmobiliaria"));
        p.setTitulo(rs.getString("titulo"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setDireccion(rs.getString("direccion"));
        p.setPrecio(rs.getBigDecimal("precio"));
        p.setAreaM2(rs.getBigDecimal("area_m2"));
        p.setEstado(rs.getString("estado"));
        p.setDestacada(rs.getBoolean("destacada"));
        p.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
        p.setNombreCiudad(rs.getString("nombre_ciudad"));
        p.setNombreTipo(rs.getString("nombre_tipo"));
        p.setNombreInmobiliaria(rs.getString("nombre_comercial"));
        p.setTelefonoInmobiliaria(rs.getString("telefono_contacto"));
        p.setUrlMiniatura(rs.getString("url_miniatura"));
        return p;
    }
}
