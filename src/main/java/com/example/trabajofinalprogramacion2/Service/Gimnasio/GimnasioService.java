package com.example.trabajofinalprogramacion2.Service.Gimnasio;

import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.GimnasioCollection;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.GimnasioDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class GimnasioService {
    private GimnasioDAO dao;
    private GimnasioCollection coleccion;

    public GimnasioService() throws SQLException {
        dao= new GimnasioDAO();
        coleccion= new GimnasioCollection();

        List<Gimnasio> gimnasios= dao.listado();

        for(Gimnasio gimnasio : gimnasios){
            coleccion.agregar(gimnasio);
        }
    }

    //AGREGAR
    public boolean agregar(Gimnasio gimnasio) throws SQLException, ObjetoNuloException{
        if(gimnasio == null){
            throw new ObjetoNuloException("No se puede agregar un gimnasio nulo");
        }

        Gimnasio gimnasioAgregado= dao.agregar(gimnasio);

        if(gimnasioAgregado.getId()!=0){ //Al retornar el objeto que se le paso, si el id esta en 0 es porque la base de datos no lo agrego.
            return coleccion.agregar(gimnasioAgregado);
        }

        return false;
    }

    //ALTA Y BAJA
    public boolean alta(Gimnasio gimnasio) throws SQLException, ObjetoNuloException {
        if(gimnasio == null){
            throw new ObjetoNuloException("No se puede dar de alta un gimnasio nulo");
        }

        if(dao.alta(gimnasio)){
            return coleccion.alta(gimnasio.getId());
        }

        return false;
    }

    public boolean baja(Gimnasio gimnasio) throws SQLException, ObjetoNuloException {
        if(gimnasio == null){
            throw new ObjetoNuloException("No se puede dar de baja un gimnasio nulo");
        }

        if(dao.baja(gimnasio)){
            return coleccion.baja(gimnasio.getId());
        }

        return false;
    }

    //MODIFICAR
    public boolean modificar(Gimnasio gimnasioModificado) throws SQLException, ObjetoNuloException, ValorNoValidoException{
        if(gimnasioModificado==null){
            throw new ObjetoNuloException("El gimnasio recibido para modificar es nulo");
        }
        if (gimnasioModificado.getId() <= 0) {
            throw new ValorNoValidoException("El ID del gimnasio no es válido");
        }

        if(dao.modificar(gimnasioModificado)){
            return coleccion.modificar(gimnasioModificado);
        }

        return false;
    }

    public boolean modificarNombre(int id, String nombreNuevo) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || nombreNuevo==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gimnasio gimnasioBuscado= coleccion.buscar(id);
        if(gimnasioBuscado==null){
            throw new ObjetoNuloException("El gimnasio a modificar no existe");
        }

        Gimnasio gimnasioCopia= new Gimnasio();
        gimnasioCopia.setId(gimnasioBuscado.getId());
        gimnasioCopia.setEstado(gimnasioBuscado.getEstado());
        gimnasioCopia.setNombre(nombreNuevo);

        if(dao.modificar(gimnasioCopia)){
            return coleccion.modificarNombre(id,nombreNuevo);
        }

        return false;
    }

    //BUSQUEDA
    public Gimnasio buscarPorId(int id) throws ValorNoValidoException {
        if (id <= 0) {
            throw new ValorNoValidoException("El ID del gimnasio no es válido");
        }

        return coleccion.buscar(id);
    }

    public List<Gimnasio> buscarPorNombre(String nombre) throws ValorNoValidoException{
        if(nombre==null){
            throw new ValorNoValidoException("El nombre a buscar no puede ser nulo");
        }

        return coleccion.buscarPorNombre(nombre);
    }

    public List<Gimnasio> buscarPorActivos(){
        return coleccion.buscarPorActivos();
    }

    public List<Gimnasio> buscarPorInactivos(){
        return coleccion.buscarPorInactivos();
    }

    //LISTADO
    public List<Gimnasio> verListado(){
        return coleccion.verListado();
    }

    public List<Gimnasio> verListadoOrdenadoPorNombre(){
        return coleccion.verListadoOrdenadoPorNombre();
    }

    public List<Gimnasio> verActivosOrdenadoPorNombre(){
        return coleccion.verActivosOrdenadoPorNombre();
    }

    public List<Gimnasio> verInactivosOrdenadoPorNombre(){
        return coleccion.verInactivosOrdenadoPorNombre();
    }
}
