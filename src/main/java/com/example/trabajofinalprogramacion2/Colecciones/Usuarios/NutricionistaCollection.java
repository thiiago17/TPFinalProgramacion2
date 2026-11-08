package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NutricionistaCollection extends UsuariosCollection<Nutricionista> {

    // MODIFICAR SALARIO

    public Nutricionista modificarSalario(int id, double salario) {

        Nutricionista nutricionista = buscar(id);

        if (nutricionista == null || salario < 0) {
            return null;
        }

        nutricionista.setSalario(salario);

        return nutricionista;
    }

    // MODIFICAR COMISION POR CONSULTA

    public Nutricionista modificarComisionPorConsulta(int id, double comisionPorConsulta) {

        Nutricionista nutricionista = buscar(id);

        if (nutricionista == null || comisionPorConsulta < 0) {
            return null;
        }

        nutricionista.setComisionPorConsulta(comisionPorConsulta);

        return nutricionista;
    }

    // TURNOS =================================================================================

    // AGREGAR TURNO

    public boolean agregarTurno(int idNutricionista, Turno turno) {

        Nutricionista nutricionista = buscar(idNutricionista);

        if (nutricionista == null || turno == null) {
            return false;
        }

        if (nutricionista.getTurnos() == null) {
            nutricionista.setTurnos(new ArrayList<>());
        }

        if (nutricionista.getTurnos().contains(turno)) {
            return false;
        }

        nutricionista.agregarTurno(turno);

        return true;
    }

    // ELIMINAR TURNO

    public boolean eliminarTurno(int idNutricionista, Turno turno) {

        Nutricionista nutricionista = buscar(idNutricionista);

        if (nutricionista == null || turno == null || nutricionista.getTurnos() == null) {
            return false;
        }

        return nutricionista.getTurnos().remove(turno);
    }


    // VER TODOS LOS TURNOS DEL NUTRICIONISTA

    public List<Turno> verTurnos(int idNutricionista) {

        Nutricionista nutricionista = buscar(idNutricionista);

        if (nutricionista == null || nutricionista.getTurnos() == null) {
            return new ArrayList<>();
        }

        List<Turno> turnos = new ArrayList<>(nutricionista.getTurnos());

        turnos.removeIf(turno -> turno == null);

        turnos.sort(Comparator.comparing(
                Turno::getFechaTurno,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));

        return turnos;
    }

    // VER TURNOS (solo los disponibles, ordenados por fecha)

    public List<Turno> verTurnosDisponibles(int idNutricionista) {

        List<Turno> disponibles = new ArrayList<>();

        Nutricionista nutricionista = buscar(idNutricionista);

        if (nutricionista == null || nutricionista.getTurnos() == null) {
            return disponibles;
        }

        for (Turno turno : nutricionista.getTurnos()) {

            // estado == true significa que el turno esta disponible (no tomado)
            if (turno != null && turno.isEstado()) {
                disponibles.add(turno);
            }
        }

        disponibles.sort(Comparator.comparing(Turno::getFechaTurno,
                Comparator.nullsLast(Comparator.naturalOrder())));

        return disponibles;
    }

    // VER TURNOS (solo los ocupados, ordenados por fecha)

    public List<Turno> verTurnosOcupados(int idNutricionista) {

        List<Turno> ocupados = new ArrayList<>();

        Nutricionista nutricionista = buscar(idNutricionista);

        if (nutricionista == null || nutricionista.getTurnos() == null) {
            return ocupados;
        }

        for (Turno turno : nutricionista.getTurnos()) {

            // estado == true significa que el turno esta disponible (no tomado)
            if (turno != null && !turno.isEstado()) {
                ocupados.add(turno);
            }
        }

        ocupados.sort(Comparator.comparing(Turno::getFechaTurno,
                Comparator.nullsLast(Comparator.naturalOrder())));

        return ocupados;
    }

}
