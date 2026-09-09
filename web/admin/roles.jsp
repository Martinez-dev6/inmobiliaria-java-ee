<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Roles — Hogar 360</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body style="padding: 30px;">

    <h1>Gestión de roles de usuario</h1>
    <p><a href="${pageContext.request.contextPath}/admin/panel">&larr; Volver al panel</a></p>

    <c:if test="${not empty mensaje}">
        <div class="alerta-error" style="background:#e3f2ff; color:#0d47a1; max-width:600px;">${mensaje}</div>
    </c:if>

    <table class="table table-bordered" style="margin-top:20px; max-width: 900px;">
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
    <script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>