package com.inmobiliaria.modelo;

/**
 * Fila del reporte de agregacion (Historia 12): no corresponde a una tabla real,
 * es el resultado de un GROUP BY sobre propiedad + ciudad.
 */
public class ReportePropiedadesPorCiudad {

    private String nombreCiudad;
    private String estado;
    private int total;

    public ReportePropiedadesPorCiudad() {
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
