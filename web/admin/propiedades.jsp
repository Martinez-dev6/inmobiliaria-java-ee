<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="admin" />
    <jsp:param name="activo" value="propiedades" />
    <jsp:param name="titulo" value="Propiedades" />
    <jsp:param name="descripcion" value="Todas las publicaciones de todas las inmobiliarias del sistema." />
    <jsp:param name="accionUrl" value="admin/reportes" />
    <jsp:param name="accionTexto" value="Ver reportes" />
    <jsp:param name="accionIcono" value="bar-chart" />
</jsp:include>

    <c:if test="${not empty idCiudadFiltro or not empty estadoFiltro}">
        <div class="d-flex flex-wrap align-items-center gap-2 mb-4">
            <span class="text-muted small">Filtro aplicado:</span>
            <c:if test="${not empty nombreCiudadFiltro}">
                <span class="badge rounded-pill" style="background-color:var(--azul);">
                    <i class="bi bi-geo-alt"></i> ${nombreCiudadFiltro}
                </span>
            </c:if>
            <c:if test="${not empty estadoFiltro}">
                <span class="badge rounded-pill text-capitalize" style="background-color:var(--azul);">
                    <i class="bi bi-tag"></i> ${estadoFiltro}
                </span>
            </c:if>
            <a href="${pageContext.request.contextPath}/admin/propiedades" class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-x-lg"></i> Quitar filtro
            </a>
            <a href="${pageContext.request.contextPath}/admin/reportes" class="btn btn-outline-claro btn-sm">
                <i class="bi bi-bar-chart"></i> Volver al reporte
            </a>
        </div>
    </c:if>

    <c:if test="${not empty errorPropiedades}">
        <div class="alerta-error mb-4">${errorPropiedades}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty propiedades}">
            <div class="estado-vacio">
                <i class="bi bi-houses"></i>
                <c:choose>
                    <c:when test="${not empty idCiudadFiltro or not empty estadoFiltro}">
                        Ya no quedan propiedades que cumplan ese filtro.
                    </c:when>
                    <c:otherwise>
                        No hay propiedades registradas todavía.
                    </c:otherwise>
                </c:choose>
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-hogar">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Propiedad</th>
                                <th>Inmobiliaria</th>
                                <th>Ciudad / Tipo</th>
                                <th>Precio</th>
                                <th>Estado</th>
                                <th class="text-end">Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${propiedades}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/propiedad?id=${p.idPropiedad}"
                                           class="fw-semibold text-decoration-none" target="_blank">${p.titulo}</a>
                                        <div class="text-muted small">${p.matriculaInmobiliaria}</div>
                                    </td>
                                    <td>
                                        <div>${p.nombreInmobiliaria}</div>
                                        <div class="text-muted small">
                                            <c:if test="${not empty p.correoInmobiliaria}">
                                                <i class="bi bi-envelope"></i> ${p.correoInmobiliaria}<br>
                                            </c:if>
                                            <c:if test="${not empty p.telefonoInmobiliaria}">
                                                <i class="bi bi-telephone"></i> ${p.telefonoInmobiliaria}
                                            </c:if>
                                        </div>
                                    </td>
                                    <td>${p.nombreCiudad} / ${p.nombreTipo}</td>
                                    <td class="text-nowrap">$<fmt:formatNumber value="${p.precio}" pattern="#,##0"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${p.estado == 'disponible'}">
                                                <span class="badge bg-success">Disponible</span>
                                            </c:when>
                                            <c:when test="${p.estado == 'inactiva'}">
                                                <span class="badge bg-secondary">Inactiva</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-info text-dark text-capitalize">${p.estado}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-end">
                                        <form action="${pageContext.request.contextPath}/admin/propiedades" method="post" class="d-inline">
                                            <input type="hidden" name="idPropiedad" value="${p.idPropiedad}">
                                            <input type="hidden" name="idCiudad" value="${idCiudadFiltro}">
                                            <input type="hidden" name="estado" value="${estadoFiltro}">
                                            <input type="hidden" name="pagina" value="${paginaActual}">
                                            <c:choose>
                                                <c:when test="${p.estado == 'inactiva'}">
                                                    <input type="hidden" name="accion" value="reactivar">
                                                    <button type="submit" class="btn btn-coral btn-sm">Reactivar</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="hidden" name="accion" value="baja">
                                                    <button type="submit" class="btn btn-outline-secondary btn-sm"
                                                            data-confirmar="Vas a dar de baja «${fn:escapeXml(p.titulo)}»: dejará de aparecer en el catálogo público. ¿Continuar?">
                                                        Dar de baja
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jspf/paginacion.jsp" />
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
