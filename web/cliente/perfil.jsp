<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi perfil — Hogar 360</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<div class="container py-5" style="max-width: 640px;">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Mi perfil</h2>
        <a href="${pageContext.request.contextPath}/cliente/panel.jsp" class="btn btn-outline-claro btn-sm">
            <i class="bi bi-arrow-left"></i> Volver al panel
        </a>
    </div>

    <c:if test="${not empty errorPerfil}">
        <div class="alerta-error mb-3">${errorPerfil}</div>
    </c:if>
    <c:if test="${not empty mensajeExito}">
        <div class="alerta-exito mb-3">${mensajeExito}</div>
    </c:if>

    <div class="card shadow-sm p-4">
        <form action="${pageContext.request.contextPath}/cliente/perfil" method="post" novalidate>

            <div class="mb-3">
                <label for="nombres" class="form-label">Nombres</label>
                <input type="text" class="form-control" id="nombres" name="nombres" required
                       value="${perfil.nombres}">
            </div>

            <div class="mb-3">
                <label for="apellidos" class="form-label">Apellidos</label>
                <input type="text" class="form-control" id="apellidos" name="apellidos" required
                       value="${perfil.apellidos}">
            </div>

            <div class="mb-3">
                <label for="documento" class="form-label">Documento</label>
                <input type="text" class="form-control" id="documento" name="documento" required
                       value="${perfil.documento}">
            </div>

            <div class="mb-3">
                <label for="telefono" class="form-label">Teléfono</label>
                <input type="text" class="form-control" id="telefono" name="telefono"
                       value="${perfil.telefono}">
            </div>

            <div class="mb-4">
                <label for="direccion" class="form-label">Dirección</label>
                <input type="text" class="form-control" id="direccion" name="direccion"
                       value="${perfil.direccion}">
            </div>

            <button type="submit" class="btn btn-coral w-100">Guardar cambios</button>
        </form>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>