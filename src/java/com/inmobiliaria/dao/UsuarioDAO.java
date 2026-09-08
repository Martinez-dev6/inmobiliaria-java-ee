package com.inmobiliaria.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import com.inmobiliaria.excepcion.CorreoDuplicadoException;
import com.inmobiliaria.modelo.Usuario;
import com.inmobiliaria.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String ROL_POR_DEFECTO = "Cliente";

    public void registrarUsuario(Usuario usuario) throws CorreoDuplicadoException, SQLException {

        String sqlInsertUsuario = "INSERT INTO usuario (correo, contrasena_hash) VALUES (?, ?) RETURNING id_usuario";
        String sqlBuscarRol = "SELECT id_rol FROM rol WHERE nombre_rol = ?";
        String sqlInsertUsuarioRol = "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (?, ?)";

        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            con.setAutoCommit(false);

            int idUsuarioGenerado;
            try (PreparedStatement psUsuario = con.prepareStatement(sqlInsertUsuario)) {
                psUsuario.setString(1, usuario.getCorreo());
                psUsuario.setString(2, usuario.getContrasenaHash());
                try (ResultSet rs = psUsuario.executeQuery()) {
                    rs.next();
                    idUsuarioGenerado = rs.getInt("id_usuario");
                }
            }

            int idRol;
            try (PreparedStatement psRol = con.prepareStatement(sqlBuscarRol)) {
                psRol.setString(1, ROL_POR_DEFECTO);
                try (ResultSet rs = psRol.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("El rol '" + ROL_POR_DEFECTO + "' no existe en la tabla rol.");
                    }
                    idRol = rs.getInt("id_rol");
                }
            }

            try (PreparedStatement psUsuarioRol = con.prepareStatement(sqlInsertUsuarioRol)) {
                psUsuarioRol.setInt(1, idUsuarioGenerado);
                psUsuarioRol.setInt(2, idRol);
                psUsuarioRol.executeUpdate();
            }

            con.commit();
            usuario.setIdUsuario(idUsuarioGenerado);

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            if ("23505".equals(e.getSQLState())) {
                throw new CorreoDuplicadoException("El correo ya se encuentra registrado.");
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public Usuario buscarPorCorreo(String correo) throws SQLException {

        String sql =
            "SELECT u.id_usuario, u.correo, u.contrasena_hash, u.activo, u.fecha_registro, r.nombre_rol " +
            "FROM usuario u " +
            "JOIN usuario_rol ur ON ur.id_usuario = u.id_usuario " +
            "JOIN rol r ON r.id_rol = ur.id_rol " +
            "WHERE u.correo = ?";

        Usuario usuario = null;
        List<String> roles = new ArrayList<>();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (usuario == null) {
                        usuario = new Usuario();
                        usuario.setIdUsuario(rs.getInt("id_usuario"));
                        usuario.setCorreo(rs.getString("correo"));
                        usuario.setContrasenaHash(rs.getString("contrasena_hash"));
                        usuario.setActivo(rs.getBoolean("activo"));
                        usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    }
                    roles.add(rs.getString("nombre_rol"));
                }
            }
        }

        if (usuario != null) {
            usuario.setRoles(roles);
        }
        return usuario;
    }
    
    public List<Usuario> listarUsuariosConRoles() throws SQLException {
    String sql =
        "SELECT u.id_usuario, u.correo, u.activo, r.nombre_rol " +
        "FROM usuario u " +
        "LEFT JOIN usuario_rol ur ON ur.id_usuario = u.id_usuario " +
        "LEFT JOIN rol r ON r.id_rol = ur.id_rol " +
        "ORDER BY u.correo";

    Map<Integer, Usuario> mapa = new LinkedHashMap<>();

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id_usuario");
            Usuario u = mapa.get(id);
            if (u == null) {
                u = new Usuario();
                u.setIdUsuario(id);
                u.setCorreo(rs.getString("correo"));
                u.setActivo(rs.getBoolean("activo"));
                u.setRoles(new ArrayList<>());
                mapa.put(id, u);
            }
            String rol = rs.getString("nombre_rol");
            if (rol != null) {
                u.getRoles().add(rol);
            }
        }
    }
    return new ArrayList<>(mapa.values());
}

public List<String> listarNombresDeRoles() throws SQLException {
    String sql = "SELECT nombre_rol FROM rol ORDER BY nombre_rol";
    List<String> roles = new ArrayList<>();
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            roles.add(rs.getString("nombre_rol"));
        }
    }
    return roles;
}

public void asignarRol(int idUsuario, String nombreRol) throws SQLException {
    String sqlBuscarRol = "SELECT id_rol FROM rol WHERE nombre_rol = ?";
    String sqlInsert = "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (?, ?)";

    try (Connection con = ConexionBD.obtenerConexion()) {
        int idRol;
        try (PreparedStatement ps = con.prepareStatement(sqlBuscarRol)) {
            ps.setString(1, nombreRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("El rol '" + nombreRol + "' no existe.");
                }
                idRol = rs.getInt("id_rol");
            }
        }
        try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idRol);
            ps.executeUpdate();
        } catch (SQLException e) {
            if (!"23505".equals(e.getSQLState())) {
                throw e; // 23505 = ya tenía ese rol; lo ignoramos, no es un error real
            }
        }
    }
}

public int contarRoles(int idUsuario) throws SQLException {
    String sql = "SELECT COUNT(*) FROM usuario_rol WHERE id_usuario = ?";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }
}

public void revocarRol(int idUsuario, String nombreRol) throws SQLException {
    String sql = "DELETE FROM usuario_rol WHERE id_usuario = ? " +
                 "AND id_rol = (SELECT id_rol FROM rol WHERE nombre_rol = ?)";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ps.setString(2, nombreRol);
        ps.executeUpdate();
    }
}

public void registrarAuditoria(int idUsuarioActor, String accion, String descripcion) throws SQLException {
    String sql = "INSERT INTO auditoria (id_usuario, accion, descripcion) VALUES (?, ?, ?)";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idUsuarioActor);
        ps.setString(2, accion);
        ps.setString(3, descripcion);
        ps.executeUpdate();
    }
}
}