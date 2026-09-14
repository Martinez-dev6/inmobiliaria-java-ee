<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis citas — Hogar 360</title>
    <link rel="icon" href="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='22' fill='%231A2332'/%3E%3Cpath d='M50 18 L84 48 H74 V82 H26 V48 H16 Z' fill='%23FFB648'/%3E%3C/svg%3E">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/WEB-INF/jspf/navbar.jsp">
    <jsp:param name="navTipo" value="volver" />
    <jsp:param name="navDestino" value="cliente/panel" />
    <jsp:param name="navTexto" value="Volver al panel" />
</jsp:include>

<div class="container py-5">
    <h2 class="mb-4">Mis citas</h2>

    <c:if test="${not empty errorCitas}">
        <div class="alerta-error mb-4">${errorCitas}</div>
    </c:if>
    <c:if test="${not empty mensajeCitas}">
        <div class="alerta-exito mb-4">${mensajeCitas}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty citas}">
            <p class="text-muted">Todavía no has agendado ninguna cita. Ve a la ficha de una propiedad en el
                <a href="${pageContext.request.contextPath}/catalogo">catálogo</a> para agendar una visita.</p>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Propiedad</th>
                            <th>Fecha y hora</th>
                            <th>Estado</th>
                            <th>Respuesta de la inmobiliaria</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${citas}">
                            <tr>
                                <td>${c.tituloPropiedad}</td>
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
                                    <c:if test="${not empty c.respuestaAgente}">
                                        ${c.respuestaAgente}
                                        <div class="text-muted small"><fmt:formatDate value="${c.fechaRespuesta}" pattern="dd/MM/yyyy"/></div>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${c.estado == 'pendiente' or c.estado == 'confirmada'}">
                                        <form action="${pageContext.request.contextPath}/cliente/citas" method="post"
                                              onsubmit="return confirm('¿Cancelar esta cita?');">
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
