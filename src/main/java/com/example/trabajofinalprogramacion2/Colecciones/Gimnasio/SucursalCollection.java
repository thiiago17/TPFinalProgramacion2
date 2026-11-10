package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.List;

public class SucursalCollection implements ABCMLCollection<Sucursal> {
    private List<Sucursal> listado = new ArrayList<>();


    //AGREGAR
    @Override
    public boolean agregar(Sucursal sucursal) throws ObjetoNuloException {
        if(sucursal == null){
            throw new ObjetoNuloException("No se puede agregar una sucursal nula");
        }

        return listado.add(sucursal);
    }

    //BUSQUEDA
    @Override
    public Sucursal buscar(int id) {
        for(Sucursal sucursal : listado){
            if(sucursal.getId()==id){
                return sucursal;
            }
        }

        return null;
    }


    //ELIMINAR
    @Override
    public boolean eliminar(int id) throws ObjetoNuloException{
        Sucursal sucursalAEliminar=buscar(id);

        if(sucursalAEliminar == null){
            throw new ObjetoNuloException("No existe la sucursal que se intenta eliminar");
        }

        return listado.remove(sucursalAEliminar);
    }

    //MODIFICACION
    @Override
    public boolean modificar(Sucursal sucursal) throws ObjetoNuloException{
        if(sucursal==null){
            throw new ObjetoNuloException("La sucursal recibida para modificar es nula");
        }

        Sucursal sucursalOriginal= buscar(sucursal.getId());

        if(sucursalOriginal==null){
            throw new ObjetoNuloException("No existe la sucursal que se intenta modificar");
        }

        int indice=listado.indexOf(sucursalOriginal);

        listado.set(indice,sucursal);

        return true;
    }

    //VER LISTADOS
    @Override
    public List<Sucursal> verListado() {
        return new ArrayList<>(listado);
    }
}
