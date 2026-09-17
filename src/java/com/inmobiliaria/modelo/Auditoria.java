package com.inmobiliaria.modelo;

import java.sql.Timestamp;

public class Auditoria {

    private int idAuditoria;
    private Integer idUsuario; // puede ser null: ON DELETE SET NULL en la FK
    private String accion;
    private String descripcion;
    private Timestamp fechaEvento;

    // Derivado de JOIN (no es columna de 'auditoria'); null si el usuario ya no existe.
    private String correoUsuario;

    public Auditoria() {
    }

    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Timestamp fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    // ===== Enlace calculado (no viene de la base) =====
    // La descripcion de cada evento menciona el objeto afectado
    // ("... la propiedad id 13 (HG-2026-0001)"). Con esto la pantalla de
    // auditoria puede ofrecer un boton "Ver" en vez de dejar al administrador
    // buscando ese id a mano.

    private static final java.util.regex.Pattern PATRON_PROPIEDAD =
            java.util.regex.Pattern.compile("propiedad id ([0-9]+)", java.util.regex.Pattern.CASE_INSENSITIVE);

    /**
     * Id de la propiedad mencionada en la descripcion, o null si el evento no
     * habla de una propiedad (cambios de rol, citas, solicitudes...).
     */
    public Integer getIdPropiedadRelacionada() {
        if (descripcion == null) {
            return null;
        }
        java.util.regex.Matcher coincidencia = PATRON_PROPIEDAD.matcher(descripcion);
        if (!coincidencia.find()) {
            return null;
        }
        try {
            return Integer.valueOf(coincidencia.group(1));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
