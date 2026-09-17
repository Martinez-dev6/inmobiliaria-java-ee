<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="accionNueva" value="${sinInmobiliaria ? '' : 'agente/propiedades?accion=nuevo'}" />

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="agente" />
    <jsp:param name="activo" value="panel" />
    <jsp:param name="titulo" value="Panel de la inmobiliaria" />
    <jsp:param name="descripcion" value="Tu catálogo y lo que tienes pendiente por responder." />
    <jsp:param name="accionUrl" value="${accionNueva}" />
    <jsp:param name="accionTexto" value="Nueva propiedad" />
    <jsp:param name="accionIcono" value="plus-lg" />
</jsp:include>

    <c:if test="${sinInmobiliaria}">
        <jsp:include page="/WEB-INF/jspf/sin-inmobiliaria.jsp" />
    </c:if>

    <div class="metricas">
        <div class="metrica">
            <div class="metrica-icono"><i class="bi bi-houses"></i></div>
            <div>
                <div class="metrica-cifra">${totalPropiedades}</div>
                <div class="metrica-rotulo">Propiedades</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono exito"><i class="bi bi-check-circle"></i></div>
            <div>
                <div class="metrica-cifra">${totalDisponibles}</div>
                <div class="metrica-rotulo">Disponibles</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono acento"><i class="bi bi-calendar-check"></i></div>
            <div>
                <div class="metrica-cifra">${citasPendientes}</div>
                <div class="metrica-rotulo">Citas por confirmar</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono acento"><i class="bi bi-file-earmark-text"></i></div>
            <div>
                <div class="metrica-cifra">${solicitudesPendientes}</div>
                <div class="metrica-rotulo">Solicitudes por revisar</div>
            </div>
        </div>
    </div>

    <div class="panel-columnas">

        <div class="bloque">
            <div class="bloque-cabecera">
                <h2>Visitas agendadas</h2>
                <a href="${pageContext.request.contextPath}/agente/citas">Ver todas</a>
            </div>
            <c:choose>
                <c:when test="${empty proximasCitas}">
                    <div class="bloque-cuerpo">
                        <p class="text-muted mb-0">No tienes visitas pendientes ni confirmadas.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="lista-compacta">
                        <c:forEach var="cita" items="${proximasCitas}">
                            <li>
                                <div class="principal">
                                    <strong>${cita.tituloPropiedad}</strong>
                                    <span>
                                        <c:choose>
                                            <c:when test="${not empty cita.nombresCliente}">${cita.nombresCliente} ${cita.apellidosCliente}</c:when>
                                            <c:otherwise>${cita.correoCliente}</c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="derecha">
                                    <div class="small fw-semibold"><fmt:formatDate value="${cita.fechaHora}" pattern="dd/MM HH:mm"/></div>
                                    <c:choose>
                                        <c:when test="${cita.estado == 'confirmada'}">
                                            <span class="badge bg-success">Confirmada</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning text-dark">Pendiente</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="bloque">
            <div class="bloque-cabecera">
                <h2>Mi operación</h2>
            </div>
            <div class="panel-accesos">
                <a class="acceso-rapido" href="${pageContext.request.contextPath}/agente/propiedades">
                    <span class="acceso-icono"><i class="bi bi-houses"></i></span>
                    <span>
                        <span class="acceso-titulo">Mis propiedades</span>
                        <span class="acceso-detalle">Publica, edita y controla la disponibilidad.</span>
                    </span>
                    <i class="bi bi-chevron-right acceso-flecha"></i>
                </a>
                <a class="acceso-rapido" href="${pageContext.request.contextPath}/agente/citas">
                    <span class="acceso-icono"><i class="bi bi-calendar-check"></i></span>
                    <span>
                        <span class="acceso-titulo">Citas</span>
                        <span class="acceso-detalle">Confirma o cancela las visitas agendadas.</span>
                    </span>
                    <i class="bi bi-chevron-right acceso-flecha"></i>
                </a>
                <a class="acceso-rapido" href="${pageContext.request.contextPath}/agente/solicitudes">
                    <span class="acceso-icono"><i class="bi bi-file-earmark-text"></i></span>
                    <span>
                        <span class="acceso-titulo">Solicitudes</span>
                        <span class="acceso-detalle">Aprueba o rechaza las solicitudes recibidas.</span>
                    </span>
                    <i class="bi bi-chevron-right acceso-flecha"></i>
                </a>
                <a class="acceso-rapido" href="${pageContext.request.contextPath}/agente/perfil">
                    <span class="acceso-icono"><i class="bi bi-building"></i></span>
                    <span>
                        <span class="acceso-titulo">Datos de la agencia</span>
                        <span class="acceso-detalle">Nombre comercial, NIT y teléfono que ven los clientes.</span>
                    </span>
                    <i class="bi bi-chevron-right acceso-flecha"></i>
                </a>
            </div>
        </div>
    </div>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
