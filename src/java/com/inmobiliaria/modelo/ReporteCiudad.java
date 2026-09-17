package com.inmobiliaria.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Agrupacion de presentacion del reporte: la consulta con GROUP BY devuelve una
 * fila por ciudad Y estado, asi que una misma ciudad aparecia repetida tantas
 * veces como estados tuviera. Aqui se juntan esas filas en una sola por ciudad,
 * con el desglose por estado dentro.
 */
public class ReporteCiudad {

    private final int idCiudad;
    private final String nombreCiudad;
    private final List<ReportePropiedadesPorCiudad> estados = new ArrayList<>();
    private int total;

    public ReporteCiudad(int idCiudad, String nombreCiudad) {
        this.idCiudad = idCiudad;
        this.nombreCiudad = nombreCiudad;
    }

    /**
     * Junta las filas ciudad+estado que devuelve el DAO en una entrada por
     * ciudad, conservando el orden alfabetico que ya trae el ORDER BY.
     */
    public static List<ReporteCiudad> agrupar(List<ReportePropiedadesPorCiudad> filas) {
        java.util.Map<Integer, ReporteCiudad> porCiudad = new java.util.LinkedHashMap<>();
        for (ReportePropiedadesPorCiudad fila : filas) {
            ReporteCiudad ciudad = porCiudad.get(fila.getIdCiudad());
            if (ciudad == null) {
                ciudad = new ReporteCiudad(fila.getIdCiudad(), fila.getNombreCiudad());
                porCiudad.put(fila.getIdCiudad(), ciudad);
            }
            ciudad.agregar(fila);
        }
        return new ArrayList<>(porCiudad.values());
    }

    public void agregar(ReportePropiedadesPorCiudad fila) {
        estados.add(fila);
        total += fila.getTotal();
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public List<ReportePropiedadesPorCiudad> getEstados() {
        return estados;
    }

    public int getTotal() {
        return total;
    }
}
