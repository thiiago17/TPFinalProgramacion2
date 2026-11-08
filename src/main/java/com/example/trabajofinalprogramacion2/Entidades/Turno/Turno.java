package com.example.trabajofinalprogramacion2.Entidades.Turno;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;

import java.time.LocalDate;
import java.util.List;

public class Turno {
    private int id;
    private Socio socio;
    private Nutricionista nutricionista;
    private LocalDate fechaTurno;
    private boolean estado;
    private double precio;

    // CONSTRUCTORES

    // Constructor vacio (lo usa el DAO para armar el turno desde la base)
    public Turno() {
        this.estado = true;
    }

    public Turno(Socio socio, Nutricionista nutricionista, LocalDate fechaTurno, boolean estado, double precio) {
        this.socio = socio;
        this.nutricionista = nutricionista;
        this.fechaTurno = fechaTurno;
        this.estado = true;
        this.precio = precio;
        if (nutricionista != null) {
            nutricionista.agregarTurno(this);
        }
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public Socio getSocio() {
        return socio;
    }

    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public boolean isEstado() {
        return estado;
    }

    public double getPrecio() {
        return precio;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // MOSTRAR

    // METODOS

    // Reservar turno

    public String sacarTurno(Socio socio)
    {

        if (socio == null) {
            return "Error: el socio no puede ser nulo.";
        }

        if (!estado) {
            return "El turno ya se encuentra reservado.";
        }

        this.socio = socio;
        this.estado = false;

        return "Turno reservado correctamente.";
    }

    // Cancelar turno

    public String cancelarTurno()
    {

        if (estado)
        {
            return "El turno ya se encuentra disponible.";
        }

        this.socio = null;
        this.estado = true;

        return "Turno cancelado correctamente.";
    }

    // Consultar turnos disponibles

    public static String verTurnosDisponibles(List<Turno> turnos) {

        StringBuilder resultado = new StringBuilder();

        for (Turno turno : turnos) {

            if (turno.isEstado()) {

                resultado.append("Fecha: ")
                        .append(turno.getFechaTurno())
                        .append(" | Nutricionista disponible")
                        .append("\n");
            }
        }

        if (resultado.isEmpty()) {
            return "No hay turnos disponibles.";
        }

        return resultado.toString();
    }

    // Consultar turnos de un usuario

    public static String verMisTurnos(Usuarios usuario, List<Turno> turnos) {

        if (usuario == null) {
            return "Error: usuario no válido.";
        }

        StringBuilder resultado = new StringBuilder();

        for (Turno turno : turnos) {

            if (turno.getSocio() != null &&
                    turno.getSocio().equals(usuario)) {

                resultado.append("Fecha: ")
                        .append(turno.getFechaTurno())
                        .append(" | Precio: ")
                        .append(turno.getPrecio())
                        .append("\n");
            }
        }

        if (resultado.isEmpty()) {
            return "No tienes turnos reservados.";
        }

        return resultado.toString();
    }
}
