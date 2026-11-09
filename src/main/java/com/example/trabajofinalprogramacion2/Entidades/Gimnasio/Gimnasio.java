package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import com.example.trabajofinalprogramacion2.Excepciones.ListadoNuloException;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.TipoGasto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gimnasio {
    private int id;
    private String nombre;
    private List<Gasto> gastos; //despues lo borro, va a la collection
    private List<Gasto> historialGastos; //despues lo borro, va a la collection
    private List<Sucursal> sucursales; //despues lo borro, va a la collection
    private int estado;

    // CONSTRUCTORES

    public Gimnasio() {
        this.gastos= new ArrayList<>();
        this.historialGastos= new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.estado = 1;
    }

    public Gimnasio(int id, String nombre, int estado) { //para selects
        this.id = id;
        this.nombre = nombre;
        this.gastos= new ArrayList<>();
        this.historialGastos= new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.estado = estado;
    }

    public Gimnasio(String nombre) { //para inserts (sin id, ni estado)
        this.nombre = nombre;
        this.gastos= new ArrayList<>();
        this.historialGastos= new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.estado = 1;
    }

    public Gimnasio(int id, int estado) {
        this.id = id;
        this.estado = estado;
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

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public void setHistorialGastos(List<Gasto> historialGastos) {
        this.historialGastos = historialGastos;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    //EQUALS, HASHCODE Y TOSTRING


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
                "nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", id=" + id +
                '}';
    }

    // MOSTRAR

    // METODOS

    private double calcularTotal(List<Gasto> listaGastos) throws ListadoNuloException{ //metodo privado, lo llama luego el metodo publico
        if(listaGastos == null){
            throw new ListadoNuloException("La lista de gastos es null");
        }

        double total=0.0;
        for(Gasto gasto : listaGastos){
            total=total+gasto.getMonto();
        }


        return total;
    }

    private double calcularTotalPorTipo(List<Gasto> listaGastos, TipoGasto tipo) throws ListadoNuloException{ //metodo privado, lo llama luego el metodo publico
        if(listaGastos == null){
            throw new ListadoNuloException("La lista de gastos es null");
        }

        double total=0.0;
        for(Gasto gasto : listaGastos){
            if(gasto.getTipoGasto() == tipo){
                total=total+gasto.getMonto();
            }
        }

        return total;
    }

    //estos metodos despues van a ir en collection
    public double calcularTotalMensual() throws ListadoNuloException{
        return calcularTotal(this.gastos);
    }

    public double calcularTotalHistorico() throws ListadoNuloException{
        return calcularTotal(this.historialGastos);
    }

    public double calcularTotalMensualPorTipo(TipoGasto tipo) throws ListadoNuloException{
        return calcularTotalPorTipo(this.gastos, tipo);
    }

    public double calcularTotalHistoricoPorTipo(TipoGasto tipo) throws ListadoNuloException{
        return calcularTotalPorTipo(this.historialGastos, tipo);
    }


}
