package com.inmobiliaria.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Propiedad {

    private int idPropiedad;
    private int idInmobiliaria;
    private int idCiudad;
    private int idTipoPropiedad;
    private String matriculaInmobiliaria;
    private String titulo;
    private String descripcion;
    private String direccion;
    private BigDecimal precio;
    private BigDecimal areaM2;
    private String estado;
    private boolean destacada;
    private Timestamp fechaPublicacion;

    // Derivados de un JOIN con ciudad/tipo_propiedad (no son columnas de 'propiedad').
    // El DAO los llena solo en las consultas de listado, para no repetir el JOIN en la vista.
    private String nombreCiudad;
    private String nombreTipo;
    private String nombreInmobiliaria; // nombre comercial de la agencia (JOIN con inmobiliaria); no es columna de 'propiedad'
    private String telefonoInmobiliaria; // teléfono de contacto de la agencia; no es columna de 'propiedad'
    private String urlMiniatura; // primera foto de la galería (para el listado); no es columna de 'propiedad'

    public Propiedad() {
    }

    public Propiedad(int idInmobiliaria, int idCiudad, int idTipoPropiedad,
                      String matriculaInmobiliaria, String titulo, String direccion, BigDecimal precio) {
        this.idInmobiliaria = idInmobiliaria;
        this.idCiudad = idCiudad;
        this.idTipoPropiedad = idTipoPropiedad;
        this.matriculaInmobiliaria = matriculaInmobiliaria;
        this.titulo = titulo;
        this.direccion = direccion;
        this.precio = precio;
    }

    public int getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public int getIdInmobiliaria() {
        return idInmobiliaria;
    }

    public void setIdInmobiliaria(int idInmobiliaria) {
        this.idInmobiliaria = idInmobiliaria;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdTipoPropiedad() {
        return idTipoPropiedad;
    }

    public void setIdTipoPropiedad(int idTipoPropiedad) {
        this.idTipoPropiedad = idTipoPropiedad;
    }

    public String getMatriculaInmobiliaria() {
        return matriculaInmobiliaria;
    }

    public void setMatriculaInmobiliaria(String matriculaInmobiliaria) {
        this.matriculaInmobiliaria = matriculaInmobiliaria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(BigDecimal areaM2) {
        this.areaM2 = areaM2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isDestacada() {
        return destacada;
    }

    public void setDestacada(boolean destacada) {
        this.destacada = destacada;
    }

    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

        public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getNombreInmobiliaria() {
        return nombreInmobiliaria;
    }

    public void setNombreInmobiliaria(String nombreInmobiliaria) {
        this.nombreInmobiliaria = nombreInmobiliaria;
    }

    public String getTelefonoInmobiliaria() {
        return telefonoInmobiliaria;
    }

    public void setTelefonoInmobiliaria(String telefonoInmobiliaria) {
        this.telefonoInmobiliaria = telefonoInmobiliaria;
    }

    public String getUrlMiniatura() {
        return urlMiniatura;
    }

    public void setUrlMiniatura(String urlMiniatura) {
        this.urlMiniatura = urlMiniatura;
    }
}
