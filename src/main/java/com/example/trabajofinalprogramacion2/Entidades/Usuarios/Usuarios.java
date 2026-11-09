package com.example.trabajofinalprogramacion2.Entidades.Usuarios;

import java.time.LocalDate;
import java.time.Period;

public abstract class Usuarios {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String password;
    private LocalDate fechaDeNacimiento;
    private boolean activo;

    // CONSTRUCTORES

    public Usuarios() {
    }

    public Usuarios(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento,  boolean activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.activo = activo;
    }

    public Usuarios(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo) {
        this.id = id;
        setNombre(nombre);
        setApellido(apellido);
        setDni(dni);
        setEmail(email);
        setPassword(password);
        setFechaDeNacimiento(fechaDeNacimiento);
        setActivo(activo);
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getNombre() {return nombre;}

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

    public boolean isActivo() {
        return activo;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido;
    }

    //Con el matches exige que sean entre 7 y 8 numeros
    public void setDni(String dni) {
        if (dni == null || !dni.matches("\\d{7,8}")) {
            throw new IllegalArgumentException("El DNI debe contener solo números y tener entre 7 y 8 dígitos.");
        }
        this.dni = dni;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@") || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email ingresado no es válido.");
        }
        this.email = email;
    }

    //Valida que contenga al menos 4 caracteres y el matches busca numeros en el String
    public void setPassword(String password) {
        if (password == null || password.length() < 4 || !password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres y contener al menos un número.");
        }
        this.password = password;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        if (fechaDeNacimiento != null && fechaDeNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser en el futuro.");
        }
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // MOSTRAR

    // METODOS

    public String verEstadoTexto (){
        if (this.activo){
            return "Activo";
        }else {
            return "Inactivo";
        }
    }

    public int calcularEdad()
    {
        LocalDate hoy = LocalDate.now();
        return Period.between(this.fechaDeNacimiento, hoy).getYears();
    }

    public void mostrarMenu()
    {

    }
}
