<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Roles — Hogar 360</title>
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
    <h2 class="mb-4">Gestión de roles de usuario</h2>

    <c:if test="${not empty mensaje}">
        <div class="alerta-error mb-4" style="background:#e3f2ff; color:#0d47a1; max-width:600px;">${mensaje}</div>
    </c:if>

    <div class="table-responsive">
    <table class="table table-bordered" style="max-width: 900px;">
        <thead>
            <tr>
                <th>Correo</th>
                <th>Estado</th>
                <c:forEach var="rolCol" items="${todosLosRoles}">
                    <th>${rolCol}</th>
                </c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${usuarios}">
                <tr>
                    <td>${u.correo}</td>
                    <td>${u.activo ? 'Activo' : 'Inactivo'}</td>
                    <c:forEach var="rolCol" items="${todosLosRoles}">
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${u.roles.contains(rolCol)}">
                                    <form action="${pageContext.request.contextPath}/admin/roles" method="post" style="display:inline;">
                                        <input type="hidden" name="accion" value="revocar">
                                        <input type="hidden" name="idUsuario" value="${u.idUsuario}">
                                        <input type="hidden" name="rol" value="${rolCol}">
                                        <button type="submit" class="btn btn-sm btn-outline-secondary">Quitar</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.request.contextPath}/admin/roles" method="post" style="display:inline;">
                                        <input type="hidden" name="accion" value="asignar">
                                        <input type="hidden" name="idUsuario" value="${u.idUsuario}">
                                        <input type="hidden" name="rol" value="${rolCol}">
                                        <button type="submit" class="btn btn-sm btn-coral">Asignar</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>