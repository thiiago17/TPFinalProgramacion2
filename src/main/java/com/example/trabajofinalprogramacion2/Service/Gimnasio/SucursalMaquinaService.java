package com.example.trabajofinalprogramacion2.Service.Gimnasio;

import com.example.trabajofinalprogramacion2.Colecciones.Gimnasio.SucursalMaquinaCollection;
import com.example.trabajofinalprogramacion2.DAO.Gimnasio.SucursalMaquinaDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.SucursalMaquina;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;

import java.sql.SQLException;
import java.util.List;

public class SucursalMaquinaService {
    private SucursalMaquinaDAO dao;
    private SucursalMaquinaCollection coleccion;

    public SucursalMaquinaService() throws SQLException {
        dao = new SucursalMaquinaDAO();
        coleccion = new SucursalMaquinaCollection();

        List<SucursalMaquina> relaciones = dao.listado();

        for (SucursalMaquina relacion : relaciones) {
            coleccion.agregar(relacion);
        }
    }


    // AGREGAR
    public boolean agregar(SucursalMaquina relacion) throws SQLException, ObjetoNuloException {
        if (relacion == null) {
            throw new ObjetoNuloException("No se puede agregar una relación nula");
        }

        SucursalMaquina sucursalMaquinaAgregada = dao.agregar(relacion);

        if (sucursalMaquinaAgregada.getIdSucursalMaquina() != 0) {
            return coleccion.agregar(sucursalMaquinaAgregada);
        }

        return false;
    }


    // ALTA Y BAJA LÓGICA
    public boolean alta(SucursalMaquina sucursalMaquina) throws SQLException, ObjetoNuloException {
        if (sucursalMaquina == null) {
            throw new ObjetoNuloException("No se puede dar de alta una relación nula");
        }

        if (dao.alta(sucursalMaquina)) {
            return coleccion.alta(sucursalMaquina.getIdSucursalMaquina());
        }

        return false;
    }

    public boolean baja(SucursalMaquina sucursalMaquina) throws SQLException, ObjetoNuloException {
        if (sucursalMaquina == null) {
            throw new ObjetoNuloException("No se puede dar de baja una relación nula");
        }

        if (dao.baja(sucursalMaquina)) {
            return coleccion.baja(sucursalMaquina.getIdSucursalMaquina());
        }

        return false;
    }

    // MODIFICACIONES
    public boolean modificar(SucursalMaquina sucursalMaquinaModificada) throws SQLException, ObjetoNuloException, ValorNoValidoException {
        if (sucursalMaquinaModificada == null) {
            throw new ObjetoNuloException("La relación recibida para modificar es nula");
        }
        if (sucursalMaquinaModificada.getIdSucursalMaquina() <= 0) {
            throw new ValorNoValidoException("El ID de la relación no es válido");
        }

        if (dao.modificar(sucursalMaquinaModificada)) {
            return coleccion.modificar(sucursalMaquinaModificada);
        }

        return false;
    }

    public boolean modificarCantidad(int id, int cantidadNueva) throws SQLException, ValorNoValidoException, ObjetoNuloException {
        if (id <= 0 || cantidadNueva < 0) {
            throw new ValorNoValidoException("Los valores son nulos o no válidos");
        }

        SucursalMaquina sucursalMaquinaBuscada = coleccion.buscar(id);
        if (sucursalMaquinaBuscada == null) {
            throw new ObjetoNuloException("La relación a modificar no existe");
        }

        SucursalMaquina sucursalMaquinaCopia = new SucursalMaquina();
        sucursalMaquinaCopia.setIdSucursalMaquina(sucursalMaquinaBuscada.getIdSucursalMaquina());
        sucursalMaquinaCopia.setIdSucursal(sucursalMaquinaBuscada.getIdSucursal());
        sucursalMaquinaCopia.setIdMaquina(sucursalMaquinaBuscada.getIdMaquina());
        sucursalMaquinaCopia.setEstado(sucursalMaquinaBuscada.getEstado());

        sucursalMaquinaCopia.setCantidad(cantidadNueva);

        if (dao.modificar(sucursalMaquinaCopia)) {
            return coleccion.modificarCantidad(id, cantidadNueva);
        }

        return false;
    }

    // BÚSQUEDAS Y FILTROS
    public List<SucursalMaquina> buscarActivosPorIdSucursal(int idSucursal) throws ValorNoValidoException {
        if (idSucursal <= 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }
        return coleccion.buscarActivosPorIdSucursal(idSucursal);
    }

    public List<SucursalMaquina> buscarInactivosPorIdSucursal(int idSucursal) throws ValorNoValidoException {
        if (idSucursal <= 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }
        return coleccion.buscarInactivosPorIdSucursal(idSucursal);
    }

    public List<SucursalMaquina> buscarInactivosPorIdMaquina(int idMaquina) throws ValorNoValidoException {
        if (idMaquina <= 0) {
            throw new ValorNoValidoException("El ID de la máquina no es válido");
        }
        return coleccion.buscarInactivosPorIdMaquina(idMaquina);
    }


    // LISTADOS ORDENADOS
    public List<SucursalMaquina> verActivosOrdenadoPorCantidadAscendente(int idSucursal) throws ValorNoValidoException {
        if (idSucursal <= 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }
        return coleccion.verActivosOrdenadoPorCantidadAscendente(idSucursal);
    }

    public List<SucursalMaquina> verActivosOrdenadoPorCantidadDescendente(int idSucursal) throws ValorNoValidoException {
        if (idSucursal <= 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es válido");
        }
        return coleccion.verActivosOrdenadoPorCantidadDescendente(idSucursal);
    }

    public List<SucursalMaquina> verListado() {
        return coleccion.verListado();
    }
}
