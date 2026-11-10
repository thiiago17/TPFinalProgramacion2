package com.example.trabajofinalprogramacion2.Entidades.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;

import java.time.LocalDate;

public class Socio extends Usuarios {
    private Double pesoActual;
    private Double altura;
    private LocalDate fechaAlta;
    private Membresia membresia;
    private Sucursal sucursalAsignada;

    // CONSTRUCTORES

    public Socio() {
    }

    public Socio(String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, Double pesoActual, Double altura, LocalDate fechaAlta, Membresia membresia) {
        super(nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        // Se usan los setters para validar: el peso y la altura son obligatorios
        setPesoActual(pesoActual);
        setAltura(altura);
        setFechaAlta(fechaAlta);
        setMembresia(membresia);
    }

    public Socio(int id, String nombre, String apellido, String dni, String email, String password, LocalDate fechaDeNacimiento, boolean activo, Double pesoActual, Double altura, LocalDate fechaAlta, Membresia membresia) {
        super(id, nombre, apellido, dni, email, password, fechaDeNacimiento, activo);
        // Se usan los setters para validar: el peso y la altura son obligatorios
        setPesoActual(pesoActual);
        setAltura(altura);
        setFechaAlta(fechaAlta);
        setMembresia(membresia);
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
            throw new IllegalArgumentException("El peso debe ser un valor logico (entre 20 kg y 300 kg).");
        }
        this.pesoActual = pesoActual;
    }

    public void setAltura(Double altura) {
        if (altura == null || altura < 0.50 || altura > 2.50) {
            throw new IllegalArgumentException("La altura debe ser un valor logico (entre 0.50 m y 2.50 m).");
        }
        this.altura = altura;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        if (fechaAlta != null && fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser en el futuro.");
        }

        if (fechaAlta==null) {
            throw new IllegalArgumentException("La fecha de alta no puede estar vacia.");
        }
        this.fechaAlta = fechaAlta;
    }

    public void setMembresia(Membresia membresia) {
        if (membresia == null) {
            throw new IllegalArgumentException("La membresía no puede ser nula.");
        }
        this.membresia = membresia;
    }

    public Sucursal getSucursalAsignada() {
        return sucursalAsignada;
    }

    public void setSucursalAsignada(Sucursal sucursalAsignada) {
        this.sucursalAsignada = sucursalAsignada;
    }

    // MOSTRAR

    @Override
    public String toString() {
        String plan = "sin plan";
        if (membresia != null && membresia.getPlan() != null) {
            plan = membresia.getPlan().name();
        }

        return super.toString()
                + " | Peso: " + pesoActual + " kg"
                + " | Altura: " + altura + " m"
                + " | Plan: " + plan
                + " | Sucursal: " + (sucursalAsignada != null ? "#" + sucursalAsignada.getId() : "sin asignar");
    }

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

    // SUCURSAL
    // Todo socio tiene una sucursal asignada (donde se inscribio), que tambien marca a que gimnasio pertenece.
    // BASICO y LIBRE -> solo pueden ir a su sucursal asignada
    // PRO            -> pueden ir a cualquier sucursal activa de ese mismo gimnasio
    // Sin membresia o con la cuota vencida no puede ingresar a ninguna

    public boolean puedeIngresarA(Sucursal sucursal) {

        if (sucursal == null || sucursalAsignada == null || sucursal.getEstado() == 0) {
            return false;
        }

        if (membresia == null || membresia.getPlan() == null || membresia.isVencida()) {
            return false;
        }

        if (membresia.getPlan() == Plan.PRO) {
            return sucursal.getIdGimnasio() == sucursalAsignada.getIdGimnasio();
        }

        return sucursal.getId() == sucursalAsignada.getId();
    }

}