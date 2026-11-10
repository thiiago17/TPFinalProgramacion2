package com.example.trabajofinalprogramacion2.DAO.Usuarios;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.DAO.FechasBD;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO implements ABCMLDAO<Administrador> {

    public Administrador agregar(Administrador admin)
    {
        String sql="Insert into administrador(nombre,apellido,dni,email,contrasenia,fechaDeNacimiento,activo,fechaIngreso,es_admin_sistema,id_gimnasio) values (?,?,?,?,?,?,?,?,?,?)";

        try(Connection conexion= ConexionBD.conectar();
            PreparedStatement ps= conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, admin.getNombre());
            ps.setString(2, admin.getApellido());
            ps.setString(3, admin.getDni());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPassword());
            FechasBD.setFecha(ps, 6, admin.getFechaDeNacimiento());
            ps.setBoolean(7, admin.isActivo());
            FechasBD.setFecha(ps, 8, admin.getFechaIngreso());
            ps.setBoolean(9, admin.isEsAdminSistema());
            setIdGimnasio(ps, 10, admin.getIdGimnasio());

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
        String sql="update administrador set activo = true where id_administrador = ?";

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

        String sql = "UPDATE administrador SET activo = false WHERE id_administrador = ?";

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
    public Administrador consulta(int clavePrimaria) {

        String sql = "SELECT * FROM administrador WHERE id_administrador = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, clavePrimaria);

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

        String sql = "UPDATE administrador SET nombre = ?, apellido = ?, dni = ?,  email = ?, contrasenia = ?, fechaDeNacimiento = ?, activo = ?, fechaIngreso = ?, es_admin_sistema = ?, id_gimnasio = ? WHERE id_administrador = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, admin.getNombre());
            ps.setString(2, admin.getApellido());
            ps.setString(3, admin.getDni());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPassword());
            FechasBD.setFecha(ps, 6, admin.getFechaDeNacimiento());
            ps.setBoolean(7, admin.isActivo());
            FechasBD.setFecha(ps, 8, admin.getFechaIngreso());
            ps.setBoolean(9, admin.isEsAdminSistema());
            setIdGimnasio(ps, 10, admin.getIdGimnasio());

            ps.setInt(11, admin.getId());

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

        String sql = "SELECT * FROM administrador";

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
        admin.setFechaDeNacimiento(FechasBD.leerFecha(rs, "fechaDeNacimiento"));
        admin.setActivo(rs.getBoolean("activo"));
        admin.setFechaIngreso(FechasBD.leerFecha(rs, "fechaIngreso"));
        admin.setId(rs.getInt("id_administrador"));
        admin.setEsAdminSistema(rs.getBoolean("es_admin_sistema"));

        int idGimnasio = rs.getInt("id_gimnasio");
        admin.setIdGimnasio(rs.wasNull() ? null : idGimnasio);

        return admin;
    }

    // El gimnasio puede ser null (administrador del sistema): en ese caso se guarda NULL en la BD
    private void setIdGimnasio(PreparedStatement ps, int posicion, Integer idGimnasio) throws SQLException {
        if (idGimnasio == null) {
            ps.setNull(posicion, Types.INTEGER);
        } else {
            ps.setInt(posicion, idGimnasio);
        }
    }
}

