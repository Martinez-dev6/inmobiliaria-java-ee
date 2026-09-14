<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error del servidor — Hogar 360</title>
    <link rel="icon" href="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='22' fill='%231A2332'/%3E%3Cpath d='M50 18 L84 48 H74 V82 H26 V48 H16 Z' fill='%23FFB648'/%3E%3C/svg%3E">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body style="min-height:100vh; display:flex; align-items:center; justify-content:center; text-align:center; font-family:'Inter',sans-serif;">
    <div>
        <p class="fw-bold fs-4 mb-4">
            Hogar <span style="color:var(--azul-claro);">3</span><span style="color:var(--ambar);">6</span><span style="color:var(--oscuro);">0</span>
        </p>
        <h1>Algo salió mal</h1>
        <p class="text-muted mb-4">Ocurrió un error inesperado en el servidor. Intenta de nuevo en unos minutos.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-coral">
            <i class="bi bi-arrow-left"></i> Volver al inicio
        </a>
    </div>
    <script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>
