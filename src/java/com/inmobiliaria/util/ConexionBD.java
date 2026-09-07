package com.inmobiliaria.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró db.properties en el classpath.");
            }

            Properties props = new Properties();
            props.load(input);

            driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            Class.forName(driver);

        } catch (IOException e) {
            throw new RuntimeException("Error al leer db.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver JDBC: " + driver, e);
        }
    }

    private ConexionBD() {
        // Clase de utilidad: nadie debe crear objetos ConexionBD con "new"
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}