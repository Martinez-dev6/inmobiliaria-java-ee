package com.inmobiliaria.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Mensajes "flash" para el patron POST-Redirect-GET.
 *
 * El controlador guarda el mensaje en la sesion justo antes de redirigir y el
 * fragmento /WEB-INF/jspf/mensajes.jsp lo pinta y lo borra en el siguiente GET.
 * Asi el usuario ve la confirmacion pero, si recarga con F5, el navegador no
 * reenvia el formulario ni el mensaje se queda pegado en pantalla.
 */
public final class Flash {

    public static final String EXITO = "flashExito";
    public static final String ERROR = "flashError";

    private Flash() {
    }

    public static void exito(HttpServletRequest request, String mensaje) {
        guardar(request, EXITO, mensaje);
    }

    public static void error(HttpServletRequest request, String mensaje) {
        guardar(request, ERROR, mensaje);
    }

    private static void guardar(HttpServletRequest request, String clave, String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        request.getSession().setAttribute(clave, mensaje);
    }
}
