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
    private List<Gasto> gastos;
    private List<Gasto> historialGastos;
    private List<Sucursal> sucursales;

    // CONSTRUCTORES

    public Gimnasio() {
        this.gastos= new ArrayList<>();
        this.historialGastos= new ArrayList<>();
        this.sucursales = new ArrayList<>();
    }

    public Gimnasio(int id, String nombre) { //para selects
        this.id = id;
        this.nombre = nombre;
        this.gastos= new ArrayList<>();
        this.historialGastos= new ArrayList<>();
        this.sucursales = new ArrayList<>();
    }

    public Gimnasio(String nombre) { //para inserts (sin id)
        this.nombre = nombre;
        this.gastos= new ArrayList<>();
        this.historialGastos= new ArrayList<>();
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
                "id=" + id +
                ", nombre='" + nombre + '\'';
    }

    // MOSTRAR

    // METODOS

    private double calcularTotal(List<Gasto> listaGastos){ //metodo privado, lo llama luego el metodo publico
        if(listaGastos == null){
            throw new ListadoNuloException("La lista de gastos es null");
        }

        double total=0.0;
        for(Gasto gasto : listaGastos){
            total=total+gasto.getMonto();
        }


        return total;
    }

    private double calcularTotalPorTipo(List<Gasto> listaGastos, TipoGasto tipo){ //metodo privado, lo llama luego el metodo publico
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

    public double calcularTotalMensual(){
        return calcularTotal(this.gastos);
    }

    public double calcularTotalHistorico(){
        return calcularTotal(this.historialGastos);
    }

    public double calcularTotalMensualPorTipo(TipoGasto tipo){
        return calcularTotalPorTipo(this.gastos, tipo);
    }

    public double calcularTotalHistoricoPorTipo(TipoGasto tipo){
        return calcularTotalPorTipo(this.historialGastos, tipo);
    }

    //Aca van los metodos de agregado/eliminacion de los listados propios de cada objeto Gimnasio
}
