package com.example.trabajofinalprogramacion2.DAO.Usuarios;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.DAO.FechasBD;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NutricionistaDAO implements ABCMLDAO<Nutricionista> {

    @Override
    public Nutricionista agregar(Nutricionista nutricionista) {
        String sql = "INSERT INTO nutricionista (dni, nombre, apellido, email, contrasenia, fechaDeNacimiento, activo, salario, comision_por_consulta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nutricionista.getDni());
            ps.setString(2, nutricionista.getNombre());
            ps.setString(3, nutricionista.getApellido());
            ps.setString(4, nutricionista.getEmail());
            ps.setString(5, nutricionista.getPassword());
            FechasBD.setFecha(ps, 6, nutricionista.getFechaDeNacimiento());
            ps.setBoolean(7, nutricionista.isActivo());
            ps.setDouble(8, nutricionista.getSalario());
            ps.setDouble(9, nutricionista.getComisionPorConsulta());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        nutricionista.setId(generatedKeys.getInt(1)); // Asignar el ID generado por la BD
                    }
                }
                return nutricionista;
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar nutricionista: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean alta(Nutricionista nutricionista) {
        String sql="update nutricionista set activo = true where id_nutricionista = ?";

        try(Connection conexion= ConexionBD.conectar();
            PreparedStatement ps= conexion.prepareStatement(sql))
        {
            ps.setInt(1, nutricionista.getId());

            return ps.executeUpdate()>0;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean baja(Nutricionista nutricionista) {
        String sql = "UPDATE nutricionista SET ACTIVO = false WHERE id_nutricionista = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, nutricionista.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al dar de baja a nutricionista: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Nutricionista consulta(Nutricionista nutricionista) {
        String sql = "SELECT * FROM nutricionista WHERE id_nutricionista = ? OR dni = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, nutricionista.getId());
            ps.setString(2, nutricionista.getDni());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearNutricionista(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar nutricionista: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean modificar(Nutricionista nutricionista) {
        String sql = "UPDATE nutricionista SET nombre = ?, apellido = ?, dni = ?, email = ?, contrasenia = ?, fechaDeNacimiento = ?, activo = ?, salario = ?, comision_por_consulta = ? WHERE id_nutricionista = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nutricionista.getNombre());
            ps.setString(2, nutricionista.getApellido());
            ps.setString(3, nutricionista.getDni());
            ps.setString(4, nutricionista.getEmail());
            ps.setString(5, nutricionista.getPassword());
            FechasBD.setFecha(ps, 6, nutricionista.getFechaDeNacimiento());
            ps.setBoolean(7, nutricionista.isActivo());
            ps.setDouble(8, nutricionista.getSalario());
            ps.setDouble(9, nutricionista.getComisionPorConsulta());
            ps.setInt(10, nutricionista.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar nutricionista: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Nutricionista> listado() {
        List<Nutricionista> lista = new ArrayList<>();
        String sql = "SELECT * FROM nutricionista";

        try (Connection conexion = ConexionBD.conectar();
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearNutricionista(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar nutricionistas: " + e.getMessage());
        }
        return lista;
    }

    // Arma un Nutricionista a partir de la fila actual del ResultSet
    private Nutricionista mapearNutricionista(ResultSet rs) throws SQLException {
        Nutricionista n = new Nutricionista();
        n.setId(rs.getInt("id_nutricionista"));
        n.setNombre(rs.getString("nombre"));
        n.setApellido(rs.getString("apellido"));
        n.setDni(rs.getString("dni"));
        n.setEmail(rs.getString("email"));
        n.setPassword(rs.getString("contrasenia"));
        n.setFechaDeNacimiento(FechasBD.leerFecha(rs, "fechaDeNacimiento"));
        n.setActivo(rs.getBoolean("activo"));
        n.setSalario(rs.getDouble("salario"));
        n.setComisionPorConsulta(rs.getDouble("comision_por_consulta"));
        return n;
    }
}
