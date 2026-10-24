package com.example.trabajofinalprogramacion2.entidades.gimnasio;

import com.example.trabajofinalprogramacion2.entidades.gastos.Gasto;

import java.util.List;

public class Gimnasio {
    private String nombre;
    private List<Gasto> gastos;
    private List<Gasto> historialGastos;

    // CONSTRUCTORES

    public Gimnasio() {
    }

    public Gimnasio(String nombre, List<Gasto> gastos, List<Gasto> historialGastos) {
        this.nombre = nombre;
        this.gastos = gastos;
        this.historialGastos = historialGastos;
    }

    // GETTERS

    public String getNombre() {
        return nombre;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public List<Gasto> getHistorialGastos() {
        return historialGastos;
    }

    // SETTERS

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public void setHistorialGastos(List<Gasto> historialGastos) {
        this.historialGastos = historialGastos;
    }

    // MOSTRAR

    // METODOS
}
