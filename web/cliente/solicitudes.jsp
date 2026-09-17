<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="cliente" />
    <jsp:param name="activo" value="solicitudes" />
    <jsp:param name="titulo" value="Mis solicitudes" />
    <jsp:param name="descripcion" value="Solicitudes de compra o arriendo que has radicado y cómo respondió cada inmobiliaria." />
    <jsp:param name="accionUrl" value="catalogo" />
    <jsp:param name="accionTexto" value="Buscar propiedades" />
    <jsp:param name="accionIcono" value="search" />
</jsp:include>

    <c:if test="${not empty errorSolicitudes}">
        <div class="alerta-error mb-4">${errorSolicitudes}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty solicitudes}">
            <div class="estado-vacio">
                <i class="bi bi-file-earmark-text"></i>
                Todavía no has radicado ninguna solicitud.
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-coral btn-sm">Ir al catálogo</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-hogar">
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
                                    <td class="fw-semibold">${s.tituloPropiedad}</td>
                                    <td class="text-capitalize">${s.tipoSolicitud}</td>
                                    <td class="text-nowrap"><fmt:formatDate value="${s.fechaSolicitud}" pattern="dd/MM/yyyy"/></td>
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
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty s.observaciones}">${s.observaciones}</c:when>
                                            <c:otherwise><span class="text-muted">—</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty s.respuestaAgente}">
                                                ${s.respuestaAgente}
                                                <div class="text-muted small"><fmt:formatDate value="${s.fechaRespuesta}" pattern="dd/MM/yyyy"/></div>
                                            </c:when>
                                            <c:otherwise><span class="text-muted">—</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
