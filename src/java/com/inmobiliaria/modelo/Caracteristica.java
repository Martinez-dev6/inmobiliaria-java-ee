package com.inmobiliaria.modelo;

public class Caracteristica {

    private int idCaracteristica;
    private String nombreCaracteristica;
    private boolean seleccionada; // solo para el formulario; no es columna de 'caracteristica'

    public Caracteristica() {
    }

    public Caracteristica(int idCaracteristica, String nombreCaracteristica) {
        this.idCaracteristica = idCaracteristica;
        this.nombreCaracteristica = nombreCaracteristica;
    }

    public int getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(int idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public String getNombreCaracteristica() {
        return nombreCaracteristica;
    }

    public void setNombreCaracteristica(String nombreCaracteristica) {
        this.nombreCaracteristica = nombreCaracteristica;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
}