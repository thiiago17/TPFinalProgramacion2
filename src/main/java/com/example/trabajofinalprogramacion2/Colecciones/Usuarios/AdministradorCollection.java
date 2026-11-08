package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.entidades.usuarios.Administrador;

import java.time.LocalDate;

public class AdministradorCollection extends UsuariosCollection<Administrador> {

    // MODIFICAR FECHA DE INGRESO

    public Administrador modificarFechaIngreso(int id, LocalDate fechaIngreso) {

        Administrador admin = (Administrador) buscar(id);

        if (admin == null || fechaIngreso == null) {
            return null;
        }

        admin.setFechaIngreso(fechaIngreso);

        return admin;
    }

}
