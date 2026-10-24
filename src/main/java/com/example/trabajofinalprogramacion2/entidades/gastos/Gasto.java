package com.example.trabajofinalprogramacion2.entidades.gastos;

import java.time.LocalDate;

public class Gasto {
    private String detalles;
    private double monto;
    private TipoGasto gasto;
    private LocalDate fecha;

    // CONSTRUCTORES

    public Gasto() {
    }

    public Gasto(String detalles, double monto, TipoGasto gasto, LocalDate fecha) {
        this.detalles = detalles;
        this.monto = monto;
        this.gasto = gasto;
        this.fecha = fecha;
    }

    // GETTERS

    public String getDetalles() {
        return detalles;
    }

    public double getMonto() {
        return monto;
    }

    public TipoGasto getGasto() {
        return gasto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    // SETTERS

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setGasto(TipoGasto gasto) {
        this.gasto = gasto;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // MOSTRAR

    // METODOS

}
