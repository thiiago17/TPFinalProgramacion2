package com.example.trabajofinalprogramacion2.Entidades.Turno;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Turno {

    // Horario de atencion: de 10:00 a 19:00, cada turno dura 30 minutos.
    // El ultimo turno empieza a las 18:30 para terminar a las 19:00.
    public static final LocalTime HORA_APERTURA = LocalTime.of(10, 0);
    public static final LocalTime HORA_CIERRE = LocalTime.of(19, 0);
    public static final int DURACION_MINUTOS = 30;

    private int id;
    private Socio socio;
    private Nutricionista nutricionista;
    private LocalDateTime fechaTurno;
    private boolean estado;
    private double precio;

    // CONSTRUCTORES

    // Constructor vacio (lo usa el DAO para armar el turno desde la base)
    public Turno() {
        this.estado = true;
    }

    // El estado se calcula solo: sin socio el turno esta disponible (true), con socio esta reservado (false)
    public Turno(Socio socio, Nutricionista nutricionista, LocalDateTime fechaTurno, double precio) {
        this.socio = socio;
        this.nutricionista = nutricionista;
        this.fechaTurno = fechaTurno;
        this.estado = (socio == null);
        this.precio = precio;
        // No se agrega a la lista del nutricionista aca: lo hace TurnosCollection.agregar
        // solo si el turno es valido (horario y sin superposicion)
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

    public LocalDateTime getFechaTurno() {
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

    public void setFechaTurno(LocalDateTime fechaTurno) {
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

    // True si el turno empieza en un horario fijo (cada 30 minutos) y termina dentro del horario de atencion
    // (el primero empieza a las 10:00 y el ultimo a las 18:30)

    public static boolean esHorarioValido(LocalDateTime fechaHora) {

        if (fechaHora == null) {
            return false;
        }

        LocalTime hora = fechaHora.toLocalTime();
        LocalTime ultimoInicio = HORA_CIERRE.minusMinutes(DURACION_MINUTOS);

        // Solo horarios fijos cada 30 minutos (10:00, 10:30, 11:00 ... 18:30)
        boolean horarioFijo = hora.getMinute() % DURACION_MINUTOS == 0
                && hora.getSecond() == 0 && hora.getNano() == 0;

        return horarioFijo && !hora.isBefore(HORA_APERTURA) && !hora.isAfter(ultimoInicio);
    }

    // True si dos turnos que empiezan en esos horarios se pisan
    // (empiezan a menos de 30 minutos uno del otro)

    public static boolean seSuperponen(LocalDateTime inicioA, LocalDateTime inicioB) {

        if (inicioA == null || inicioB == null) {
            return false;
        }

        long minutos = Math.abs(Duration.between(inicioA, inicioB).toMinutes());

        return minutos < DURACION_MINUTOS;
    }

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
