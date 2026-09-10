package com.inmobiliaria.modelo;

import java.sql.Timestamp;

public class Favorito {

    private int idUsuario;
    private int idPropiedad;
    private Timestamp fechaMarcado;

    public Favorito() {
    }

    public Favorito(int idUsuario, int idPropiedad) {
        this.idUsuario = idUsuario;
        this.idPropiedad = idPropiedad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public Timestamp getFechaMarcado() {
        return fechaMarcado;
    }

    public void setFechaMarcado(Timestamp fechaMarcado) {
        this.fechaMarcado = fechaMarcado;
    }
}