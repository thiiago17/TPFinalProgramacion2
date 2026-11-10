package com.example.trabajofinalprogramacion2.Entidades.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.Empleado;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

public class Entrenador extends Usuarios implements Empleado {
    private String especialidad;
    private Double salario;
    private Set<Socio> alumnosAsociados;
    private Sucursal sucursalAsignada;
    private LocalDate fechaIngreso;

    // CONSTRUCTORES

    public Entrenador() {
    }

    public Entrenador(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, String especialidad, Double salario, Set<Socio> alumnosAsociados, Sucursal sucursalAsignada) {
        super(nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.especialidad = especialidad;
        this.salario = salario;
        this.alumnosAsociados = alumnosAsociados;
        this.sucursalAsignada= sucursalAsignada;
    }

    public Entrenador(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, String especialidad, Double salario, Set<Socio> alumnosAsociados, Sucursal sucursalAsignada) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.especialidad = especialidad;
        this.salario = salario;
        this.alumnosAsociados = alumnosAsociados;
        this.sucursalAsignada= sucursalAsignada;
    }

    // GETTERS

    public String getEspecialidad() {
        return especialidad;
    }

    public Double getSalario() {
        return salario;
    }

    public Set<Socio> getAlumnosAsociados() {
        return alumnosAsociados;
    }

    public Sucursal getSucursalAsignada() {
        return sucursalAsignada;
    }

    // SETTERS

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public void setAlumnosAsociados(Set<Socio> alumnosAsociados) {
        this.alumnosAsociados = alumnosAsociados;
    }

    public void setSucursalAsignada(Sucursal sucursalAsignada) {
        this.sucursalAsignada = sucursalAsignada;
    }

    public void agregarSocio(Socio socio) {
        if (socio != null) {
            if (this.alumnosAsociados == null) {
                this.alumnosAsociados = new HashSet<>();
            }
            this.alumnosAsociados.add(socio);
        }
    }

    // FECHA DE INGRESO (puede venir en NULL de la BD para los cargados antes de agregar la columna)

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        if (fechaIngreso != null && fechaIngreso.isAfter(LocalDate.now())) {
            throw new ValorNoValidoException("La fecha de ingreso no puede ser en el futuro.");
        }
        this.fechaIngreso = fechaIngreso;
    }

    // INTERFAZ EMPLEADO

    // Años completos desde que ingreso. Sin fecha de ingreso devuelve 0
    @Override
    public double calcularAniosAntiguedad() {
        if (fechaIngreso == null) {
            return 0;
        }
        return Period.between(fechaIngreso, LocalDate.now()).getYears();
    }

    // Salario mensual x 12. Si no tiene salario cargado devuelve 0
    @Override
    public double calcularSueldoAnual() {
        if (salario == null) {
            return 0;
        }
        return salario * 12;
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Especialidad: " + (especialidad != null ? especialidad : "-")
                + " | Salario: $" + salario
                + " | Alumnos: " + (alumnosAsociados != null ? alumnosAsociados.size() : 0)
                + " | Sucursal: " + (sucursalAsignada != null ? "#" + sucursalAsignada.getId() : "sin asignar");
    }


}
