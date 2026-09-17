package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CaracteristicaDAO;
import com.inmobiliaria.dao.CiudadDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.dao.TipoPropiedadDAO;
import com.inmobiliaria.modelo.Caracteristica;
import com.inmobiliaria.modelo.FiltroCatalogo;
import com.inmobiliaria.modelo.Propiedad;
import com.inmobiliaria.util.Paginacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CatalogoController", urlPatterns = {"/catalogo"})
public class CatalogoController extends HttpServlet {

    /** Propiedades por página del catálogo (3 columnas x 3 filas). */
    private static final int POR_PAGINA = 9;

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final CiudadDAO ciudadDAO = new CiudadDAO();
    private final TipoPropiedadDAO tipoPropiedadDAO = new TipoPropiedadDAO();
    private final CaracteristicaDAO caracteristicaDAO = new CaracteristicaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FiltroCatalogo filtro = leerFiltro(request);

        try {
            int total = propiedadDAO.contarConFiltros(filtro);
            Paginacion paginacion = new Paginacion(request.getParameter("pagina"), POR_PAGINA, total);

            List<Propiedad> propiedades = propiedadDAO.buscarConFiltros(
                    filtro, paginacion.getLimite(), paginacion.getDesplazamiento());

            request.setAttribute("propiedades", propiedades);
            request.setAttribute("ciudades", ciudadDAO.listarTodas());
            request.setAttribute("tipos", tipoPropiedadDAO.listarTodos());
            request.setAttribute("caracteristicas",
                    marcarSeleccionadas(caracteristicaDAO.listarTodas(), filtro.getIdsCaracteristicas()));

            paginacion.publicar(request, construirUrlBase(request));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorCatalogo", "No se pudo cargar el catálogo. Intenta más tarde.");
        }

        // Se devuelven los filtros elegidos para que el formulario los recuerde.
        request.setAttribute("filtro", filtro);
        request.setAttribute("precioMinTexto", request.getParameter("precioMin"));
        request.setAttribute("precioMaxTexto", request.getParameter("precioMax"));
        request.setAttribute("areaMinTexto", request.getParameter("areaMin"));

        request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
    }

    private FiltroCatalogo leerFiltro(HttpServletRequest request) {
        FiltroCatalogo filtro = new FiltroCatalogo();
        filtro.setIdCiudad(parsearEntero(request.getParameter("idCiudad")));
        filtro.setIdTipoPropiedad(parsearEntero(request.getParameter("idTipoPropiedad")));
        filtro.setPrecioMin(parsearMoneda(request.getParameter("precioMin")));
        filtro.setPrecioMax(parsearMoneda(request.getParameter("precioMax")));
        filtro.setAreaMin(parsearDecimal(request.getParameter("areaMin")));
        filtro.setIdsCaracteristicas(parsearIds(request.getParameterValues("caracteristicas")));
        filtro.setOrden(FiltroCatalogo.Orden.desdeTexto(request.getParameter("orden")));
        return filtro;
    }

    /** Marca en la lista qué características venían tildadas, para repintarlas. */
    private List<Caracteristica> marcarSeleccionadas(List<Caracteristica> todas, List<Integer> seleccionadas) {
        for (Caracteristica c : todas) {
            c.setSeleccionada(seleccionadas.contains(c.getIdCaracteristica()));
        }
        return todas;
    }

    /**
     * URL del propio catálogo con los filtros actuales, lista para que el
     * paginador le concatene "pagina=N" sin perder la búsqueda del usuario.
     */
    private String construirUrlBase(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(request.getContextPath()).append("/catalogo?");
        agregarParametro(url, request, "idCiudad");
        agregarParametro(url, request, "idTipoPropiedad");
        agregarParametro(url, request, "precioMin");
        agregarParametro(url, request, "precioMax");
        agregarParametro(url, request, "areaMin");
        agregarParametro(url, request, "orden");
        agregarParametros(url, request, "caracteristicas");
        return url.toString();
    }

    private void agregarParametro(StringBuilder url, HttpServletRequest request, String nombre) {
        anexar(url, nombre, request.getParameter(nombre));
    }

    private void agregarParametros(StringBuilder url, HttpServletRequest request, String nombre) {
        String[] valores = request.getParameterValues(nombre);
        if (valores == null) {
            return;
        }
        for (String valor : valores) {
            anexar(url, nombre, valor);
        }
    }

    private void anexar(StringBuilder url, String nombre, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return;
        }
        try {
            url.append(nombre).append("=").append(URLEncoder.encode(valor.trim(), "UTF-8")).append("&");
        } catch (UnsupportedEncodingException e) {
            // UTF-8 siempre está disponible; si fallara, se omite el filtro.
        }
    }

    private List<Integer> parsearIds(String[] valores) {
        List<Integer> ids = new ArrayList<>();
        if (valores == null) {
            return ids;
        }
        for (String valor : valores) {
            Integer id = parsearEntero(valor);
            if (id != null) {
                ids.add(id);
            }
        }
        return ids;
    }

    private Integer parsearEntero(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parsearDecimal(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Los filtros de precio se escriben con separadores de miles (120.000.000).
     * El JS los limpia antes de enviar; aquí se limpian igual por si no corrió.
     */
    private BigDecimal parsearMoneda(String texto) {
        if (texto == null) {
            return null;
        }
        String soloDigitos = texto.replaceAll("[^0-9]", "");
        if (soloDigitos.isEmpty()) {
            return null;
        }
        return new BigDecimal(soloDigitos);
    }
}
