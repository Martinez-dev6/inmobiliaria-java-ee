package com.inmobiliaria.modelo;

/**
 * Ficha comercial de una agencia (tabla 'inmobiliaria').
 *
 * OJO: no es lo mismo que el ROL "Inmobiliaria". El rol solo dice que la cuenta
 * puede entrar al panel de agente; esta ficha es la que guarda el nombre
 * comercial, el NIT y el telefono, y es la que referencian las propiedades por
 * su id_inmobiliaria. Sin ella no se puede publicar nada.
 */
public class Inmobiliaria {

    private int idInmobiliaria;
    private int idUsuario;
    private String nombreComercial;
    private String nit;
    private String telefonoContacto;

    public Inmobiliaria() {
    }

    public int getIdInmobiliaria() {
        return idInmobiliaria;
    }

    public void setIdInmobiliaria(int idInmobiliaria) {
        this.idInmobiliaria = idInmobiliaria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }
}
