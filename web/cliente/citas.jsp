<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="cliente" />
    <jsp:param name="activo" value="citas" />
    <jsp:param name="titulo" value="Mis citas" />
    <jsp:param name="descripcion" value="Visitas que has agendado y la respuesta de cada inmobiliaria." />
    <jsp:param name="accionUrl" value="catalogo" />
    <jsp:param name="accionTexto" value="Buscar propiedades" />
    <jsp:param name="accionIcono" value="search" />
</jsp:include>

    <c:if test="${not empty errorCitas}">
        <div class="alerta-error mb-4">${errorCitas}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty citas}">
            <div class="estado-vacio">
                <i class="bi bi-calendar-event"></i>
                Todavía no has agendado ninguna cita.
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
                                <th>Inmobiliaria</th>
                                <th>Fecha y hora</th>
                                <th>Estado</th>
                                <th>Respuesta de la inmobiliaria</th>
                                <th class="text-end">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${citas}">
                                <tr>
                                    <td class="fw-semibold">${c.tituloPropiedad}</td>
                                    <%-- Con quien es la visita y como contactarlo: sin esto
                                         la reserva no decia quien iba a atenderla. --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty c.nombreInmobiliaria}">
                                                <div>${c.nombreInmobiliaria}</div>
                                                <c:if test="${not empty c.telefonoInmobiliaria}">
                                                    <div class="text-muted small text-nowrap">
                                                        <i class="bi bi-telephone"></i> ${c.telefonoInmobiliaria}
                                                    </div>
                                                </c:if>
                                                <c:if test="${not empty c.correoInmobiliaria}">
                                                    <div class="text-muted small">
                                                        <i class="bi bi-envelope"></i> ${c.correoInmobiliaria}
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise><span class="text-muted">—</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-nowrap"><fmt:formatDate value="${c.fechaHora}" pattern="dd/MM/yyyy HH:mm"/></td>
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
                                        <c:choose>
                                            <c:when test="${not empty c.respuestaAgente}">
                                                ${c.respuestaAgente}
                                                <div class="text-muted small"><fmt:formatDate value="${c.fechaRespuesta}" pattern="dd/MM/yyyy"/></div>
                                            </c:when>
                                            <c:otherwise><span class="text-muted">—</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-end">
                                        <c:if test="${c.estado == 'pendiente' or c.estado == 'confirmada'}">
                                            <form action="${pageContext.request.contextPath}/cliente/citas" method="post" class="d-inline"
                                                  data-confirmar="Vas a cancelar esta visita. ¿Continuar?">
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
            </div>
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
