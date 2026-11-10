package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.List;

public class GimnasioCollection implements ABCMLCollection<Gimnasio> {
    private List<Gimnasio> listado = new ArrayList<>();


    //AGREGAR
    @Override
    public boolean agregar(Gimnasio gimnasio) throws ObjetoNuloException {
        if(gimnasio == null){
            throw new ObjetoNuloException("No se puede agregar un gimnasio nulo");
        }

        return listado.add(gimnasio);
    }

    //BUSQUEDA
    @Override
    public Gimnasio buscar(int id) {
        for(Gimnasio gimnasio : listado){
            if(gimnasio.getId()==id){
                return gimnasio;
            }
        }

        return null;
    }


    //ELIMINAR
    @Override
    public boolean eliminar(int id) throws ObjetoNuloException{
        Gimnasio gimnasioAEliminar=buscar(id);

        if(gimnasioAEliminar == null){
            throw new ObjetoNuloException("No existe el gimnasio que se intenta eliminar");
        }

        return listado.remove(gimnasioAEliminar);
    }

    //MODIFICACION
    @Override
    public boolean modificar(Gimnasio gimnasio) throws ObjetoNuloException{
        if(gimnasio==null){
            throw new ObjetoNuloException("El gimnasio recibido para modificar es nulo");
        }

        Gimnasio gimnasioOriginal= buscar(gimnasio.getId());

        if(gimnasioOriginal==null){
            throw new ObjetoNuloException("No existe el gimnasio que se intenta modificar");
        }

        int indice=listado.indexOf(gimnasioOriginal);

        listado.set(indice,gimnasio);

        return true;
    }

    //VER LISTADOS
    @Override
    public List<Gimnasio> verListado() {
        return new ArrayList<>(listado);
    }
}
