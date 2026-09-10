<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Solicitudes recibidas — Hogar 360</title>

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
                                Cliente: ${s.correoCliente} · <fmt:formatDate value="${s.fechaSolicitud}" pattern="dd/MM/yyyy"/>
                            </p>
                            <c:if test="${not empty s.observaciones}">
                                <p class="small mb-1">"${s.observaciones}"</p>
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
                                    <span class="badge bg-warning text-dark mb-2">Pendiente</span><br>
                                    <form action="${pageContext.request.contextPath}/agente/solicitudes" method="post" class="d-inline">
                                        <input type="hidden" name="idSolicitud" value="${s.idSolicitud}">
                                        <input type="hidden" name="accion" value="aprobar">
                                        <button type="submit" class="btn btn-coral btn-sm">Aprobar</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/agente/solicitudes" method="post" class="d-inline">
                                        <input type="hidden" name="idSolicitud" value="${s.idSolicitud}">
                                        <input type="hidden" name="accion" value="rechazar">
                                        <button type="submit" class="btn btn-outline-secondary btn-sm">Rechazar</button>
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
