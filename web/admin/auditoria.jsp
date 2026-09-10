<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auditoría — Hogar 360</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">Hogar 360</a>
        <a href="${pageContext.request.contextPath}/admin/panel" class="btn btn-outline-claro btn-sm">
            <i class="bi bi-arrow-left"></i> Volver al panel
        </a>
    </div>
</nav>

<div class="container py-5">
    <h2 class="mb-1">Auditoría del sistema</h2>
    <p class="text-muted mb-4">Últimos 200 eventos registrados.</p>

    <c:if test="${not empty errorAuditoria}">
        <div class="alerta-error mb-4">${errorAuditoria}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty registros}">
            <p class="text-muted">No hay eventos de auditoría registrados todavía.</p>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table align-middle table-sm">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Usuario</th>
                            <th>Acción</th>
                            <th>Descripción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${registros}">
                            <tr>
                                <td><fmt:formatDate value="${r.fechaEvento}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty r.correoUsuario}">${r.correoUsuario}</c:when>
                                        <c:otherwise><span class="text-muted">(usuario eliminado)</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${r.accion}</td>
                                <td>${r.descripcion}</td>
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
