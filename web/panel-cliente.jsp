<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Panel Cliente — Hogar 360</title></head>
<body style="font-family: sans-serif; padding: 40px;">
    <h1>Panel de Cliente (temporal)</h1>
    <p>Sesión activa como: <strong>${sessionScope.correo}</strong></p>
    <p>Roles: <strong>${sessionScope.roles}</strong></p>
    <p><a href="logout">Cerrar sesión</a></p>
</body>
</html>