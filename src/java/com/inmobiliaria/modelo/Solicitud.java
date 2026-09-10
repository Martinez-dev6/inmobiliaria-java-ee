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

    // Derivados de JOIN (no son columnas de 'solicitud'); se llenan solo en consultas de listado.
    private String tituloPropiedad;
    private String correoCliente;

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
}
