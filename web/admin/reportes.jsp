<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="admin" />
    <jsp:param name="activo" value="reportes" />
    <jsp:param name="titulo" value="Propiedades por ciudad" />
    <jsp:param name="descripcion" value="Cuántas publicaciones hay en cada ciudad y en qué estado. Entra a cualquier grupo para revisarlas." />
    <jsp:param name="accionUrl" value="admin/propiedades" />
    <jsp:param name="accionTexto" value="Ver todas" />
    <jsp:param name="accionIcono" value="houses" />
</jsp:include>

    <c:if test="${not empty errorReporte}">
        <div class="alerta-error mb-4">${errorReporte}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty ciudades}">
            <div class="estado-vacio">
                <i class="bi bi-bar-chart"></i>
                Todavía no hay propiedades publicadas, así que no hay nada que agrupar.
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-hogar">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Ciudad</th>
                                <th>Desglose por estado</th>
                                <th class="text-end">Total</th>
                                <th class="text-end">Detalle</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%-- Una sola fila por ciudad: los estados van dentro como
                                 chips, cada uno enlazando al listado ya filtrado. --%>
                            <c:forEach var="ciudad" items="${ciudades}">
                                <tr>
                                    <td class="fw-semibold text-nowrap">
                                        <i class="bi bi-geo-alt text-muted"></i> ${ciudad.nombreCiudad}
                                    </td>
                                    <td>
                                        <div class="d-flex flex-wrap gap-2">
                                            <c:forEach var="e" items="${ciudad.estados}">
                                                <a class="chip-estado"
                                                   href="${pageContext.request.contextPath}/admin/propiedades?idCiudad=${ciudad.idCiudad}&amp;estado=${e.estado}">
                                                    <span class="punto ${e.estado}"></span>
                                                    <span class="text-capitalize">${e.estado}</span>
                                                    <span class="conteo">${e.total}</span>
                                                </a>
                                            </c:forEach>
                                        </div>
                                    </td>
                                    <td class="text-end"><strong>${ciudad.total}</strong></td>
                                    <td class="text-end">
                                        <a class="btn btn-outline-claro btn-sm text-nowrap"
                                           href="${pageContext.request.contextPath}/admin/propiedades?idCiudad=${ciudad.idCiudad}">
                                            Ver propiedades
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="2" class="text-muted">Total del sistema</td>
                                <td class="text-end"><strong>${totalGeneral}</strong></td>
                                <td></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
