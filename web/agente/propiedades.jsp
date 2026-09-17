<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- Sin ficha de agencia el botón de publicar no puede funcionar, así que no se ofrece. --%>
<c:set var="accionNueva" value="${sinInmobiliaria ? '' : 'agente/propiedades?accion=nuevo'}" />

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="agente" />
    <jsp:param name="activo" value="propiedades" />
    <jsp:param name="titulo" value="Mis propiedades" />
    <jsp:param name="descripcion" value="Publica, edita y controla la disponibilidad de tu catálogo." />
    <jsp:param name="accionUrl" value="${accionNueva}" />
    <jsp:param name="accionTexto" value="Nueva propiedad" />
    <jsp:param name="accionIcono" value="plus-lg" />
</jsp:include>

    <c:if test="${sinInmobiliaria}">
        <jsp:include page="/WEB-INF/jspf/sin-inmobiliaria.jsp" />
    </c:if>

    <c:if test="${not empty errorPropiedad}">
        <div class="alerta-error mb-4">${errorPropiedad}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty propiedades}">
            <div class="estado-vacio">
                <i class="bi bi-houses"></i>
                Todavía no has publicado ninguna propiedad.
                <c:if test="${not sinInmobiliaria}">
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/agente/propiedades?accion=nuevo" class="btn btn-coral btn-sm">
                        <i class="bi bi-plus-lg"></i> Publicar la primera
                    </a>
                </div>
                </c:if>
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-hogar">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Propiedad</th>
                                <th>Ciudad / Tipo</th>
                                <th>Precio</th>
                                <th>Estado</th>
                                <th class="text-end">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${propiedades}">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center gap-3">
                                            <c:choose>
                                                <c:when test="${not empty p.urlMiniatura}">
                                                    <img src="${pageContext.request.contextPath}/${p.urlMiniatura}"
                                                         alt="${p.titulo}" loading="lazy"
                                                         style="width:56px;height:56px;object-fit:cover;border-radius:10px;flex:0 0 auto;"
                                                         onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%2760%27 height=%2760%27%3E%3Crect width=%2760%27 height=%2760%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                                                </c:when>
                                                <c:otherwise>
                                                    <div style="width:56px;height:56px;background:var(--claro);border-radius:10px;flex:0 0 auto;"></div>
                                                </c:otherwise>
                                            </c:choose>
                                            <div>
                                                <div class="fw-semibold">${p.titulo}</div>
                                                <div class="text-muted small">${p.matriculaInmobiliaria}</div>
                                            </div>
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
                                        <c:if test="${p.destacada}">
                                            <span class="badge" style="background-color:var(--ambar);color:var(--oscuro);">Destacada</span>
                                        </c:if>
                                    </td>
                                    <td class="text-end text-nowrap">
                                        <a href="${pageContext.request.contextPath}/agente/propiedades?accion=editar&id=${p.idPropiedad}"
                                           class="btn btn-outline-secondary btn-sm">
                                            <i class="bi bi-pencil"></i> Editar
                                        </a>

                                        <form action="${pageContext.request.contextPath}/agente/propiedades" method="post" class="d-inline">
                                            <input type="hidden" name="idPropiedad" value="${p.idPropiedad}">
                                            <c:choose>
                                                <c:when test="${p.estado == 'disponible'}">
                                                    <input type="hidden" name="accion" value="baja">
                                                    <button type="submit" class="btn btn-outline-secondary btn-sm"
                                                            data-confirmar="Vas a dar de baja «${fn:escapeXml(p.titulo)}»: dejará de aparecer en el catálogo. ¿Continuar?">
                                                        Dar de baja
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="hidden" name="accion" value="reactivar">
                                                    <button type="submit" class="btn btn-outline-claro btn-sm">Reactivar</button>
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
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
