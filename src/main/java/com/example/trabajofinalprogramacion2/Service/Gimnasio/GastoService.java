package com.example.trabajofinalprogramacion2.Service.Gimnasio;

import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.GastoCollection;
import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.GimnasioCollection;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.GastoDAO;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.GimnasioDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.TipoGasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GastoService {
    private GastoDAO dao;
    private GastoCollection coleccion;

    public GastoService() {
        dao= new GastoDAO();
        coleccion= new GastoCollection();

        List<Gasto> gastos= dao.listado();

        for(Gasto gasto : gastos){
            coleccion.agregar(gasto);
        }
    }

    //AGREGAR
    public boolean agregar(Gasto gasto) throws SQLException, ObjetoNuloException {
        if(gasto == null){
            throw new ObjetoNuloException("No se puede agregar un gasto nulo");
        }

        Gasto gastoAgregado= dao.agregar(gasto);

        if(gastoAgregado.getId()!=0){ //Al retornar el objeto que se le paso, si el id esta en 0 es porque la base de datos no lo agrego.
            return coleccion.agregar(gastoAgregado);
        }

        return false;
    }

    //ALTA Y BAJA
    public boolean alta(Gasto gasto) throws SQLException, ObjetoNuloException {
        if(gasto == null){
            throw new ObjetoNuloException("No se puede dar de alta un gasto nulo");
        }

        if(dao.alta(gasto)){
            return coleccion.alta(gasto.getId());
        }

        return false;
    }

    public boolean baja(Gasto gasto) throws SQLException, ObjetoNuloException {
        if(gasto == null){
            throw new ObjetoNuloException("No se puede dar de baja un gasto nulo");
        }

        if(dao.baja(gasto)){
            return coleccion.baja(gasto.getId());
        }

        return false;
    }

    //MODIFICACION
    public boolean modificar(Gasto gastoModificado) throws SQLException, ObjetoNuloException, ValorNoValidoException {
        if(gastoModificado==null){
            throw new ObjetoNuloException("El gasto recibido para modificar es nulo");
        }
        if (gastoModificado.getId() <= 0) {
            throw new ValorNoValidoException("El ID del gasto no es válido");
        }

        if(dao.modificar(gastoModificado)){
            return coleccion.modificar(gastoModificado);
        }

        return false;
    }

    public boolean modificarDetalles(int id, String detallesNuevo) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || detallesNuevo==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gastoBuscado= coleccion.buscar(id);
        if(gastoBuscado==null){
            throw new ObjetoNuloException("El gasto a modificar no existe");
        }

        Gasto gastoCopia= new Gasto();
        gastoCopia.setId(gastoBuscado.getId());
        gastoCopia.setEstado(gastoBuscado.getEstado());
        gastoCopia.setMonto(gastoBuscado.getMonto());
        gastoCopia.setFecha(gastoBuscado.getFecha());
        gastoCopia.setTipoGasto(gastoBuscado.getTipoGasto());
        gastoCopia.setIdGimnasio(gastoBuscado.getIdGimnasio());
        gastoCopia.setDetalles(detallesNuevo);

        if(dao.modificar(gastoCopia)){
            return coleccion.modificarDetalles(id,detallesNuevo);
        }

        return false;
    }

    public boolean modificarMonto(int id, double montoNuevo) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || montoNuevo < 0){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gastoBuscado= coleccion.buscar(id);
        if(gastoBuscado==null){
            throw new ObjetoNuloException("El gasto a modificar no existe");
        }

        Gasto gastoCopia= new Gasto();
        gastoCopia.setId(gastoBuscado.getId());
        gastoCopia.setEstado(gastoBuscado.getEstado());
        gastoCopia.setDetalles(gastoBuscado.getDetalles());
        gastoCopia.setFecha(gastoBuscado.getFecha());
        gastoCopia.setTipoGasto(gastoBuscado.getTipoGasto());
        gastoCopia.setIdGimnasio(gastoBuscado.getIdGimnasio());
        gastoCopia.setMonto(montoNuevo);

        if(dao.modificar(gastoCopia)){
            return coleccion.modificarMonto(id,montoNuevo);
        }

        return false;
    }

    public boolean modificarTipoDeGasto(int id, TipoGasto tipo) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || tipo==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gastoBuscado= coleccion.buscar(id);
        if(gastoBuscado==null){
            throw new ObjetoNuloException("El gasto a modificar no existe");
        }

        Gasto gastoCopia= new Gasto();
        gastoCopia.setId(gastoBuscado.getId());
        gastoCopia.setEstado(gastoBuscado.getEstado());
        gastoCopia.setDetalles(gastoBuscado.getDetalles());
        gastoCopia.setFecha(gastoBuscado.getFecha());
        gastoCopia.setMonto(gastoBuscado.getMonto());
        gastoCopia.setIdGimnasio(gastoBuscado.getIdGimnasio());
        gastoCopia.setTipoGasto(tipo);

        if(dao.modificar(gastoCopia)){
            return coleccion.modificarTipoDeGasto(id,tipo);
        }

        return false;
    }


    public boolean modificarFecha(int id, LocalDate fecha) throws SQLException, ValorNoValidoException, ObjetoNuloException{
        if(id <= 0 || fecha==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gastoBuscado= coleccion.buscar(id);
        if(gastoBuscado==null){
            throw new ObjetoNuloException("El gasto a modificar no existe");
        }

        Gasto gastoCopia= new Gasto();
        gastoCopia.setId(gastoBuscado.getId());
        gastoCopia.setEstado(gastoBuscado.getEstado());
        gastoCopia.setDetalles(gastoBuscado.getDetalles());
        gastoCopia.setTipoGasto(gastoBuscado.getTipoGasto());
        gastoCopia.setMonto(gastoBuscado.getMonto());
        gastoCopia.setIdGimnasio(gastoBuscado.getIdGimnasio());
        gastoCopia.setFecha(fecha);

        if(dao.modificar(gastoCopia)){
            return coleccion.modificarFecha(id,fecha);
        }

        return false;
    }

    //BUSQUEDA
    public List<Gasto> buscarGastosHistoricos(int idGimnasio){
        if(idGimnasio <= 0){
            throw new ValorNoValidoException("El parametro de busqueda no es valido");
        }

        return coleccion.buscarGastosHistoricos(idGimnasio);
    }

    public List<Gasto> buscarGastosMensuales(int idGimnasio, int mes, int anio){
        if(idGimnasio <= 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarGastosMensuales(idGimnasio, mes, anio);
    }

    public List<Gasto> buscarGastosAnuales(int idGimnasio, int anio){
        if(idGimnasio <= 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarGastosAnuales(idGimnasio, anio);
    }

    public List<Gasto> buscarTipoDeGastoHistorico(int idGimnasio, TipoGasto tipo){
        if(idGimnasio <= 0 || tipo==null){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarPorTipoDeGastoHistorico(idGimnasio,tipo);
    }

    public List<Gasto> buscarTipoDeGastoMensual(int idGimnasio, TipoGasto tipo, int mes, int anio){
        if(idGimnasio <= 0 || tipo==null || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarPorTipoDeGastoMensual(idGimnasio,tipo,mes,anio);
    }

    public List<Gasto> buscarTipoDeGastoAnual(int idGimnasio, TipoGasto tipo, int anio){
        if(idGimnasio <= 0 || tipo==null || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarPorTipoDeGastoAnual(idGimnasio,tipo,anio);
    }

    public List<Gasto> buscarHistoricoPorMontoMenorA(int idGimnasio, double monto){
        if(idGimnasio <= 0 || monto < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarHistoricoPorMontoMenorA(idGimnasio,monto);
    }

    public List<Gasto> buscarHistoricoPorMontoMayorA(int idGimnasio, double monto){
        if(idGimnasio <= 0 || monto < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarHistoricoPorMontoMayorA(idGimnasio,monto);
    }

    public List<Gasto> buscarMensualPorMontoMenorA(int idGimnasio, double monto, int mes, int anio){
        if(idGimnasio <= 0 || monto < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarMensualPorMontoMenorA(idGimnasio,monto,mes,anio);
    }

    public List<Gasto> buscarMensualPorMontoMayorA(int idGimnasio, double monto, int mes, int anio){
        if(idGimnasio <= 0 || monto < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarMensualPorMontoMayorA(idGimnasio,monto,mes,anio);
    }

    public List<Gasto> buscarAnualPorMontoMenorA(int idGimnasio, double monto, int anio){
        if(idGimnasio <= 0 || monto < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarAnualPorMontoMenorA(idGimnasio,monto,anio);
    }

    public List<Gasto> buscarAnualPorMontoMayorA(int idGimnasio, double monto, int anio){
        if(idGimnasio <= 0 || monto < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return coleccion.buscarAnualPorMontoMayorA(idGimnasio,monto,anio);
    }

    public List<Gasto> buscarPorInactivos(){
        return coleccion.buscarPorInactivos();
    }

    //LISTADOS
    public List<Gasto> verListado(){
        return coleccion.verListado();
    }

    //por monto ascendente
    public List<Gasto> verListadoHistoricoOrdenadoPorMontoAscendente(int idGimnasio){
        if(idGimnasio <= 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        return coleccion.verListadoHistoricoOrdenadoPorMontoAscendente(idGimnasio);
    }

    public List<Gasto> verListadoMensualOrdenadoPorMontoAscendente(int idGimnasio, int mes, int anio){
        if(idGimnasio <= 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return coleccion.verListadoMensualOrdenadoPorMontoAscendente(idGimnasio,mes,anio);
    }

    public List<Gasto> verListadoAnualOrdenadoPorMontoAscendente(int idGimnasio, int anio){
        if(idGimnasio <= 0|| anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        return coleccion.verListadoAnualOrdenadoPorMontoAscendente(idGimnasio,anio);
    }

    //por monto descendente
    public List<Gasto> verListadoHistoricoOrdenadoPorMontoDescendente(int idGimnasio){
        if(idGimnasio <= 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        return coleccion.verListadoHistoricoOrdenadoPorMontoDescendente(idGimnasio);
    }

    public List<Gasto> verListadoMensualOrdenadoPorMontoDescendente(int idGimnasio, int mes, int anio){
        if(idGimnasio <= 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return coleccion.verListadoMensualOrdenadoPorMontoDescendente(idGimnasio,mes,anio);
    }

    public List<Gasto> verListadoAnualOrdenadoPorMontoDescendente(int idGimnasio, int anio){
        if(idGimnasio <= 0|| anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        return coleccion.verListadoAnualOrdenadoPorMontoDescendente(idGimnasio,anio);
    }

    //por fecha ascendente
    public List<Gasto> verListadoHistoricoOrdenadoPorFechaAscendente(int idGimnasio){
        if(idGimnasio <= 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        return coleccion.verListadoHistoricoOrdenadoPorFechaAscendente(idGimnasio);
    }

    public List<Gasto> verListadoMensualOrdenadoPorFechaAscendente(int idGimnasio, int mes, int anio){
        if(idGimnasio <= 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return coleccion.verListadoMensualOrdenadoPorFechaAscendente(idGimnasio,mes,anio);
    }

    public List<Gasto> verListadoAnualOrdenadoPorFechaAscendente(int idGimnasio, int anio){
        if(idGimnasio <= 0|| anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        return coleccion.verListadoAnualOrdenadoPorFechaAscendente(idGimnasio,anio);
    }

    //por fecha descendente
    public List<Gasto> verListadoHistoricoOrdenadoPorFechaDescendente(int idGimnasio){
        if(idGimnasio <= 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        return coleccion.verListadoHistoricoOrdenadoPorFechaDescendente(idGimnasio);
    }

    public List<Gasto> verListadoMensualOrdenadoPorFechaDescendente(int idGimnasio, int mes, int anio){
        if(idGimnasio <= 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return coleccion.verListadoMensualOrdenadoPorFechaDescendente(idGimnasio,mes,anio);
    }

    public List<Gasto> verListadoAnualOrdenadoPorFechaDescendente(int idGimnasio, int anio){
        if(idGimnasio <= 0|| anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        return coleccion.verListadoAnualOrdenadoPorFechaDescendente(idGimnasio,anio);
    }

    //CALCULOS
    public double calcularTotalHistorico(int idGimnasio){
        if(idGimnasio <= 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        return coleccion.calcularTotalHistorico(idGimnasio);
    }

    public double calcularTotalMensual(int idGimnasio, int mes, int anio){
        if(idGimnasio <= 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return coleccion.calcularTotalMensual(idGimnasio,mes,anio);
    }

    public double calcularTotalAnual(int idGimnasio, int anio){
        if(idGimnasio <= 0|| anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        return coleccion.calcularTotalAnual(idGimnasio,anio);
    }

    public double calcularTotalHistoricoPorTipo(int idGimnasio, TipoGasto tipo){
        if(idGimnasio <= 0 || tipo==null){
            throw new ValorNoValidoException("El parametro no es valido");
        }

        return coleccion.calcularTotalHistoricoPorTipo(tipo,idGimnasio);
    }

    public double calcularTotalMensualPorTipo(int idGimnasio, int mes, int anio, TipoGasto tipo){
        if(idGimnasio <= 0 || mes < 1 || mes > 12 || anio < 0 || tipo==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return coleccion.calcularTotalMensualPorTipo(tipo,idGimnasio,mes,anio);
    }

    public double calcularTotalAnualPorTipo(int idGimnasio, int anio, TipoGasto tipo){
        if(idGimnasio <= 0|| anio < 0 || tipo==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        return coleccion.calcularTotalAnualPorTipo(tipo,idGimnasio,anio);
    }
}
