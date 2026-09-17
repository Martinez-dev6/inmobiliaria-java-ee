<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    Mensajes "flash": los controladores los dejan en sesión justo antes de
    redirigir (patrón POST-Redirect-GET) y aquí se pintan y se borran, para que
    no reaparezcan al recargar la página.
--%>
<c:if test="${not empty sessionScope.flashExito}">
    <div class="alerta-exito mb-4">
        <i class="bi bi-check-circle-fill"></i> ${sessionScope.flashExito}
    </div>
    <c:remove var="flashExito" scope="session" />
</c:if>
<c:if test="${not empty sessionScope.flashError}">
    <div class="alerta-error mb-4">
        <i class="bi bi-exclamation-triangle-fill"></i> ${sessionScope.flashError}
    </div>
    <c:remove var="flashError" scope="session" />
</c:if>
