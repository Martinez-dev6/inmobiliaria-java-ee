package com.inmobiliaria.dao;

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
}