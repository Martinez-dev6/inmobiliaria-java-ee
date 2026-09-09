package com.inmobiliaria.modelo;

public class TipoPropiedad {

    private int idTipoPropiedad;
    private String nombreTipo;

    public TipoPropiedad() {
    }

    public TipoPropiedad(int idTipoPropiedad, String nombreTipo) {
        this.idTipoPropiedad = idTipoPropiedad;
        this.nombreTipo = nombreTipo;
    }

    public int getIdTipoPropiedad() {
        return idTipoPropiedad;
    }

    public void setIdTipoPropiedad(int idTipoPropiedad) {
        this.idTipoPropiedad = idTipoPropiedad;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
}