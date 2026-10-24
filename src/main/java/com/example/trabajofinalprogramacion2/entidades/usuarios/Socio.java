package com.example.trabajofinalprogramacion2.entidades.usuarios;

import com.example.trabajofinalprogramacion2.entidades.membresias.Membresia;

import java.time.LocalDate;

public class Socio extends Usuarios {
    private Double pesoActual;
    private Double altura;
    private LocalDate fechaAlta;
    private Membresia membresia;

    // CONSTRUCTORES

    public Socio() {
    }

    public Socio(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, int edad, boolean activo, Double pesoActual, Double altura, LocalDate fechaAlta, Membresia membresia) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, edad, activo);
        this.pesoActual = pesoActual;
        this.altura = altura;
        this.fechaAlta = fechaAlta;
        this.membresia = membresia;
    }

    // GETTERS

    public Double getPesoActual() {
        return pesoActual;
    }

    public Double getAltura() {
        return altura;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    // SETTERS

    public void setPesoActual(Double pesoActual) {
        this.pesoActual = pesoActual;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    // MOSTRAR

    // METODOS
}
