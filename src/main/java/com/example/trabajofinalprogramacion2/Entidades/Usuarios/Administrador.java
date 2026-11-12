package com.example.trabajofinalprogramacion2.Entidades.Usuarios;

import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

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
        setFechaIngreso(fechaIngreso);
    }

    public Administrador(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, LocalDate fechaIngreso) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        setFechaIngreso(fechaIngreso);
    }

    // GETTERS

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    // SETTERS

    public void setFechaIngreso(LocalDate fechaIngreso) {
        if (fechaIngreso != null && fechaIngreso.isAfter(LocalDate.now())) {
            throw new ValorNoValidoException("La fecha de ingreso no puede ser en el futuro.");
        }
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
        if (idGimnasio != null && idGimnasio <= 0) {
            throw new ValorNoValidoException("El ID del gimnasio debe ser un valor válido.");
        }
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