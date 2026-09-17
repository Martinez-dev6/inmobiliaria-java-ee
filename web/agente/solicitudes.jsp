<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="agente" />
    <jsp:param name="activo" value="solicitudes" />
    <jsp:param name="titulo" value="Solicitudes" />
    <jsp:param name="descripcion" value="Revisa los soportes del cliente y responde aprobando o rechazando." />
</jsp:include>

    <c:if test="${sinInmobiliaria}">
        <jsp:include page="/WEB-INF/jspf/sin-inmobiliaria.jsp" />
    </c:if>

    <c:if test="${not empty errorSolicitudes}">
        <div class="alerta-error mb-4">${errorSolicitudes}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty solicitudes}">
            <div class="estado-vacio">
                <i class="bi bi-file-earmark-text"></i>
                Todavía no has recibido solicitudes para tus propiedades.
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="s" items="${solicitudes}">
                <div class="tarjeta-hogar">
                    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                        <div>
                            <h6 class="mb-1">
                                ${s.tituloPropiedad}
                                <span class="badge bg-light text-dark border text-capitalize ms-1">${s.tipoSolicitud}</span>
                            </h6>
                            <p class="text-muted small mb-2">
                                <c:choose>
                                    <c:when test="${not empty s.nombresCliente}">${s.nombresCliente} ${s.apellidosCliente}</c:when>
                                    <c:otherwise>${s.correoCliente}</c:otherwise>
                                </c:choose>
                                · <i class="bi bi-envelope"></i> ${s.correoCliente}
                                <c:if test="${not empty s.telefonoCliente}"> · <i class="bi bi-telephone"></i> ${s.telefonoCliente}</c:if>
                                · <i class="bi bi-calendar3"></i> <fmt:formatDate value="${s.fechaSolicitud}" pattern="dd/MM/yyyy"/>
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
                                    <i class="bi bi-paperclip"></i>
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
                                    <form action="${pageContext.request.contextPath}/agente/solicitudes" method="post" style="min-width:240px;">
                                        <input type="hidden" name="idSolicitud" value="${s.idSolicitud}">
                                        <textarea name="respuesta" class="form-control form-control-sm mb-2" rows="2"
                                                  style="resize:none;"
                                                  placeholder="Mensaje para el cliente (opcional)"></textarea>
                                        <div class="d-flex gap-2 justify-content-end">
                                            <button type="submit" name="accion" value="aprobar" class="btn btn-coral btn-sm">Aprobar</button>
                                            <button type="submit" name="accion" value="rechazar" class="btn btn-outline-secondary btn-sm"
                                                    data-confirmar="Vas a rechazar esta solicitud. El cliente verá tu respuesta y no podrás deshacerlo. ¿Continuar?">
                                                Rechazar
                                            </button>
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

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
