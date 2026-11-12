package com.example.trabajofinalprogramacion2.Service.Gimnasio;

import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.GimnasioCollection;
import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.MaquinaCollection;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.GimnasioDAO;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.MaquinaDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Maquina;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.sql.SQLException;
import java.util.List;

public class MaquinaService {
    private MaquinaDAO dao;
    private MaquinaCollection coleccion;

    public MaquinaService() throws SQLException{
        dao= new MaquinaDAO();
        coleccion= new MaquinaCollection();

        List<Maquina> maquinas= dao.listado();

        for(Maquina maquina : maquinas){
            coleccion.agregar(maquina);
        }
    }

    //AGREGAR
    public boolean agregar(Maquina maquina) throws SQLException, ObjetoNuloException {
        if(maquina == null){
            throw new ObjetoNuloException("No se puede agregar una maquina nula");
        }

        Maquina maquinaAgregada= dao.agregar(maquina);

        if(maquinaAgregada.getId()!=0){ //Al retornar el objeto que se le paso, si el id esta en 0 es porque la base de datos no lo agrego.
            return coleccion.agregar(maquinaAgregada);
        }

        return false;
    }

    //ALTA Y BAJA
    public boolean alta(Maquina maquina) throws SQLException, ObjetoNuloException {
        if(maquina == null){
            throw new ObjetoNuloException("No se puede dar de alta una maquina nula");
        }

        if(dao.alta(maquina)){
            return coleccion.alta(maquina.getId());
        }

        return false;
    }

    public boolean baja(Maquina maquina) throws SQLException, ObjetoNuloException {
        if(maquina == null){
            throw new ObjetoNuloException("No se puede dar de baja una maquina nula");
        }

        if(dao.baja(maquina)){
            return coleccion.baja(maquina.getId());
        }

        return false;
    }

    //MODIFICACION
    public boolean modificar(Maquina maquinaModificada) throws SQLException, ObjetoNuloException, ValorNoValidoException {
        if(maquinaModificada==null){
            throw new ObjetoNuloException("La maquina recibida para modificar es nula");
        }
        if (maquinaModificada.getId() <= 0) {
            throw new ValorNoValidoException("El ID de la maquina no es válido");
        }

        if(dao.modificar(maquinaModificada)){
            return coleccion.modificar(maquinaModificada);
        }

        return false;
    }

    public boolean modificarNombre(int id, String nombreNuevo) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || nombreNuevo==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Maquina maquinaBuscada= coleccion.buscar(id);
        if(maquinaBuscada==null){
            throw new ObjetoNuloException("La maquina a modificar no existe");
        }

        Maquina maquinaCopia= new Maquina();
        maquinaCopia.setId(maquinaBuscada.getId());
        maquinaCopia.setDescripcion(maquinaBuscada.getDescripcion());
        maquinaCopia.setPesoLimite(maquinaBuscada.getPesoLimite());
        maquinaCopia.setGrupoMuscular(maquinaBuscada.getGrupoMuscular());
        maquinaCopia.setEstado(maquinaBuscada.getEstado());
        maquinaCopia.setNombre(nombreNuevo);

        if(dao.modificar(maquinaCopia)){
            return coleccion.modificarNombre(id,nombreNuevo);
        }

        return false;
    }

    public boolean modificarDescripcion(int id, String descripcionNueva) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || descripcionNueva==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        Maquina maquinaBuscada= coleccion.buscar(id);
        if(maquinaBuscada==null){
            throw new ObjetoNuloException("La maquina a modificar no existe");
        }

        Maquina maquinaCopia= new Maquina();
        maquinaCopia.setId(maquinaBuscada.getId());
        maquinaCopia.setNombre(maquinaBuscada.getNombre());
        maquinaCopia.setPesoLimite(maquinaBuscada.getPesoLimite());
        maquinaCopia.setGrupoMuscular(maquinaBuscada.getGrupoMuscular());
        maquinaCopia.setEstado(maquinaBuscada.getEstado());
        maquinaCopia.setDescripcion(descripcionNueva);

        if(dao.modificar(maquinaCopia)){
            return coleccion.modificarDescripcion(id,descripcionNueva);
        }

        return false;
    }

    public boolean modificarPesoLimite(int id, int pesoLimiteNuevo) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || pesoLimiteNuevo < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        Maquina maquinaBuscada= coleccion.buscar(id);
        if(maquinaBuscada==null){
            throw new ObjetoNuloException("La maquina a modificar no existe");
        }

        Maquina maquinaCopia= new Maquina();
        maquinaCopia.setId(maquinaBuscada.getId());
        maquinaCopia.setNombre(maquinaBuscada.getNombre());
        maquinaCopia.setDescripcion(maquinaBuscada.getDescripcion());
        maquinaCopia.setGrupoMuscular(maquinaBuscada.getGrupoMuscular());
        maquinaCopia.setEstado(maquinaBuscada.getEstado());
        maquinaCopia.setPesoLimite(pesoLimiteNuevo);

        if(dao.modificar(maquinaCopia)){
            return coleccion.modificarPesoLimite(id,pesoLimiteNuevo);
        }

        return false;
    }

    public boolean modificarGrupoMuscular(int id, String grupoMuscularNuevo) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || grupoMuscularNuevo==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        Maquina maquinaBuscada= coleccion.buscar(id);
        if(maquinaBuscada==null){
            throw new ObjetoNuloException("La maquina a modificar no existe");
        }

        Maquina maquinaCopia= new Maquina();
        maquinaCopia.setId(maquinaBuscada.getId());
        maquinaCopia.setNombre(maquinaBuscada.getNombre());
        maquinaCopia.setPesoLimite(maquinaBuscada.getPesoLimite());
        maquinaCopia.setDescripcion(maquinaBuscada.getDescripcion());
        maquinaCopia.setEstado(maquinaBuscada.getEstado());
        maquinaCopia.setGrupoMuscular(grupoMuscularNuevo);

        if(dao.modificar(maquinaCopia)){
            return coleccion.modificarGrupoMuscular(id,grupoMuscularNuevo);
        }

        return false;
    }

    //BUSQUEDA
    public Maquina buscarPorNombre(String nombre){
        if(nombre==null){
            throw new ValorNoValidoException("El nombre a buscar no puede ser nulo");
        }

        return coleccion.buscarPorNombre(nombre);
    }

    public List<Maquina> buscarPorActivos(){
        return coleccion.buscarPorActivos();
    }

    public List<Maquina> buscarPorInactivos(){
        return coleccion.buscarPorInactivos();
    }

    //LISTADOS
    public List<Maquina> verListado(){
        return coleccion.verListado();
    }

    public List<Maquina> verActivosOrdenadoPorNombre(){
        return coleccion.verActivosOrdenadoPorNombre();
    }

    public List<Maquina> verInactivosOrdenadoPorNombre(){
        return coleccion.verInactivosOrdenadoPorNombre();
    }

    public List<Maquina> verActivosOrdenadoPorGrupoMuscular(){
        return coleccion.verActivosOrdenadoPorGrupoMuscular();
    }

    public List<Maquina> verInactivosOrdenadoPorGrupoMuscular(){
        return coleccion.verInactivosOrdenadoPorGrupoMuscular();
    }

    public List<Maquina> verActivosOrdenadoPorPesoLimiteAscendente(){
        return coleccion.verActivosOrdenadoPorPesoLimiteAscendente();
    }

    public List<Maquina> verInactivosOrdenadoPorPesoLimiteAscendente(){
        return coleccion.verInactivosOrdenadoPorPesoLimiteAscendente();
    }

    public List<Maquina> verActivosOrdenadoPorPesoLimiteDescendente(){
        return coleccion.verActivosOrdenadoPorPesoLimiteDescendente();
    }

    public List<Maquina> verInactivosOrdenadoPorPesoLimiteDescendente(){
        return coleccion.verInactivosOrdenadoPorPesoLimiteDescendente();
    }
}
