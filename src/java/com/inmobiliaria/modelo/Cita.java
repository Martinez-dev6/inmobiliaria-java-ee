package com.inmobiliaria.modelo;

import java.sql.Timestamp;

public class Cita {

    private int idCita;
    private int idPropiedad;
    private int idCliente;
    private Timestamp fechaHora;
    private String estado;
    private String respuestaAgente;
    private Timestamp fechaRespuesta;

    // Derivados de JOIN (no son columnas de 'cita'); se llenan solo en consultas de listado.
    private String tituloPropiedad;
    private String correoCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String telefonoCliente;

    // Derivados de JOIN para la vista del cliente: quien publica la propiedad y
    // como contactarlo. Sin esto la reserva no decia con quien era la visita.
    private String nombreInmobiliaria;
    private String telefonoInmobiliaria;
    private String correoInmobiliaria;

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

    public String getCorreoInmobiliaria() {
        return correoInmobiliaria;
    }

    public void setCorreoInmobiliaria(String correoInmobiliaria) {
        this.correoInmobiliaria = correoInmobiliaria;
    }
}
