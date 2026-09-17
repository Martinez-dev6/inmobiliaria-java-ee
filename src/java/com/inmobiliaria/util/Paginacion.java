package com.inmobiliaria.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Calculo de paginacion compartido por los listados (catalogo, propiedades del
 * administrador, auditoria). Traduce el parametro "pagina" de la URL a los
 * valores LIMIT/OFFSET que necesitan los DAO y deja en el request lo que el
 * fragmento /WEB-INF/jspf/paginacion.jsp usa para pintar los botones.
 */
public final class Paginacion {

    private final int pagina;
    private final int tamano;
    private final int totalRegistros;
    private final int totalPaginas;

    public Paginacion(String paginaTexto, int tamano, int totalRegistros) {
        this.tamano = tamano;
        this.totalRegistros = totalRegistros;
        this.totalPaginas = Math.max(1, (int) Math.ceil(totalRegistros / (double) tamano));

        int solicitada = parsear(paginaTexto);
        // Una pagina fuera de rango (enlace viejo, ?pagina=999) se acota en vez
        // de devolver un listado vacio sin explicacion.
        this.pagina = Math.min(Math.max(solicitada, 1), totalPaginas);
    }

    public int getPagina() {
        return pagina;
    }

    public int getLimite() {
        return tamano;
    }

    public int getDesplazamiento() {
        return (pagina - 1) * tamano;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * Publica en el request lo que necesita el fragmento de paginacion.
     *
     * @param urlBase URL con los filtros ya incluidos y lista para concatenar
     *                "pagina=N" (debe terminar en "?" o en "&").
     */
    public void publicar(HttpServletRequest request, String urlBase) {
        request.setAttribute("paginaActual", pagina);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("totalRegistros", totalRegistros);
        request.setAttribute("urlPaginacion", urlBase);
    }

    private int parsear(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return 1;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
