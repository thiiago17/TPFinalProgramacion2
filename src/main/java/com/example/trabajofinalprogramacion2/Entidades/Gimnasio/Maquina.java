package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

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
        this.estado = 1;
    }

    // con descripcion y nombre
    public Maquina(String nombre, String descripcion, double pesoLimite, String grupoMuscular) {
        setNombre(nombre);
        setDescripcion(descripcion);
        setPesoLimite(pesoLimite);
        setGrupoMuscular(grupoMuscular);
        this.estado = 1;
    }

    public Maquina(int id, String nombre, String descripcion, double pesoLimite, String grupoMuscular, int estado) {
        setId(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        setPesoLimite(pesoLimite);
        setGrupoMuscular(grupoMuscular);
        setEstado(estado);
    }

    // sin descripcion y con nombre

    public Maquina(int id, String nombre, double pesoLimite, String grupoMuscular, int estado) {
        setId(id);
        setNombre(nombre);
        setPesoLimite(pesoLimite);
        setGrupoMuscular(grupoMuscular);
        setEstado(estado);
    }

    public Maquina(String nombre, double pesoLimite, String grupoMuscular) {
        setNombre(nombre);
        setPesoLimite(pesoLimite);
        setGrupoMuscular(grupoMuscular);
        this.estado = 1;
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
        if (id < 0) {
            throw new ValorNoValidoException("El ID de la máquina no puede ser negativo.");
        }
        this.id = id;
    }

    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new ObjetoNuloException("El nombre de la máquina no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new ValorNoValidoException("El nombre de la máquina no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && descripcion.isBlank()) {
            throw new ValorNoValidoException("La descripción de la máquina no puede estar vacía.");
        }
        this.descripcion = descripcion;
    }

    public void setPesoLimite(double pesoLimite) {
        if (pesoLimite < 0.0) {
            throw new ValorNoValidoException("El peso límite no puede ser un valor negativo.");
        }
        this.pesoLimite = pesoLimite;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        if (grupoMuscular == null) {
            throw new ObjetoNuloException("El grupo muscular no puede ser nulo.");
        }
        if (grupoMuscular.isBlank()) {
            throw new ValorNoValidoException("El grupo muscular no puede estar vacío.");
        }
        this.grupoMuscular = grupoMuscular;
    }

    public void setEstado(int estado) {
        if (estado != 0 && estado != 1) {
            throw new ValorNoValidoException("El estado debe ser 0 (inactivo) o 1 (activo).");
        }
        this.estado = estado;
    }

    // EQUALS, HASHCODE Y TOSTRING

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
                ", nombre='" + (nombre != null ? nombre : "-") + '\'' +
                ", descripcion='" + (descripcion != null ? descripcion : "-") + '\'' +
                ", pesoLimite=" + pesoLimite +
                ", grupoMuscular='" + (grupoMuscular != null ? grupoMuscular : "-") + '\'' +
                ", estado=" + estado +
                '}';
    }
}
