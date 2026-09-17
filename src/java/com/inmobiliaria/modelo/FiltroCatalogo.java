package com.inmobiliaria.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Criterios de busqueda del catalogo publico.
 *
 * Se agrupan en un objeto porque ya son seis y pasarlos sueltos obligaba a
 * cambiar la firma del DAO cada vez que se agrega uno (y a recordar el orden
 * de los parametros al llamarlo).
 */
public class FiltroCatalogo {

    /** Criterios de ordenamiento permitidos; cualquier otro valor cae en RECIENTES. */
    public enum Orden {
        RECIENTES("p.fecha_publicacion DESC"),
        PRECIO_ASC("p.precio ASC"),
        PRECIO_DESC("p.precio DESC"),
        AREA_DESC("p.area_m2 DESC NULLS LAST");

        private final String sql;

        Orden(String sql) {
            this.sql = sql;
        }

        public String getSql() {
            return sql;
        }

        /**
         * Traduce el valor que llega por la URL. Nunca se concatena texto del
         * usuario en el ORDER BY: solo se acepta uno de estos nombres.
         */
        public static Orden desdeTexto(String texto) {
            if (texto == null) {
                return RECIENTES;
            }
            try {
                return Orden.valueOf(texto.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return RECIENTES;
            }
        }
    }

    private Integer idCiudad;
    private Integer idTipoPropiedad;
    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private BigDecimal areaMin;
    private List<Integer> idsCaracteristicas = new ArrayList<>();
    private Orden orden = Orden.RECIENTES;

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Integer getIdTipoPropiedad() {
        return idTipoPropiedad;
    }

    public void setIdTipoPropiedad(Integer idTipoPropiedad) {
        this.idTipoPropiedad = idTipoPropiedad;
    }

    public BigDecimal getPrecioMin() {
        return precioMin;
    }

    public void setPrecioMin(BigDecimal precioMin) {
        this.precioMin = precioMin;
    }

    public BigDecimal getPrecioMax() {
        return precioMax;
    }

    public void setPrecioMax(BigDecimal precioMax) {
        this.precioMax = precioMax;
    }

    public BigDecimal getAreaMin() {
        return areaMin;
    }

    public void setAreaMin(BigDecimal areaMin) {
        this.areaMin = areaMin;
    }

    public List<Integer> getIdsCaracteristicas() {
        return idsCaracteristicas;
    }

    public void setIdsCaracteristicas(List<Integer> idsCaracteristicas) {
        this.idsCaracteristicas = (idsCaracteristicas == null) ? new ArrayList<>() : idsCaracteristicas;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = (orden == null) ? Orden.RECIENTES : orden;
    }

    /** true si el usuario no acoto nada; en EL se consulta como ${filtro.vacio}. */
    public boolean isVacio() {
        return idCiudad == null && idTipoPropiedad == null
                && precioMin == null && precioMax == null && areaMin == null
                && idsCaracteristicas.isEmpty() && orden == Orden.RECIENTES;
    }
}
