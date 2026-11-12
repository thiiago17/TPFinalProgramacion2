package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import com.example.trabajofinalprogramacion2.Excepciones.ListadoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.util.Map;

public class Sucursal extends Gimnasio {
    private Map<Maquina, Integer> maquinas; // despues lo borro, esto va a la clase collection
    private String calle;
    private int altura;
    private String ciudad;
    private int idGimnasio;

    // CONSTRUCTORES

    public Sucursal() {
        super();
    }

    public Sucursal(int id, String nombre, int estado, String calle, int altura, String ciudad, int idGimnasio) {
        super(id, nombre, estado);
        setCalle(calle);
        setAltura(altura);
        setCiudad(ciudad);
        setIdGimnasio(idGimnasio);
    }

    public Sucursal(String nombre, String calle, int altura, String ciudad, int idGimnasio) {
        super(nombre);
        setCalle(calle);
        setAltura(altura);
        setCiudad(ciudad);
        setIdGimnasio(idGimnasio);
    }

    public Sucursal(String calle, int altura, String ciudad, int idGimnasio) {
        super();
        setCalle(calle);
        setAltura(altura);
        setCiudad(ciudad);
        setIdGimnasio(idGimnasio);
    }

    public Sucursal(int id, int estado, String calle, int altura, String ciudad, int idGimnasio) {
        super(id, estado);
        setCalle(calle);
        setAltura(altura);
        setCiudad(ciudad);
        setIdGimnasio(idGimnasio);
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
        if (calle == null) {
            throw new ObjetoNuloException("La calle no puede ser nula.");
        }
        if (calle.isBlank()) {
            throw new ValorNoValidoException("La calle no puede estar vacía.");
        }
        this.calle = calle;
    }

    public void setAltura(int altura) {
        if (altura < 0) {
            throw new ValorNoValidoException("La altura no puede ser un valor negativo.");
        }
        this.altura = altura;
    }

    public void setCiudad(String ciudad) {
        if (ciudad == null) {
            throw new ObjetoNuloException("La ciudad no puede ser nula.");
        }
        if (ciudad.isBlank()) {
            throw new ValorNoValidoException("La ciudad no puede estar vacía.");
        }
        this.ciudad = ciudad;
    }

    public void setIdGimnasio(int idGimnasio) {
        if (idGimnasio <= 0) {
            throw new ValorNoValidoException("El ID del gimnasio asociado no es válido.");
        }
        this.idGimnasio = idGimnasio;
    }

    // EQUALS, HASHCODE Y TOSTRING

    @Override
    public String toString() {
        return super.toString() + " Sucursal{" +
                "calle='" + (calle != null ? calle : "-") + '\'' +
                ", altura=" + altura +
                ", ciudad='" + (ciudad != null ? ciudad : "-") + '\'' +
                ", idGimnasio=" + idGimnasio +
                '}';
    }

    // METODOS
}