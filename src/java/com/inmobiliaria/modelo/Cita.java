package com.inmobiliaria.modelo;

import java.sql.Timestamp;

public class Cita {

    private int idCita;
    private int idPropiedad;
    private int idCliente;
    private Timestamp fechaHora;
    private String estado;

    // Derivados de JOIN (no son columnas de 'cita'); se llenan solo en consultas de listado.
    private String tituloPropiedad;
    private String correoCliente;

    public Cita() {
    }

    public Cita(int idPropiedad, int idCliente, Timestamp fechaHora) {
        this.idPropiedad = idPropiedad;
        this.idCliente = idCliente;
        this.fechaHora = fechaHora;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
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

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
