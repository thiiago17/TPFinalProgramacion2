package com.example.trabajofinalprogramacion2.Entidades.Membresias;

import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import java.time.LocalDate;

public class Membresia {
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Plan plan;

    // CONSTRUCTORES

    public Membresia() {
    }

    public Membresia(LocalDate fechaInicio, LocalDate fechaVencimiento, Plan plan) {
        setFechaInicio(fechaInicio);
        setFechaVencimiento(fechaVencimiento);
        setPlan(plan);
    }

    // GETTERS

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Plan getPlan() {
        return plan;
    }


    // SETTERS


    public void setFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            throw new ValorNoValidoException("La fecha de inicio no puede ser nula.");
        }
        this.fechaInicio = fechaInicio;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new ValorNoValidoException("La fecha de vencimiento no puede ser nula.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setPlan(Plan plan) {
        if (plan == null) {
            throw new ObjetoNuloException("El plan seleccionado no puede ser nulo.");
        }
        this.plan = plan;
    }

    // MOSTRAR

    // METODOS

    // Sin fecha de vencimiento (membresia recien creada sin pagar) se considera vencida
    public boolean isVencida(){
        if (fechaVencimiento == null) {
            return true;
        }
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    public String verPlanActual(){
        return "Plan actual: "+plan.name();
    }

    //Metodo abonar cuota

    public String abonarCuota(){

        if (this.plan == null) {
            return "No se puede abonar, primero debe elegir un plan.";
        }
        if (isVencida()){
            this.fechaInicio = LocalDate.now();
            this.fechaVencimiento = LocalDate.now().plusMonths(1);
        } else {
            return "No se puede abonar. Usted ya está al dia con la cuota.";
        }

        return "Cuota abonada con exito.\n" +
                "Plan: " + this.plan.name() + "\n" +
                "Monto: $" + this.plan.getPrecio() + "\n" +
                "Nuevo vencimiento: " + this.fechaVencimiento;
    }

    public String mostrarPlanes (){
        String textoPlanes= "Planes disponibles: \n";
        for (Plan p:Plan.values()){
            textoPlanes += "- " + p.name() + " ($" + p.getPrecio() + ")\n";
        }
        return textoPlanes;
    }

    //Metodo elegir plan

    public void elegirPlan (Plan nuevoPlan){
        if (nuevoPlan==null){
            throw new ObjetoNuloException("El plan seleccionado no puede estar vacio");
        }
        this.plan=nuevoPlan;

        if (this.fechaInicio==null){
            this.fechaInicio=LocalDate.now();
        }
    }

    public String verDiasRestantes (){
        LocalDate hoy= LocalDate.now();

        if (isVencida()){
            return "La membresia esta vencida.";
        }

        // ChronoUnit.DAYS cuenta el total de dias (Period.getDays solo daba los dias sueltos, sin los meses)
        long dias = java.time.temporal.ChronoUnit.DAYS.between(hoy, this.fechaVencimiento);

        return "Faltan " + dias + " días para que venza la membresía.";
    }


    @Override
    public String toString() {
        return "Membresia{" +
                "fechaInicio=" + fechaInicio +
                ", fechaVencimiento=" + fechaVencimiento +
                ", plan=" + plan +
                '}';
    }
}
