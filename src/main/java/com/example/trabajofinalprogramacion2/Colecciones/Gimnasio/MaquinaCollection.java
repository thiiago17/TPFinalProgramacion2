package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Maquina;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//estos metodos los deberia invocar el administrador del sistema, ya que van a actuar sobre los datos de las maquinas que hay en el sistema, no las especificas de cada sucursal
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

    public List<Maquina> buscarPorActivos(){
        List<Maquina> maquinasEncontradas= new ArrayList<>();

        for(Maquina maquina : listado){
            if(maquina.getEstado()==1){
                maquinasEncontradas.add(maquina);
            }
        }

        return maquinasEncontradas;
    }

    public List<Maquina> buscarPorInactivos(){
        List<Maquina> maquinasEncontradas= new ArrayList<>();

        for(Maquina maquina : listado){
            if(maquina.getEstado()==0){
                maquinasEncontradas.add(maquina);
            }
        }

        return maquinasEncontradas;
    }

    public Maquina buscarPorNombre(String nombre) throws ValorNoValidoException {
        if (nombre == null) {
            throw new ValorNoValidoException("El nombre a buscar no es válido");
        }

        for (Maquina maquina : listado) {
            if (maquina.getNombre().equalsIgnoreCase(nombre) && maquina.getEstado() == 1) {
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

    public boolean alta(int id) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        Maquina maquina=buscar(id);

        if(maquina==null){
            throw new ObjetoNuloException("No existe la maquina que se intenta dar de alta");
        }

        maquina.setEstado(1);

        return true;
    }

    public boolean baja(int id) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        Maquina maquina=buscar(id);

        if(maquina==null){
            throw new ObjetoNuloException("No existe la maquina que se intenta dar de baja");
        }

        maquina.setEstado(0);

        return true;
    }

    public boolean modificarNombre(int id, String nombreNuevo) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0 || nombreNuevo==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        Maquina maquina=buscar(id);

        if(maquina==null){
            throw new ObjetoNuloException("No existe la maquina que se intenta modificar");
        }

        maquina.setNombre(nombreNuevo);

        return true;
    }

    public boolean modificarDescripcion(int id, String descripcionNueva) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0 || descripcionNueva==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        Maquina maquina=buscar(id);

        if(maquina==null){
            throw new ObjetoNuloException("No existe la maquina que se intenta modificar");
        }

        maquina.setDescripcion(descripcionNueva);

        return true;
    }

    public boolean modificarPesoLimite(int id, double pesoLimiteNuevo) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0 || pesoLimiteNuevo < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        Maquina maquina=buscar(id);

        if(maquina==null){
            throw new ObjetoNuloException("No existe la maquina que se intenta modificar");
        }

        maquina.setPesoLimite(pesoLimiteNuevo);

        return true;
    }

    public boolean modificarGrupoMuscular(int id, String grupoMuscularNuevo) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0 || grupoMuscularNuevo==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        Maquina maquina=buscar(id);

        if(maquina==null){
            throw new ObjetoNuloException("No existe la maquina que se intenta modificar");
        }

        maquina.setGrupoMuscular(grupoMuscularNuevo);

        return true;
    }

    //VER LISTADOS
    @Override
    public List<Maquina> verListado() {
        return new ArrayList<>(listado);
    }

    public List<Maquina> verActivosOrdenadoPorNombre(){
        List<Maquina> listaOrdenada= buscarPorActivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getNombre));

        return listaOrdenada;
    }

    public List<Maquina> verInactivosOrdenadoPorNombre(){
        List<Maquina> listaOrdenada= buscarPorInactivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getNombre));

        return listaOrdenada;
    }

    public List<Maquina> verActivosOrdenadoPorGrupoMuscular(){
        List<Maquina> listaOrdenada= buscarPorActivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getGrupoMuscular));

        return listaOrdenada;
    }

    public List<Maquina> verInactivosOrdenadoPorGrupoMuscular(){
        List<Maquina> listaOrdenada= buscarPorInactivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getGrupoMuscular));

        return listaOrdenada;
    }

    public List<Maquina> verActivosOrdenadoPorPesoLimiteAscendente(){
        List<Maquina> listaOrdenada= buscarPorActivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getPesoLimite));

        return listaOrdenada;
    }

    public List<Maquina> verInactivosOrdenadoPorPesoLimiteAscendente(){
        List<Maquina> listaOrdenada= buscarPorInactivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getPesoLimite));

        return listaOrdenada;
    }

    public List<Maquina> verActivosOrdenadoPorPesoLimiteDescendente(){
        List<Maquina> listaOrdenada= buscarPorActivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getPesoLimite).reversed());

        return listaOrdenada;
    }

    public List<Maquina> verInactivosOrdenadoPorPesoLimiteDescendente(){
        List<Maquina> listaOrdenada= buscarPorInactivos();

        listaOrdenada.sort(Comparator.comparing(Maquina::getPesoLimite).reversed());

        return listaOrdenada;
    }


}
