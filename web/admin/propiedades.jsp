<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de propiedades — Hogar 360</title>
    <link rel="icon" href="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='22' fill='%231A2332'/%3E%3Cpath d='M50 18 L84 48 H74 V82 H26 V48 H16 Z' fill='%23FFB648'/%3E%3C/svg%3E">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/WEB-INF/jspf/navbar.jsp">
    <jsp:param name="navTipo" value="volver" />
    <jsp:param name="navDestino" value="admin/panel" />
    <jsp:param name="navTexto" value="Volver al panel" />
</jsp:include>

<div class="container py-5">
    <h2 class="mb-1">Gestión de propiedades</h2>
    <p class="text-muted mb-4">Todas las propiedades publicadas por todas las inmobiliarias del sistema.</p>

    <c:if test="${not empty errorPropiedades}">
        <div class="alerta-error mb-4">${errorPropiedades}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty propiedades}">
            <p class="text-muted">No hay propiedades registradas todavía.</p>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Propiedad</th>
                            <th>Inmobiliaria</th>
                            <th>Ciudad / Tipo</th>
                            <th>Precio</th>
                            <th>Estado</th>
                            <th>Acción</th>
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
                                <td>$<fmt:formatNumber value="${p.precio}" pattern="#,##0"/></td>
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
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/propiedades" method="post" class="d-inline">
                                        <input type="hidden" name="idPropiedad" value="${p.idPropiedad}">
                                        <c:choose>
                                            <c:when test="${p.estado == 'inactiva'}">
                                                <input type="hidden" name="accion" value="reactivar">
                                                <button type="submit" class="btn btn-coral btn-sm">Reactivar</button>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="hidden" name="accion" value="baja">
                                                <button type="submit" class="btn btn-outline-secondary btn-sm">Dar de baja</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>
