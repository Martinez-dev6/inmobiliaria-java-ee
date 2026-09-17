<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    Navbar compartido de Hogar 360. Se incluye con <jsp:include> (no <%@ include %>)
    para que cada página conserve su propio taglib sin choques de traducción.

    Parámetros (<jsp:param>):
      navTipo   = "publico" | "app" | "volver" (por defecto "volver")
      navActivo = "inicio" | "catalogo"          (solo para navTipo=publico)
      navDestino = ruta relativa al contexto      (solo para navTipo=volver)
      navTexto   = texto del botón                (solo para navTipo=volver)
--%>
<nav class="navbar navbar-expand-lg navbar-hogar">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
            Hogar <span style="color:var(--azul-claro);">3</span><span style="color:var(--ambar);">6</span><span style="color:var(--oscuro);">0</span>
        </a>

        <c:choose>
            <c:when test="${param.navTipo == 'publico'}">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuPrincipal">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="menuPrincipal">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link ${param.navActivo == 'inicio' ? 'active' : ''}" href="${pageContext.request.contextPath}/">Inicio</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.navActivo == 'catalogo' ? 'active' : ''}" href="${pageContext.request.contextPath}/catalogo">Catálogo</a>
                        </li>
                    </ul>
                    <div class="d-flex gap-2 flex-wrap">
                        <c:choose>
                            <c:when test="${not empty sessionScope.correo}">
                                <%-- Una cuenta puede tener varios roles a la vez. Se ofrece un
                                     acceso por cada panel al que de verdad puede entrar: con una
                                     sola opción "Mi panel" los demás quedaban inalcanzables. --%>
                                <c:if test="${sessionScope.roles.contains('Administrador')}">
                                    <a href="${pageContext.request.contextPath}/admin/panel" class="btn btn-outline-claro btn-sm">
                                        <i class="bi bi-shield-check"></i> Administrador
                                    </a>
                                </c:if>
                                <c:if test="${sessionScope.roles.contains('Inmobiliaria')}">
                                    <a href="${pageContext.request.contextPath}/agente/panel" class="btn btn-outline-claro btn-sm">
                                        <i class="bi bi-building"></i> Inmobiliaria
                                    </a>
                                </c:if>
                                <c:if test="${sessionScope.roles.contains('Cliente')}">
                                    <a href="${pageContext.request.contextPath}/cliente/panel" class="btn btn-outline-claro btn-sm">
                                        <i class="bi bi-person"></i> Mi cuenta
                                    </a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/logout" class="btn btn-coral btn-sm">Cerrar sesión</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/acceso.jsp" class="btn btn-outline-claro btn-sm">Iniciar sesión</a>
                                <a href="${pageContext.request.contextPath}/acceso.jsp?panelActivo=registro" class="btn btn-coral btn-sm">Registrarse</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:when>
            <c:when test="${param.navTipo == 'app'}">
                <div class="d-flex gap-2">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary btn-sm">
                        <i class="bi bi-house"></i> Inicio
                    </a>
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-claro btn-sm">Cerrar sesión</a>
                </div>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/${param.navDestino}" class="btn btn-outline-claro btn-sm">
                    ${param.navTexto}
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
