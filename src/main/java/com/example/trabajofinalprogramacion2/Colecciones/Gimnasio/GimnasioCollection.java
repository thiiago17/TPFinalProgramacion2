package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Gimnasio> buscarPorNombre(String nombre) throws ValorNoValidoException{
        if(nombre==null){
            throw new ValorNoValidoException("El nombre a buscar no puede ser nulo");
        }

        List<Gimnasio> gimnasiosEncontrados= new ArrayList<>();

        for(Gimnasio gimnasio : listado){
            if(nombre.equalsIgnoreCase(gimnasio.getNombre()) && gimnasio.getEstado()==1){
                gimnasiosEncontrados.add(gimnasio);
            }
        }
        return gimnasiosEncontrados;
    }

    public List<Gimnasio> buscarPorActivos() {
        List<Gimnasio> gimnasiosEncontrados= new ArrayList<>();

        for(Gimnasio gimnasio : listado){
            if(gimnasio.getEstado()==1){
                gimnasiosEncontrados.add(gimnasio);
            }
        }

        return gimnasiosEncontrados;
    }

    public List<Gimnasio> buscarPorInactivos() {
        List<Gimnasio> gimnasiosEncontrados= new ArrayList<>();

        for(Gimnasio gimnasio : listado){
            if(gimnasio.getEstado()==0){
                gimnasiosEncontrados.add(gimnasio);
            }
        }

        return gimnasiosEncontrados;
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

    public boolean modificarNombre(int id, String nombre) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || nombre==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gimnasio gimnasio=buscar(id);

        if(gimnasio==null){
            throw new ObjetoNuloException("No existe el gimnasio que se intenta modificar");
        }

        gimnasio.setNombre(nombre);

        return true;
    }

    public boolean alta(int id) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El id es invalido");
        }

        Gimnasio gimnasio=buscar(id);

        if(gimnasio==null){
            throw new ObjetoNuloException("No existe el gimnasio que se intenta dar de alta");
        }

        gimnasio.setEstado(1);

        return true;
    }

    public boolean baja(int id) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El id es invalido");
        }

        Gimnasio gimnasio=buscar(id);

        if(gimnasio==null){
            throw new ObjetoNuloException("No existe el gimnasio que se intenta dar de baja");
        }

        gimnasio.setEstado(0);

        return true;
    }

    //VER LISTADOS
    @Override
    public List<Gimnasio> verListado() {
        return new ArrayList<>(listado);
    }

    public List<Gimnasio> verListadoOrdenadoPorNombre(){
        List<Gimnasio> listaOrdenada= new ArrayList<>(listado);

        listaOrdenada.sort(Comparator.comparing(Gimnasio::getNombre)); //ordena por nombre

        return listaOrdenada;
    }

    public List<Gimnasio> verActivosOrdenadoPorNombre(){
        List<Gimnasio> listaOrdenada= buscarPorActivos();

        listaOrdenada.sort(Comparator.comparing(Gimnasio::getNombre));

        return listaOrdenada;
    }

    public List<Gimnasio> verInactivosOrdenadoPorNombre(){
        List<Gimnasio> listaOrdenada= buscarPorInactivos();

        listaOrdenada.sort(Comparator.comparing(Gimnasio::getNombre));

        return listaOrdenada;
    }
}
