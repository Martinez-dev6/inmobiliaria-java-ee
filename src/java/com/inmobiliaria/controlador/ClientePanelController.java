package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CitaDAO;
import com.inmobiliaria.dao.PerfilDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.dao.SolicitudDAO;
import com.inmobiliaria.modelo.Cita;
import com.inmobiliaria.modelo.Perfil;

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

@WebServlet(name = "ClientePanelController", urlPatterns = {"/cliente/panel"})
public class ClientePanelController extends HttpServlet {

    /** Cuantas visitas caben en el bloque del panel antes de mandar a "Ver todas". */
    private static final int VISITAS_EN_PANEL = 4;

    private final PerfilDAO perfilDAO = new PerfilDAO();
    private final CitaDAO citaDAO = new CitaDAO();
    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final PropiedadDAO propiedadDAO = new PropiedadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        try {
            Perfil perfil = perfilDAO.buscarPorIdUsuario(idUsuario);
            request.setAttribute("perfil", perfil);

            // La cifra y la lista salen del mismo conjunto: antes la metrica
            // contaba todas las citas (canceladas incluidas) y la lista solo las
            // futuras, asi que el panel decia "1 visita" y "no tienes visitas".
            List<Cita> vigentes = vigentes(citaDAO.listarPorCliente(idUsuario));
            request.setAttribute("totalCitas", vigentes.size());
            request.setAttribute("proximasCitas",
                    vigentes.subList(0, Math.min(VISITAS_EN_PANEL, vigentes.size())));

            request.setAttribute("totalSolicitudes", solicitudDAO.listarPorCliente(idUsuario).size());
            request.setAttribute("favoritas", propiedadDAO.listarFavoritasDeUsuario(idUsuario));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/cliente/panel.jsp").forward(request, response);
    }

    /**
     * Visitas todavia abiertas (pendientes o confirmadas): primero las futuras,
     * de la mas cercana a la mas lejana, y despues las que ya pasaron sin que
     * nadie las cerrara, de la mas reciente a la mas antigua. Estas ultimas
     * antes se descartaban y el cliente perdia de vista una cita que seguia viva.
     */
    private List<Cita> vigentes(List<Cita> citas) {
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

        return ordenadas;
    }
}
