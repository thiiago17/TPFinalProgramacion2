package com.example.trabajofinalprogramacion2.entidades.usuarios;

import java.time.LocalDate;

public class Administrador extends Usuarios {
    private LocalDate fechaIngreso;

    // CONSTRUCTORES

    public Administrador() {
    }

    public Administrador(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, int edad, boolean activo, LocalDate fechaIngreso) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, edad, activo);
        this.fechaIngreso = fechaIngreso;
    }

    // GETTERS

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    // SETTERS

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    // MOSTRAR

    // METODOS
}
