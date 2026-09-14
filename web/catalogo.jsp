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
<body>

<jsp:include page="/WEB-INF/jspf/navbar.jsp">
    <jsp:param name="navTipo" value="publico" />
    <jsp:param name="navActivo" value="catalogo" />
</jsp:include>

<div class="container py-5">

    <h2 class="mb-4">Catálogo de propiedades</h2>

    <form method="get" action="${pageContext.request.contextPath}/catalogo" class="row g-3 mb-5">
        <div class="col-md-3">
            <label class="form-label">Ciudad</label>
            <select class="form-select" name="idCiudad">
                <option value="">Todas</option>
                <c:forEach var="c" items="${ciudades}">
                    <option value="${c.idCiudad}" ${c.idCiudad == idCiudadSeleccionada ? 'selected' : ''}>${c.nombreCiudad}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-3">
            <label class="form-label">Tipo</label>
            <select class="form-select" name="idTipoPropiedad">
                <option value="">Todos</option>
                <c:forEach var="t" items="${tipos}">
                    <option value="${t.idTipoPropiedad}" ${t.idTipoPropiedad == idTipoSeleccionado ? 'selected' : ''}>${t.nombreTipo}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <label class="form-label">Precio mín.</label>
            <input type="number" class="form-control" name="precioMin" value="${precioMinTexto}">
        </div>
        <div class="col-md-2">
            <label class="form-label">Precio máx.</label>
            <input type="number" class="form-control" name="precioMax" value="${precioMaxTexto}">
        </div>
        <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-coral w-100">Buscar</button>
        </div>
    </form>

    <c:if test="${not empty errorCatalogo}">
        <div class="alerta-error mb-4">${errorCatalogo}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty propiedades}">
            <p class="text-muted">No se encontraron propiedades con esos filtros.</p>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <c:forEach var="p" items="${propiedades}">
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm">
                            <c:choose>
                                <c:when test="${not empty p.urlMiniatura}">
                                    <img src="${pageContext.request.contextPath}/${p.urlMiniatura}" class="card-img-top"
                                         style="height:200px;object-fit:cover;" alt="${p.titulo}"
                                         onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%27400%27 height=%27200%27%3E%3Crect width=%27400%27 height=%27200%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                                </c:when>
                                <c:otherwise>
                                    <div style="height:200px;background:#e0e0e0;"></div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <span class="badge mb-2" style="background-color:var(--azul);">${p.nombreTipo}</span>
                                <h5 class="card-title">${p.titulo}</h5>
                                <p class="card-text text-muted mb-1">${p.nombreCiudad}</p>
                                <p class="card-text fw-bold mb-1">$<fmt:formatNumber value="${p.precio}" pattern="#,##0"/></p>
                                <p class="card-text small text-muted">Publica: ${p.nombreInmobiliaria}</p>
                                <a href="${pageContext.request.contextPath}/propiedad?id=${p.idPropiedad}" class="btn btn-outline-claro btn-sm">Ver detalle</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>