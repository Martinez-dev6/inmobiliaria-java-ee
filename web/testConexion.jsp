<%@ page import="com.inmobiliaria.util.ConexionBD" %>
<%@ page import="java.sql.Connection" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Prueba de Conexión</title></head>
<body>
<%
    try (Connection conexion = ConexionBD.obtenerConexion()) {
        if (conexion != null && !conexion.isClosed()) {
%>
            <h2 style="color: green;">✅ Conexión exitosa a inmobiliaria_db</h2>
<%
        }
    } catch (Exception e) {
%>
        <h2 style="color: red;">❌ Error de conexión</h2>
        <pre><%= e.getMessage() %></pre>
<%
    }
%>
</body>
</html>