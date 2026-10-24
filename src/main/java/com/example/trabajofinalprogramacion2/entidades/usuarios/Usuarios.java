package com.example.trabajofinalprogramacion2.entidades.usuarios;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Usuarios {
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String password;
    private LocalDate fechaDeNacimiento;
    private int edad;
    private boolean activo;

    // CONSTRUCTORES

    public Usuarios() {
    }

    public Usuarios(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, int edad, boolean activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.edad = edad;
        this.activo = activo;
    }

    // GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isActivo() {
        return activo;
    }

    // SETTERS

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // MOSTRAR

    // METODOS

    public abstract void mostrarMenu();
}
