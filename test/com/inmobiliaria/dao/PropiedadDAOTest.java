package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Propiedad;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PropiedadDAOTest {

    private final PropiedadDAO dao = new PropiedadDAO();

    @Test
    public void buscarPorId_devuelveLaPrimeraPropiedadSembrada() throws Exception {
        Propiedad p = dao.buscarPorId(1);

        assertNotNull("La propiedad con id 1 debe existir (sembrada en el DML)", p);
        assertEquals("MAT-0001", p.getMatriculaInmobiliaria());
    }

    @Test
    public void buscarPorId_devuelveNullSiNoExiste() throws Exception {
        Propiedad p = dao.buscarPorId(999999);
        assertNull("Un id inexistente debe devolver null, no lanzar excepción", p);
    }

    @Test
    public void buscarConFiltros_soloDevuelvePropiedadesDisponibles() throws Exception {
        List<Propiedad> resultado = dao.buscarConFiltros(null, null, null, null);

        assertNotNull(resultado);
        for (Propiedad p : resultado) {
            assertEquals("Toda propiedad del catálogo público debe estar 'disponible'",
                    "disponible", p.getEstado());
        }
    }

    @Test
    public void buscarConFiltros_respetaElRangoDePrecio() throws Exception {
        BigDecimal precioMax = new BigDecimal("100000000");
        List<Propiedad> resultado = dao.buscarConFiltros(null, null, null, precioMax);

        for (Propiedad p : resultado) {
            assertTrue("Ninguna propiedad debe superar el precio máximo filtrado",
                    p.getPrecio().compareTo(precioMax) <= 0);
        }
    }
}
