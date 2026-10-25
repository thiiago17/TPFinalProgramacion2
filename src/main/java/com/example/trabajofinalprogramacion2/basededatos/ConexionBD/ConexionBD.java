package com.example.trabajofinalprogramacion2.basededatos.ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static final String url="jdbc:mysql://localhost:3306/TrabajoPracticoFinal2";
    public static final String user="root";
    public static final String password="";

    public static Connection conectar()throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

}
