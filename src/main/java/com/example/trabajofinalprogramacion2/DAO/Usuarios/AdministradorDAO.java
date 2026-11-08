package com.example.trabajofinalprogramacion2.DAO.Usuarios;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO implements ABCMLDAO<Administrador> {

    public Administrador agregar(Administrador admin)
    {
        String sql="Insert into Administrador(nombre,apellido,dni,email,contrasenia,fechaDeNacimiento,activo,fechaIngreso) values (?,?,?,?,?,?,?,?)";

        try(Connection conexion= ConexionBD.conectar();
            PreparedStatement ps= conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, admin.getNombre());
            ps.setString(2, admin.getApellido());
            ps.setString(3, admin.getDni());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPassword());
            ps.setDate(6, Date.valueOf(admin.getFechaDeNacimiento()));
            ps.setBoolean(7, admin.isActivo());
            ps.setDate(8, Date.valueOf(admin.getFechaIngreso()));

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next())
                    {
                        admin.setId(rs.getInt(1));
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return null;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }
            return admin;

        }   catch(SQLException e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return null;
            }
    }

    // ALTA

    @Override
    public boolean alta(Administrador admin) {
        String sql="update Administrador set activo = true where id_administrador = ?";

        try(Connection conexion= ConexionBD.conectar();
            PreparedStatement ps= conexion.prepareStatement(sql))
        {
            ps.setInt(1, admin.getId());

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

    // BAJA

    @Override
    public boolean baja(Administrador admin) {

        String sql = "UPDATE Administrador SET activo = false WHERE id_administrador = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, admin.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al dar de baja al administrador: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // CONSULTA

    @Override
    public Administrador consulta(Administrador admin) {

        String sql = "SELECT * FROM Administrador WHERE id_administrador = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, admin.getId());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearAdministrador(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar administrador: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    // MODIFICACION

    @Override
    public boolean modificar(Administrador admin) {

        String sql = "UPDATE Administrador SET nombre = ?, apellido = ?, dni = ?,  email = ?, contrasenia = ?, fechaDeNacimiento = ?, activo = ?, fechaIngreso = ? WHERE id_administrador = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, admin.getNombre());
            ps.setString(2, admin.getApellido());
            ps.setString(3, admin.getDni());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPassword());
            ps.setDate(6, Date.valueOf(admin.getFechaDeNacimiento()));
            ps.setBoolean(7, admin.isActivo());
            ps.setDate(8, Date.valueOf(admin.getFechaIngreso()));

            ps.setInt(9, admin.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar administrador: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // LISTADO

    @Override
    public List<Administrador> listado() {

        List<Administrador> administradores = new ArrayList<>();

        String sql = "SELECT * FROM Administrador";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                administradores.add(mapearAdministrador(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar administradores: " + e.getMessage());
            e.printStackTrace();
        }

        return administradores;
    }


    // METODO AUXILIAR PARA CONVERTIR RESULTSET EN ADMINISTRADOR

    private Administrador mapearAdministrador(ResultSet rs) throws SQLException {

        Administrador admin = new Administrador();

        admin.setNombre(rs.getString("nombre"));
        admin.setApellido(rs.getString("apellido"));
        admin.setDni(rs.getString("dni"));
        admin.setEmail(rs.getString("email"));
        admin.setPassword(rs.getString("contrasenia"));
        admin.setFechaDeNacimiento(rs.getDate("fechaDeNacimiento").toLocalDate());
        admin.setActivo(rs.getBoolean("activo"));
        admin.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());
        admin.setId(rs.getInt("id_administrador"));

        return admin;
    }

    // BUSCAR EN DAO

    public Administrador buscarDao(int id)
    {
        String sql="select * from Administrador where id_administrador = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql))
        {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearAdministrador(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Error al buscar administrador: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

