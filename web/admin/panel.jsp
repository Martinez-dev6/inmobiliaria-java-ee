<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="admin" />
    <jsp:param name="activo" value="panel" />
    <jsp:param name="titulo" value="Panel de administración" />
    <jsp:param name="descripcion" value="Estado general de Hogar 360 en un vistazo." />
    <jsp:param name="accionUrl" value="admin/propiedades" />
    <jsp:param name="accionTexto" value="Moderar propiedades" />
    <jsp:param name="accionIcono" value="houses" />
</jsp:include>

    <div class="metricas">
        <div class="metrica">
            <div class="metrica-icono"><i class="bi bi-people"></i></div>
            <div>
                <div class="metrica-cifra">${totalUsuarios}</div>
                <div class="metrica-rotulo">Usuarios registrados</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono"><i class="bi bi-person"></i></div>
            <div>
                <div class="metrica-cifra">${totalClientes}</div>
                <div class="metrica-rotulo">Clientes</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono"><i class="bi bi-building"></i></div>
            <div>
                <div class="metrica-cifra">${totalAgentes}</div>
                <div class="metrica-rotulo">Inmobiliarias</div>
            </div>
        </div>
        <div class="metrica">
            <div class="metrica-icono acento"><i class="bi bi-houses"></i></div>
            <div>
                <div class="metrica-cifra">${totalPropiedades}</div>
                <div class="metrica-rotulo">Propiedades</div>
            </div>
        </div>
    </div>

    <div class="panel-columnas">

        <div class="bloque">
            <div class="bloque-cabecera">
                <h2>Distribución por ciudad</h2>
                <a href="${pageContext.request.contextPath}/admin/reportes">Ver reporte completo</a>
            </div>
            <c:choose>
                <c:when test="${empty ciudades}">
                    <div class="bloque-cuerpo">
                        <p class="text-muted mb-0">Todavía no hay propiedades publicadas.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="lista-compacta">
                        <c:forEach var="ciudad" items="${ciudades}" end="5">
                            <li class="fila-enlace">
                                <a href="${pageContext.request.contextPath}/admin/propiedades?idCiudad=${ciudad.idCiudad}">
                                    <span class="principal">
                                        <strong>${ciudad.nombreCiudad}</strong>
                                        <span>
                                            <c:forEach var="e" items="${ciudad.estados}" varStatus="f">
                                                <span class="punto-estado ${e.estado}"></span>${e.total} ${e.estado}<c:if test="${!f.last}"> &nbsp;</c:if>
                                            </c:forEach>
                                        </span>
                                    </span>
                                    <span class="derecha conteo-fila">
                                        <span class="numero">${ciudad.total}</span>
                                        <span class="unidad">Propiedades</span>
                                    </span>
                                    <i class="bi bi-chevron-right chevron"></i>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>

        <div>
            <div class="bloque">
                <div class="bloque-cabecera">
                    <h2>Gestión del sistema</h2>
                </div>
                <div class="panel-accesos">
                    <a class="acceso-rapido" href="${pageContext.request.contextPath}/admin/propiedades">
                        <span class="acceso-icono"><i class="bi bi-houses"></i></span>
                        <span>
                            <span class="acceso-titulo">Propiedades</span>
                            <span class="acceso-detalle">Revisa publicaciones y contacta al agente.</span>
                        </span>
                        <i class="bi bi-chevron-right acceso-flecha"></i>
                    </a>
                    <a class="acceso-rapido" href="${pageContext.request.contextPath}/admin/roles">
                        <span class="acceso-icono"><i class="bi bi-people"></i></span>
                        <span>
                            <span class="acceso-titulo">Roles</span>
                            <span class="acceso-detalle">Asigna o revoca permisos de cada cuenta.</span>
                        </span>
                        <i class="bi bi-chevron-right acceso-flecha"></i>
                    </a>
                    <a class="acceso-rapido" href="${pageContext.request.contextPath}/admin/auditoria">
                        <span class="acceso-icono"><i class="bi bi-clipboard-data"></i></span>
                        <span>
                            <span class="acceso-titulo">Auditoría</span>
                            <span class="acceso-detalle">Historial de eventos del sistema.</span>
                        </span>
                        <i class="bi bi-chevron-right acceso-flecha"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
