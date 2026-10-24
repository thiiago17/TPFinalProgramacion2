package com.example.trabajofinalprogramacion2.entidades.gimnasio;

import com.example.trabajofinalprogramacion2.entidades.gastos.Gasto;

import java.util.List;
import java.util.Map;

public class Sucursal extends Gimnasio {
    private Map<Maquina, Integer> maquinas;
    private String calle;
    private int altura;
    private String ciudad;

    // CONSTRUCTORES

    public Sucursal() {
    }

    public Sucursal(String nombre, List<Gasto> gastos, List<Gasto> historialGastos, Map<Maquina, Integer> maquinas, String calle, int altura, String ciudad) {
        super(nombre, gastos, historialGastos);
        this.maquinas = maquinas;
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
    }

    // GETTERS

    public Map<Maquina, Integer> getMaquinas() {
        return maquinas;
    }

    public String getCalle() {
        return calle;
    }

    public int getAltura() {
        return altura;
    }

    public String getCiudad() {
        return ciudad;
    }

    // MOSTRAR

    public void setMaquinas(Map<Maquina, Integer> maquinas) {
        this.maquinas = maquinas;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    // METODOS
}
