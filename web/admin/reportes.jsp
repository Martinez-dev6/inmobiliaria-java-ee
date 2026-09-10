<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte de propiedades — Hogar 360</title>

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
    <h2 class="mb-1">Propiedades por ciudad y estado</h2>
    <p class="text-muted mb-4">Consulta de agregación (GROUP BY / HAVING) sobre toda la base de propiedades.</p>

    <c:if test="${not empty errorReporte}">
        <div class="alerta-error mb-4">${errorReporte}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty filas}">
            <p class="text-muted">No hay datos para mostrar.</p>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Ciudad</th>
                            <th>Estado</th>
                            <th>Total de propiedades</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="fila" items="${filas}">
                            <tr>
                                <td>${fila.nombreCiudad}</td>
                                <td class="text-capitalize">${fila.estado}</td>
                                <td><strong>${fila.total}</strong></td>
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
