package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.SucursalMaquina;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.util.*;

public class SucursalMaquinaCollection implements ABCMLCollection<SucursalMaquina> {
    private Map<SucursalMaquina,Integer> mapMaquinas= new HashMap<>(); //el Integer es la cantidad

    //AGREGAR
    @Override
    public boolean agregar(SucursalMaquina sucursalMaquina) throws ObjetoNuloException {
        if (sucursalMaquina == null) {
            throw new ObjetoNuloException("El objeto SucursalMaquina no puede ser nulo");
        }

        mapMaquinas.put(sucursalMaquina, sucursalMaquina.getCantidad());
        return true;
    }

    //BUSQUEDA
    @Override
    public SucursalMaquina buscar(int id) {
        for (SucursalMaquina relacion : mapMaquinas.keySet()) {
            if (relacion.getIdSucursalMaquina() == id) {
                return relacion;
            }
        }
        return null;
    }

    public List<SucursalMaquina> buscarActivosPorIdSucursal(int idSucursal) throws ValorNoValidoException {
        if (idSucursal < 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }

        List<SucursalMaquina> encontradas = new ArrayList<>();
        for (SucursalMaquina relacion : mapMaquinas.keySet()) {
            if (relacion.getIdSucursal() == idSucursal && relacion.getEstado() == 1) {
                encontradas.add(relacion);
            }
        }
        return encontradas;
    }

    public List<SucursalMaquina> buscarActivosPorIdMaquina(int idMaquina) throws ValorNoValidoException { //esta deberia usarla el admin del sistema, porque trae todas las maquinas sin importar su sucursal
        if (idMaquina < 0) {
            throw new ValorNoValidoException("El ID de la máquina no es válido");
        }

        List<SucursalMaquina> encontradas = new ArrayList<>();
        for (SucursalMaquina relacion : mapMaquinas.keySet()) {
            if (relacion.getIdMaquina() == idMaquina && relacion.getEstado() == 1) {
                encontradas.add(relacion);
            }
        }
        return encontradas;
    }

    public List<SucursalMaquina> buscarInactivosPorIdSucursal(int idSucursal) throws ValorNoValidoException {
        if (idSucursal < 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }

        List<SucursalMaquina> encontradas = new ArrayList<>();
        for (SucursalMaquina relacion : mapMaquinas.keySet()) {
            if (relacion.getIdSucursal() == idSucursal && relacion.getEstado() == 0) {
                encontradas.add(relacion);
            }
        }
        return encontradas;
    }

    public List<SucursalMaquina> buscarInactivosPorIdMaquina(int idMaquina) throws ValorNoValidoException { //esta deberia usarla el admin del sistema, porque trae todas las maquinas sin importar su sucursal
        if (idMaquina < 0) {
            throw new ValorNoValidoException("El ID de la máquina no es válido");
        }

        List<SucursalMaquina> encontradas = new ArrayList<>();
        for (SucursalMaquina relacion : mapMaquinas.keySet()) {
            if (relacion.getIdMaquina() == idMaquina && relacion.getEstado() == 0) {
                encontradas.add(relacion);
            }
        }
        return encontradas;
    }

    //ELIMINAR
    @Override
    public boolean eliminar(int id) throws ValorNoValidoException, ObjetoNuloException {
        if (id < 0) {
            throw new ValorNoValidoException("El id es inválido");
        }

        SucursalMaquina relacion = buscar(id);

        if (relacion == null) {
            throw new ObjetoNuloException("No existe la relacion que se intenta eliminar");
        }

        mapMaquinas.remove(relacion);
        return true;
    }

    //MODIFICACION
    @Override
    public boolean modificar(SucursalMaquina objetoModificado) throws ObjetoNuloException {
        if (objetoModificado == null) {
            throw new ObjetoNuloException("El objeto a modificar no puede ser nulo");
        }

        SucursalMaquina relacionOriginal = buscar(objetoModificado.getIdSucursalMaquina());

        if (relacionOriginal == null) {
            throw new ObjetoNuloException("No existe la relación que se intenta modificar");
        }

        mapMaquinas.remove(relacionOriginal);

        mapMaquinas.put(objetoModificado, objetoModificado.getCantidad());

        return true;
    }

    public boolean modificarCantidad(int id, int nuevaCantidad) throws ValorNoValidoException, ObjetoNuloException {
        if (id < 0 || nuevaCantidad < 0) {
            throw new ValorNoValidoException("Los valores no son válidos");
        }

        SucursalMaquina relacion = buscar(id);

        if (relacion == null) {
            throw new ObjetoNuloException("No existe la relacion que se intenta modificar");
        }

        relacion.setCantidad(nuevaCantidad);
        mapMaquinas.put(relacion, nuevaCantidad);

        return true;
    }

    //LISTADO
    @Override
    public List<SucursalMaquina> verListado() {
        return new ArrayList<>(mapMaquinas.keySet());
    }

    public List<SucursalMaquina> verActivosOrdenadoPorCantidadAscendente(int idSucursal) throws ValorNoValidoException{
        if (idSucursal < 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }

        List<SucursalMaquina> listaOrdenada=buscarActivosPorIdSucursal(idSucursal);

        listaOrdenada.sort(Comparator.comparing(SucursalMaquina::getCantidad));

        return listaOrdenada;
    }

    public List<SucursalMaquina> verActivosOrdenadoPorCantidadDescendente(int idSucursal) throws ValorNoValidoException{
        if (idSucursal < 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }

        List<SucursalMaquina> listaOrdenada=buscarActivosPorIdSucursal(idSucursal);

        listaOrdenada.sort(Comparator.comparing(SucursalMaquina::getCantidad).reversed());

        return listaOrdenada;
    }

    //OTROS METODOS
    public int contarMaquinasPorSucursal(int idSucursal) throws ValorNoValidoException {
        if (idSucursal < 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }

        int total = 0;
        List<SucursalMaquina> maquinasEnSucursal = buscarActivosPorIdSucursal(idSucursal);

        for (SucursalMaquina relacion : maquinasEnSucursal) {
            total = total + relacion.getCantidad();
        }

        return total;
    }
}
