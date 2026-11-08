package com.example.trabajofinalprogramacion2.DAO.Usuarios;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO implements ABCMLDAO<Entrenador> {


    @Override
    public Entrenador agregar(Entrenador entrenador) {
        String sql = "INSERT INTO entrenador (dni, nombre, apellido, email, password, fecha_nacimiento, activo, especialidad, salario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, entrenador.getDni());
            pstmt.setString(2, entrenador.getNombre());
            pstmt.setString(3, entrenador.getApellido());
            pstmt.setString(4, entrenador.getEmail());
            pstmt.setString(5, entrenador.getPassword());
            pstmt.setDate(6, java.sql.Date.valueOf(entrenador.getFechaDeNacimiento()));
            pstmt.setBoolean(7, entrenador.isActivo());
            pstmt.setString(8, entrenador.getEspecialidad());
            pstmt.setDouble(9, entrenador.getSalario());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entrenador.setId(generatedKeys.getInt(1)); // Asignar el ID generado por la BD
                    }
                }
                return entrenador;
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar entrenador: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean alta(Entrenador entrenador) {
        return agregar(entrenador)!=null;
    }

    @Override
    public boolean baja(Entrenador entrenador) {
        String sql = "DELETE FROM entrenador WHERE id = ? ";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, entrenador.getId());


            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al dar de baja (eliminar) entrenador: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Entrenador consulta(Entrenador objeto) {
        String sql = "SELECT * FROM entrenador WHERE id = ? OR dni = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, objeto.getId());
            pstmt.setString(2, objeto.getDni());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Entrenador e = new Entrenador();
                    e.setId(rs.getInt("id"));
                    e.setNombre(rs.getString("nombre"));
                    e.setApellido(rs.getString("apellido"));
                    e.setDni(rs.getString("dni"));
                    e.setEmail(rs.getString("email"));
                    e.setPassword(rs.getString("password"));
                    e.setFechaDeNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    e.setActivo(rs.getBoolean("activo"));
                    e.setEspecialidad(rs.getString("especialidad"));
                    e.setSalario(rs.getDouble("salario"));
                    return e;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar entrenador: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean modificar(Entrenador entrenador) {
        String sql = "UPDATE entrenador SET nombre = ?, apellido = ?, dni= ?, email = ?, password = ?, fecha_nacimiento = ?, activo = ?, especialidad = ?, salario = ? WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entrenador.getNombre());
            pstmt.setString(2, entrenador.getApellido());
            pstmt.setString(3,entrenador.getDni());
            pstmt.setString(4, entrenador.getEmail());
            pstmt.setString(5, entrenador.getPassword());
            pstmt.setDate(6, java.sql.Date.valueOf(entrenador.getFechaDeNacimiento()));
            pstmt.setBoolean(7, entrenador.isActivo());
            pstmt.setString(8, entrenador.getEspecialidad());
            pstmt.setDouble(9, entrenador.getSalario());
            pstmt.setInt(10, entrenador.getId());


            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar entrenador: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Entrenador> listado() {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenador";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Entrenador e = new Entrenador();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setDni(rs.getString("dni"));
                e.setEmail(rs.getString("email"));
                e.setPassword(rs.getString("password"));
                e.setFechaDeNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                e.setActivo(rs.getBoolean("activo"));
                e.setEspecialidad(rs.getString("especialidad"));
                e.setSalario(rs.getDouble("salario"));

                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar entrenadores: " + e.getMessage());
        }
        return lista;
    }
}
