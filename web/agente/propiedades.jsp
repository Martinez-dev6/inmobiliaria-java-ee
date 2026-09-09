<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis propiedades — Hogar 360</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<div class="container py-5">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Mis propiedades</h2>
        <div>
            <a href="${pageContext.request.contextPath}/agente/panel" class="btn btn-outline-claro btn-sm">
                <i class="bi bi-arrow-left"></i> Volver al panel
            </a>
            <a href="${pageContext.request.contextPath}/agente/propiedades?accion=nuevo" class="btn btn-coral btn-sm">
                <i class="bi bi-plus-lg"></i> Nueva propiedad
            </a>
        </div>
    </div>

    <c:if test="${not empty errorPropiedad}">
        <div class="alerta-error mb-3">${errorPropiedad}</div>
    </c:if>
    <c:if test="${not empty mensaje}">
        <div class="alerta-exito mb-3">${mensaje}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty propiedades}">
            <p class="text-muted">Todavía no has publicado ninguna propiedad.</p>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Foto</th>
                            <th>Matrícula</th>
                            <th>Título</th>
                            <th>Ciudad</th>
                            <th>Tipo</th>
                            <th>Precio</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${propiedades}">
                                                        <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty p.urlMiniatura}">
                                            <img src="${pageContext.request.contextPath}/${p.urlMiniatura}"
                                                 alt="${p.titulo}"
                                                 style="width:60px;height:60px;object-fit:cover;border-radius:6px;"
                                                 onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%2760%27 height=%2760%27%3E%3Crect width=%2760%27 height=%2760%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                                        </c:when>
                                        <c:otherwise>
                                            <div style="width:60px;height:60px;background:#e0e0e0;border-radius:6px;"></div>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${p.matriculaInmobiliaria}</td>
                                <td>${p.titulo}</td>
                                <td>${p.nombreCiudad}</td>
                                <td>${p.nombreTipo}</td>
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
                                            <span class="badge bg-info text-dark">${p.estado}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/agente/propiedades?accion=editar&id=${p.idPropiedad}"
                                       class="btn btn-outline-secondary btn-sm">Editar</a>

                                    <form action="${pageContext.request.contextPath}/agente/propiedades" method="post" class="d-inline">
                                        <input type="hidden" name="idPropiedad" value="${p.idPropiedad}">
                                        <c:choose>
                                            <c:when test="${p.estado == 'disponible'}">
                                                <input type="hidden" name="accion" value="baja">
                                                <button type="submit" class="btn btn-outline-secondary btn-sm">Dar de baja</button>
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
        </c:otherwise>
    </c:choose>

</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>