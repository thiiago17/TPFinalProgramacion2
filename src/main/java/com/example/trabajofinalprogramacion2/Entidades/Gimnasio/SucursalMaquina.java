package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import java.util.Objects;

public class SucursalMaquina {
    private int idSucursalMaquina;
    private int idSucursal;
    private int idMaquina;
    private int cantidad;
    private int estado;

    public SucursalMaquina() {
        this.cantidad=1;
        this.estado=1;
    }

    public SucursalMaquina(int idSucursalMaquina, int idSucursal, int idMaquina, int cantidad) {
        this.idSucursalMaquina = idSucursalMaquina;
        this.idSucursal = idSucursal;
        this.idMaquina = idMaquina;
        this.cantidad = cantidad;
        this.estado=1;
    }

    public SucursalMaquina(int idSucursalMaquina, int idSucursal, int idMaquina) {
        this.idSucursalMaquina = idSucursalMaquina;
        this.idSucursal = idSucursal;
        this.idMaquina = idMaquina;
        this.cantidad = 1;
        this.estado=1;
    }

    public SucursalMaquina(int idSucursal, int idMaquina) {
        this.idSucursal = idSucursal;
        this.idMaquina = idMaquina;
        this.cantidad = 1;
        this.estado=1;
    }

    public int getIdSucursalMaquina() {
        return idSucursalMaquina;
    }

    public void setIdSucursalMaquina(int idSucursalMaquina) {
        this.idSucursalMaquina = idSucursalMaquina;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SucursalMaquina that = (SucursalMaquina) o;
        return idSucursalMaquina == that.idSucursalMaquina;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idSucursalMaquina);
    }
}
