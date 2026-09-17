<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--
    Shell de las vistas autenticadas: abre el documento, pinta la barra lateral
    y la barra superior, y deja abierto el contenedor del contenido. Se cierra
    con /WEB-INF/jspf/app-fin.jsp.

    Parámetros (<jsp:param>):
      seccion     = "admin" | "agente" | "cliente"  (qué menú pintar)
      activo      = clave del ítem de menú activo
      titulo      = título de la página (va al <title> y a la barra superior)
      descripcion = línea de apoyo bajo el título (opcional)
      accionUrl / accionTexto / accionIcono = botón principal de la barra (opcional)
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.titulo} — Hogar 360</title>
    <link rel="icon" href="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='22' fill='%231A2332'/%3E%3Cpath d='M50 18 L84 48 H74 V82 H26 V48 H16 Z' fill='%23FFB648'/%3E%3C/svg%3E">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body class="cuerpo-app">

<div class="app">

    <aside class="app-lateral" id="barraLateral">

        <a class="app-marca" href="${pageContext.request.contextPath}/">
            Hogar <span style="color:var(--azul-claro);">3</span><span style="color:var(--ambar);">6</span><span>0</span>
        </a>

        <nav class="app-nav">
            <c:choose>
                <c:when test="${param.seccion == 'admin'}">
                    <span class="app-nav-titulo">Administración</span>
                    <a class="app-nav-item ${param.activo == 'panel' ? 'activo' : ''}" href="${pageContext.request.contextPath}/admin/panel">
                        <i class="bi bi-grid-1x2"></i> Panel
                    </a>
                    <a class="app-nav-item ${param.activo == 'propiedades' ? 'activo' : ''}" href="${pageContext.request.contextPath}/admin/propiedades">
                        <i class="bi bi-houses"></i> Propiedades
                    </a>
                    <a class="app-nav-item ${param.activo == 'roles' ? 'activo' : ''}" href="${pageContext.request.contextPath}/admin/roles">
                        <i class="bi bi-people"></i> Roles
                    </a>
                    <a class="app-nav-item ${param.activo == 'reportes' ? 'activo' : ''}" href="${pageContext.request.contextPath}/admin/reportes">
                        <i class="bi bi-bar-chart"></i> Reportes
                    </a>
                    <a class="app-nav-item ${param.activo == 'auditoria' ? 'activo' : ''}" href="${pageContext.request.contextPath}/admin/auditoria">
                        <i class="bi bi-clipboard-data"></i> Auditoría
                    </a>
                </c:when>
                <c:when test="${param.seccion == 'agente'}">
                    <span class="app-nav-titulo">Inmobiliaria</span>
                    <a class="app-nav-item ${param.activo == 'panel' ? 'activo' : ''}" href="${pageContext.request.contextPath}/agente/panel">
                        <i class="bi bi-grid-1x2"></i> Panel
                    </a>
                    <a class="app-nav-item ${param.activo == 'propiedades' ? 'activo' : ''}" href="${pageContext.request.contextPath}/agente/propiedades">
                        <i class="bi bi-houses"></i> Mis propiedades
                    </a>
                    <a class="app-nav-item ${param.activo == 'citas' ? 'activo' : ''}" href="${pageContext.request.contextPath}/agente/citas">
                        <i class="bi bi-calendar-check"></i> Citas
                        <c:if test="${citasPendientes > 0}"><span class="app-nav-contador">${citasPendientes}</span></c:if>
                    </a>
                    <a class="app-nav-item ${param.activo == 'solicitudes' ? 'activo' : ''}" href="${pageContext.request.contextPath}/agente/solicitudes">
                        <i class="bi bi-file-earmark-text"></i> Solicitudes
                        <c:if test="${solicitudesPendientes > 0}"><span class="app-nav-contador">${solicitudesPendientes}</span></c:if>
                    </a>
                    <a class="app-nav-item ${param.activo == 'perfil' ? 'activo' : ''}" href="${pageContext.request.contextPath}/agente/perfil">
                        <i class="bi bi-building"></i> Datos de la agencia
                    </a>
                </c:when>
                <c:otherwise>
                    <span class="app-nav-titulo">Mi cuenta</span>
                    <a class="app-nav-item ${param.activo == 'panel' ? 'activo' : ''}" href="${pageContext.request.contextPath}/cliente/panel">
                        <i class="bi bi-grid-1x2"></i> Panel
                    </a>
                    <a class="app-nav-item ${param.activo == 'catalogo' ? 'activo' : ''}" href="${pageContext.request.contextPath}/catalogo">
                        <i class="bi bi-search"></i> Buscar propiedades
                    </a>
                    <a class="app-nav-item ${param.activo == 'favoritos' ? 'activo' : ''}" href="${pageContext.request.contextPath}/cliente/favoritos">
                        <i class="bi bi-heart"></i> Favoritos
                    </a>
                    <a class="app-nav-item ${param.activo == 'citas' ? 'activo' : ''}" href="${pageContext.request.contextPath}/cliente/citas">
                        <i class="bi bi-calendar-event"></i> Mis citas
                    </a>
                    <a class="app-nav-item ${param.activo == 'solicitudes' ? 'activo' : ''}" href="${pageContext.request.contextPath}/cliente/solicitudes">
                        <i class="bi bi-file-earmark-text"></i> Mis solicitudes
                    </a>
                    <a class="app-nav-item ${param.activo == 'perfil' ? 'activo' : ''}" href="${pageContext.request.contextPath}/cliente/perfil">
                        <i class="bi bi-person-circle"></i> Mi perfil
                    </a>
                </c:otherwise>
            </c:choose>

            <span class="app-nav-titulo">Sitio</span>
            <a class="app-nav-item" href="${pageContext.request.contextPath}/">
                <i class="bi bi-house"></i> Ir al inicio
            </a>
            <a class="app-nav-item" href="${pageContext.request.contextPath}/logout">
                <i class="bi bi-box-arrow-right"></i> Cerrar sesión
            </a>
        </nav>

        <div class="app-pie">
            <div class="app-usuario">
                <div class="app-usuario-cabecera">
                    <div class="app-avatar">${fn:substring(sessionScope.correo, 0, 1)}</div>
                    <div class="app-usuario-datos">
                        <div class="app-usuario-correo" title="${sessionScope.correo}">${sessionScope.correo}</div>
                        <div class="app-usuario-rol">
                            <c:choose>
                                <c:when test="${param.seccion == 'admin'}">Administrador</c:when>
                                <c:when test="${param.seccion == 'agente'}">Inmobiliaria</c:when>
                                <c:otherwise>Cliente</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <%-- Una cuenta puede tener varios roles a la vez: aquí se ofrecen
                     los otros paneles, que de otro modo quedarían inalcanzables. --%>
                <c:set var="otrosPaneles" value="${false}" />
                <c:if test="${sessionScope.roles.contains('Administrador') and param.seccion != 'admin'}"><c:set var="otrosPaneles" value="${true}" /></c:if>
                <c:if test="${sessionScope.roles.contains('Inmobiliaria') and param.seccion != 'agente'}"><c:set var="otrosPaneles" value="${true}" /></c:if>
                <c:if test="${sessionScope.roles.contains('Cliente') and param.seccion != 'cliente'}"><c:set var="otrosPaneles" value="${true}" /></c:if>

                <c:if test="${otrosPaneles}">
                    <div class="app-cambio-rol">
                        <span class="titulo">Cambiar de panel</span>
                        <c:if test="${sessionScope.roles.contains('Administrador') and param.seccion != 'admin'}">
                            <a href="${pageContext.request.contextPath}/admin/panel"><i class="bi bi-shield-check"></i> Administrador</a>
                        </c:if>
                        <c:if test="${sessionScope.roles.contains('Inmobiliaria') and param.seccion != 'agente'}">
                            <a href="${pageContext.request.contextPath}/agente/panel"><i class="bi bi-building"></i> Inmobiliaria</a>
                        </c:if>
                        <c:if test="${sessionScope.roles.contains('Cliente') and param.seccion != 'cliente'}">
                            <a href="${pageContext.request.contextPath}/cliente/panel"><i class="bi bi-person"></i> Cliente</a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </aside>

    <div class="app-velo" id="veloLateral"></div>

    <div class="app-principal">

        <header class="app-barra">
            <div class="app-barra-titulo">
                <button type="button" class="boton-menu" id="botonMenu" aria-label="Abrir menú">
                    <i class="bi bi-list"></i>
                </button>
                <div>
                    <h1>${param.titulo}</h1>
                    <c:if test="${not empty param.descripcion}">
                        <p class="app-descripcion">${param.descripcion}</p>
                    </c:if>
                </div>
            </div>
            <c:if test="${not empty param.accionUrl}">
                <div class="app-barra-acciones">
                    <a href="${pageContext.request.contextPath}/${param.accionUrl}" class="btn btn-coral">
                        <c:if test="${not empty param.accionIcono}"><i class="bi bi-${param.accionIcono}"></i> </c:if>${param.accionTexto}
                    </a>
                </div>
            </c:if>
        </header>

        <main class="app-contenido">

            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />
