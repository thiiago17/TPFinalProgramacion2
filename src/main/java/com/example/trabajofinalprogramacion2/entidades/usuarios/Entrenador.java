package com.example.trabajofinalprogramacion2.entidades.usuarios;

import java.time.LocalDate;
import java.util.Set;

public class Entrenador extends Usuarios {
    private String especialidad;
    private Double salario;
    private Set<Socio> alumnosAsociados;

    // CONSTRUCTORES

    public Entrenador() {
    }

    public Entrenador(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, String especialidad, Double salario, Set<Socio> alumnosAsociados) {
        super(nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.especialidad = especialidad;
        this.salario = salario;
        this.alumnosAsociados = alumnosAsociados;
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

    // MOSTRAR

    // METODOS

}
