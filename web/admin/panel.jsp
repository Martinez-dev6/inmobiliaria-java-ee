<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administrador — Hogar 360</title>
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
    <h2 class="mb-1">Panel de administración</h2>
    <p class="text-muted mb-4">${sessionScope.correo}</p>

    <div class="row g-4 mb-4">
        <div class="col-6 col-md-3">
            <div class="card shadow-sm p-3 text-center">
                <div class="fs-3 fw-bold">${totalUsuarios}</div>
                <div class="text-muted small">Usuarios totales</div>
            </div>
        </div>
        <div class="col-6 col-md-3">
            <div class="card shadow-sm p-3 text-center">
                <div class="fs-3 fw-bold">${totalClientes}</div>
                <div class="text-muted small">Clientes</div>
            </div>
        </div>
        <div class="col-6 col-md-3">
            <div class="card shadow-sm p-3 text-center">
                <div class="fs-3 fw-bold">${totalAgentes}</div>
                <div class="text-muted small">Agentes</div>
            </div>
        </div>
        <div class="col-6 col-md-3">
            <div class="card shadow-sm p-3 text-center">
                <div class="fs-3 fw-bold">${totalPropiedades}</div>
                <div class="text-muted small">Propiedades (todo el sistema)</div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <div class="col-md-4">
            <div class="card shadow-sm p-4 h-100">
                <h5><i class="bi bi-people"></i> Gestión de roles</h5>
                <p class="text-muted small">Asigna o revoca roles, activa/inactiva cuentas.</p>
                <a href="${pageContext.request.contextPath}/admin/roles" class="btn btn-coral btn-sm mt-auto">Ir a gestión de roles</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm p-4 h-100">
                <h5><i class="bi bi-bar-chart"></i> Reportes</h5>
                <p class="text-muted small">Propiedades por ciudad y estado.</p>
                <a href="${pageContext.request.contextPath}/admin/reportes" class="btn btn-outline-claro btn-sm mt-auto">Ver reportes</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm p-4 h-100">
                <h5><i class="bi bi-clipboard-data"></i> Auditoría</h5>
                <p class="text-muted small">Historial de eventos del sistema.</p>
                <a href="${pageContext.request.contextPath}/admin/auditoria" class="btn btn-outline-claro btn-sm mt-auto">Ver auditoría</a>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>
