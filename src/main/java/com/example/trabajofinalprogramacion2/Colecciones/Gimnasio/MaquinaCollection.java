package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Maquina;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.List;

public class MaquinaCollection implements ABCMLCollection<Maquina> {
    private List<Maquina> listado = new ArrayList<>();

    //AGREGAR
    @Override
    public boolean agregar(Maquina maquina) throws ObjetoNuloException {
        if(maquina == null){
            throw new ObjetoNuloException("No se puede agregar una maquina nula");
        }

        return listado.add(maquina);
    }

    //BUSQUEDA
    @Override
    public Maquina buscar(int id) {
        for(Maquina maquina : listado){
            if(maquina.getId()==id){
                return maquina;
            }
        }

        return null;
    }


    //ELIMINAR
    @Override
    public boolean eliminar(int id) throws ObjetoNuloException{
        Maquina maquinaAEliminar=buscar(id);

        if(maquinaAEliminar == null){
            throw new ObjetoNuloException("No existe la maquina que se intenta eliminar");
        }

        return listado.remove(maquinaAEliminar);
    }

    //MODIFICACION
    @Override
    public boolean modificar(Maquina maquina) throws ObjetoNuloException{
        if(maquina==null){
            throw new ObjetoNuloException("La maquina recibida para modificar es nula");
        }

        Maquina maquinaOriginal= buscar(maquina.getId());

        if(maquinaOriginal==null){
            throw new ObjetoNuloException("No existe la maquina que se intenta modificar");
        }

        int indice=listado.indexOf(maquinaOriginal);

        listado.set(indice,maquina);

        return true;
    }

    //VER LISTADOS
    @Override
    public List<Maquina> verListado() {
        return new ArrayList<>(listado);
    }
}
