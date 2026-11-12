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
    private List<Gasto> gastos; // despues lo borro, va a la collection
    private List<Gasto> historialGastos; // despues lo borro, va a la collection
    private List<Sucursal> sucursales; // despues lo borro, va a la collection
    private int estado;

    // CONSTRUCTORES

    public Gimnasio() {
        this.gastos = new ArrayList<>();
        this.historialGastos = new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.estado = 1;
    }

    public Gimnasio(int id, String nombre, int estado) { // para selects
        setId(id);
        setNombre(nombre);
        setEstado(estado);
        this.gastos = new ArrayList<>();
        this.historialGastos = new ArrayList<>();
        this.sucursales = new ArrayList<>();
    }

    public Gimnasio(String nombre) { // para inserts (sin id, ni estado)
        setNombre(nombre);
        this.gastos = new ArrayList<>();
        this.historialGastos = new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.estado = 1;
    }

    public Gimnasio(int id, int estado) {
        setId(id);
        setEstado(estado);
        this.gastos = new ArrayList<>();
        this.historialGastos = new ArrayList<>();
        this.sucursales = new ArrayList<>();
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public List<Gasto> getHistorialGastos() {
        return historialGastos;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
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

    public void setGastos(List<Gasto> gastos) {
        if (gastos == null) {
            throw new ListadoNuloException("La lista de gastos no puede ser nula.");
        }
        this.gastos = gastos;
    }

    public void setHistorialGastos(List<Gasto> historialGastos) {
        if (historialGastos == null) {
            throw new ListadoNuloException("El historial de gastos no puede ser nulo.");
        }
        this.historialGastos = historialGastos;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        if (sucursales == null) {
            throw new ListadoNuloException("El listado de sucursales no puede ser nulo.");
        }
        this.sucursales = sucursales;
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

    // METODOS

    private double calcularTotal(List<Gasto> listaGastos) throws ListadoNuloException {
        if (listaGastos == null) {
            throw new ListadoNuloException("La lista de gastos es null");
        }

        double total = 0.0;
        for (Gasto gasto : listaGastos) {
            if (gasto != null) {
                total = total + gasto.getMonto();
            }
        }

        return total;
    }

    private double calcularTotalPorTipo(List<Gasto> listaGastos, TipoGasto tipo) throws ListadoNuloException, ObjetoNuloException {
        if (listaGastos == null) {
            throw new ListadoNuloException("La lista de gastos es null");
        }
        if (tipo == null) {
            throw new ObjetoNuloException("El tipo de gasto a filtrar no puede ser nulo");
        }

        double total = 0.0;
        for (Gasto gasto : listaGastos) {
            if (gasto != null && gasto.getTipoGasto() == tipo) {
                total = total + gasto.getMonto();
            }
        }

        return total;
    }

    // estos metodos despues van a ir en collection
    public double calcularTotalMensual() throws ListadoNuloException {
        return calcularTotal(this.gastos);
    }

    public double calcularTotalHistorico() throws ListadoNuloException {
        return calcularTotal(this.historialGastos);
    }

    public double calcularTotalMensualPorTipo(TipoGasto tipo) throws ListadoNuloException, ObjetoNuloException {
        return calcularTotalPorTipo(this.gastos, tipo);
    }

    public double calcularTotalHistoricoPorTipo(TipoGasto tipo) throws ListadoNuloException, ObjetoNuloException {
        return calcularTotalPorTipo(this.historialGastos, tipo);
    }
}