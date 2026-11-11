package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.ArrayList;
import java.util.Comparator;
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

    //BUSQUEDA (los que devuelven un objeto incluyen a los inactivos (estado=0)
    @Override
    public Sucursal buscar(int id) {
        for(Sucursal sucursal : listado){
            if(sucursal.getId()==id){
                return sucursal;
            }
        }

        return null;
    }

    public Sucursal buscarPorDireccion(String calle, int altura) throws ValorNoValidoException {
        if(calle==null || altura < 0){
            throw new ValorNoValidoException("Alguno de los valores de la direccion no es valido");
        }

        for(Sucursal sucursal : listado){
            if(sucursal.getCalle().equalsIgnoreCase(calle) && sucursal.getAltura()==altura){
                return sucursal;
            }
        }

        return null;
    }

    public List<Sucursal> buscarPorCiudad(String ciudad) throws ValorNoValidoException{
        if(ciudad==null){
            throw new ValorNoValidoException("La ciudad a buscar no puede ser nula");
        }

        List<Sucursal> sucursalesEncontradas= new ArrayList<>();

        for(Sucursal sucursal : listado){
            if(sucursal.getCiudad().equalsIgnoreCase(ciudad) && sucursal.getEstado()==1){
                sucursalesEncontradas.add(sucursal);
            }
        }

        return sucursalesEncontradas;
    }

    public List<Sucursal> buscarPorGimnasio(int idGimnasio) throws ValorNoValidoException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("El gimnasio no es valido");
        }

        List<Sucursal> sucursalesEncontradas= new ArrayList<>();

        for(Sucursal sucursal : listado){
            if(sucursal.getIdGimnasio()==idGimnasio && sucursal.getEstado()==1){
                sucursalesEncontradas.add(sucursal);
            }
        }

        return sucursalesEncontradas;
    }

    public List<Sucursal> buscarPorGimnasioYCiudad(int idGimnasio, String ciudad) throws ValorNoValidoException{
        if(idGimnasio < 0 || ciudad==null){
            throw new ValorNoValidoException("Algunos de los valores no son validos");
        }

        List<Sucursal> sucursalesEncontradas= new ArrayList<>();

        for(Sucursal sucursal : listado){
            if(sucursal.getIdGimnasio()==idGimnasio && sucursal.getCiudad().equalsIgnoreCase(ciudad) && sucursal.getEstado()==1){
                sucursalesEncontradas.add(sucursal);
            }
        }

        return sucursalesEncontradas;
    }

    public Sucursal buscarPorGimnasioYDireccion(int idGimnasio, String calle, int altura) throws ValorNoValidoException{
        if(idGimnasio < 0 || calle==null || altura < 0){
            throw new ValorNoValidoException("Algunos de los valores no son validos");
        }

        for(Sucursal sucursal : listado){
            if(sucursal.getIdGimnasio()==idGimnasio && sucursal.getCalle().equalsIgnoreCase(calle) && sucursal.getAltura()==altura){
                return sucursal;
            }
        }

        return null;
    }

    public List<Sucursal> buscarPorActivos(){
        List<Sucursal> sucursalesEncontradas= new ArrayList<>();

        for(Sucursal sucursal : listado){
            if(sucursal.getEstado()==1){
                sucursalesEncontradas.add(sucursal);
            }
        }

        return sucursalesEncontradas;
    }

    public List<Sucursal> buscarPorInactivos(){
        List<Sucursal> sucursalesEncontradas= new ArrayList<>();

        for(Sucursal sucursal : listado){
            if(sucursal.getEstado()==0){
                sucursalesEncontradas.add(sucursal);
            }
        }

        return sucursalesEncontradas;
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

    public boolean alta(int id) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El id es invalido");
        }

        Sucursal sucursal=buscar(id);

        if(sucursal==null){
            throw new ObjetoNuloException("No existe la sucursal que se intenta dar de alta");
        }

        sucursal.setEstado(1);

        return true;
    }

    public boolean baja(int id) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El id es invalido");
        }

        Sucursal sucursal=buscar(id);

        if(sucursal==null){
            throw new ObjetoNuloException("No existe la sucursal que se intenta dar de baja");
        }

        sucursal.setEstado(0);

        return true;
    }

    public boolean modificarCalle(int id, String calleNueva) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || calleNueva==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Sucursal sucursal=buscar(id);

        if(sucursal==null){
            throw new ObjetoNuloException("No existe la sucursal que se intenta modificar");
        }

        sucursal.setCalle(calleNueva);

        return true;
    }

    public boolean modificarAltura(int id, int alturaNueva) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || alturaNueva < 0){
            throw new ValorNoValidoException("Los valores no son validos");
        }

        Sucursal sucursal=buscar(id);

        if(sucursal==null){
            throw new ObjetoNuloException("No existe la sucursal que se intenta modificar");
        }

        sucursal.setAltura(alturaNueva);

        return true;
    }

    public boolean modificarCiudad(int id, String ciudadNueva) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || ciudadNueva==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Sucursal sucursal=buscar(id);

        if(sucursal==null){
            throw new ObjetoNuloException("No existe la sucursal que se intenta modificar");
        }

        sucursal.setCiudad(ciudadNueva);

        return true;
    }

    //VER LISTADOS (para el administrador del sistema, ya que muestra TODAS las sucursales del sistema)
    @Override
    public List<Sucursal> verListado() {
        return new ArrayList<>(listado);
    }

    public List<Sucursal> verListadoOrdenadoPorCalle(){
        List<Sucursal> listaOrdenada= new ArrayList<>(listado);

        listaOrdenada.sort(Comparator.comparing(Sucursal::getCalle));

        return listaOrdenada;
    }

    public List<Sucursal> verListadoOrdenadoPorCiudad(){
        List<Sucursal> listaOrdenada= new ArrayList<>(listado);

        listaOrdenada.sort(Comparator.comparing(Sucursal::getCiudad));

        return listaOrdenada;
    }

    public List<Sucursal> verActivosOrdenadoPorCalle(){
        List<Sucursal> listaOrdenada= buscarPorActivos();

        listaOrdenada.sort(Comparator.comparing(Sucursal::getCalle));

        return listaOrdenada;
    }

    public List<Sucursal> verActivosOrdenadoPorCiudad(){
        List<Sucursal> listaOrdenada= buscarPorActivos();

        listaOrdenada.sort(Comparator.comparing(Sucursal::getCiudad));

        return listaOrdenada;
    }

    public List<Sucursal> verInactivosOrdenadoPorCalle(){
        List<Sucursal> listaOrdenada= buscarPorInactivos();

        listaOrdenada.sort(Comparator.comparing(Sucursal::getCalle));

        return listaOrdenada;
    }

    public List<Sucursal> verInactivosOrdenadoPorCiudad(){
        List<Sucursal> listaOrdenada= buscarPorInactivos();

        listaOrdenada.sort(Comparator.comparing(Sucursal::getCiudad));

        return listaOrdenada;
    }
}
