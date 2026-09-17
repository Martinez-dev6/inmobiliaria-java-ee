<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catálogo de propiedades — Hogar 360</title>
    <link rel="icon" href="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='22' fill='%231A2332'/%3E%3Cpath d='M50 18 L84 48 H74 V82 H26 V48 H16 Z' fill='%23FFB648'/%3E%3C/svg%3E">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body style="background:#F6F8FC;">

<jsp:include page="/WEB-INF/jspf/navbar.jsp">
    <jsp:param name="navTipo" value="publico" />
    <jsp:param name="navActivo" value="catalogo" />
</jsp:include>

<div class="container py-4">

    <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

    <div class="catalogo-columnas">

        <div>
            <div class="d-flex flex-wrap justify-content-between align-items-end gap-2 mb-3">
                <div>
                    <h2 class="mb-1">Catálogo de propiedades</h2>
                    <p class="text-muted mb-0">
                        <c:choose>
                            <c:when test="${totalRegistros > 0}">${totalRegistros} propiedad(es) disponible(s) con los filtros actuales.</c:when>
                            <c:otherwise>Filtra por ciudad, tipo y presupuesto para encontrar tu próximo hogar.</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>

            <c:if test="${not empty errorCatalogo}">
                <div class="alerta-error mb-4">${errorCatalogo}</div>
            </c:if>

            <c:choose>
                <c:when test="${empty propiedades}">
                    <div class="estado-vacio">
                        <i class="bi bi-search"></i>
                        No se encontraron propiedades con esos filtros.
                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-outline-claro btn-sm">Ver todo el catálogo</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row g-4">
                        <c:forEach var="p" items="${propiedades}">
                            <div class="col-sm-6 col-xl-4">
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
                                        <span class="badge mb-2 align-self-start" style="background-color:var(--azul);">${p.nombreTipo}</span>
                                        <h5 class="card-title">${p.titulo}</h5>
                                        <p class="card-text text-muted mb-1"><i class="bi bi-geo-alt"></i> ${p.nombreCiudad}</p>
                                        <p class="card-text fw-bold fs-5 mb-1">$<fmt:formatNumber value="${p.precio}" pattern="#,##0"/></p>
                                        <p class="card-text small text-muted">Publica: ${p.nombreInmobiliaria}</p>
                                        <a href="${pageContext.request.contextPath}/propiedad?id=${p.idPropiedad}" class="btn btn-outline-claro w-100 mt-auto">Ver detalle</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <jsp:include page="/WEB-INF/jspf/paginacion.jsp" />
                </c:otherwise>
            </c:choose>
        </div>

        <%-- Rail de filtros: se queda fijo al desplazar y en móvil pasa arriba. --%>
        <aside>
            <form method="get" action="${pageContext.request.contextPath}/catalogo" class="filtros-rail">
                <div class="d-flex justify-content-between align-items-center mb-1">
                    <h2>Filtros</h2>
                    <c:if test="${not filtro.vacio}">
                        <a href="${pageContext.request.contextPath}/catalogo" class="small text-decoration-none" style="color:var(--coral);">Limpiar todo</a>
                    </c:if>
                </div>

                <div class="filtros-grupo">
                    <label class="form-label" for="filtroCiudad">Ciudad</label>
                    <select class="form-select" id="filtroCiudad" name="idCiudad">
                        <option value="">Todas las ciudades</option>
                        <c:forEach var="c" items="${ciudades}">
                            <option value="${c.idCiudad}" ${c.idCiudad == filtro.idCiudad ? 'selected' : ''}>${c.nombreCiudad}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="filtros-grupo">
                    <label class="form-label" for="filtroTipo">Tipo de propiedad</label>
                    <select class="form-select" id="filtroTipo" name="idTipoPropiedad">
                        <option value="">Todos los tipos</option>
                        <c:forEach var="t" items="${tipos}">
                            <option value="${t.idTipoPropiedad}" ${t.idTipoPropiedad == filtro.idTipoPropiedad ? 'selected' : ''}>${t.nombreTipo}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="filtros-grupo">
                    <label class="form-label" for="precioMin">Presupuesto</label>
                    <div class="campo-moneda mb-2">
                        <span class="simbolo">$</span>
                        <input type="text" inputmode="numeric" data-moneda class="form-control"
                               id="precioMin" name="precioMin" placeholder="Mínimo" value="${precioMinTexto}">
                    </div>
                    <div class="campo-moneda">
                        <span class="simbolo">$</span>
                        <input type="text" inputmode="numeric" data-moneda class="form-control"
                               id="precioMax" name="precioMax" placeholder="Máximo" value="${precioMaxTexto}">
                    </div>
                </div>

                <div class="filtros-grupo">
                    <label class="form-label" for="areaMin">Área mínima (m²)</label>
                    <input type="number" min="0" step="1" class="form-control"
                           id="areaMin" name="areaMin" placeholder="Ej. 80" value="${areaMinTexto}">
                </div>

                <c:if test="${not empty caracteristicas}">
                    <div class="filtros-grupo">
                        <span class="form-label d-block">Características</span>
                        <div class="lista-casillas">
                            <c:forEach var="car" items="${caracteristicas}">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="caracteristicas"
                                           value="${car.idCaracteristica}" id="fc${car.idCaracteristica}"
                                           <c:if test="${car.seleccionada}">checked</c:if>>
                                    <label class="form-check-label" for="fc${car.idCaracteristica}">${car.nombreCaracteristica}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>

                <div class="filtros-grupo">
                    <label class="form-label" for="orden">Ordenar por</label>
                    <select class="form-select" id="orden" name="orden">
                        <option value="RECIENTES" ${filtro.orden == 'RECIENTES' ? 'selected' : ''}>Más recientes</option>
                        <option value="PRECIO_ASC" ${filtro.orden == 'PRECIO_ASC' ? 'selected' : ''}>Precio: menor a mayor</option>
                        <option value="PRECIO_DESC" ${filtro.orden == 'PRECIO_DESC' ? 'selected' : ''}>Precio: mayor a menor</option>
                        <option value="AREA_DESC" ${filtro.orden == 'AREA_DESC' ? 'selected' : ''}>Área: mayor primero</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-coral w-100 mt-4">
                    <i class="bi bi-search"></i> Aplicar filtros
                </button>
            </form>
        </aside>
    </div>

</div>

<%-- Sin el bundle de Bootstrap el botón de hamburguesa del navbar no despliega
     nada en pantallas estrechas: el menú superior quedaba inservible. --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/moneda.js"></script>
<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>
