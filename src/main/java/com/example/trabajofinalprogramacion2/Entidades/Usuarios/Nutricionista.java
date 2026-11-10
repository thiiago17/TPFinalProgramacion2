package com.example.trabajofinalprogramacion2.Entidades.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.Empleado;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Nutricionista extends Usuarios implements Empleado {
    private List<Turno> turnos = new ArrayList<>();
    private double salario;
    private double comisionPorConsulta;
    private Sucursal sucursalAsignada;
    private LocalDate fechaIngreso;

    // CONSTRUCTORES

    public Nutricionista() {
    }

    public Nutricionista(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, List<Turno> turnos, double salario, double comisionPorConsulta) {
        super(nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.turnos = turnos;
        this.salario = salario;
        this.comisionPorConsulta = comisionPorConsulta;
    }

    public Nutricionista(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, List<Turno> turnos, double salario, double comisionPorConsulta) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.turnos = turnos;
        this.salario = salario;
        this.comisionPorConsulta = comisionPorConsulta;
    }

    // GETTERS

    public List<Turno> getTurnos() {
        return turnos;
    }

    public double getSalario() {
        return salario;
    }

    public double getComisionPorConsulta() {
        return comisionPorConsulta;
    }

    // SETTERS

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setComisionPorConsulta(double comisionPorConsulta) {
        this.comisionPorConsulta = comisionPorConsulta;
    }

    // Trabaja en una sola sucursal (en la BD: nutricionista.id_sucursal)

    public Sucursal getSucursalAsignada() {
        return sucursalAsignada;
    }

    public void setSucursalAsignada(Sucursal sucursalAsignada) {
        this.sucursalAsignada = sucursalAsignada;
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

    // Salario mensual x 12 + la comision de los turnos que tiene reservados
    @Override
    public double calcularSueldoAnual() {
        return salario * 12 + calcularExtraPorComision();
    }

    // MOSTRAR

    @Override
    public String toString() {
        return super.toString()
                + " | Salario: $" + salario
                + " | Comisión: $" + comisionPorConsulta
                + " | Sucursal: " + (sucursalAsignada != null ? "#" + sucursalAsignada.getId() : "sin asignar");
    }

    // METODOS

    public double calcularExtraPorComision()
    {

        int consultasReservadas = 0;

        for (Turno turno : turnos) {
            if (turno.estaReservado()) {
                consultasReservadas++;
            }
        }

        return consultasReservadas * comisionPorConsulta;
    }

    public void agregarTurno(Turno turno)
    {
        if (turno != null && !turnos.contains(turno)) {
            turnos.add(turno);
        }
    }
}
