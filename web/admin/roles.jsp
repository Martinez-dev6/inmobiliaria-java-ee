<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="admin" />
    <jsp:param name="activo" value="roles" />
    <jsp:param name="titulo" value="Roles de usuario" />
    <jsp:param name="descripcion" value="Una cuenta puede tener varios roles a la vez y cambiar de panel desde la barra lateral." />
</jsp:include>

    <c:if test="${not empty errorRoles}">
        <div class="alerta-error mb-4">${errorRoles}</div>
    </c:if>

    <div class="bloque mb-4">
        <div class="bloque-cuerpo d-flex align-items-start gap-3">
            <i class="bi bi-shield-lock fs-4" style="color:var(--coral);"></i>
            <div>
                <strong class="d-block">Sobre el rol Administrador y el estado de la cuenta</strong>
                <span class="text-muted small">
                    Asignar el rol Administrador entrega el control total del sistema, por eso se pide
                    confirmación. Y una vez asignado, esa cuenta queda fuera de tu alcance
                    <i class="bi bi-lock-fill" style="color:var(--coral);"></i> : no puedes cambiarle
                    ningún rol ni desactivarla. Cada administrador gestiona la suya, y sólo puede
                    renunciar al rol si queda otro administrador activo.
                    <br>
                    Una cuenta desactivada conserva sus datos, sus roles y su historial, pero no puede
                    iniciar sesión. Tampoco puedes desactivar la tuya: perderías el acceso.
                </span>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty usuarios}">
            <div class="estado-vacio">
                <i class="bi bi-people"></i>
                No hay usuarios registrados todavía.
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-hogar">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Estado</th>
                                <c:forEach var="rolCol" items="${todosLosRoles}">
                                    <th class="text-center">${rolCol}</th>
                                </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${usuarios}">
                                <c:set var="esYoMismo" value="${u.idUsuario == sessionScope.idUsuario}" />
                                <%-- Regla de la pantalla: un administrador no se mete con la cuenta
                                     de otro administrador. Ni sus roles ni su estado; lo único que
                                     se permite sobre ella es reactivarla. --%>
                                <c:set var="esOtroAdmin" value="${u.roles.contains('Administrador') and not esYoMismo}" />
                                <c:set var="avisoIntocable" value="La cuenta de otro administrador no se gestiona desde aquí: sólo esa persona puede cambiar sus roles" />
                                <tr>
                                    <td class="fw-semibold">
                                        ${u.correo}
                                        <c:if test="${esYoMismo}">
                                            <span class="badge bg-light text-dark border ms-1">tú</span>
                                        </c:if>
                                    </td>
                                    <td class="text-nowrap">
                                        <c:choose>
                                            <c:when test="${u.activo}">
                                                <span class="badge bg-success">Activo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Inactivo</span>
                                            </c:otherwise>
                                        </c:choose>

                                        <%-- Una cuenta inactiva conserva sus datos y sus roles, pero no
                                             puede iniciar sesión. Cuando no se puede cambiar el estado (la
                                             propia cuenta, o la de otro administrador) la insignia se queda
                                             sola: el motivo ya está en la nota de arriba y en los candados
                                             de las columnas de roles. --%>
                                        <c:choose>
                                            <c:when test="${not u.activo}">
                                                <form action="${pageContext.request.contextPath}/admin/roles" method="post" class="d-inline">
                                                    <input type="hidden" name="accion" value="activar">
                                                    <input type="hidden" name="idUsuario" value="${u.idUsuario}">
                                                    <button type="submit" class="btn btn-sm btn-outline-claro ms-2"
                                                            data-confirmar="Vas a reactivar la cuenta de ${fn:escapeXml(u.correo)}: volverá a poder iniciar sesión. ¿Continuar?">
                                                        <i class="bi bi-check-lg"></i> Activar
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:when test="${not esYoMismo and not esOtroAdmin}">
                                                <form action="${pageContext.request.contextPath}/admin/roles" method="post" class="d-inline">
                                                    <input type="hidden" name="accion" value="desactivar">
                                                    <input type="hidden" name="idUsuario" value="${u.idUsuario}">
                                                    <button type="submit" class="btn btn-sm btn-outline-secondary ms-2"
                                                            data-confirmar="Vas a desactivar la cuenta de ${fn:escapeXml(u.correo)}: no podrá iniciar sesión hasta que la reactives. ¿Continuar?">
                                                        <i class="bi bi-slash-circle"></i> Desactivar
                                                    </button>
                                                </form>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <c:forEach var="rolCol" items="${todosLosRoles}">
                                        <td class="text-center">
                                            <c:choose>
                                                <%-- Cuenta de otro administrador: ninguno de sus roles se
                                                     toca desde aquí, tenga ese rol o no. Si sólo se blindara
                                                     el rol Administrador, un admin podría seguir quitándole
                                                     el de Cliente o el de Inmobiliaria a otro. --%>
                                                <c:when test="${esOtroAdmin}">
                                                    <%-- Misma pastilla tenga el rol o no, para que la fila se
                                                         lea de un vistazo; la variante "sin" va apagada. --%>
                                                    <span class="rol-bloqueado ${u.roles.contains(rolCol) ? '' : 'sin'}"
                                                          title="${avisoIntocable}">
                                                        <i class="bi bi-lock-fill"></i> ${rolCol}
                                                    </span>
                                                </c:when>

                                                <%-- Ya tiene el rol: se puede revocar, salvo el propio
                                                     Administrador cuando no queda ningún otro. --%>
                                                <c:when test="${u.roles.contains(rolCol)}">
                                                    <c:choose>
                                                        <c:when test="${rolCol == 'Administrador' and totalAdministradores <= 1}">
                                                            <span class="rol-bloqueado" title="No puedes renunciar: eres el único administrador activo que queda">
                                                                <i class="bi bi-lock-fill"></i> Único admin
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="avisoQuitar" value="Vas a quitarle el rol ${rolCol} a ${fn:escapeXml(u.correo)}. ¿Continuar?" />
                                                            <c:if test="${rolCol == 'Administrador'}">
                                                                <c:set var="avisoQuitar" value="Vas a renunciar a TU rol de Administrador y perderás el acceso al panel de administración. ¿Continuar?" />
                                                            </c:if>
                                                            <form action="${pageContext.request.contextPath}/admin/roles" method="post" class="d-inline">
                                                                <input type="hidden" name="accion" value="revocar">
                                                                <input type="hidden" name="idUsuario" value="${u.idUsuario}">
                                                                <input type="hidden" name="rol" value="${rolCol}">
                                                                <button type="submit" class="btn btn-sm btn-outline-secondary"
                                                                        data-confirmar="${avisoQuitar}">
                                                                    <i class="bi bi-dash-lg"></i> Quitar
                                                                </button>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <%-- Dar el rol de administrador entrega el control del sistema,
                                                         así que ese botón sí pide confirmación explícita. --%>
                                                    <c:set var="avisoAsignar" value="" />
                                                    <c:if test="${rolCol == 'Administrador'}">
                                                        <c:set var="avisoAsignar" value="Vas a dar acceso de ADMINISTRADOR a ${fn:escapeXml(u.correo)}: podrá moderar propiedades, cambiar roles y ver la auditoría. ¿Continuar?" />
                                                    </c:if>
                                                    <form action="${pageContext.request.contextPath}/admin/roles" method="post" class="d-inline">
                                                        <input type="hidden" name="accion" value="asignar">
                                                        <input type="hidden" name="idUsuario" value="${u.idUsuario}">
                                                        <input type="hidden" name="rol" value="${rolCol}">
                                                        <button type="submit" class="btn btn-sm btn-coral" data-confirmar="${avisoAsignar}">
                                                            <i class="bi bi-plus-lg"></i> Asignar
                                                        </button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
