package com.example.trabajofinalprogramacion2.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Conversiones entre LocalDate y DATE de MySQL que toleran valores null
public class FechasBD {

    private FechasBD() {
    }

    // Guarda la fecha en el PreparedStatement, o NULL si la fecha es null
    public static void setFecha(PreparedStatement ps, int posicion, LocalDate fecha) throws SQLException {
        if (fecha == null) {
            ps.setNull(posicion, Types.DATE);
        } else {
            ps.setDate(posicion, Date.valueOf(fecha));
        }
    }

    // Lee la fecha de la columna, devuelve null si en la BD esta en NULL
    public static LocalDate leerFecha(ResultSet rs, String columna) throws SQLException {
        Date fecha = rs.getDate(columna);
        return fecha != null ? fecha.toLocalDate() : null;
    }

    // Guarda fecha y hora (columna DATETIME), o NULL si es null
    public static void setFechaHora(PreparedStatement ps, int posicion, LocalDateTime fechaHora) throws SQLException {
        if (fechaHora == null) {
            ps.setNull(posicion, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(posicion, Timestamp.valueOf(fechaHora));
        }
    }

    // Lee fecha y hora de la columna, devuelve null si en la BD esta en NULL
    public static LocalDateTime leerFechaHora(ResultSet rs, String columna) throws SQLException {
        Timestamp fechaHora = rs.getTimestamp(columna);
        return fechaHora != null ? fechaHora.toLocalDateTime() : null;
    }
}
