package com.inmobiliaria.excepcion;

public class CorreoDuplicadoException extends Exception {

    public CorreoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}