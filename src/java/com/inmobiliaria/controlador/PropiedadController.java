package com.inmobiliaria.controlador;

import com.inmobiliaria.dao.CaracteristicaDAO;
import com.inmobiliaria.dao.CiudadDAO;
import com.inmobiliaria.dao.ImagenPropiedadDAO;
import com.inmobiliaria.dao.InmobiliariaDAO;
import com.inmobiliaria.dao.PropiedadCaracteristicaDAO;
import com.inmobiliaria.dao.PropiedadDAO;
import com.inmobiliaria.dao.TipoPropiedadDAO;
import com.inmobiliaria.excepcion.MatriculaDuplicadaException;
import com.inmobiliaria.modelo.Caracteristica;
import com.inmobiliaria.modelo.Ciudad;
import com.inmobiliaria.modelo.Propiedad;
import com.inmobiliaria.modelo.TipoPropiedad;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "PropiedadController", urlPatterns = {"/agente/propiedades"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 25 * 1024 * 1024
)
public class PropiedadController extends HttpServlet {

    private final PropiedadDAO propiedadDAO = new PropiedadDAO();
    private final CiudadDAO ciudadDAO = new CiudadDAO();
    private final TipoPropiedadDAO tipoPropiedadDAO = new TipoPropiedadDAO();
    private final InmobiliariaDAO inmobiliariaDAO = new InmobiliariaDAO();
    private final ImagenPropiedadDAO imagenPropiedadDAO = new ImagenPropiedadDAO();
    private final CaracteristicaDAO caracteristicaDAO = new CaracteristicaDAO();
    private final PropiedadCaracteristicaDAO propiedadCaracteristicaDAO = new PropiedadCaracteristicaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("nuevo".equals(accion)) {
            mostrarFormulario(request, response, new Propiedad(), null, new ArrayList<>());
        } else if ("editar".equals(accion)) {
            mostrarFormularioEdicion(request, response);
        } else {
            mostrarListado(request, response, null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("crear".equals(accion)) {
            procesarCrear(request, response);
        } else if ("actualizar".equals(accion)) {
            procesarActualizar(request, response);
        } else if ("baja".equals(accion) || "reactivar".equals(accion)) {
            procesarCambioEstado(request, response, accion);
        } else {
            mostrarListado(request, response, "Acción no reconocida.");
        }
    }

    // ===================== LISTADO =====================

    private void mostrarListado(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        if (idInmobiliaria == null) {
            request.setAttribute("errorPropiedad", "Tu cuenta no está asociada a ninguna inmobiliaria.");
            request.getRequestDispatcher("/agente/propiedades.jsp").forward(request, response);
            return;
        }

        try {
            request.setAttribute("propiedades", propiedadDAO.listarPorInmobiliaria(idInmobiliaria));
        } catch (SQLException e) {
            e.printStackTrace();
            mensaje = "No se pudo cargar el listado de propiedades.";
        }

        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
        }
        request.getRequestDispatcher("/agente/propiedades.jsp").forward(request, response);
    }

    // ===================== FORMULARIO =====================

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response,
                                    Propiedad propiedad, String error, List<Integer> idsCaracteristicasSeleccionadas)
            throws ServletException, IOException {
        try {
            request.setAttribute("ciudades", ciudadDAO.listarTodas());
            request.setAttribute("tipos", tipoPropiedadDAO.listarTodos());
            request.setAttribute("caracteristicas", cargarCaracteristicasParaFormulario(idsCaracteristicasSeleccionadas));
        } catch (SQLException e) {
            e.printStackTrace();
            error = "No se pudieron cargar los catálogos.";
        }

        request.setAttribute("propiedad", propiedad);
        if (error != null) {
            request.setAttribute("errorPropiedad", error);
        }
        request.getRequestDispatcher("/agente/propiedad-form.jsp").forward(request, response);
    }

    private List<Caracteristica> cargarCaracteristicasParaFormulario(List<Integer> idsSeleccionados) throws SQLException {
        List<Caracteristica> todas = caracteristicaDAO.listarTodas();
        for (Caracteristica c : todas) {
            c.setSeleccionada(idsSeleccionados.contains(c.getIdCaracteristica()));
        }
        return todas;
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        int idPropiedad = Integer.parseInt(request.getParameter("id"));

        try {
            Propiedad propiedad = propiedadDAO.buscarPorId(idPropiedad);

            if (propiedad == null || idInmobiliaria == null
                    || propiedad.getIdInmobiliaria() != idInmobiliaria) {
                mostrarListado(request, response, "Esa propiedad no existe o no te pertenece.");
                return;
            }

            request.setAttribute("imagenes", imagenPropiedadDAO.listarPorPropiedad(idPropiedad));
            List<Integer> idsSeleccionados = propiedadCaracteristicaDAO.listarIdsPorPropiedad(idPropiedad);

            mostrarFormulario(request, response, propiedad, null, idsSeleccionados);

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarListado(request, response, "No se pudo cargar la propiedad.");
        }
    }

    // ===================== CREAR =====================

    private void procesarCrear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        if (idInmobiliaria == null) {
            mostrarListado(request, response, "Tu cuenta no está asociada a ninguna inmobiliaria.");
            return;
        }

        Propiedad propiedad = leerFormulario(request);
        propiedad.setIdInmobiliaria(idInmobiliaria);

        List<Integer> idsCaracteristicas = parsearIds(request.getParameterValues("caracteristicas"));

        String error = validar(propiedad, true);
        if (error != null) {
            mostrarFormulario(request, response, propiedad, error, idsCaracteristicas);
            return;
        }

        try {
            int idGenerado = propiedadDAO.crear(propiedad);
            guardarFotosNuevas(request, idGenerado);
            propiedadCaracteristicaDAO.asignarCaracteristicas(idGenerado, idsCaracteristicas);
        } catch (MatriculaDuplicadaException e) {
            mostrarFormulario(request, response, propiedad, e.getMessage(), idsCaracteristicas);
            return;
        } catch (SQLException | IOException | ServletException e) {
            e.printStackTrace();
            mostrarFormulario(request, response, propiedad, "No se pudo guardar la propiedad. Intenta más tarde.", idsCaracteristicas);
            return;
        }

        mostrarListado(request, response, "Propiedad publicada correctamente.");
    }

    // ===================== ACTUALIZAR =====================

    private void procesarActualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        if (idInmobiliaria == null) {
            mostrarListado(request, response, "Tu cuenta no está asociada a ninguna inmobiliaria.");
            return;
        }

        Propiedad propiedad = leerFormulario(request);
        propiedad.setIdInmobiliaria(idInmobiliaria);
        propiedad.setIdPropiedad(Integer.parseInt(request.getParameter("idPropiedad")));

        List<Integer> idsCaracteristicas = parsearIds(request.getParameterValues("caracteristicas"));

        String error = validar(propiedad, false);
        if (error != null) {
            mostrarFormulario(request, response, propiedad, error, idsCaracteristicas);
            return;
        }

        try {
            int filas = propiedadDAO.actualizar(propiedad);
            if (filas == 0) {
                mostrarListado(request, response, "Esa propiedad no existe o no te pertenece.");
                return;
            }
            eliminarFotosMarcadas(request, propiedad.getIdPropiedad());
            guardarFotosNuevas(request, propiedad.getIdPropiedad());
            propiedadCaracteristicaDAO.asignarCaracteristicas(propiedad.getIdPropiedad(), idsCaracteristicas);
        } catch (SQLException | IOException | ServletException e) {
            e.printStackTrace();
            mostrarFormulario(request, response, propiedad, "No se pudo actualizar la propiedad. Intenta más tarde.", idsCaracteristicas);
            return;
        }

        mostrarListado(request, response, "Propiedad actualizada correctamente.");
    }

    // ===================== BAJA / REACTIVAR =====================

    private void procesarCambioEstado(HttpServletRequest request, HttpServletResponse response, String accion)
            throws ServletException, IOException {

        Integer idInmobiliaria = resolverIdInmobiliaria(request);
        int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
        String nuevoEstado = "baja".equals(accion) ? "inactiva" : "disponible";

        try {
            int filas = (idInmobiliaria == null) ? 0
                    : propiedadDAO.cambiarEstado(idPropiedad, idInmobiliaria, nuevoEstado);

            if (filas == 0) {
                mostrarListado(request, response, "Esa propiedad no existe o no te pertenece.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarListado(request, response, "No se pudo actualizar el estado de la propiedad.");
            return;
        }

        mostrarListado(request, response, "baja".equals(accion) ? "Propiedad dada de baja." : "Propiedad reactivada.");
    }

    // ===================== IMÁGENES =====================

    private void guardarFotosNuevas(HttpServletRequest request, int idPropiedad)
            throws IOException, ServletException, SQLException {

        String directorioReal = getServletContext().getRealPath("/img/propiedades");
        File directorio = new File(directorioReal);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        for (Part parte : request.getParts()) {
            if (!"imagenes".equals(parte.getName())) {
                continue;
            }
            String nombreOriginal = parte.getSubmittedFileName();
            if (nombreOriginal == null || nombreOriginal.trim().isEmpty() || parte.getSize() == 0) {
                continue;
            }

            String extension = "";
            int punto = nombreOriginal.lastIndexOf('.');
            if (punto >= 0) {
                extension = nombreOriginal.substring(punto);
            }
            String nombreUnico = UUID.randomUUID().toString() + extension;

            parte.write(directorioReal + File.separator + nombreUnico);

            imagenPropiedadDAO.agregarImagen(idPropiedad, "img/propiedades/" + nombreUnico);
        }
    }

    private void eliminarFotosMarcadas(HttpServletRequest request, int idPropiedad) throws SQLException {
        String[] idsAEliminar = request.getParameterValues("eliminarImagen");
        if (idsAEliminar == null) {
            return;
        }
        for (String idTexto : idsAEliminar) {
            try {
                int idImagen = Integer.parseInt(idTexto);
                String urlEliminada = imagenPropiedadDAO.eliminarImagen(idImagen, idPropiedad);
                if (urlEliminada != null) {
                    borrarArchivoFisico(urlEliminada);
                }
            } catch (NumberFormatException ignorado) {
                // valor inválido en el checkbox, se ignora
            }
        }
    }

    private void borrarArchivoFisico(String rutaRelativa) {
        String rutaReal = getServletContext().getRealPath("/" + rutaRelativa);
        if (rutaReal != null) {
            File archivo = new File(rutaReal);
            if (archivo.exists()) {
                archivo.delete();
            }
        }
    }

    // ===================== AUXILIARES =====================

    private Propiedad leerFormulario(HttpServletRequest request) {
        Propiedad propiedad = new Propiedad();
        propiedad.setTitulo(request.getParameter("titulo"));
        propiedad.setDescripcion(request.getParameter("descripcion"));
        propiedad.setDireccion(request.getParameter("direccion"));
        propiedad.setMatriculaInmobiliaria(request.getParameter("matriculaInmobiliaria"));

        try {
            propiedad.setIdCiudad(Integer.parseInt(request.getParameter("idCiudad")));
        } catch (NumberFormatException e) {
            propiedad.setIdCiudad(0);
        }
        try {
            propiedad.setIdTipoPropiedad(Integer.parseInt(request.getParameter("idTipoPropiedad")));
        } catch (NumberFormatException e) {
            propiedad.setIdTipoPropiedad(0);
        }

        propiedad.setPrecio(parsearDecimal(request.getParameter("precio")));
        propiedad.setAreaM2(parsearDecimal(request.getParameter("areaM2")));
        propiedad.setDestacada(request.getParameter("destacada") != null);

        return propiedad;
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

    private List<Integer> parsearIds(String[] valores) {
        List<Integer> ids = new ArrayList<>();
        if (valores == null) {
            return ids;
        }
        for (String valor : valores) {
            try {
                ids.add(Integer.parseInt(valor));
            } catch (NumberFormatException ignorado) {
                // valor inválido, se ignora
            }
        }
        return ids;
    }

    private String validar(Propiedad propiedad, boolean esCreacion) {
        if (esCreacion && (propiedad.getMatriculaInmobiliaria() == null
                || propiedad.getMatriculaInmobiliaria().trim().isEmpty())) {
            return "La matrícula inmobiliaria es obligatoria.";
        }
        if (propiedad.getTitulo() == null || propiedad.getTitulo().trim().isEmpty()) {
            return "El título es obligatorio.";
        }
        if (propiedad.getDireccion() == null || propiedad.getDireccion().trim().isEmpty()) {
            return "La dirección es obligatoria.";
        }
        if (propiedad.getIdCiudad() == 0) {
            return "Selecciona una ciudad.";
        }
        if (propiedad.getIdTipoPropiedad() == 0) {
            return "Selecciona un tipo de propiedad.";
        }
        if (propiedad.getPrecio() == null || propiedad.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return "El precio debe ser un número válido mayor que cero.";
        }
        if (propiedad.getAreaM2() != null && propiedad.getAreaM2().compareTo(BigDecimal.ZERO) <= 0) {
            return "El área, si se indica, debe ser mayor que cero.";
        }
        return null;
    }

    private Integer resolverIdInmobiliaria(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        int idUsuario = (int) sesion.getAttribute("idUsuario");
        try {
            return inmobiliariaDAO.buscarIdInmobiliariaPorUsuario(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}