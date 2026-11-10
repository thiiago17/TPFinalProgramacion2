package com.example.trabajofinalprogramacion2.Entidades.Usuarios;

import java.time.LocalDate;

// Hay dos tipos de administrador (en la misma tabla):
// - Administrador del sistema (esAdminSistema = true): maneja todos los gimnasios, idGimnasio = null
// - Administrador de gimnasio (esAdminSistema = false): maneja solo su gimnasio (un gimnasio puede tener mas de uno)

public class Administrador extends Usuarios {
    private LocalDate fechaIngreso;
    private boolean esAdminSistema;
    private Integer idGimnasio;

    // CONSTRUCTORES

    public Administrador() {
    }

    public Administrador(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, LocalDate fechaIngreso) {
        super(nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.fechaIngreso = fechaIngreso;
    }

    public Administrador(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, LocalDate fechaIngreso) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.fechaIngreso = fechaIngreso;
    }

    // GETTERS

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    // SETTERS

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean isEsAdminSistema() {
        return esAdminSistema;
    }

    public void setEsAdminSistema(boolean esAdminSistema) {
        this.esAdminSistema = esAdminSistema;
    }

    public Integer getIdGimnasio() {
        return idGimnasio;
    }

    public void setIdGimnasio(Integer idGimnasio) {
        this.idGimnasio = idGimnasio;
    }

    // MOSTRAR

    @Override
    public String toString() {
        if (esAdminSistema) {
            return super.toString() + " | Admin del sistema";
        }
        return super.toString() + " | Gimnasio #" + idGimnasio;
    }

    // METODOS

    // True si el tipo de administrador y el gimnasio son coherentes:
    // el del sistema no tiene gimnasio, el de gimnasio si tiene que tener uno
    public boolean tieneGimnasioValido() {
        if (esAdminSistema) {
            return idGimnasio == null;
        }
        return idGimnasio != null && idGimnasio > 0;
    }

    // True si este administrador puede manejar ese gimnasio
    public boolean puedeAdministrar(int idGimnasio) {
        return esAdminSistema || (this.idGimnasio != null && this.idGimnasio == idGimnasio);
    }

}
