package com.example.trabajofinalprogramacion2.Entidades.Turno;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Implementa Comparable para que las listas de turnos se puedan ordenar por fecha con Collections.sort(lista)

public class Turno implements Comparable<Turno> {

    // Horario de atencion: de 10:00 a 19:00, cada turno dura 30 minutos.
    // El ultimo turno empieza a las 18:30 para terminar a las 19:00.
    public static final LocalTime HORA_APERTURA = LocalTime.of(10, 0);
    public static final LocalTime HORA_CIERRE = LocalTime.of(19, 0);
    public static final int DURACION_MINUTOS = 30;

    private int id;
    private Socio socio;
    private Nutricionista nutricionista;
    private LocalDateTime fechaTurno;
    private EstadoTurno estado;
    private double precio;

    // CONSTRUCTORES

    // Constructor vacio (lo usa el DAO para armar el turno desde la base)
    public Turno() {
        this.estado = EstadoTurno.DISPONIBLE;
    }

    // El estado se calcula solo: sin socio el turno esta disponible, con socio esta reservado
    public Turno(Socio socio, Nutricionista nutricionista, LocalDateTime fechaTurno, double precio) {
        setSocio(socio);
        setNutricionista(nutricionista);
        setFechaTurno(fechaTurno);
        this.estado = (socio == null) ? EstadoTurno.DISPONIBLE : EstadoTurno.RESERVADO;
        setPrecio(precio);
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

    public EstadoTurno getEstado() {
        return estado;
    }

    public boolean estaDisponible() {
        return estado == EstadoTurno.DISPONIBLE;
    }

    public boolean estaReservado() {
        return estado == EstadoTurno.RESERVADO;
    }

    public boolean estaDadoDeBaja() {
        return estado == EstadoTurno.DADO_DE_BAJA;
    }

    public double getPrecio() {
        return precio;
    }

    // SETTERS

    public void setId(int id) {
        if (id <= 0) {
            throw new ValorNoValidoException("El ID del turno debe ser mayor a 0.");
        }
        this.id = id;
    }

    public void setSocio(Socio socio) {
        if (socio == null){
            throw new ObjetoNuloException("El socio del turno no puede ser nulo.");
        }
        this.socio = socio;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        if (nutricionista == null) {
            throw new ObjetoNuloException("El nutricionista del turno no puede ser nulo.");
        }
        this.nutricionista = nutricionista;
    }

    public void setFechaTurno(LocalDateTime fechaTurno) {
        if (fechaTurno == null) {
            throw new ObjetoNuloException("La fecha y hora del turno no pueden ser nulas.");
        }
        this.fechaTurno = fechaTurno;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new ObjetoNuloException("El estado del turno no puede ser nulo.");
        }
        this.estado = estado;
    }

    public void setPrecio(double precio) {
        if (precio < 0.0) {
            throw new ValorNoValidoException("El precio del turno no puede ser un valor negativo.");
        }
        this.precio = precio;
    }

    // MOSTRAR

    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public String toString() {
        String fecha = "-";
        if (fechaTurno != null) {
            fecha = fechaTurno.format(FORMATO_FECHA_HORA);
        }

        // Si el nutricionista no se vinculo con el objeto completo, solo tenemos su id
        String textoNutricionista = "-";
        if (nutricionista != null) {
            if (nutricionista.getNombre() != null) {
                textoNutricionista = nutricionista.getNombre() + " " + nutricionista.getApellido();
            } else {
                textoNutricionista = "#" + nutricionista.getId();
            }
        }

        String textoSocio = "";
        if (socio != null) {
            if (socio.getNombre() != null) {
                textoSocio = " | Socio: " + socio.getNombre() + " " + socio.getApellido();
            } else {
                textoSocio = " | Socio #" + socio.getId();
            }
        }

        return "Turno #" + id + " | " + fecha
                + " | Nutricionista: " + textoNutricionista
                + " | $" + precio
                + " | " + estado
                + textoSocio;
    }

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

        if (estaDadoDeBaja()) {
            return "El turno se encuentra dado de baja.";
        }

        if (estaReservado()) {
            return "El turno ya se encuentra reservado.";
        }

        this.socio = socio;
        this.estado = EstadoTurno.RESERVADO;

        return "Turno reservado correctamente.";
    }

    // Cancelar turno

    public String cancelarTurno()
    {

        if (!estaReservado())
        {
            return "El turno no se encuentra reservado.";
        }

        this.socio = null;
        this.estado = EstadoTurno.DISPONIBLE;

        return "Turno cancelado correctamente.";
    }

    // Consultar turnos disponibles

    public static String verTurnosDisponibles(List<Turno> turnos) {

        StringBuilder resultado = new StringBuilder();

        for (Turno turno : turnos) {

            if (turno.estaDisponible()) {

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

    // COMPARAR (ordena por fecha y hora; los turnos sin fecha van al final)
    // Devuelve negativo si este turno va antes que el otro, positivo si va despues y 0 si es la misma fecha

    @Override
    public int compareTo(Turno otro) {

        if (this.fechaTurno == null && otro.fechaTurno == null) {
            return 0;
        }
        if (this.fechaTurno == null) {
            return 1;
        }
        if (otro.fechaTurno == null) {
            return -1;
        }

        return this.fechaTurno.compareTo(otro.fechaTurno);
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