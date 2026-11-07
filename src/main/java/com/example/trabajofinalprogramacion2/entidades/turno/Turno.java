package com.example.trabajofinalprogramacion2.entidades.turno;

import com.example.trabajofinalprogramacion2.entidades.usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.entidades.usuarios.Socio;

import java.time.LocalDate;

public class Turno {
    private Socio socio;
    private Nutricionista nutricionista;
    private LocalDate fechaTurno;
    private boolean estado;
    private double precio;

    // CONSTRUCTORES

    public Turno(Socio socio, Nutricionista nutricionista, LocalDate fechaTurno, boolean estado, double precio) {
        this.socio = socio;
        this.nutricionista = nutricionista;
        this.fechaTurno = fechaTurno;
        this.estado = estado;
        this.precio = precio;
    }

    // GETTERS

    public Socio getSocio() {
        return socio;
    }

    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public boolean isEstado() {
        return estado;
    }

    public double getPrecio() {
        return precio;
    }

    // SETTERS

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // MOSTRAR

    // METODOS

}
