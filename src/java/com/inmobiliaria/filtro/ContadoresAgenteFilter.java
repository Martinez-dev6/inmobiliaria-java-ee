package com.inmobiliaria.filtro;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.dao.InmobiliariaDAO;
import com.inmobiliaria.dao.SolicitudDAO;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Deja en cada peticion de /agente/* cuantas citas y solicitudes estan sin
 * atender, que es lo que pinta el aviso rojo del menu lateral.
 *
 * Antes solo las calculaba AgentePanelController, asi que los avisos aparecian
 * en el panel y desaparecian al entrar a cualquier otra seccion: el menu es
 * compartido (app-inicio.jsp) pero los datos que necesitaba no lo eran. Un
 * filtro es el lugar natural para algo que toda la seccion necesita.
 */
@WebFilter(urlPatterns = {"/agente/*"})
public class ContadoresAgenteFilter implements Filter {

    private final InmobiliariaDAO inmobiliariaDAO = new InmobiliariaDAO();
    private final CitaDAO citaDAO = new CitaDAO();
    private final SolicitudDAO solicitudDAO = new SolicitudDAO();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession sesion = request.getSession(false);

        if (sesion != null && sesion.getAttribute("idUsuario") != null) {
            try {
                int idUsuario = (int) sesion.getAttribute("idUsuario");
                Integer idInmobiliaria = inmobiliariaDAO.buscarIdInmobiliariaPorUsuario(idUsuario);

                if (idInmobiliaria != null) {
                    request.setAttribute("citasPendientes",
                            citaDAO.contarPendientesPorInmobiliaria(idInmobiliaria));
                    request.setAttribute("solicitudesPendientes",
                            solicitudDAO.contarPendientesPorInmobiliaria(idInmobiliaria));
                }
            } catch (SQLException e) {
                // Los contadores son un adorno del menu: si fallan, la pagina
                // debe abrirse igual, solo que sin los avisos.
                e.printStackTrace();
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void destroy() { }
}
