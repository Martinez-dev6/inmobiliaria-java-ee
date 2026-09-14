package com.inmobiliaria.modelo;

import java.sql.Timestamp;

public class Solicitud {

    private int idSolicitud;
    private int idPropiedad;
    private int idCliente;
    private String tipoSolicitud;
    private String estado;
    private Timestamp fechaSolicitud;
    private String observaciones;
    private String respuestaAgente;
    private Timestamp fechaRespuesta;

    // Derivados de JOIN (no son columnas de 'solicitud'); se llenan solo en consultas de listado.
    private String tituloPropiedad;
    private String correoCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String telefonoCliente;

    public Solicitud() {
    }

    public Solicitud(int idPropiedad, int idCliente, String tipoSolicitud, String observaciones) {
        this.idPropiedad = idPropiedad;
        this.idCliente = idCliente;
        this.tipoSolicitud = tipoSolicitud;
        this.observaciones = observaciones;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRespuestaAgente() {
        return respuestaAgente;
    }

    public void setRespuestaAgente(String respuestaAgente) {
        this.respuestaAgente = respuestaAgente;
    }

    public Timestamp getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Timestamp fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getTituloPropiedad() {
        return tituloPropiedad;
    }

    public void setTituloPropiedad(String tituloPropiedad) {
        this.tituloPropiedad = tituloPropiedad;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }
}
