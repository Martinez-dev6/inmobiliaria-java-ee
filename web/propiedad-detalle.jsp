<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${propiedad.titulo} — Hogar 360</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">Hogar 360</a>
        <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-outline-claro btn-sm">
            <i class="bi bi-arrow-left"></i> Volver al catálogo
        </a>
    </div>
</nav>

<div class="container py-5">

    <h2>${propiedad.titulo}</h2>
    <p class="text-muted">${propiedad.direccion}, ${propiedad.nombreCiudad} · ${propiedad.nombreTipo}</p>

    <div class="row mb-4">
        <c:choose>
            <c:when test="${empty imagenes}">
                <div class="col-12">
                    <div style="height:320px;background:#e0e0e0;border-radius:8px;"></div>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="img" items="${imagenes}" varStatus="fila">
                    <div class="${fila.first ? 'col-12 mb-3' : 'col-4 col-md-3 mb-3'}">
                        <img src="${pageContext.request.contextPath}/${img.urlImagen}"
                             style="width:100%;height:${fila.first ? '320px' : '110px'};object-fit:cover;border-radius:8px;"
                             alt="${propiedad.titulo}"
                             onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%27400%27 height=%27300%27%3E%3Crect width=%27400%27 height=%27300%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="row">
        <div class="col-md-8">
            <h4>$<fmt:formatNumber value="${propiedad.precio}" pattern="#,##0"/></h4>
            <c:if test="${not empty propiedad.areaM2}">
                <p class="text-muted">${propiedad.areaM2} m²</p>
            </c:if>
            <p>${propiedad.descripcion}</p>

            <c:if test="${not empty nombresCaracteristicas}">
                <h5 class="mt-4">Características</h5>
                <div>
                    <c:forEach var="nombre" items="${nombresCaracteristicas}">
                        <span class="badge bg-light text-dark border me-2 mb-2">${nombre}</span>
                    </c:forEach>
                </div>
            </c:if>
        </div>

        <div class="col-md-4">
            <div class="card shadow-sm p-3">
                <h6>Publica</h6>
                <p class="mb-1 fw-bold">${propiedad.nombreInmobiliaria}</p>
                <c:if test="${not empty propiedad.telefonoInmobiliaria}">
                    <p class="mb-0"><i class="bi bi-telephone"></i> ${propiedad.telefonoInmobiliaria}</p>
                </c:if>

                <c:if test="${not empty sessionScope.roles and sessionScope.roles.contains('Cliente')}">
                    <!-- Favorito -->
                    <form action="${pageContext.request.contextPath}/cliente/favoritos" method="post" class="mt-3">
                        <input type="hidden" name="idPropiedad" value="${propiedad.idPropiedad}">
                        <c:choose>
                            <c:when test="${esFavorita}">
                                <input type="hidden" name="accion" value="desmarcar">
                                <button type="submit" class="btn btn-outline-claro w-100">
                                    <i class="bi bi-heart-fill" style="color:#cc0033;"></i> Quitar de favoritos
                                </button>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="accion" value="marcar">
                                <button type="submit" class="btn btn-outline-claro w-100">
                                    <i class="bi bi-heart"></i> Marcar como favorito
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </form>

                    <!-- Agendar cita -->
                    <hr>
                    <h6>Agendar visita</h6>
                    <form action="${pageContext.request.contextPath}/cliente/citas" method="post">
                        <input type="hidden" name="idPropiedad" value="${propiedad.idPropiedad}">
                        <input type="datetime-local" class="form-control mb-2" name="fechaHora" required>
                        <button type="submit" class="btn btn-coral w-100 btn-sm">Agendar</button>
                    </form>

                    <!-- Radicar solicitud -->
                    <hr>
                    <h6>Radicar solicitud de compra/arriendo</h6>
                    <form action="${pageContext.request.contextPath}/cliente/solicitudes" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="idPropiedad" value="${propiedad.idPropiedad}">
                        <select class="form-select form-select-sm mb-2" name="tipoSolicitud" required>
                            <option value="">Tipo de solicitud...</option>
                            <option value="compra">Compra</option>
                            <option value="arriendo">Arriendo</option>
                        </select>
                        <textarea class="form-control form-control-sm mb-2" name="observaciones" rows="2"
                                  placeholder="Observaciones (opcional)"></textarea>
                        <label class="form-label small mb-1">Documentos de soporte (opcional)</label>
                        <input type="file" class="form-control form-control-sm mb-2" name="documentos" multiple>
                        <button type="submit" class="btn btn-outline-claro w-100 btn-sm">Radicar solicitud</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>
