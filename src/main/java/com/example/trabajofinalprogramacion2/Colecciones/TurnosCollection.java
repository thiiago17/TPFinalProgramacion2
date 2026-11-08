package com.example.trabajofinalprogramacion2.Colecciones;

import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Coleccion "duenia" de los turnos: el alta y la baja de un turno se hacen aca,
// y se mantiene sincronizada la lista de turnos de cada Nutricionista.

public class TurnosCollection {

    private List<Turno> turnos = new ArrayList<>();

    // LISTADO =================================================================================

    // Todos los turnos, ordenados por fecha
    public List<Turno> verListado() {
        return ordenarPorFecha(new ArrayList<>(turnos));
    }

    // ALTA =================================================================================

    public boolean agregar(Turno turno) {

        if (turno == null || turno.getFechaTurno() == null || turno.getPrecio() < 0) {
            return false;
        }

        if (turnos.contains(turno)) {
            return false;
        }

        // Un nutricionista no puede tener dos turnos en la misma fecha
        if (existeTurno(turno.getNutricionista(), turno.getFechaTurno())) {
            return false;
        }

        turnos.add(turno);

        // Mantener sincronizada la lista del nutricionista
        if (turno.getNutricionista() != null) {
            turno.getNutricionista().agregarTurno(turno);
        }

        return true;
    }

    // BAJA =================================================================================

    public boolean eliminar(Turno turno) {

        if (turno == null || !turnos.remove(turno)) {
            return false;
        }

        // Sacarlo tambien de la lista del nutricionista
        Nutricionista nutricionista = turno.getNutricionista();

        if (nutricionista != null && nutricionista.getTurnos() != null) {
            nutricionista.getTurnos().remove(turno);
        }

        return true;
    }

    // RESERVAR / CANCELAR =================================================================================

    public String sacarTurno(Turno turno, Socio socio) {

        if (turno == null || !turnos.contains(turno)) {
            return "Error: el turno no existe.";
        }

        return turno.sacarTurno(socio);
    }

    public String cancelarTurno(Turno turno) {

        if (turno == null || !turnos.contains(turno)) {
            return "Error: el turno no existe.";
        }

        return turno.cancelarTurno();
    }

    // MODIFICACIONES =================================================================================

    // MODIFICAR FECHA

    public Turno modificarFecha(Turno turno, LocalDate nuevaFecha) {

        if (turno == null || nuevaFecha == null || !turnos.contains(turno)) {
            return null;
        }

        if (nuevaFecha.equals(turno.getFechaTurno())) {
            return turno;
        }

        // Que el nutricionista no tenga ya otro turno en esa fecha
        if (existeTurno(turno.getNutricionista(), nuevaFecha)) {
            return null;
        }

        turno.setFechaTurno(nuevaFecha);

        return turno;
    }

    // MODIFICAR PRECIO

    public Turno modificarPrecio(Turno turno, double precio) {

        if (turno == null || precio < 0 || !turnos.contains(turno)) {
            return null;
        }

        turno.setPrecio(precio);

        return turno;
    }

    // MODIFICAR NUTRICIONISTA (pasa el turno de la lista de uno a la del otro)

    public Turno modificarNutricionista(Turno turno, Nutricionista nuevoNutricionista) {

        if (turno == null || nuevoNutricionista == null || !turnos.contains(turno)) {
            return null;
        }

        Nutricionista anterior = turno.getNutricionista();

        if (mismoUsuario(anterior, nuevoNutricionista)) {
            return turno;
        }

        if (existeTurno(nuevoNutricionista, turno.getFechaTurno())) {
            return null;
        }

        if (anterior != null && anterior.getTurnos() != null) {
            anterior.getTurnos().remove(turno);
        }

        turno.setNutricionista(nuevoNutricionista);
        nuevoNutricionista.agregarTurno(turno);

        return turno;
    }

    // BUSQUEDAS =================================================================================

    // POR NUTRICIONISTA

    public List<Turno> buscarPorNutricionista(Nutricionista nutricionista) {

        List<Turno> encontrados = new ArrayList<>();

        if (nutricionista == null) {
            return encontrados;
        }

        for (Turno turno : turnos) {
            if (mismoUsuario(turno.getNutricionista(), nutricionista)) {
                encontrados.add(turno);
            }
        }

        return ordenarPorFecha(encontrados);
    }

    // POR SOCIO

    public List<Turno> buscarPorSocio(Socio socio) {

        List<Turno> encontrados = new ArrayList<>();

        if (socio == null) {
            return encontrados;
        }

        for (Turno turno : turnos) {
            if (mismoUsuario(turno.getSocio(), socio)) {
                encontrados.add(turno);
            }
        }

        return ordenarPorFecha(encontrados);
    }

    // POR FECHA

    public List<Turno> buscarPorFecha(LocalDate fecha) {

        List<Turno> encontrados = new ArrayList<>();

        if (fecha == null) {
            return encontrados;
        }

        for (Turno turno : turnos) {
            if (fecha.equals(turno.getFechaTurno())) {
                encontrados.add(turno);
            }
        }

        return encontrados;
    }

    // ENTRE DOS FECHAS (inclusive)

    public List<Turno> buscarEntreFechas(LocalDate desde, LocalDate hasta) {

        List<Turno> encontrados = new ArrayList<>();

        if (desde == null || hasta == null || desde.isAfter(hasta)) {
            return encontrados;
        }

        for (Turno turno : turnos) {

            LocalDate fecha = turno.getFechaTurno();

            if (fecha != null && !fecha.isBefore(desde) && !fecha.isAfter(hasta)) {
                encontrados.add(turno);
            }
        }

        return ordenarPorFecha(encontrados);
    }

    // POR ESTADO

    // estado == true -> disponible
    public List<Turno> verDisponibles() {
        return filtrarPorEstado(true);
    }

    // estado == false -> reservado
    public List<Turno> verReservados() {
        return filtrarPorEstado(false);
    }

    // POR PRECIO MAXIMO

    public List<Turno> buscarPorPrecioMaximo(double precioMaximo) {

        List<Turno> encontrados = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getPrecio() <= precioMaximo) {
                encontrados.add(turno);
            }
        }

        return ordenarPorFecha(encontrados);
    }

    // METODOS AUXILIARES =================================================================================

    private List<Turno> filtrarPorEstado(boolean estado) {

        List<Turno> encontrados = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.isEstado() == estado) {
                encontrados.add(turno);
            }
        }

        return ordenarPorFecha(encontrados);
    }

    private boolean existeTurno(Nutricionista nutricionista, LocalDate fecha) {

        if (nutricionista == null || fecha == null) {
            return false;
        }

        for (Turno turno : turnos) {
            if (mismoUsuario(turno.getNutricionista(), nutricionista)
                    && fecha.equals(turno.getFechaTurno())) {
                return true;
            }
        }

        return false;
    }

    // Usuarios no tiene equals: se comparan por referencia o por id (si ya tienen id asignado)
    private boolean mismoUsuario(Usuarios a, Usuarios b) {

        if (a == null || b == null) {
            return false;
        }

        return a == b || (a.getId() != 0 && a.getId() == b.getId());
    }

    private List<Turno> ordenarPorFecha(List<Turno> lista) {

        lista.sort(Comparator.comparing(Turno::getFechaTurno,
                Comparator.nullsLast(Comparator.naturalOrder())));

        return lista;
    }

}
