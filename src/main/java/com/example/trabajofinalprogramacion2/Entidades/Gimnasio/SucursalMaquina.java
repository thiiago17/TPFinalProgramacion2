package com.example.trabajofinalprogramacion2.Entidades.Gimnasio;

import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.util.Objects;

public class SucursalMaquina {
    private int idSucursalMaquina;
    private int idSucursal;
    private int idMaquina;
    private int cantidad;
    private int estado;

    public SucursalMaquina() {
        this.cantidad = 1;
        this.estado = 1;
    }

    public SucursalMaquina(int idSucursalMaquina, int idSucursal, int idMaquina, int cantidad) {
        setIdSucursalMaquina(idSucursalMaquina);
        setIdSucursal(idSucursal);
        setIdMaquina(idMaquina);
        setCantidad(cantidad);
        this.estado = 1;
    }

    public SucursalMaquina(int idSucursalMaquina, int idSucursal, int idMaquina) {
        setIdSucursalMaquina(idSucursalMaquina);
        setIdSucursal(idSucursal);
        setIdMaquina(idMaquina);
        this.cantidad = 1;
        this.estado = 1;
    }

    public SucursalMaquina(int idSucursal, int idMaquina) {
        setIdSucursal(idSucursal);
        setIdMaquina(idMaquina);
        this.cantidad = 1;
        this.estado = 1;
    }

    public int getIdSucursalMaquina() {
        return idSucursalMaquina;
    }

    public void setIdSucursalMaquina(int idSucursalMaquina) {
        if (idSucursalMaquina < 0) {
            throw new ValorNoValidoException("El ID de la relación sucursal-máquina no puede ser negativo.");
        }
        this.idSucursalMaquina = idSucursalMaquina;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        if (idSucursal <= 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido.");
        }
        this.idSucursal = idSucursal;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        if (idMaquina <= 0) {
            throw new ValorNoValidoException("El ID de la máquina no es válido.");
        }
        this.idMaquina = idMaquina;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new ValorNoValidoException("La cantidad de máquinas no puede ser un valor negativo.");
        }
        this.cantidad = cantidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        if (estado != 0 && estado != 1) {
            throw new ValorNoValidoException("El estado debe ser 0 (inactivo) o 1 (activo).");
        }
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

    @Override
    public String toString() {
        return "SucursalMaquina{" +
                "idSucursalMaquina=" + idSucursalMaquina +
                ", idSucursal=" + idSucursal +
                ", idMaquina=" + idMaquina +
                ", cantidad=" + cantidad +
                ", estado=" + estado +
                '}';
    }
}