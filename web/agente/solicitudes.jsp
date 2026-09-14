<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Solicitudes recibidas — Hogar 360</title>
    <link rel="icon" href="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='22' fill='%231A2332'/%3E%3Cpath d='M50 18 L84 48 H74 V82 H26 V48 H16 Z' fill='%23FFB648'/%3E%3C/svg%3E">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/WEB-INF/jspf/navbar.jsp">
    <jsp:param name="navTipo" value="volver" />
    <jsp:param name="navDestino" value="agente/panel" />
    <jsp:param name="navTexto" value="Volver al panel" />
</jsp:include>

<div class="container py-5">
    <h2 class="mb-4">Solicitudes recibidas</h2>

    <c:if test="${not empty errorSolicitudes}">
        <div class="alerta-error mb-4">${errorSolicitudes}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty solicitudes}">
            <p class="text-muted">Todavía no has recibido solicitudes para tus propiedades.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="s" items="${solicitudes}">
                <div class="card shadow-sm p-3 mb-3">
                    <div class="d-flex justify-content-between align-items-start flex-wrap">
                        <div>
                            <h6 class="mb-1">${s.tituloPropiedad} — <span class="text-capitalize">${s.tipoSolicitud}</span></h6>
                            <p class="text-muted small mb-1">
                                Cliente:
                                <c:choose>
                                    <c:when test="${not empty s.nombresCliente}">${s.nombresCliente} ${s.apellidosCliente}</c:when>
                                    <c:otherwise>${s.correoCliente}</c:otherwise>
                                </c:choose>
                                · <i class="bi bi-envelope"></i> ${s.correoCliente}
                                <c:if test="${not empty s.telefonoCliente}"> · <i class="bi bi-telephone"></i> ${s.telefonoCliente}</c:if>
                                · <fmt:formatDate value="${s.fechaSolicitud}" pattern="dd/MM/yyyy"/>
                            </p>
                            <c:if test="${not empty s.observaciones}">
                                <p class="small mb-1">"${s.observaciones}"</p>
                            </c:if>
                            <c:if test="${not empty s.respuestaAgente}">
                                <p class="small mb-1"><strong>Tu respuesta:</strong> "${s.respuestaAgente}"</p>
                            </c:if>

                            <c:set var="documentos" value="${documentosPorSolicitud[s.idSolicitud]}"/>
                            <c:if test="${not empty documentos}">
                                <p class="small mb-0">
                                    Documentos:
                                    <c:forEach var="d" items="${documentos}" varStatus="fila">
                                        <a href="${pageContext.request.contextPath}/${d.urlArchivo}" target="_blank">Documento ${fila.count}</a><c:if test="${!fila.last}">, </c:if>
                                    </c:forEach>
                                </p>
                            </c:if>
                        </div>

                        <div class="text-end">
                            <c:choose>
                                <c:when test="${s.estado == 'aprobada'}">
                                    <span class="badge bg-success mb-2">Aprobada</span>
                                </c:when>
                                <c:when test="${s.estado == 'rechazada'}">
                                    <span class="badge bg-secondary mb-2">Rechazada</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-warning text-dark mb-2">Pendiente</span>
                                    <form action="${pageContext.request.contextPath}/agente/solicitudes" method="post" style="min-width:220px;">
                                        <input type="hidden" name="idSolicitud" value="${s.idSolicitud}">
                                        <textarea name="respuesta" class="form-control form-control-sm mb-2" rows="2"
                                                  style="resize:none;"
                                                  placeholder="Mensaje para el cliente (opcional)"></textarea>
                                        <div class="d-flex gap-2 justify-content-end">
                                            <button type="submit" name="accion" value="aprobar" class="btn btn-coral btn-sm">Aprobar</button>
                                            <button type="submit" name="accion" value="rechazar" class="btn btn-outline-secondary btn-sm">Rechazar</button>
                                        </div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>
