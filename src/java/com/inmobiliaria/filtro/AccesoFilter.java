package com.inmobiliaria.filtro;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/cliente/*", "/agente/*", "/admin/*"})
public class AccesoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String rutaSolicitada = request.getRequestURI().substring(request.getContextPath().length());
        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/acceso-denegado.jsp");
            return;
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) sesion.getAttribute("roles");
        String rolRequerido = rolSegunRuta(rutaSolicitada);

        if (rolRequerido != null && (roles == null || !roles.contains(rolRequerido))) {
            response.sendRedirect(request.getContextPath() + "/acceso-denegado.jsp");
            return;
        }

        chain.doFilter(req, res);
    }

    private String rolSegunRuta(String ruta) {
        if (ruta.startsWith("/admin/")) return "Administrador";
        if (ruta.startsWith("/agente/")) return "Inmobiliaria";
        if (ruta.startsWith("/cliente/")) return "Cliente";
        return null;
    }

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void destroy() { }
}