package com.example.trabajofinalprogramacion2.entidades.membresias;

public enum Plan {
    BASICO(50000),
    LIBRE(60000),
    PRO(70000);

    private final double precio;

    Plan(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

}

