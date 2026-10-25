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

    public String mostrarPlanes (){
        String textoPlanes= "Planes disponibles: \n";
        for (Plan p:Plan.values()){
            textoPlanes+= "- "+p.name()+"\n";
        }
        return textoPlanes;
    }

    //Metodo elegir plan
}
