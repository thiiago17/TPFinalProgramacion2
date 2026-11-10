package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.List;

public class GastoCollection implements ABCMLCollection<Gasto> {
    private List<Gasto> listado = new ArrayList<>();

    //AGREGAR
    @Override
    public boolean agregar(Gasto gasto) throws ObjetoNuloException {
        if(gasto == null){
            throw new ObjetoNuloException("No se puede agregar un gasto nulo");
        }

        return listado.add(gasto);
    }

    //BUSQUEDA
    @Override
    public Gasto buscar(int id) {
        for(Gasto gasto : listado){
            if(gasto.getId()==id){
                return gasto;
            }
        }

        return null;
    }


    //ELIMINAR
    @Override
    public boolean eliminar(int id) throws ObjetoNuloException{
        Gasto gastoAEliminar=buscar(id);

        if(gastoAEliminar == null){
            throw new ObjetoNuloException("No existe el gasto que se intenta eliminar");
        }

        return listado.remove(gastoAEliminar);
    }

    //MODIFICACION
    @Override
    public boolean modificar(Gasto gasto) throws ObjetoNuloException{
        if(gasto==null){
            throw new ObjetoNuloException("El gasto recibido para modificar es nulo");
        }

        Gasto gastoOriginal= buscar(gasto.getId());

        if(gastoOriginal==null){
            throw new ObjetoNuloException("No existe el gasto que se intenta modificar");
        }

        int indice=listado.indexOf(gastoOriginal);

        listado.set(indice,gasto);

        return true;
    }

    //VER LISTADOS
    @Override
    public List<Gasto> verListado() {
        return new ArrayList<>(listado);
    }
}

