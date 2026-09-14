<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis solicitudes — Hogar 360</title>
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
    <h2 class="mb-4">Mis solicitudes</h2>

    <c:if test="${not empty errorSolicitudes}">
        <div class="alerta-error mb-4">${errorSolicitudes}</div>
    </c:if>
    <c:if test="${not empty mensajeSolicitudes}">
        <div class="alerta-exito mb-4">${mensajeSolicitudes}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty solicitudes}">
            <p class="text-muted">Todavía no has radicado ninguna solicitud. Ve a la ficha de una propiedad en el
                <a href="${pageContext.request.contextPath}/catalogo">catálogo</a> para radicar una.</p>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Propiedad</th>
                            <th>Tipo</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Observaciones</th>
                            <th>Respuesta de la inmobiliaria</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${solicitudes}">
                            <tr>
                                <td>${s.tituloPropiedad}</td>
                                <td class="text-capitalize">${s.tipoSolicitud}</td>
                                <td><fmt:formatDate value="${s.fechaSolicitud}" pattern="dd/MM/yyyy"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.estado == 'aprobada'}">
                                            <span class="badge bg-success">Aprobada</span>
                                        </c:when>
                                        <c:when test="${s.estado == 'rechazada'}">
                                            <span class="badge bg-secondary">Rechazada</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning text-dark">Pendiente</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${s.observaciones}</td>
                                <td>
                                    <c:if test="${not empty s.respuestaAgente}">
                                        ${s.respuestaAgente}
                                        <div class="text-muted small"><fmt:formatDate value="${s.fechaRespuesta}" pattern="dd/MM/yyyy"/></div>
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
