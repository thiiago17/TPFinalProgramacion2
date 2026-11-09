package com.example.trabajofinalprogramacion2.Entidades.Gastos;

import java.time.LocalDate;
import java.util.Objects;

public class Gasto {
    private int id;
    private String detalles;
    private double monto;
    private TipoGasto tipoGasto;
    private LocalDate fecha;
    private int idGimnasio;
    private int estado;

    // CONSTRUCTORES

    public Gasto() {
        this.estado=1;
    }

    //con detalles
    public Gasto(String detalles, double monto, TipoGasto gasto, LocalDate fecha, int idGimnasio) {
        this.detalles = detalles;
        this.monto = monto;
        this.tipoGasto = gasto;
        this.fecha = fecha;
        this.idGimnasio = idGimnasio;
        this.estado=1;
    }

    public Gasto(int id, String detalles, double monto, TipoGasto tipoGasto, LocalDate fecha, int idGimnasio) {
        this.id = id;
        this.detalles = detalles;
        this.monto = monto;
        this.tipoGasto = tipoGasto;
        this.fecha = fecha;
        this.idGimnasio = idGimnasio;
        this.estado=1;
    }

    //sin detalles
    public Gasto(int id, double monto, TipoGasto tipoGasto, LocalDate fecha, int idGimnasio) {
        this.id = id;
        this.monto = monto;
        this.tipoGasto = tipoGasto;
        this.fecha = fecha;
        this.idGimnasio = idGimnasio;
        this.estado=1;
    }

    public Gasto(double monto, TipoGasto tipoGasto, LocalDate fecha, int idGimnasio) {
        this.monto = monto;
        this.tipoGasto = tipoGasto;
        this.fecha = fecha;
        this.idGimnasio = idGimnasio;
        this.estado=1;
    }
// GETTERS

    public int getId() {
        return id;
    }

    public String getDetalles() {
        return detalles;
    }

    public double getMonto() {
        return monto;
    }

    public TipoGasto getTipoGasto() {
        return tipoGasto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getIdGimnasio() {
        return idGimnasio;
    }

    public int getEstado() {
        return estado;
    }
    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setTipoGasto(TipoGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setIdGimnasio(int idGimnasio) {
        this.idGimnasio = idGimnasio;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    //EQUALS, HASHCODE Y TOSTRING

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Gasto gasto = (Gasto) o;
        return id == gasto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", detalles='" + detalles + '\'' +
                ", monto=" + monto +
                ", tipoGasto=" + tipoGasto +
                ", fecha=" + fecha +
                ", idGimnasio=" + idGimnasio +
                '}';
    }

    // MOSTRAR

    // METODOS

}
