package com.example.trabajofinalprogramacion2.basededatos.Administadores;

import com.example.trabajofinalprogramacion2.basededatos.ConexionBD.ConexionBD;
import com.example.trabajofinalprogramacion2.entidades.usuarios.Administrador;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdministradorDAO implements ABCMLDAO<Administrador> {

    public boolean agregar(Administrador admin)
    {
        String sql="Insert into Administrador(nombre,apellido,dni,email,contrasenia,fechaDeNacimiento,activo,fechaIngreso) values (?,?,?,?,?,?,?,?,?)";

        try(Connection conexion= ConexionBD.conectar();
            PreparedStatement ps= conexion.prepareStatement(sql))
        {
            ps.setString(1, admin.getNombre());
            ps.setString(2, admin.getApellido());
            ps.setString(3, admin.getDni());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPassword());
            ps.setDate(6, Date.valueOf(admin.getFechaDeNacimiento()));
            ps.setBoolean(7, admin.isActivo());
            ps.setDate(8, Date.valueOf(admin.getFechaIngreso()));

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
    public boolean alta(Administrador objeto) {
        return false;
    }

    @Override
    public boolean baja(Administrador objeto) {
        return false;
    }

    @Override
    public Administrador consulta(String dni) {
        return null;
    }

    @Override
    public boolean modificacion(Administrador objeto) {
        return false;
    }

    @Override
    public List<Administrador> listado() {
        return List.of();
    }
}
