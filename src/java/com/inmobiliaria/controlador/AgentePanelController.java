package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.dao.InmobiliariaDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.modelo.Cita;
import com.inmobiliaria.modelo.Propiedad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "AgentePanelController", urlPatterns = {"/agente/panel"})
public class AgentePanelController extends HttpServlet {

    /** Cuantas visitas caben en el bloque del panel antes de mandar a "Ver todas". */
    private static final int VISITAS_EN_PANEL = 5;

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final InmobiliariaDAO inmobiliariaDAO = new InmobiliariaDAO();
    private final CitaDAO citaDAO = new CitaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        try {
            Integer idInmobiliaria = inmobiliariaDAO.buscarIdInmobiliariaPorUsuario(idUsuario);

            request.setAttribute("sinInmobiliaria", idInmobiliaria == null);

            if (idInmobiliaria != null) {
                List<Propiedad> propiedades = propiedadDAO.listarPorInmobiliaria(idInmobiliaria);

                long disponibles = propiedades.stream().filter(p -> "disponible".equals(p.getEstado())).count();
                long inactivas = propiedades.stream().filter(p -> "inactiva".equals(p.getEstado())).count();
                long destacadas = propiedades.stream().filter(Propiedad::isDestacada).count();

                request.setAttribute("totalPropiedades", propiedades.size());
                request.setAttribute("totalDisponibles", disponibles);
                request.setAttribute("totalInactivas", inactivas);
                request.setAttribute("totalDestacadas", destacadas);

                // Agenda de los proximos dias: lo primero que el agente necesita ver.
                // Los contadores de pendientes los deja ContadoresAgenteFilter,
                // que los calcula para todas las paginas de la seccion.
                request.setAttribute("proximasCitas", proximas(citaDAO.listarPorInmobiliaria(idInmobiliaria)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/agente/panel.jsp").forward(request, response);
    }

    /**
     * Citas todavia abiertas (pendientes o confirmadas): primero las futuras, de
     * la mas cercana a la mas lejana, y despues las que ya pasaron sin confirmar
     * ni cerrar, de la mas reciente a la mas antigua. Estas ultimas antes se
     * descartaban, asi que el bloque quedaba vacio mientras la metrica de "citas
     * por confirmar" seguia marcando pendientes.
     */
    private List<Cita> proximas(List<Cita> citas) {
        Timestamp ahora = new Timestamp(System.currentTimeMillis());

        List<Cita> abiertas = citas.stream()
                .filter(c -> "pendiente".equals(c.getEstado()) || "confirmada".equals(c.getEstado()))
                .filter(c -> c.getFechaHora() != null)
                .collect(Collectors.toList());

        List<Cita> ordenadas = new ArrayList<>(abiertas.stream()
                .filter(c -> c.getFechaHora().after(ahora))
                .sorted(Comparator.comparing(Cita::getFechaHora))
                .collect(Collectors.toList()));

        ordenadas.addAll(abiertas.stream()
                .filter(c -> !c.getFechaHora().after(ahora))
                .sorted(Comparator.comparing(Cita::getFechaHora).reversed())
                .collect(Collectors.toList()));

        return ordenadas.subList(0, Math.min(VISITAS_EN_PANEL, ordenadas.size()));
    }
}
