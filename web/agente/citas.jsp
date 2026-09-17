<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="agente" />
    <jsp:param name="activo" value="citas" />
    <jsp:param name="titulo" value="Citas" />
    <jsp:param name="descripcion" value="Confirma o cancela las visitas que solicitan los clientes." />
</jsp:include>

    <c:if test="${sinInmobiliaria}">
        <jsp:include page="/WEB-INF/jspf/sin-inmobiliaria.jsp" />
    </c:if>

    <c:if test="${not empty errorCitas}">
        <div class="alerta-error mb-4">${errorCitas}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty citas}">
            <div class="estado-vacio">
                <i class="bi bi-calendar-check"></i>
                Todavía no tienes citas agendadas en ninguna de tus propiedades.
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-hogar">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Propiedad</th>
                                <th>Cliente</th>
                                <th>Fecha y hora</th>
                                <th>Estado</th>
                                <th class="text-end">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${citas}">
                                <tr>
                                    <td class="fw-semibold">${c.tituloPropiedad}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty c.nombresCliente}">${c.nombresCliente} ${c.apellidosCliente}</c:when>
                                            <c:otherwise>${c.correoCliente}</c:otherwise>
                                        </c:choose>
                                        <div class="text-muted small">
                                            <i class="bi bi-envelope"></i> ${c.correoCliente}
                                            <c:if test="${not empty c.telefonoCliente}"> · <i class="bi bi-telephone"></i> ${c.telefonoCliente}</c:if>
                                        </div>
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
                                        <c:if test="${not empty c.respuestaAgente}">
                                            <div class="text-muted small mt-1">"${c.respuestaAgente}"</div>
                                        </c:if>
                                    </td>
                                    <td class="text-end">
                                        <c:if test="${c.estado == 'pendiente'}">
                                            <form action="${pageContext.request.contextPath}/agente/citas" method="post" style="min-width:220px;">
                                                <input type="hidden" name="idCita" value="${c.idCita}">
                                                <textarea name="respuesta" class="form-control form-control-sm mb-2" rows="1"
                                                          style="resize:none;"
                                                          placeholder="Mensaje para el cliente (opcional)"></textarea>
                                                <div class="d-flex gap-1 justify-content-end">
                                                    <button type="submit" name="accion" value="confirmar" class="btn btn-coral btn-sm">Confirmar</button>
                                                    <button type="submit" name="accion" value="cancelar" class="btn btn-outline-secondary btn-sm"
                                                            data-confirmar="Vas a cancelar esta cita. El cliente lo verá en su panel. ¿Continuar?">
                                                        Cancelar
                                                    </button>
                                                </div>
                                            </form>
                                        </c:if>
                                        <c:if test="${c.estado == 'confirmada'}">
                                            <form action="${pageContext.request.contextPath}/agente/citas" method="post" style="min-width:220px;">
                                                <input type="hidden" name="idCita" value="${c.idCita}">
                                                <textarea name="respuesta" class="form-control form-control-sm mb-2" rows="1"
                                                          style="resize:none;"
                                                          placeholder="Mensaje para el cliente (opcional)"></textarea>
                                                <div class="d-flex gap-1 justify-content-end">
                                                    <button type="submit" name="accion" value="realizada" class="btn btn-outline-claro btn-sm">Marcar realizada</button>
                                                    <button type="submit" name="accion" value="cancelar" class="btn btn-outline-secondary btn-sm"
                                                            data-confirmar="Vas a cancelar esta cita ya confirmada. El cliente lo verá en su panel. ¿Continuar?">
                                                        Cancelar
                                                    </button>
                                                </div>
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
