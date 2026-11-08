package com.example.trabajofinalprogramacion2.entidades.usuarios;

import com.example.trabajofinalprogramacion2.entidades.gimnasio.Sucursal;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Entrenador extends Usuarios {
    private String especialidad;
    private Double salario;
    private Set<Socio> alumnosAsociados;
    private Sucursal sucursalAsignada;

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

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ ENTRENADOR: " + getNombre() + " "+getApellido() +"---");
        System.out.println("Especialidad: " + (especialidad != null ? especialidad : "No especificada"));
        System.out.println("1. Ver alumnos asociados");
        System.out.println("2. Asignar rutina / socio");
        System.out.println("3. Ver perfil");
        System.out.println("4. Cerrar sesión");

    }

    @Override
    public String toString() {
        return super.toString() + ", Entrenador{" +
                ": "+getNombre()+" "+getApellido()+
                "especialidad='" + especialidad + '\'' +
                ", salario=" + salario +
                ", alumnosAsociados=" + (alumnosAsociados != null ? alumnosAsociados.size() : 0) +
                ", sucursalAsignada=" + (sucursalAsignada != null ? sucursalAsignada.toString() : "Ninguna") +
                '}';
    }


}
