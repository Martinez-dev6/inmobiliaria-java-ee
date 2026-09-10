<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Citas de mis propiedades — Hogar 360</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">Hogar 360</a>
        <a href="${pageContext.request.contextPath}/agente/panel" class="btn btn-outline-claro btn-sm">
            <i class="bi bi-arrow-left"></i> Volver al panel
        </a>
    </div>
</nav>

<div class="container py-5">
    <h2 class="mb-4">Citas de mis propiedades</h2>

    <c:if test="${not empty errorCitas}">
        <div class="alerta-error mb-4">${errorCitas}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty citas}">
            <p class="text-muted">Todavía no tienes citas agendadas en ninguna de tus propiedades.</p>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Propiedad</th>
                            <th>Cliente</th>
                            <th>Fecha y hora</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${citas}">
                            <tr>
                                <td>${c.tituloPropiedad}</td>
                                <td>${c.correoCliente}</td>
                                <td><fmt:formatDate value="${c.fechaHora}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${c.estado == 'confirmada'}">
                                            <span class="badge bg-success">Confirmada</span>
                                        </c:when>
                                        <c:when test="${c.estado == 'cancelada'}">
                                            <span class="badge bg-secondary">Cancelada</span>
                                        </c:when>
                                        <c:when test="${c.estado == 'realizada'}">
                                            <span class="badge bg-info text-dark">Realizada</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning text-dark">Pendiente</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${c.estado == 'pendiente'}">
                                        <form action="${pageContext.request.contextPath}/agente/citas" method="post" class="d-inline">
                                            <input type="hidden" name="idCita" value="${c.idCita}">
                                            <input type="hidden" name="accion" value="confirmar">
                                            <button type="submit" class="btn btn-outline-claro btn-sm">Confirmar</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/agente/citas" method="post" class="d-inline">
                                            <input type="hidden" name="idCita" value="${c.idCita}">
                                            <input type="hidden" name="accion" value="cancelar">
                                            <button type="submit" class="btn btn-outline-secondary btn-sm">Cancelar</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${c.estado == 'confirmada'}">
                                        <form action="${pageContext.request.contextPath}/agente/citas" method="post" class="d-inline">
                                            <input type="hidden" name="idCita" value="${c.idCita}">
                                            <input type="hidden" name="accion" value="realizada">
                                            <button type="submit" class="btn btn-outline-claro btn-sm">Marcar realizada</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/agente/citas" method="post" class="d-inline">
                                            <input type="hidden" name="idCita" value="${c.idCita}">
                                            <input type="hidden" name="accion" value="cancelar">
                                            <button type="submit" class="btn btn-outline-secondary btn-sm">Cancelar</button>
                                        </form>
                                    </c:if>
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
