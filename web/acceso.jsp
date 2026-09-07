<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso — Hogar 360</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&family=Inter:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="css/estilo.css" rel="stylesheet">

</head>
<body class="fondo-acceso">

    <div class="contenedor-acceso ${(panelActivo == 'registro' or animarRegreso) ? 'derecha-activa' : ''}"
     id="contenedorAcceso"
     data-animar-regreso="${animarRegreso ? 'true' : 'false'}">

        <!-- FORMULARIO: INICIAR SESIÓN -->
<div class="formulario-panel formulario-login">
    <form action="login" method="post" novalidate>
        <h2>Inicia sesión</h2>

        <c:if test="${not empty errorLogin}">
            <div class="alerta-error mb-3">${errorLogin}</div>
        </c:if>
            
        <c:if test="${not empty mensajeExito}">
            <div class="alerta-exito mb-3">${mensajeExito}</div>
        </c:if>

        <div class="mb-3">
            <label for="correoLogin" class="form-label">Correo electrónico</label>
            <input type="email" class="form-control" id="correoLogin" name="correo" required
                   value="${empty panelActivo ? correoPrevio : ''}">
        </div>

        <div class="mb-3">
            <label for="contrasenaLogin" class="form-label">Contraseña</label>
            <input type="password" class="form-control" id="contrasenaLogin" name="contrasena" required minlength="6">
        </div>

        <button type="submit" class="btn btn-coral">Iniciar sesión</button>
    </form>
</div>

<!-- FORMULARIO: REGISTRO -->
        <div class="formulario-panel formulario-registro">
            <form action="registro" method="post" novalidate>
                <h2>Crea tu cuenta</h2>

                <c:if test="${not empty errorRegistro}">
                    <div class="alerta-error mb-3">${errorRegistro}</div>
                </c:if>

                <div class="mb-3">
                    <label for="correoRegistro" class="form-label">Correo electrónico</label>
                    <input type="email" class="form-control" id="correoRegistro" name="correo" required
                           value="${panelActivo == 'registro' ? correoPrevio : ''}">
                </div>

                <div class="mb-3">
                    <label for="contrasenaRegistro" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="contrasenaRegistro" name="contrasena" required minlength="6">
                </div>

                <div class="mb-3">
                    <label for="confirmarContrasena" class="form-label">Confirmar contraseña</label>
                    <input type="password" class="form-control" id="confirmarContrasena" name="confirmarContrasena" required minlength="6">
                </div>

                <button type="submit" class="btn btn-coral">Registrarme</button>
            </form>
        </div>

        <!-- PANEL DESLIZANTE (solo escritorio, ver acceso.css) -->
        <div class="panel-overlay">
            <div class="overlay-cara overlay-izquierda">
                <h2>¡Bienvenido!</h2>
                <p>Regístrate para empezar a buscar tu próxima propiedad.</p>
                 <button type="button" class="btn btn-outline-claro" id="btnIrRegistro">Regístrate</button>
            </div>
            <div class="overlay-cara overlay-derecha">
                <h2>¡Hola de nuevo!</h2>
                <p>Ingresa tus datos para acceder a tu cuenta en Hogar 360.</p>
                <button type="button" class="btn btn-outline-claro" id="btnIrLogin">Iniciar sesión</button>
            </div>
        </div>

        <!-- Enlaces de respaldo, solo visibles en celular (ver acceso.css) -->
        <div class="switch-movil">
            <span class="switch-movil-login">¿No tienes cuenta? <a href="#" id="linkIrRegistroMovil">Regístrate</a></span>
            <span class="switch-movil-registro">¿Ya tienes cuenta? <a href="#" id="linkIrLoginMovil">Inicia sesión</a></span>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/acceso.js"></script>
</body>
</html>