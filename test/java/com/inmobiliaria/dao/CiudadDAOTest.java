package com.inmobiliaria.dao;

import com.inmobiliaria.modelo.Ciudad;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CiudadDAOTest {

    @Test
    public void listarTodas_devuelveAlMenosLasCiudadesSembradas() throws Exception {
        CiudadDAO dao = new CiudadDAO();
        List<Ciudad> ciudades = dao.listarTodas();

        assertNotNull(ciudades);
        assertTrue("Debe haber al menos 6 ciudades sembradas en el DML", ciudades.size() >= 6);

        boolean tieneBucaramanga = ciudades.stream()
                .anyMatch(c -> "Bucaramanga".equals(c.getNombreCiudad()));
        assertTrue("Bucaramanga debe existir en el catálogo sembrado", tieneBucaramanga);
    }
}
