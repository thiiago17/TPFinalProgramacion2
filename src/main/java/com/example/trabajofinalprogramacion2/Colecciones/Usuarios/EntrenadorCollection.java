package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.entidades.usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.List;

public class EntrenadorCollection implements ABCMLCollection<Entrenador> {
    private List<Entrenador> listaEntrenadores;

    public EntrenadorCollection() {
        this.listaEntrenadores = new ArrayList<>();
    }

    @Override
    public List<Entrenador> verListado() {
        return List.of();
    }

    @Override
    public boolean agregar(Entrenador objeto) {
        if (buscar(objeto.getId())==null)
        {
            listaEntrenadores.add(objeto);
            return true;
        }
        return false;
    }

    @Override
    public boolean modificar(Entrenador objeto) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public Entrenador buscar(int id) {
        return null;
    }
}
