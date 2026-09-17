<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="cliente" />
    <jsp:param name="activo" value="favoritos" />
    <jsp:param name="titulo" value="Mis favoritos" />
    <jsp:param name="descripcion" value="Las propiedades que has guardado para volver a verlas." />
    <jsp:param name="accionUrl" value="catalogo" />
    <jsp:param name="accionTexto" value="Buscar más" />
    <jsp:param name="accionIcono" value="search" />
</jsp:include>

    <c:if test="${not empty errorFavoritos}">
        <div class="alerta-error mb-4">${errorFavoritos}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty favoritas}">
            <div class="estado-vacio">
                <i class="bi bi-heart"></i>
                Todavía no has marcado ninguna propiedad como favorita.
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-coral btn-sm">Explorar el catálogo</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <c:forEach var="p" items="${favoritas}">
                    <div class="col-md-6 col-xl-4">
                        <div class="card h-100 border-0 shadow-sm rounded-4 overflow-hidden">
                            <c:choose>
                                <c:when test="${not empty p.urlMiniatura}">
                                    <img src="${pageContext.request.contextPath}/${p.urlMiniatura}" class="card-img-top"
                                         style="height:190px;object-fit:cover;" alt="${p.titulo}" loading="lazy"
                                         onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%27400%27 height=%27200%27%3E%3Crect width=%27400%27 height=%27200%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                                </c:when>
                                <c:otherwise>
                                    <div style="height:190px;background:#e0e0e0;"></div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${p.titulo}</h5>
                                <p class="card-text text-muted mb-1">${p.nombreCiudad} · ${p.nombreTipo}</p>
                                <p class="card-text fw-bold mb-2">$<fmt:formatNumber value="${p.precio}" pattern="#,##0"/></p>
                                <a href="${pageContext.request.contextPath}/propiedad?id=${p.idPropiedad}" class="btn btn-outline-claro w-100 mt-auto">Ver detalle</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
