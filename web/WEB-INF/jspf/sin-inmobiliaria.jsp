<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    Aviso que reemplaza al escueto "Tu cuenta no está asociada a ninguna
    inmobiliaria". Se muestra cuando la cuenta tiene el ROL de Inmobiliaria pero
    todavía no existe su ficha en la tabla 'inmobiliaria', que es lo que de
    verdad referencian las propiedades.
--%>
<div class="aviso-configuracion mb-4">
    <div class="icono"><i class="bi bi-building-exclamation"></i></div>
    <div>
        <h2>Todavía falta registrar tu inmobiliaria</h2>
        <p>
            Tienes el rol de Inmobiliaria, por eso puedes entrar a este panel. Pero cada
            propiedad se publica <strong>a nombre de una agencia</strong>, y esa ficha
            (nombre comercial, NIT y teléfono) aún no existe para tu cuenta.
        </p>
        <ul>
            <li>Hasta completarla no puedes publicar, ni recibirás citas ni solicitudes.</li>
            <li>Sólo toma un minuto y queda habilitado de inmediato.</li>
        </ul>
        <a href="${pageContext.request.contextPath}/agente/perfil" class="btn btn-coral btn-sm">
            <i class="bi bi-building"></i> Registrar mi inmobiliaria
        </a>
    </div>
</div>
