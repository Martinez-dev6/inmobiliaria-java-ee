<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="saludo" value="Bienvenido a Hogar 360" />
<c:if test="${not empty perfil.nombres}"><c:set var="saludo" value="Hola, ${perfil.nombres}" /></c:if>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="cliente" />
    <jsp:param name="activo" value="panel" />
    <jsp:param name="titulo" value="${saludo}" />
    <jsp:param name="descripcion" value="Tus búsquedas, visitas y solicitudes en un solo lugar." />
    <jsp:param name="accionUrl" value="catalogo" />
    <jsp:param name="accionTexto" value="Buscar propiedades" />
    <jsp:param name="accionIcono" value="search" />
</jsp:include>

    <c:if test="${empty perfil}">
        <div class="alerta-error mb-4">
            Todavía no has completado tu perfil.
            <a href="${pageContext.request.contextPath}/cliente/perfil">Complétalo aquí</a> para que las
            inmobiliarias puedan contactarte.
        </div>
    </c:if>

    <div class="metricas">
        <div class="metrica">
            <div class="metrica-icono acento"><i class="bi bi-heart-fill"></i></div>
            <div>
                <div class="metrica-cifra">${fn:length(favoritas)}</div>
                <div class="metrica-rotulo">Favoritos</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono"><i class="bi bi-calendar-event"></i></div>
            <div>
                <div class="metrica-cifra">${totalCitas}</div>
                <div class="metrica-rotulo">Visitas agendadas</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono"><i class="bi bi-file-earmark-text"></i></div>
            <div>
                <div class="metrica-cifra">${totalSolicitudes}</div>
                <div class="metrica-rotulo">Solicitudes</div>
            </div>
        </div>
    </div>

    <div class="panel-columnas">

        <div>
            <div class="bloque">
                <div class="bloque-cabecera">
                    <h2>Visitas agendadas</h2>
                    <a href="${pageContext.request.contextPath}/cliente/citas">Ver todas</a>
                </div>
                <c:choose>
                    <c:when test="${empty proximasCitas}">
                        <div class="bloque-cuerpo">
                            <p class="text-muted mb-0">
                                No tienes visitas agendadas.
                                <a href="${pageContext.request.contextPath}/catalogo">Busca una propiedad</a> y agenda la primera.
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <ul class="lista-compacta">
                            <c:forEach var="cita" items="${proximasCitas}">
                                <li>
                                    <div class="principal">
                                        <strong>${cita.tituloPropiedad}</strong>
                                        <span>
                                            <fmt:formatDate value="${cita.fechaHora}" pattern="EEEE d 'de' MMMM, HH:mm"/>
                                            <c:if test="${not empty cita.nombreInmobiliaria}"> · ${cita.nombreInmobiliaria}</c:if>
                                        </span>
                                    </div>
                                    <div class="derecha">
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
                    <h2>Mis favoritos</h2>
                    <a href="${pageContext.request.contextPath}/cliente/favoritos">Ver todos</a>
                </div>
                <c:choose>
                    <c:when test="${empty favoritas}">
                        <div class="bloque-cuerpo">
                            <p class="text-muted mb-0">Todavía no has guardado ninguna propiedad.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <ul class="lista-compacta">
                            <c:forEach var="p" items="${favoritas}" end="3">
                                <li>
                                    <div class="principal">
                                        <strong>${p.titulo}</strong>
                                        <span>${p.nombreCiudad} · ${p.nombreTipo}</span>
                                    </div>
                                    <div class="derecha">
                                        <div class="small fw-semibold">$<fmt:formatNumber value="${p.precio}" pattern="#,##0"/></div>
                                        <a class="btn btn-outline-claro btn-sm mt-1" href="${pageContext.request.contextPath}/propiedad?id=${p.idPropiedad}">Ver detalle</a>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="bloque">
            <div class="bloque-cabecera">
                <h2>Accesos rápidos</h2>
            </div>
            <div class="panel-accesos">
                <a class="acceso-rapido" href="${pageContext.request.contextPath}/catalogo">
                    <span class="acceso-icono"><i class="bi bi-search"></i></span>
                    <span>
                        <span class="acceso-titulo">Buscar propiedades</span>
                        <span class="acceso-detalle">Filtra por ciudad, tipo y presupuesto.</span>
                    </span>
                    <i class="bi bi-chevron-right acceso-flecha"></i>
                </a>
                <a class="acceso-rapido" href="${pageContext.request.contextPath}/cliente/solicitudes">
                    <span class="acceso-icono"><i class="bi bi-file-earmark-text"></i></span>
                    <span>
                        <span class="acceso-titulo">Mis solicitudes</span>
                        <span class="acceso-detalle">${totalSolicitudes} solicitud(es) de compra o arriendo.</span>
                    </span>
                    <i class="bi bi-chevron-right acceso-flecha"></i>
                </a>
                <a class="acceso-rapido" href="${pageContext.request.contextPath}/cliente/perfil">
                    <span class="acceso-icono"><i class="bi bi-person-circle"></i></span>
                    <span>
                        <span class="acceso-titulo">Mi perfil</span>
                        <span class="acceso-detalle">Actualiza tus datos de contacto.</span>
                    </span>
                    <i class="bi bi-chevron-right acceso-flecha"></i>
                </a>
            </div>
        </div>
    </div>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
