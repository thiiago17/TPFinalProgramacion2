package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import com.example.trabajofinalprogramacion2.Excepciones.ListadoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.TipoGasto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gimnasio {
    private int id;
    private String nombre;
    private int estado;

    // CONSTRUCTORES

    public Gimnasio() {
        this.estado = 1;
    }

    public Gimnasio(int id, String nombre, int estado) { // para selects
        setId(id);
        setNombre(nombre);
        setEstado(estado);
    }

    public Gimnasio(String nombre) { // para inserts (sin id, ni estado)
        setNombre(nombre);
        this.estado = 1;
    }

    public Gimnasio(int id, int estado) {
        setId(id);
        setEstado(estado);
    }

    public Gimnasio(int id) {
        setId(id);
    }
    // GETTERS

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEstado() {
        return estado;
    }

    // SETTERS

    public void setId(int id) {
        if (id < 0) {
            throw new ValorNoValidoException("El ID del gimnasio no puede ser negativo.");
        }
        this.id = id;
    }

    public void setNombre(String nombre) {
        if (nombre != null && nombre.isBlank()) {
            throw new ValorNoValidoException("El nombre del gimnasio no puede estar vacío.");
        }
        this.nombre = nombre;
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
        Gimnasio gimnasio = (Gimnasio) o;
        return id == gimnasio.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gimnasio{" +
                "id=" + id +
                ", nombre='" + (nombre != null ? nombre : "-") + '\'' +
                ", estado=" + estado +
                '}';
    }
}