package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.entidades.usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.entidades.usuarios.Socio;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EntrenadorCollection extends UsuariosCollection<Entrenador> {


    public Entrenador modificarEspecialidad(int id, String especialidad) {
        Entrenador entrenador = buscar(id);

        if (entrenador == null || especialidad == null || especialidad.isBlank()) {
            return null;
        }

        entrenador.setEspecialidad(especialidad);
        return entrenador;
    }


    public Entrenador modificarSalario(int id, Double salario) {
        Entrenador entrenador = buscar(id);

        if (entrenador == null || salario == null || salario < 0) {
            return null;
        }

        entrenador.setSalario(salario);
        return entrenador;
    }


    public boolean agregarSocio(int idEntrenador, Socio socio) {
        Entrenador entrenador = buscar(idEntrenador);

        if (entrenador == null || socio == null) {
            return false;
        }


        if (entrenador.getAlumnosAsociados() == null) {
            entrenador.setAlumnosAsociados(new HashSet<>());
        }

        return entrenador.getAlumnosAsociados().add(socio);
    }

}