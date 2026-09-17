<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/jspf/app-inicio.jsp">
    <jsp:param name="seccion" value="cliente" />
    <jsp:param name="activo" value="perfil" />
    <jsp:param name="titulo" value="Mi perfil" />
    <jsp:param name="descripcion" value="Estos datos se comparten con la inmobiliaria cuando agendas una visita o radicas una solicitud." />
</jsp:include>

    <c:if test="${not empty errorPerfil}">
        <div class="alerta-error mb-3" style="max-width:720px;">${errorPerfil}</div>
    </c:if>

    <div class="bloque" style="max-width:720px;">
        <div class="bloque-cuerpo">
            <form action="${pageContext.request.contextPath}/cliente/perfil" method="post" novalidate>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="nombres" class="form-label">Nombres</label>
                        <input type="text" class="form-control" id="nombres" name="nombres" required
                               value="${perfil.nombres}">
                    </div>

                    <div class="col-md-6">
                        <label for="apellidos" class="form-label">Apellidos</label>
                        <input type="text" class="form-control" id="apellidos" name="apellidos" required
                               value="${perfil.apellidos}">
                    </div>

                    <div class="col-md-6">
                        <label for="documento" class="form-label">Documento</label>
                        <input type="text" class="form-control" id="documento" name="documento" required
                               value="${perfil.documento}">
                    </div>

                    <div class="col-md-6">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="text" class="form-control" id="telefono" name="telefono"
                               value="${perfil.telefono}">
                    </div>

                    <div class="col-12">
                        <label for="direccion" class="form-label">Dirección</label>
                        <input type="text" class="form-control" id="direccion" name="direccion"
                               value="${perfil.direccion}">
                    </div>
                </div>

                <button type="submit" class="btn btn-coral w-100 mt-4">Guardar cambios</button>
            </form>
        </div>
    </div>

<jsp:include page="/WEB-INF/jspf/app-fin.jsp" />
