package com.example.trabajofinalprogramacion2.basededatos.ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            System.getenv("DB_URL");

    private static final String USER =
            System.getenv("DB_USER");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    public static Connection conectar() throws SQLException {

        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException(
                    "Faltan configurar las variables de entorno"
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}