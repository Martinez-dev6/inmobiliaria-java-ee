<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="yaExiste" value="${not empty inmobiliaria.nombreComercial}" />

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="agente" />
    <jsp:param name="activo" value="perfil" />
    <jsp:param name="titulo" value="Datos de la inmobiliaria" />
    <jsp:param name="descripcion" value="Esta es la ficha que ven los clientes en cada publicación tuya." />
</jsp:include>

    <c:if test="${not yaExiste}">
        <div class="aviso-configuracion mb-4" style="max-width:760px;">
            <div class="icono"><i class="bi bi-building-exclamation"></i></div>
            <div>
                <h2>Falta registrar tu inmobiliaria</h2>
                <p>
                    Tener el rol de Inmobiliaria te deja entrar a este panel, pero las publicaciones
                    se guardan a nombre de una agencia concreta. Mientras no exista esa ficha, no se
                    puede crear ninguna propiedad.
                </p>
                <p class="mb-0">Completa el formulario y quedará habilitado al instante.</p>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty errorInmobiliaria}">
        <div class="alerta-error mb-3" style="max-width:760px;">${errorInmobiliaria}</div>
    </c:if>

    <div class="bloque" style="max-width:760px;">
        <div class="bloque-cuerpo">
            <form action="${pageContext.request.contextPath}/agente/perfil" method="post" novalidate>

                <div class="mb-3">
                    <label for="nombreComercial" class="form-label">Nombre comercial</label>
                    <input type="text" class="form-control" id="nombreComercial" name="nombreComercial"
                           maxlength="150" required placeholder="Ej. Vargas Bienes Raíces"
                           value="${inmobiliaria.nombreComercial}">
                    <div class="form-text">Es el nombre que aparece como "Publica: ..." en el catálogo.</div>
                </div>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="nit" class="form-label">NIT — opcional</label>
                        <input type="text" class="form-control" id="nit" name="nit" maxlength="30"
                               placeholder="901234567-1" value="${inmobiliaria.nit}">
                    </div>
                    <div class="col-md-6">
                        <label for="telefonoContacto" class="form-label">Teléfono de contacto — opcional</label>
                        <input type="text" class="form-control" id="telefonoContacto" name="telefonoContacto"
                               maxlength="20" placeholder="6076001122" value="${inmobiliaria.telefonoContacto}">
                        <div class="form-text">Sólo lo ven los usuarios con sesión iniciada.</div>
                    </div>
                </div>

                <button type="submit" class="btn btn-coral w-100 mt-4">
                    ${yaExiste ? 'Guardar cambios' : 'Registrar mi inmobiliaria'}
                </button>
            </form>
        </div>
    </div>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
