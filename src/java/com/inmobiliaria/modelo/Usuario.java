package com.inmobiliaria.modelo;

import java.sql.Timestamp;
import java.util.List;

public class Usuario {

    private int idUsuario;
    private String correo;
    private String contrasenaHash;
    private boolean activo;
    private Timestamp fechaRegistro;

    // Derivado de usuario_rol + rol (no es columna de 'usuario').
    // Se llena en el DAO cuando se necesita saber los roles del usuario.
    private List<String> roles;

    public Usuario() {
    }

    public Usuario(String correo, String contrasenaHash) {
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}