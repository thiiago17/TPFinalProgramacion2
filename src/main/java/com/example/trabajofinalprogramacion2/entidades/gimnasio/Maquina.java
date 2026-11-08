package com.example.trabajofinalprogramacion2.entidades.gimnasio;

import java.util.Objects;

public class Maquina {
    private int id;
    private String nombre;
    private String descripcion;
    private double pesoLimite;
    private String grupoMuscular;

    // CONSTRUCTORES

    public Maquina() {
    }

    //con descripcion y nombre
    public Maquina(String nombre, String descripcion, double pesoLimite, String grupoMuscular) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
    }

    public Maquina(int id, String nombre, String descripcion, double pesoLimite, String grupoMuscular) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
    }

    //sin descripcion y con nombre

    public Maquina(int id, String nombre, double pesoLimite, String grupoMuscular) {
        this.id = id;
        this.nombre = nombre;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
    }

    public Maquina(String nombre, double pesoLimite, String grupoMuscular) {
        this.nombre = nombre;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
    }


// GETTERS

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPesoLimite() {
        return pesoLimite;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPesoLimite(double pesoLimite) {
        this.pesoLimite = pesoLimite;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }


    //EQUALS, HASHCODE Y TOSTRING

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Maquina maquina = (Maquina) o;
        return id == maquina.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Maquina{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", pesoLimite=" + pesoLimite +
                ", grupoMuscular='" + grupoMuscular + '\'' +
                '}';
    }

    // MOSTRAR

    // METODOS
}
