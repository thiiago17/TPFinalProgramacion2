package com.example.trabajofinalprogramacion2.Entidades.Gastos;

import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

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
        this.estado = 1;
    }

    // con detalles
    public Gasto(String detalles, double monto, TipoGasto gasto, LocalDate fecha, int idGimnasio) {
        setDetalles(detalles);
        setMonto(monto);
        setTipoGasto(gasto);
        setFecha(fecha);
        setIdGimnasio(idGimnasio);
        this.estado = 1;
    }

    public Gasto(int id, String detalles, double monto, TipoGasto tipoGasto, LocalDate fecha, int idGimnasio, int estado) {
        setId(id);
        setDetalles(detalles);
        setMonto(monto);
        setTipoGasto(tipoGasto);
        setFecha(fecha);
        setIdGimnasio(idGimnasio);
        setEstado(estado);
    }

    // sin detalles
    public Gasto(int id, double monto, TipoGasto tipoGasto, LocalDate fecha, int idGimnasio) {
        setId(id);
        setMonto(monto);
        setTipoGasto(tipoGasto);
        setFecha(fecha);
        setIdGimnasio(idGimnasio);
        this.estado = 1;
    }

    public Gasto(double monto, TipoGasto tipoGasto, LocalDate fecha, int idGimnasio) {
        setMonto(monto);
        setTipoGasto(tipoGasto);
        setFecha(fecha);
        setIdGimnasio(idGimnasio);
        this.estado = 1;
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
        if (id < 0) {
            throw new ValorNoValidoException("El ID del gasto no puede ser negativo.");
        }
        this.id = id;
    }

    public void setDetalles(String detalles) {
        if (detalles != null && detalles.isBlank()) {
            throw new ValorNoValidoException("Los detalles del gasto no pueden estar vacíos.");
        }
        this.detalles = detalles;
    }

    public void setMonto(double monto) {
        if (monto < 0.0) {
            throw new ValorNoValidoException("El monto del gasto no puede ser un valor negativo.");
        }
        this.monto = monto;
    }

    public void setTipoGasto(TipoGasto tipoGasto) {
        if (tipoGasto == null) {
            throw new ObjetoNuloException("El tipo de gasto no puede ser nulo.");
        }
        this.tipoGasto = tipoGasto;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new ObjetoNuloException("La fecha del gasto no puede ser nula.");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new ValorNoValidoException("La fecha del gasto no puede ser en el futuro.");
        }
        this.fecha = fecha;
    }

    public void setIdGimnasio(int idGimnasio) {
        if (idGimnasio <= 0) {
            throw new ValorNoValidoException("El ID del gimnasio asociado no es válido.");
        }
        this.idGimnasio = idGimnasio;
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
                ", detalles='" + (detalles != null ? detalles : "-") + '\'' +
                ", monto=" + monto +
                ", tipoGasto=" + tipoGasto +
                ", fecha=" + fecha +
                ", idGimnasio=" + idGimnasio +
                ", estado=" + estado +
                '}';
    }
}