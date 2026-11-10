package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
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

    // MODIFICAR SUCURSAL

    public Nutricionista modificarSucursal(int id, Sucursal sucursal) {

        Nutricionista nutricionista = buscar(id);

        if (nutricionista == null || sucursal == null) {
            return null;
        }

        nutricionista.setSucursalAsignada(sucursal);

        return nutricionista;
    }

    // TURNOS =================================================================================

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

            if (turno != null && turno.estaDisponible()) {
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

            if (turno != null && turno.estaReservado()) {
                ocupados.add(turno);
            }
        }

        ocupados.sort(Comparator.comparing(Turno::getFechaTurno,
                Comparator.nullsLast(Comparator.naturalOrder())));

        return ocupados;
    }

}
