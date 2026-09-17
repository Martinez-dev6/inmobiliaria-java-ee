<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="admin" />
    <jsp:param name="activo" value="auditoria" />
    <jsp:param name="titulo" value="Auditoría" />
    <jsp:param name="descripcion" value="Historial de eventos registrados por la aplicación, del más reciente al más antiguo." />
</jsp:include>

    <c:if test="${not empty errorAuditoria}">
        <div class="alerta-error mb-4">${errorAuditoria}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty registros}">
            <div class="estado-vacio">
                <i class="bi bi-clipboard-data"></i>
                No hay eventos de auditoría registrados todavía.
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-hogar">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Fecha</th>
                                <th>Usuario</th>
                                <th>Acción</th>
                                <th>Descripción</th>
                                <th class="text-end">Ir a</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${registros}">
                                <tr>
                                    <td class="text-nowrap"><fmt:formatDate value="${r.fechaEvento}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty r.correoUsuario}">${r.correoUsuario}</c:when>
                                            <c:otherwise><span class="text-muted">(usuario eliminado)</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><span class="badge bg-light text-dark border">${r.accion}</span></td>
                                    <td>${r.descripcion}</td>
                                    <td class="text-end">
                                        <%-- Cuando el evento menciona una propiedad, se ofrece el
                                             atajo a su ficha en vez de dejar buscando el id a mano. --%>
                                        <c:choose>
                                            <c:when test="${not empty r.idPropiedadRelacionada}">
                                                <a class="btn btn-outline-claro btn-sm text-nowrap"
                                                   href="${pageContext.request.contextPath}/propiedad?id=${r.idPropiedadRelacionada}"
                                                   target="_blank">
                                                    <i class="bi bi-box-arrow-up-right"></i> Ver propiedad
                                                </a>
                                            </c:when>
                                            <c:when test="${r.accion == 'cambio_rol'
                                                            or r.accion == 'activar_usuario'
                                                            or r.accion == 'desactivar_usuario'}">
                                                <a class="btn btn-outline-claro btn-sm text-nowrap"
                                                   href="${pageContext.request.contextPath}/admin/roles">
                                                    <i class="bi bi-people"></i> Ver roles
                                                </a>
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

            <jsp:include page="/WEB-INF/jspf/paginacion.jsp" />
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
