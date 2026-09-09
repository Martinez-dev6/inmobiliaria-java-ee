<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi panel — Hogar 360</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">Hogar 360</a>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-claro btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container py-5">
    <h2 class="mb-1">
        <c:choose>
            <c:when test="${not empty perfil.nombres}">Hola, ${perfil.nombres}</c:when>
            <c:otherwise>Bienvenido</c:otherwise>
        </c:choose>
    </h2>
    <p class="text-muted mb-4">${sessionScope.correo}</p>

    <c:if test="${empty perfil}">
        <div class="alerta-error mb-4">
            Todavía no has completado tu perfil.
            <a href="${pageContext.request.contextPath}/cliente/perfil">Complétalo aquí</a>.
        </div>
    </c:if>

    <div class="row g-4">
        <div class="col-md-4">
            <div class="card shadow-sm p-4 h-100">
                <h5><i class="bi bi-person-circle"></i> Mi perfil</h5>
                <p class="text-muted small">Actualiza tus datos personales.</p>
                <a href="${pageContext.request.contextPath}/cliente/perfil" class="btn btn-outline-claro btn-sm mt-auto">Ir a mi perfil</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm p-4 h-100">
                <h5><i class="bi bi-search"></i> Buscar propiedades</h5>
                <p class="text-muted small">Explora el catálogo con filtros.</p>
                <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-outline-claro btn-sm mt-auto">Ver catálogo</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm p-4 h-100 bg-light">
                <h5><i class="bi bi-calendar-event"></i> Mis citas y solicitudes</h5>
                <p class="text-muted small">Próximamente — Sprint 3.</p>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>