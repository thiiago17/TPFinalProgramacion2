package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import java.util.Objects;

public class Maquina {
    private int id;
    private String nombre;
    private String descripcion;
    private double pesoLimite;
    private String grupoMuscular;
    private int estado;

    // CONSTRUCTORES

    public Maquina() {
        this.estado=1;
    }

    //con descripcion y nombre
    public Maquina(String nombre, String descripcion, double pesoLimite, String grupoMuscular) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
        this.estado=1;
    }

    public Maquina(int id, String nombre, String descripcion, double pesoLimite, String grupoMuscular, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
        this.estado= estado;
    }

    //sin descripcion y con nombre

    public Maquina(int id, String nombre, double pesoLimite, String grupoMuscular, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
        this.estado=estado;
    }

    public Maquina(String nombre, double pesoLimite, String grupoMuscular) {
        this.nombre = nombre;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
        this.estado=1;
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

    public int getEstado() {
        return estado;
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

    public void setEstado(int estado) {
        this.estado = estado;
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
                ", estado=" + estado +
                '}';
    }
// MOSTRAR

    // METODOS
}
