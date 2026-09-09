<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${propiedad.idPropiedad == 0 ? 'Nueva propiedad' : 'Editar propiedad'} — Hogar 360</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilo.css" rel="stylesheet">
</head>
<body>

<div class="container py-5" style="max-width: 720px;">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">${propiedad.idPropiedad == 0 ? 'Nueva propiedad' : 'Editar propiedad'}</h2>
        <a href="${pageContext.request.contextPath}/agente/propiedades" class="btn btn-outline-claro btn-sm">
            <i class="bi bi-arrow-left"></i> Volver al listado
        </a>
    </div>

    <c:if test="${not empty errorPropiedad}">
        <div class="alerta-error mb-3">${errorPropiedad}</div>
    </c:if>

    <div class="card shadow-sm p-4">
                <form action="${pageContext.request.contextPath}/agente/propiedades" method="post"
              enctype="multipart/form-data" novalidate>

            <c:choose>
                <c:when test="${propiedad.idPropiedad == 0}">
                    <input type="hidden" name="accion" value="crear">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="accion" value="actualizar">
                    <input type="hidden" name="idPropiedad" value="${propiedad.idPropiedad}">
                </c:otherwise>
            </c:choose>

            <div class="mb-3">
                <label for="matriculaInmobiliaria" class="form-label">Matrícula inmobiliaria</label>
                <c:choose>
                    <c:when test="${propiedad.idPropiedad == 0}">
                        <input type="text" class="form-control" id="matriculaInmobiliaria" name="matriculaInmobiliaria"
                               required value="${propiedad.matriculaInmobiliaria}">
                    </c:when>
                    <c:otherwise>
                        <input type="text" class="form-control" id="matriculaInmobiliaria" name="matriculaInmobiliaria"
                               value="${propiedad.matriculaInmobiliaria}" readonly>
                        <div class="form-text">La matrícula no se puede modificar después de crear la propiedad.</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="mb-3">
                <label for="titulo" class="form-label">Título</label>
                <input type="text" class="form-control" id="titulo" name="titulo" required
                       value="${propiedad.titulo}">
            </div>

            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea class="form-control" id="descripcion" name="descripcion" rows="3">${propiedad.descripcion}</textarea>
            </div>

            <div class="mb-3">
                <label for="direccion" class="form-label">Dirección</label>
                <input type="text" class="form-control" id="direccion" name="direccion" required
                       value="${propiedad.direccion}">
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="idCiudad" class="form-label">Ciudad</label>
                    <select class="form-select" id="idCiudad" name="idCiudad" required>
                        <option value="">Selecciona...</option>
                        <c:forEach var="c" items="${ciudades}">
                            <option value="${c.idCiudad}" ${c.idCiudad == propiedad.idCiudad ? 'selected' : ''}>
                                ${c.nombreCiudad}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="idTipoPropiedad" class="form-label">Tipo de propiedad</label>
                    <select class="form-select" id="idTipoPropiedad" name="idTipoPropiedad" required>
                        <option value="">Selecciona...</option>
                        <c:forEach var="t" items="${tipos}">
                            <option value="${t.idTipoPropiedad}" ${t.idTipoPropiedad == propiedad.idTipoPropiedad ? 'selected' : ''}>
                                ${t.nombreTipo}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 mb-4">
                    <label for="precio" class="form-label">Precio (COP)</label>
                    <input type="number" step="0.01" min="0.01" class="form-control" id="precio" name="precio" required
                           value="${propiedad.precio}">
                </div>
                <div class="col-md-6 mb-4">
                    <label for="areaM2" class="form-label">Área (m²) — opcional</label>
                    <input type="number" step="0.01" min="0.01" class="form-control" id="areaM2" name="areaM2"
                           value="${propiedad.areaM2}">
                </div>
                        </div>

            <div class="form-check form-switch mb-4">
                <input class="form-check-input" type="checkbox" id="destacada" name="destacada"
                       ${propiedad.destacada ? 'checked' : ''}>
                <label class="form-check-label" for="destacada">Mostrar esta propiedad en "Destacadas" de la landing</label>
            </div>

            <div class="mb-4">
                <label class="form-label">Características</label>
                <div class="row">
                    <c:forEach var="car" items="${caracteristicas}">
                        <div class="col-6 col-md-4">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="caracteristicas"
                                       value="${car.idCaracteristica}" id="car${car.idCaracteristica}"
                                       <c:if test="${car.seleccionada}">checked</c:if>>
                                <label class="form-check-label" for="car${car.idCaracteristica}">${car.nombreCaracteristica}</label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <c:if test="${propiedad.idPropiedad != 0}">
                <div class="mb-3">
                    <label class="form-label">Fotos actuales</label>
                    <c:choose>
                        <c:when test="${empty imagenes}">
                            <p class="text-muted small">Todavía no tiene fotos.</p>
                        </c:when>
                        <c:otherwise>
                            <div class="d-flex flex-wrap gap-3">
                                <c:forEach var="img" items="${imagenes}">
                                    <div class="text-center">
                                        <img src="${pageContext.request.contextPath}/${img.urlImagen}"
                                             alt="Foto de la propiedad"
                                             style="width:100px;height:100px;object-fit:cover;border-radius:8px;"
                                             onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%27http://www.w3.org/2000/svg%27 width=%27100%27 height=%27100%27%3E%3Crect width=%27100%27 height=%27100%27 fill=%27%23e0e0e0%27/%3E%3C/svg%3E'">
                                        <div class="form-check mt-1">
                                            <input class="form-check-input" type="checkbox" name="eliminarImagen"
                                                   value="${img.idImagen}" id="del${img.idImagen}">
                                            <label class="form-check-label small" for="del${img.idImagen}">Eliminar</label>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <div class="mb-4">
                <label for="imagenes" class="form-label">
                    ${propiedad.idPropiedad == 0 ? 'Fotos de la propiedad' : 'Agregar más fotos'}
                </label>
                <input type="file" class="form-control" id="imagenes" name="imagenes" accept="image/*" multiple>
            </div>

            <button type="submit" class="btn btn-coral w-100">
                ${propiedad.idPropiedad == 0 ? 'Publicar propiedad' : 'Guardar cambios'}
            </button>
        </form>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/transicion.js"></script>
</body>
</html>