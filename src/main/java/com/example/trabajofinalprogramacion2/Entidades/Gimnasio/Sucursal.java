package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;

import java.util.List;
import java.util.Map;

public class Sucursal extends Gimnasio {
    private Map<Maquina, Integer> maquinas; //despues lo borro, esto va a la clase collection
    private String calle;
    private int altura;
    private String ciudad;
    private int idGimnasio;

    // CONSTRUCTORES

    public Sucursal() {
    }

    public Sucursal(int id, String nombre, int estado, String calle, int altura, String ciudad, int idGimnasio) {
        super(id, nombre, estado);
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.idGimnasio = idGimnasio;
    }

    public Sucursal(String nombre, String calle, int altura, String ciudad, int idGimnasio) {
        super(nombre);
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.idGimnasio = idGimnasio;
    }

    public Sucursal(String calle, int altura, String ciudad, int idGimnasio) {
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.idGimnasio = idGimnasio;
    }

    public Sucursal(int id, int estado, String calle, int altura, String ciudad, int idGimnasio) {
        super(id, estado);
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.idGimnasio = idGimnasio;
    }
// GETTERS

    public String getCalle() {
        return calle;
    }

    public int getAltura() {
        return altura;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getIdGimnasio() {
        return idGimnasio;
    }

    // SETTERS

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setIdGimnasio(int idGimnasio) {
        this.idGimnasio = idGimnasio;
    }

    // EQUALS, HASHCODE Y TOSTRING

    @Override
    public String toString() {
        return super.toString() + "Sucursal{" +
                "calle='" + calle + '\'' +
                ", altura=" + altura +
                ", ciudad='" + ciudad + '\'' +
                ", idGimnasio=" + idGimnasio +
                '}';
    }

    // METODOS
}
