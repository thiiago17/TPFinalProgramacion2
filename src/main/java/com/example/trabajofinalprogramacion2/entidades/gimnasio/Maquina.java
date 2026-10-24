package com.example.trabajofinalprogramacion2.entidades.gimnasio;

public class Maquina {
    private String nombre;
    private String descripcion;
    private double pesoLimite;
    private String grupoMuscular;

    // CONSTRUCTORES

    public Maquina() {
    }

    public Maquina(String nombre, String descripcion, double pesoLimite, String grupoMuscular) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoLimite = pesoLimite;
        this.grupoMuscular = grupoMuscular;
    }

    // GETTERS

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

    // MOSTRAR

    // METODOS
}
