package com.inmobiliaria.dao;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FavoritoDAOTest {

    private final FavoritoDAO dao = new FavoritoDAO();

    // Usuario/propiedad de prueba: cliente id 1 y propiedad id 1, ambos sembrados en el DML.
    private static final int ID_USUARIO_PRUEBA = 1;
    private static final int ID_PROPIEDAD_PRUEBA = 1;

    @After
    public void limpiar() throws Exception {
        // Deja la base de datos como estaba antes de la prueba, sin importar si fallo o no.
        dao.desmarcar(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA);
    }

    @Test
    public void marcarYDesmarcar_funcionanComoUnCicloCompleto() throws Exception {
        dao.desmarcar(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA); // aseguro estado inicial limpio

        assertFalse("No debería estar marcada al iniciar la prueba",
                dao.existe(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA));

        dao.marcar(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA);
        assertTrue("Debe quedar marcada después de marcar()",
                dao.existe(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA));

        dao.marcar(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA); // marcar dos veces no debe fallar (ON CONFLICT DO NOTHING)
        assertTrue(dao.existe(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA));

        dao.desmarcar(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA);
        assertFalse("No debe existir después de desmarcar()",
                dao.existe(ID_USUARIO_PRUEBA, ID_PROPIEDAD_PRUEBA));
    }
}
