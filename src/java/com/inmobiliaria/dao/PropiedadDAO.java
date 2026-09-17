package com.inmobiliaria.dao;

import com.inmobiliaria.excepcion.MatriculaDuplicadaException;
import com.inmobiliaria.modelo.FiltroCatalogo;
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

    /**
     * Lista TODAS las propiedades de TODAS las inmobiliarias, con el correo del
     * agente dueño de cada una (para el panel de moderación del administrador).
     */
    public List<Propiedad> listarTodas() throws SQLException {
        return listarTodas(null, null);
    }

    /**
     * Misma consulta, pero pudiendo acotar por ciudad y/o estado. La usa el
     * reporte de administración para "bajar" desde una fila agregada
     * (ciudad + estado) hasta las propiedades concretas que la componen.
     * Con los dos parámetros en null devuelve el listado completo.
     */
    public List<Propiedad> listarTodas(Integer idCiudad, String estado) throws SQLException {
        return listarTodas(idCiudad, estado, Integer.MAX_VALUE, 0);
    }

    /**
     * Version paginada: devuelve como maximo {@code limite} filas saltando las
     * primeras {@code desplazamiento}. Se combina con
     * {@link #contarTodas(Integer, String)} para calcular el numero de paginas.
     */
    public List<Propiedad> listarTodas(Integer idCiudad, String estado, int limite, int desplazamiento)
            throws SQLException {

        List<Object> parametros = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT p.id_propiedad, p.id_inmobiliaria, p.id_ciudad, p.id_tipo_propiedad, " +
                "       p.matricula_inmobiliaria, p.titulo, p.descripcion, p.direccion, " +
                "       p.precio, p.area_m2, p.estado, p.destacada, p.fecha_publicacion, " +
                "       c.nombre_ciudad, t.nombre_tipo, i.nombre_comercial, i.telefono_contacto, " +
                "       u.correo AS correo_inmobiliaria, " +
                "       (SELECT ip.url_imagen FROM imagen_propiedad ip " +
                "        WHERE ip.id_propiedad = p.id_propiedad ORDER BY ip.orden LIMIT 1) AS url_miniatura " +
                "FROM propiedad p " +
                "JOIN ciudad c ON c.id_ciudad = p.id_ciudad " +
                "JOIN tipo_propiedad t ON t.id_tipo_propiedad = p.id_tipo_propiedad " +
                "JOIN inmobiliaria i ON i.id_inmobiliaria = p.id_inmobiliaria " +
                "JOIN usuario u ON u.id_usuario = i.id_usuario ");

        sql.append(condicionesAdmin(idCiudad, estado, parametros));
        sql.append("ORDER BY p.fecha_publicacion DESC LIMIT ? OFFSET ?");
        parametros.add(limite);
        parametros.add(desplazamiento);

        List<Propiedad> propiedades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            aplicarParametros(ps, parametros);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Propiedad p = mapearFila(rs);
                    p.setCorreoInmobiliaria(rs.getString("correo_inmobiliaria"));
                    propiedades.add(p);
                }
            }
        }

        return propiedades;
    }

    /** Cuenta las propiedades que cumplen los mismos filtros del listado del administrador. */
    public int contarTodas(Integer idCiudad, String estado) throws SQLException {

        List<Object> parametros = new ArrayList<>();
        String sql = "SELECT COUNT(*) FROM propiedad p " + condicionesAdmin(idCiudad, estado, parametros);

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            aplicarParametros(ps, parametros);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    /**
     * Arma el WHERE del listado del administrador y va acumulando los valores
     * en {@code parametros}, para que el listado y el conteo no se separen.
     */
    private String condicionesAdmin(Integer idCiudad, String estado, List<Object> parametros) {

        StringBuilder condiciones = new StringBuilder("WHERE 1 = 1 ");

        if (idCiudad != null) {
            condiciones.append("AND p.id_ciudad = ? ");
            parametros.add(idCiudad);
        }
        if (estado != null && !estado.trim().isEmpty()) {
            condiciones.append("AND p.estado = ? ");
            parametros.add(estado.trim());
        }

        return condiciones.toString();
    }

    private void aplicarParametros(PreparedStatement ps, List<Object> parametros) throws SQLException {
        for (int i = 0; i < parametros.size(); i++) {
            ps.setObject(i + 1, parametros.get(i));
        }
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

    /**
     * Formato unico de matricula del sistema: HG-<anio>-<consecutivo de 4 digitos>.
     * El agente ya no la escribe a mano (antes podia teclear cualquier cosa y el
     * error solo aparecia al chocar con el UNIQUE de la tabla).
     */
    public static final String PREFIJO_MATRICULA = "HG";

    /**
     * Devuelve la siguiente matricula libre del anio en curso. Puede haber una
     * carrera si dos agentes publican en el mismo instante; en ese caso el
     * UNIQUE de la tabla lo impide y quien llama reintenta.
     */
    public String generarMatricula() throws SQLException {

        String prefijo = PREFIJO_MATRICULA + "-" + java.time.Year.now().getValue() + "-";

        String sql = "SELECT matricula_inmobiliaria FROM propiedad " +
                     "WHERE matricula_inmobiliaria LIKE ? " +
                     "ORDER BY matricula_inmobiliaria DESC LIMIT 1";

        int siguiente = 1;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, prefijo + "%");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ultima = rs.getString(1);
                    try {
                        siguiente = Integer.parseInt(ultima.substring(prefijo.length())) + 1;
                    } catch (NumberFormatException | StringIndexOutOfBoundsException ignorado) {
                        // Matricula heredada que no sigue el formato: se empieza de nuevo.
                        siguiente = 1;
                    }
                }
            }
        }

        return prefijo + String.format("%04d", siguiente);
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

    /**
     * Cambia el estado de una propiedad sin restringir por inmobiliaria — solo
     * para uso del administrador (moderación), a diferencia de cambiarEstado()
     * que un agente usa sobre sus propias propiedades.
     */
    public int cambiarEstadoAdmin(int idPropiedad, String nuevoEstado) throws SQLException {

        String sql = "UPDATE propiedad SET estado = ? WHERE id_propiedad = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPropiedad);

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

    /**
     * Catalogo publico paginado. Se combina con {@link #contarConFiltros} para
     * saber cuantas paginas hay; ambos comparten el mismo WHERE.
     */
    public List<Propiedad> buscarConFiltros(FiltroCatalogo filtro, int limite, int desplazamiento)
            throws SQLException {

        List<Object> parametros = new ArrayList<>();

        StringBuilder sql = new StringBuilder(SELECT_BASE);
        sql.append(condicionesCatalogo(filtro, parametros));
        // El ORDER BY sale de un enum, nunca de texto escrito por el usuario.
        sql.append("ORDER BY ").append(filtro.getOrden().getSql()).append(" LIMIT ? OFFSET ?");
        parametros.add(limite);
        parametros.add(desplazamiento);

        List<Propiedad> propiedades = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            aplicarParametros(ps, parametros);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propiedades.add(mapearFila(rs));
                }
            }
        }

        return propiedades;
    }

    /** Cuenta los resultados del catalogo con esos mismos filtros. */
    public int contarConFiltros(FiltroCatalogo filtro) throws SQLException {

        List<Object> parametros = new ArrayList<>();
        String sql = "SELECT COUNT(*) FROM propiedad p " + condicionesCatalogo(filtro, parametros);

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            aplicarParametros(ps, parametros);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private String condicionesCatalogo(FiltroCatalogo filtro, List<Object> parametros) {

        StringBuilder condiciones = new StringBuilder("WHERE p.estado = 'disponible' ");

        if (filtro.getIdCiudad() != null) {
            condiciones.append("AND p.id_ciudad = ? ");
            parametros.add(filtro.getIdCiudad());
        }
        if (filtro.getIdTipoPropiedad() != null) {
            condiciones.append("AND p.id_tipo_propiedad = ? ");
            parametros.add(filtro.getIdTipoPropiedad());
        }
        if (filtro.getPrecioMin() != null) {
            condiciones.append("AND p.precio >= ? ");
            parametros.add(filtro.getPrecioMin());
        }
        if (filtro.getPrecioMax() != null) {
            condiciones.append("AND p.precio <= ? ");
            parametros.add(filtro.getPrecioMax());
        }
        if (filtro.getAreaMin() != null) {
            condiciones.append("AND p.area_m2 >= ? ");
            parametros.add(filtro.getAreaMin());
        }

        // Caracteristicas: se piden TODAS las marcadas, no cualquiera. El
        // HAVING COUNT(DISTINCT ...) = n es lo que convierte el OR del IN en AND.
        List<Integer> ids = filtro.getIdsCaracteristicas();
        if (!ids.isEmpty()) {
            condiciones.append("AND p.id_propiedad IN (")
                       .append("SELECT pc.id_propiedad FROM propiedad_caracteristica pc ")
                       .append("WHERE pc.id_caracteristica IN (");
            for (int i = 0; i < ids.size(); i++) {
                condiciones.append(i == 0 ? "?" : ", ?");
                parametros.add(ids.get(i));
            }
            condiciones.append(") GROUP BY pc.id_propiedad ")
                       .append("HAVING COUNT(DISTINCT pc.id_caracteristica) = ?) ");
            parametros.add(ids.size());
        }

        return condiciones.toString();
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
