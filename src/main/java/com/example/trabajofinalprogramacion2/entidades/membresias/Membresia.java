package com.example.trabajofinalprogramacion2.entidades.membresias;

import java.time.LocalDate;

public class Membresia {
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Plan plan;

    // CONSTRUCTORES

    public Membresia() {
    }

    public Membresia(LocalDate fechaInicio, LocalDate fechaVencimiento, Plan plan) {
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.plan = plan;
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
        this.fechaInicio = fechaInicio;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    // MOSTRAR

    // METODOS

    public boolean isVencida(){
        if (fechaVencimiento==null){
            return true;
        }
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    public String verPlanActual(){
        if (plan==null){
            return "Sin plan asignado";
        }

        return "Plan actual: "+plan.name();
    }

    //Metodo abonar cuota

    public String abonarCuota(){
        if (this.plan==null){
            return "No se puede abonar, primero debe elegir un plan";
        }

        if (isVencida()){
            this.fechaInicio=LocalDate.now();
            this.fechaVencimiento=LocalDate.now().plusMonths(1);
        }else {
            return "Usted esta al dia con la cuota";
        }

        return "Cuota abonada con éxito.\n" +
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
            throw new IllegalArgumentException("El plan seleccionado no puede estar vacio");
        }
        this.plan=nuevoPlan;

        if (this.fechaInicio==null){
            this.fechaInicio=LocalDate.now();
        }
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
