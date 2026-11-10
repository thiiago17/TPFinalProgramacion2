package com.example.trabajofinalprogramacion2.Entidades.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;

import java.time.LocalDate;

public class Socio extends Usuarios {
    private Double pesoActual;
    private Double altura;
    private LocalDate fechaAlta;
    private Membresia membresia;

    // CONSTRUCTORES

    public Socio() {
    }

    public Socio(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, Double pesoActual, Double altura, LocalDate fechaAlta, Membresia membresia) {
        super(nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.pesoActual = pesoActual;
        this.altura = altura;
        this.fechaAlta = fechaAlta;
        this.membresia = membresia;
    }

    public Socio(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, Double pesoActual, Double altura, LocalDate fechaAlta, Membresia membresia) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        this.pesoActual = pesoActual;
        this.altura = altura;
        this.fechaAlta = fechaAlta;
        this.membresia = membresia;
    }

    // GETTERS

    public Double getPesoActual() {
        return pesoActual;
    }

    public Double getAltura() {
        return altura;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    // SETTERS

    public void setPesoActual(Double pesoActual) {
        if (pesoActual == null || pesoActual <= 20.0 || pesoActual > 300.0) {
            throw new IllegalArgumentException("El peso debe ser un valor lógico (entre 20 kg y 300 kg).");
        }
        this.pesoActual = pesoActual;
    }

    public void setAltura(Double altura) {
        if (altura == null || altura < 0.50 || altura > 2.50) {
            throw new IllegalArgumentException("La altura debe ser un valor lógico (entre 0.50 m y 2.50 m).");
        }
        this.altura = altura;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        if (fechaAlta != null && fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser en el futuro.");
        }
        this.fechaAlta = fechaAlta;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    // MOSTRAR

    // METODOS
    public double calcularIMC() {
        if (pesoActual==null || altura==null || pesoActual<=0 || altura<=0){
            return 0;
        }
        return pesoActual/(altura*altura);
    }

    public String verEstadoIMC(){
        double IMC=calcularIMC();

        if (IMC==0){
            return "Datos de altura o peso invalidos.";
        }else if(IMC<18.5){
            return "Bajo peso";
        }else if (IMC>=18.5 && IMC<25.0){
            return "Peso normal (Saludable)";
        }else if (IMC>=25.0 && IMC<30.0) {
            return "Sobrepeso";
        }else if (IMC>=30.0 && IMC<34.9){
            return "Obesidad (Grado I)";
        }else if (IMC>=34.9 && IMC<39.9){
            return "Obesidad severa (Grado II)";
        }else {
            return "Obesidad mórbida / Grado III";
        }
    }

}
