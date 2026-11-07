package com.example.trabajofinalprogramacion2.entidades.usuarios;

import com.example.trabajofinalprogramacion2.entidades.turno.Turno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Nutricionista extends Usuarios{
    private List<Turno> turnos = new ArrayList<>();
    private double salario;
    private double comisionPorConsulta;

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

    // MOSTRAR

    // METODOS

    public double calcularExtraPorComision()
    {
        return salario+(comisionPorConsulta*turnos.size());
    }
}
