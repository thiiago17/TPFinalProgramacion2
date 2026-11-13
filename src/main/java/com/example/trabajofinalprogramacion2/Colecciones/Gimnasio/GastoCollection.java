package com.example.trabajofinalprogramacion2.Colecciones.Gimnasio;

import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.TipoGasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Excepciones.ListadoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import javax.xml.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Gasto> buscarGastosHistoricos(int idGimnasio) throws ValorNoValidoException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("El parametro de busqueda no es valido");
        }

        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : listado){
            if(gasto.getIdGimnasio()==idGimnasio && gasto.getEstado()==1){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarGastosMensuales(int idGimnasio, int mes, int anio) throws ValorNoValidoException {
        if(idGimnasio < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosEncontrados=new ArrayList<>();

        for(Gasto gasto : listado){
            if(gasto.getIdGimnasio()==idGimnasio && gasto.getFecha().getMonthValue()==mes && gasto.getFecha().getYear()==anio && gasto.getEstado()==1){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarGastosAnuales(int idGimnasio, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosEncontrados=new ArrayList<>();

        for(Gasto gasto : listado){
            if(gasto.getIdGimnasio()==idGimnasio && gasto.getFecha().getYear()==anio && gasto.getEstado()==1){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarPorTipoDeGastoHistorico(int idGimnasio, TipoGasto tipo) throws ValorNoValidoException {
        if(idGimnasio < 0 || tipo==null){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosHistoricos= buscarGastosHistoricos(idGimnasio); //ya filtra id y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosHistoricos){
            if(gasto.getTipoGasto()==tipo){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarPorTipoDeGastoMensual(int idGimnasio, TipoGasto tipo, int mes, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || tipo==null || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosMensuales= buscarGastosMensuales(idGimnasio,mes,anio); //ya filtra id, mes, anio y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosMensuales){
            if(gasto.getTipoGasto()==tipo){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarPorTipoDeGastoAnual(int idGimnasio, TipoGasto tipo, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || tipo==null || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosAnuales= buscarGastosAnuales(idGimnasio,anio); //ya filtra id, anio y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosAnuales){
            if(gasto.getTipoGasto()==tipo){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarHistoricoPorMontoMenorA(int idGimnasio, double monto) throws ValorNoValidoException{
        if(idGimnasio < 0 || monto < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosHistoricos= buscarGastosHistoricos(idGimnasio); //ya filtra id y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosHistoricos){
            if(gasto.getMonto() < monto){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarHistoricoPorMontoMayorA(int idGimnasio, double monto) throws ValorNoValidoException{
        if(idGimnasio < 0 || monto < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosHistoricos= buscarGastosHistoricos(idGimnasio); //ya filtra id y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosHistoricos){
            if(gasto.getMonto() > monto){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarMensualPorMontoMenorA(int idGimnasio, double monto, int mes, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || monto < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosMensuales= buscarGastosMensuales(idGimnasio, mes, anio); //ya filtra id, mes, anio y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosMensuales){
            if(gasto.getMonto() < monto){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarMensualPorMontoMayorA(int idGimnasio, double monto, int mes, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || monto < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosMensuales= buscarGastosMensuales(idGimnasio, mes, anio); //ya filtra id, mes, anio y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosMensuales){
            if(gasto.getMonto() > monto){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarAnualPorMontoMenorA(int idGimnasio, double monto, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || monto < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosAnuales= buscarGastosAnuales(idGimnasio, anio); //ya filtra id, anio y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosAnuales){
            if(gasto.getMonto() < monto){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarAnualPorMontoMayorA(int idGimnasio, double monto,int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || monto < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        List<Gasto> gastosAnuales= buscarGastosAnuales(idGimnasio, anio); //ya filtra id, anio y estado
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : gastosAnuales){
            if(gasto.getMonto() > monto){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
    }

    public List<Gasto> buscarPorInactivos(int idGimnasio) {
        List<Gasto> gastosEncontrados= new ArrayList<>();

        for(Gasto gasto : listado){
            if(gasto.getEstado()==0 && gasto.getIdGimnasio() == idGimnasio){
                gastosEncontrados.add(gasto);
            }
        }

        return gastosEncontrados;
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

    public boolean alta(int id) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El id es invalido");
        }

        Gasto gasto=buscar(id);

        if(gasto==null){
            throw new ObjetoNuloException("No existe el gasto que se intenta dar de alta");
        }

        gasto.setEstado(1);

        return true;
    }

    public boolean baja(int id) throws ValorNoValidoException,ObjetoNuloException{
        if(id < 0){
            throw new ValorNoValidoException("El id es invalido");
        }

        Gasto gasto=buscar(id);

        if(gasto==null){
            throw new ObjetoNuloException("No existe el gasto que se intenta dar de baja");
        }

        gasto.setEstado(0);

        return true;
    }

    public boolean modificarDetalles(int id, String detalles) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || detalles==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gasto=buscar(id);

        if(gasto==null){
            throw new ObjetoNuloException("No existe el gasto que se intenta modificar");
        }

        gasto.setDetalles(detalles);

        return true;
    }

    public boolean modificarMonto(int id, double monto) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || monto < 0){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gasto=buscar(id);

        if(gasto==null){
            throw new ObjetoNuloException("No existe el gasto que se intenta modificar");
        }

        gasto.setMonto(monto);

        return true;
    }

    public boolean modificarTipoDeGasto(int id, TipoGasto tipo) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || tipo==null){
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gasto=buscar(id);

        if(gasto==null){
            throw new ObjetoNuloException("No existe el gasto que se intenta modificar");
        }

        gasto.setTipoGasto(tipo);

        return true;
    }

    public boolean modificarFecha(int id, LocalDate fecha) throws ValorNoValidoException, ObjetoNuloException{
        if(id < 0 || fecha==null || fecha.isAfter(LocalDate.now())){ //isAfter(LocalDate.now()) verifica que no sea una fecha del futuro
            throw new ValorNoValidoException("Los valores son nulos o no validos");
        }

        Gasto gasto=buscar(id);

        if(gasto==null){
            throw new ObjetoNuloException("No existe el gasto que se intenta modificar");
        }

        gasto.setFecha(fecha);

        return true;
    }

    //VER LISTADOS
    @Override
    public List<Gasto> verListado() {
        return new ArrayList<>(listado);
    }

    //por monto ascendente
    public List<Gasto> verListadoHistoricoOrdenadoPorMontoAscendente(int idGimnasio) throws ValorNoValidoException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosHistoricos(idGimnasio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getMonto));

        return listaOrdenada;
    }

    public List<Gasto> verListadoMensualOrdenadoPorMontoAscendente(int idGimnasio, int mes, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosMensuales(idGimnasio, mes, anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getMonto));

        return listaOrdenada;
    }

    public List<Gasto> verListadoAnualOrdenadoPorMontoAscendente(int idGimnasio, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosAnuales(idGimnasio,anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getMonto));

        return listaOrdenada;
    }

    //por monto descendente

    public List<Gasto> verListadoHistoricoOrdenadoPorMontoDescendente(int idGimnasio) throws ValorNoValidoException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosHistoricos(idGimnasio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getMonto).reversed());

        return listaOrdenada;
    }

    public List<Gasto> verListadoMensualOrdenadoPorMontoDescendente(int idGimnasio, int mes, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosMensuales(idGimnasio, mes, anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getMonto).reversed());

        return listaOrdenada;
    }

    public List<Gasto> verListadoAnualOrdenadoPorMontoDescendente(int idGimnasio, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosAnuales(idGimnasio,anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getMonto).reversed());

        return listaOrdenada;
    }

    //por fecha ascendente

    public List<Gasto> verListadoHistoricoOrdenadoPorFechaAscendente(int idGimnasio) throws ValorNoValidoException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosHistoricos(idGimnasio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getFecha));

        return listaOrdenada;
    }

    public List<Gasto> verListadoMensualOrdenadoPorFechaAscendente(int idGimnasio, int mes, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosMensuales(idGimnasio, mes, anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getFecha));

        return listaOrdenada;
    }

    public List<Gasto> verListadoAnualOrdenadoPorFechaAscendente(int idGimnasio, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosAnuales(idGimnasio,anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getFecha));

        return listaOrdenada;
    }

    //por fecha descendente

    public List<Gasto> verListadoHistoricoOrdenadoPorFechaDescendente(int idGimnasio) throws ValorNoValidoException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("El parametro no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosHistoricos(idGimnasio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getFecha).reversed());

        return listaOrdenada;
    }

    public List<Gasto> verListadoMensualOrdenadoPorFechaDescendente(int idGimnasio, int mes, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosMensuales(idGimnasio, mes, anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getFecha).reversed());

        return listaOrdenada;
    }

    public List<Gasto> verListadoAnualOrdenadoPorFechaDescendente(int idGimnasio, int anio) throws ValorNoValidoException{
        if(idGimnasio < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }
        List<Gasto> listaOrdenada= buscarGastosAnuales(idGimnasio,anio);

        listaOrdenada.sort(Comparator.comparing(Gasto::getFecha).reversed());

        return listaOrdenada;
    }

    //CALCULOS
    private double calcularTotal(List<Gasto> listaGastos) throws ListadoNuloException { //metodo privado, lo llama luego el metodo publico
        if(listaGastos == null){
            throw new ListadoNuloException("La lista de gastos es null");
        }

        double total=0.0;
        for(Gasto gasto : listaGastos){
            total=total+gasto.getMonto();
        }

        return total;
    }

    private double calcularTotalPorTipo(List<Gasto> listaGastos, TipoGasto tipo) throws ListadoNuloException{ //metodo privado, lo llama luego el metodo publico
        if(listaGastos == null){
            throw new ListadoNuloException("La lista de gastos es null");
        }

        double total=0.0;
        for(Gasto gasto : listaGastos){
            if(gasto.getTipoGasto() == tipo){
                total=total+gasto.getMonto();
            }
        }

        return total;
    }

    public double calcularTotalMensual(int idGimnasio, int mes, int anio) throws ValorNoValidoException, ListadoNuloException{
        if(idGimnasio < 0 || mes < 1 || mes > 12 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return calcularTotal(buscarGastosMensuales(idGimnasio,mes,anio));
    }

    public double calcularTotalAnual(int idGimnasio, int anio) throws ValorNoValidoException, ListadoNuloException{
        if(idGimnasio < 0 || anio < 0){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return calcularTotal(buscarGastosAnuales(idGimnasio,anio));
    }

    public double calcularTotalHistorico(int idGimnasio) throws ValorNoValidoException, ListadoNuloException{
        if(idGimnasio < 0){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }

        return calcularTotal(buscarGastosHistoricos(idGimnasio));
    }

    public double calcularTotalMensualPorTipo(TipoGasto tipo, int idGimnasio, int mes, int anio) throws ValorNoValidoException, ListadoNuloException{
        if(idGimnasio < 0 || mes < 1 || mes > 12 || anio < 0 || tipo==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return calcularTotalPorTipo(buscarGastosMensuales(idGimnasio, mes, anio), tipo);
    }

    public double calcularTotalAnualPorTipo(TipoGasto tipo, int idGimnasio, int anio) throws ValorNoValidoException, ListadoNuloException{
        if(idGimnasio < 0 || anio < 0 || tipo==null){
            throw new ValorNoValidoException("Alguno de los parametros no es valido");
        }

        return calcularTotalPorTipo(buscarGastosAnuales(idGimnasio, anio), tipo);
    }

    public double calcularTotalHistoricoPorTipo(TipoGasto tipo, int idGimnasio) throws ValorNoValidoException, ListadoNuloException{
        if(idGimnasio < 0 || tipo==null){
            throw new ValorNoValidoException("Alguno de los parametros de busqueda no es valido");
        }
        return calcularTotalPorTipo(buscarGastosHistoricos(idGimnasio), tipo);
    }

}
