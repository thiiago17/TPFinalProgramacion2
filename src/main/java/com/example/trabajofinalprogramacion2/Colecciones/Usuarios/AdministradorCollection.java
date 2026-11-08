package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;

import java.time.LocalDate;

public class AdministradorCollection extends UsuariosCollection<Administrador> {

    // MODIFICAR FECHA DE INGRESO

    public Administrador modificarFechaIngreso(int id, LocalDate fechaIngreso) {

        Administrador admin = buscar(id);

        if (admin == null || fechaIngreso == null) {
            return null;
        }

        admin.setFechaIngreso(fechaIngreso);

        return admin;
    }

}
