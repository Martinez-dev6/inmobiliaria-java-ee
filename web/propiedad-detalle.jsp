<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${propiedad.titulo} — Hogar 360</title>
    <link rel="icon" href="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='22' fill='%231A2332'/%3E%3Cpath d='M50 18 L84 48 H74 V82 H26 V48 H16 Z' fill='%23FFB648'/%3E%3C/svg%3E">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/WEB-INF/jspf/navbar.jsp">
    <jsp:param name="navTipo" value="volver" />
    <jsp:param name="navDestino" value="catalogo" />
    <jsp:param name="navTexto" value="Volver al catálogo" />
</jsp:include>

<div class="container py-5">

    <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

    <h2>${propiedad.titulo}</h2>
    <p class="text-muted">${propiedad.direccion}, ${propiedad.nombreCiudad} · ${propiedad.nombreTipo}</p>

    <c:choose>
        <c:when test="${empty imagenes}">
            <div class="galeria-vacia mb-4">
                <i class="bi bi-image"></i>
                <span>Esta publicación todavía no tiene fotos</span>
            </div>
        </c:when>
        <c:otherwise>
            <div class="galeria mb-4" id="galeria">

                <figure class="galeria-principal" id="galeriaPrincipal"
                        role="button" tabindex="0" aria-label="Ampliar las fotos de la propiedad">
                    <img id="galeriaImagen"
                         src="${pageContext.request.contextPath}/${imagenes[0].urlImagen}"
                         alt="${propiedad.titulo}"
                         onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%27800%27 height=%27500%27%3E%3Crect width=%27800%27 height=%27500%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                    <span class="galeria-lupa"><i class="bi bi-arrows-angle-expand"></i> Ampliar</span>
                    <span class="galeria-contador" id="galeriaContador"></span>
                </figure>

                <div class="galeria-miniaturas">
                    <c:forEach var="img" items="${imagenes}" varStatus="fila">
                        <button type="button" class="galeria-miniatura ${fila.first ? 'activa' : ''}"
                                data-src="${pageContext.request.contextPath}/${img.urlImagen}"
                                aria-label="Ver foto ${fila.count}">
                            <img src="${pageContext.request.contextPath}/${img.urlImagen}" alt=""
                                 onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%27200%27 height=%27150%27%3E%3Crect width=%27200%27 height=%27150%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                        </button>
                    </c:forEach>
                </div>

            </div>
        </c:otherwise>
    </c:choose>

    <div class="row">
        <div class="col-md-8">
            <h4>$<fmt:formatNumber value="${propiedad.precio}" pattern="#,##0"/></h4>
            <c:if test="${not empty propiedad.areaM2}">
                <p class="text-muted">${propiedad.areaM2} m²</p>
            </c:if>
            <p>${propiedad.descripcion}</p>

            <c:if test="${not empty nombresCaracteristicas}">
                <h5 class="mt-4">Características</h5>
                <div>
                    <c:forEach var="nombre" items="${nombresCaracteristicas}">
                        <span class="badge bg-light text-dark border me-2 mb-2">${nombre}</span>
                    </c:forEach>
                </div>
            </c:if>
        </div>

        <div class="col-md-4">
            <c:set var="haySesion" value="${not empty sessionScope.correo}" />
            <c:set var="esCliente" value="${not empty sessionScope.roles and sessionScope.roles.contains('Cliente')}" />

            <div class="card shadow-sm border-0 rounded-4 p-3 mb-3">
                <h6 class="text-muted small text-uppercase mb-2" style="letter-spacing:.08em;">Publica</h6>
                <p class="mb-1 fw-bold">${propiedad.nombreInmobiliaria}</p>

                <%-- Los datos de contacto son para usuarios registrados: un
                     visitante anónimo no debe poder cosechar teléfonos. --%>
                <c:choose>
                    <c:when test="${haySesion}">
                        <c:if test="${not empty propiedad.telefonoInmobiliaria}">
                            <p class="mb-0"><i class="bi bi-telephone"></i> ${propiedad.telefonoInmobiliaria}</p>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <p class="mb-0 text-muted small">
                            <i class="bi bi-lock"></i> Datos de contacto visibles al iniciar sesión.
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:choose>
                <%-- Visitante sin cuenta: en vez de no ver nada, se le explica
                     qué gana registrándose y se le lleva directo al registro. --%>
                <c:when test="${not haySesion}">
                    <div class="aviso-visitante">
                        <span class="icono"><i class="bi bi-calendar-heart"></i></span>
                        <h6>¿Te interesa esta propiedad?</h6>
                        <p>Crea tu cuenta para agendar una visita, guardarla en favoritos
                           y radicar tu solicitud de compra o arriendo.</p>
                        <a href="${pageContext.request.contextPath}/acceso.jsp?panelActivo=registro"
                           class="btn btn-coral w-100 mb-2">Registrarme</a>
                        <a href="${pageContext.request.contextPath}/acceso.jsp"
                           class="btn btn-outline-claro w-100">Ya tengo cuenta</a>
                    </div>
                </c:when>

                <c:when test="${esCliente}">
                    <div class="card shadow-sm border-0 rounded-4 p-3">
                        <!-- Favorito -->
                        <form action="${pageContext.request.contextPath}/cliente/favoritos" method="post">
                            <input type="hidden" name="idPropiedad" value="${propiedad.idPropiedad}">
                            <c:choose>
                                <c:when test="${esFavorita}">
                                    <input type="hidden" name="accion" value="desmarcar">
                                    <button type="submit" class="btn btn-outline-claro w-100">
                                        <i class="bi bi-heart-fill" style="color:#cc0033;"></i> Quitar de favoritos
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="accion" value="marcar">
                                    <button type="submit" class="btn btn-outline-claro w-100">
                                        <i class="bi bi-heart"></i> Marcar como favorito
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>

                        <!-- Agendar cita -->
                        <hr>
                        <h6>Agendar visita</h6>
                        <form action="${pageContext.request.contextPath}/cliente/citas" method="post">
                            <input type="hidden" name="idPropiedad" value="${propiedad.idPropiedad}">
                            <input type="datetime-local" class="form-control mb-2" name="fechaHora"
                                   min="${fechaMinima}" required>
                            <div class="form-text mb-2">Sólo puedes elegir fechas futuras.</div>
                            <button type="submit" class="btn btn-coral w-100 btn-sm">Agendar</button>
                        </form>

                        <!-- Radicar solicitud -->
                        <hr>
                        <h6>Radicar solicitud de compra/arriendo</h6>
                        <form action="${pageContext.request.contextPath}/cliente/solicitudes" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="idPropiedad" value="${propiedad.idPropiedad}">
                            <select class="form-select form-select-sm mb-2" name="tipoSolicitud" required>
                                <option value="">Tipo de solicitud...</option>
                                <option value="compra">Compra</option>
                                <option value="arriendo">Arriendo</option>
                            </select>
                            <textarea class="form-control form-control-sm mb-2" name="observaciones" rows="2"
                                      placeholder="Observaciones (opcional)"></textarea>
                            <label class="form-label small mb-1">Documentos de soporte (opcional)</label>
                            <input type="file" class="form-control form-control-sm mb-2" name="documentos" multiple>
                            <button type="submit" class="btn btn-outline-claro w-100 btn-sm">Radicar solicitud</button>
                        </form>
                    </div>
                </c:when>

                <%-- Sesión iniciada pero sin rol Cliente (agente o admin): se le
                     dice por qué no ve los botones en vez de dejar el hueco. --%>
                <c:otherwise>
                    <div class="card shadow-sm border-0 rounded-4 p-3">
                        <p class="text-muted small mb-0">
                            <i class="bi bi-info-circle"></i>
                            Agendar visitas y radicar solicitudes es para cuentas con rol Cliente.
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>

<!-- Visor de fotos a pantalla completa -->
<div class="lightbox" id="lightbox" aria-hidden="true" role="dialog" aria-modal="true"
     aria-label="Fotos de la propiedad">
    <button type="button" class="lightbox-cerrar" data-lightbox-cerrar aria-label="Cerrar">
        <i class="bi bi-x-lg"></i>
    </button>
    <button type="button" class="lightbox-nav lightbox-anterior" data-lightbox-mover="-1" aria-label="Foto anterior">
        <i class="bi bi-chevron-left"></i>
    </button>
    <img class="lightbox-imagen" id="lightboxImagen" src="" alt="${propiedad.titulo}">
    <button type="button" class="lightbox-nav lightbox-siguiente" data-lightbox-mover="1" aria-label="Foto siguiente">
        <i class="bi bi-chevron-right"></i>
    </button>
    <span class="lightbox-contador" id="lightboxContador"></span>
</div>

<script src="${pageContext.request.contextPath}/js/galeria.js"></script>
<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>
