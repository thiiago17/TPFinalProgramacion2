package com.example.trabajofinalprogramacion2.Service.Gimnasio;

import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.GimnasioCollection;
import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.SucursalCollection;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.GimnasioDAO;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.SucursalDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.sql.SQLException;
import java.util.List;

public class SucursalService {
    private SucursalDAO dao;
    private SucursalCollection coleccion;

    public SucursalService() throws SQLException {
        dao= new SucursalDAO();
        coleccion= new SucursalCollection();

        List<Sucursal> sucursales= dao.listado();

        for(Sucursal sucursal : sucursales){
            coleccion.agregar(sucursal);
        }
    }

    //AGREGAR
    public boolean agregar(Sucursal sucursal) throws SQLException, ObjetoNuloException {
        if(sucursal == null){
            throw new ObjetoNuloException("No se puede agregar una sucursal nula");
        }

        Sucursal sucursalAgregada= dao.agregar(sucursal);

        if(sucursalAgregada.getId()!=0){ //Al retornar el objeto que se le paso, si el id esta en 0 es porque la base de datos no lo agrego.
            return coleccion.agregar(sucursalAgregada);
        }

        return false;
    }

    //BAJA Y ALTA
    public boolean alta(Sucursal sucursal) throws SQLException, ObjetoNuloException {
        if(sucursal == null){
            throw new ObjetoNuloException("No se puede dar de alta una sucursal nula");
        }

        if(dao.alta(sucursal)){
            return coleccion.alta(sucursal.getId());
        }

        return false;
    }

    public boolean baja(Sucursal sucursal) throws SQLException, ObjetoNuloException {
        if(sucursal == null){
            throw new ObjetoNuloException("No se puede dar de baja una sucursal nula");
        }

        if(dao.baja(sucursal)){
            return coleccion.baja(sucursal.getId());
        }

        return false;
    }

    //MODIFICACION
    public boolean modificar(Sucursal sucursalModificada) throws SQLException, ObjetoNuloException, ValorNoValidoException {
        if(sucursalModificada==null){
            throw new ObjetoNuloException("La sucursal recibida para modificar es nula");
        }
        if (sucursalModificada.getId() <= 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }

        if(dao.modificar(sucursalModificada)){
            return coleccion.modificar(sucursalModificada);
        }

        return false;
    }

    public boolean modificarCalle(int id, String calleNueva) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || calleNueva==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Sucursal sucursalBuscada= coleccion.buscar(id);
        if(sucursalBuscada==null){
            throw new ObjetoNuloException("La sucursal a modificar no existe");
        }

        Sucursal sucursalCopia= new Sucursal();
        sucursalCopia.setId(sucursalBuscada.getId());
        sucursalCopia.setAltura(sucursalBuscada.getAltura());
        sucursalCopia.setCiudad(sucursalBuscada.getCiudad());
        sucursalCopia.setEstado(sucursalBuscada.getEstado());
        sucursalCopia.setIdGimnasio(sucursalBuscada.getIdGimnasio());
        sucursalCopia.setCalle(calleNueva);

        if(dao.modificar(sucursalCopia)){
            return coleccion.modificarCalle(id,calleNueva);
        }

        return false;
    }

    public boolean modificarAltura(int id, int alturaNueva) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || alturaNueva < 0){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Sucursal sucursalBuscada= coleccion.buscar(id);
        if(sucursalBuscada==null){
            throw new ObjetoNuloException("La sucursal a modificar no existe");
        }

        Sucursal sucursalCopia= new Sucursal();
        sucursalCopia.setId(sucursalBuscada.getId());
        sucursalCopia.setCalle(sucursalBuscada.getCalle());
        sucursalCopia.setCiudad(sucursalBuscada.getCiudad());
        sucursalCopia.setEstado(sucursalBuscada.getEstado());
        sucursalCopia.setIdGimnasio(sucursalBuscada.getIdGimnasio());
        sucursalCopia.setAltura(alturaNueva);

        if(dao.modificar(sucursalCopia)){
            return coleccion.modificarAltura(id,alturaNueva);
        }

        return false;
    }

    public boolean modificarCiudad(int id, String ciudadNueva) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || ciudadNueva==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Sucursal sucursalBuscada= coleccion.buscar(id);
        if(sucursalBuscada==null){
            throw new ObjetoNuloException("La sucursal a modificar no existe");
        }

        Sucursal sucursalCopia= new Sucursal();
        sucursalCopia.setId(sucursalBuscada.getId());
        sucursalCopia.setAltura(sucursalBuscada.getAltura());
        sucursalCopia.setCalle(sucursalBuscada.getCalle());
        sucursalCopia.setEstado(sucursalBuscada.getEstado());
        sucursalCopia.setIdGimnasio(sucursalBuscada.getIdGimnasio());
        sucursalCopia.setCiudad(ciudadNueva);

        if(dao.modificar(sucursalCopia)){
            return coleccion.modificarCiudad(id,ciudadNueva);
        }

        return false;
    }


    //BUSQUEDA (los que devuelven un objeto incluyen a los inactivos (estado=0))
    public Sucursal buscarPorDireccion(String calle, int altura) throws ValorNoValidoException{
        if(calle==null || altura < 0){
            throw new ValorNoValidoException("Alguno de los valores de la direccion no es valido");
        }

        return coleccion.buscarPorDireccion(calle,altura);
    }

    public List<Sucursal> buscarPorCiudad(String ciudad) throws ValorNoValidoException{
        if(ciudad==null){
            throw new ValorNoValidoException("La ciudad a buscar no puede ser nula");
        }

        return coleccion.buscarPorCiudad(ciudad);
    }

    public List<Sucursal> buscarPorGimnasio(int idGimnasio) throws ValorNoValidoException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("El gimnasio no es valido");
        }

        return coleccion.buscarPorGimnasio(idGimnasio);
    }

    public List<Sucursal> buscarPorGimnasioYCiudad(int idGimnasio, String ciudad) throws ValorNoValidoException{
        if(idGimnasio < 0 || ciudad==null){
            throw new ValorNoValidoException("Algunos de los valores no son validos");
        }

        return coleccion.buscarPorGimnasioYCiudad(idGimnasio,ciudad);
    }

    public Sucursal buscarPorGimnasioYDireccion(int idGimnasio, String calle, int altura) throws ValorNoValidoException{
        if(idGimnasio < 0 || calle==null || altura < 0){
            throw new ValorNoValidoException("Algunos de los valores no son validos");
        }

        return coleccion.buscarPorGimnasioYDireccion(idGimnasio,calle,altura);
    }

    public List<Sucursal> buscarPorActivos() throws ValorNoValidoException{
        return coleccion.buscarPorActivos();
    }

    public List<Sucursal> buscarPorInactivos() throws ValorNoValidoException{
        return coleccion.buscarPorInactivos();
    }

    //LISTADOS (para el administrador del sistema, ya que muestra TODAS las sucursales del sistema)
    public List<Sucursal> verListado(){
        return coleccion.verListado();
    }

    public List<Sucursal> verListadoOrdenadoPorCalle(){
        return coleccion.verListadoOrdenadoPorCalle();
    }

    public List<Sucursal> verListadoOrdenadoPorCiudad(){
        return coleccion.verListadoOrdenadoPorCiudad();
    }

    public List<Sucursal> verActivosOrdenadoPorCalle(){
        return coleccion.verActivosOrdenadoPorCalle();
    }

    public List<Sucursal> verActivosOrdenadoPorCiudad(){
        return coleccion.verActivosOrdenadoPorCiudad();
    }

    public List<Sucursal> verInactivosOrdenadoPorCalle(){
        return coleccion.verInactivosOrdenadoPorCalle();
    }

    public List<Sucursal> verInactivosOrdenadoPorCiudad(){
        return coleccion.verInactivosOrdenadoPorCiudad();
    }

}
