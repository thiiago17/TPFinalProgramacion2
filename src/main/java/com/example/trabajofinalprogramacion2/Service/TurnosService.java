package com.example.trabajofinalprogramacion2.Service;

import com.example.trabajofinalprogramacion2.Colecciones.TurnosCollection;
import com.example.trabajofinalprogramacion2.DAO.TurnosDao;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Service.Usuarios.NutricionistaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TurnosService {
    TurnosDao dao;
    TurnosCollection coleccion;

    // CONSTRUCTORES

    // Carga los turnos sin vincularlos a los nutricionistas de NutricionistaService
    public TurnosService() {
        this(null);
    }

    // Carga los turnos y los vincula a los nutricionistas reales de NutricionistaService,
    // asi la lista de turnos de cada nutricionista queda cargada (verTurnos, etc.)
    public TurnosService(NutricionistaService nutricionistaService) {

        dao = new TurnosDao();
        coleccion = new TurnosCollection();

        // El DAO arma el nutricionista solo con su id: si tenemos el service,
        // lo reemplazamos por el objeto completo que ya esta en memoria
        List<Turno> turnos = dao.listado();

        for (Turno turno : turnos) {

            if (nutricionistaService != null && turno.getNutricionista() != null) {

                Nutricionista real = nutricionistaService.buscar(turno.getNutricionista().getId());

                if (real != null) {
                    turno.setNutricionista(real);
                }
            }

            coleccion.agregar(turno);
        }
    }


    // =========================
    // ABCML
    // =========================

    // VER LISTADO (ordenado por fecha)

    public List<Turno> verListado() {
        return coleccion.verListado();
    }


    // AGREGAR

    public boolean agregar(Turno turno) {

        // Validamos antes de guardar en MySQL lo mismo que valida la coleccion,
        // para que no quede un turno en la BD que despues no entra en memoria
        if (turno == null || !Turno.esHorarioValido(turno.getFechaTurno()) || turno.getPrecio() < 0) {
            return false;
        }

        if (nutricionistaOcupado(turno.getNutricionista(), turno.getFechaTurno(), turno)) {
            return false;
        }

        Turno nuevo = dao.agregar(turno);

        if (nuevo != null) {
            return coleccion.agregar(nuevo);
        }

        return false;
    }


    // MODIFICAR OBJETO COMPLETO

    public boolean modificar(Turno turno) {

        if (turno == null || buscar(turno.getId()) == null) {
            return false;
        }

        return dao.modificar(turno);
    }


    // ELIMINAR (BORRA EL TURNO DE LA BD)
    // Solo se pueden eliminar turnos disponibles, para no borrar la reserva de un socio

    public boolean eliminar(int id) {

        Turno turno = buscar(id);

        if (turno == null || !turno.isEstado()) {
            return false;
        }

        if (dao.baja(turno)) {
            return coleccion.eliminar(turno);
        }

        return false;
    }


    // BUSCAR POR ID

    public Turno buscar(int id) {

        for (Turno turno : coleccion.verListado()) {
            if (turno.getId() == id) {
                return turno;
            }
        }

        return null;
    }


    // CONSULTA EN BASE DE DATOS (POR ID)

    public Turno consultar(int id) {

        Turno buscado = new Turno();
        buscado.setId(id);

        return dao.consulta(buscado);
    }


    // =========================
    // RESERVAR / CANCELAR
    // =========================

    // RESERVAR (el DAO solo reserva si el turno sigue disponible en la BD,
    // y si sale bien actualiza tambien el objeto en memoria)

    public boolean reservar(int idTurno, Socio socio) {

        Turno turno = buscar(idTurno);

        if (turno == null || socio == null || !turno.isEstado()) {
            return false;
        }

        return dao.reservar(turno, socio);
    }


    // CANCELAR RESERVA (el turno vuelve a quedar disponible)

    public boolean cancelar(int idTurno) {

        Turno turno = buscar(idTurno);

        if (turno == null || turno.isEstado()) {
            return false;
        }

        return dao.cancelar(turno);
    }


    // =========================
    // BUSQUEDAS ESPECIFICAS
    // =========================

    // POR NUTRICIONISTA

    public List<Turno> buscarPorNutricionista(Nutricionista nutricionista) {
        return coleccion.buscarPorNutricionista(nutricionista);
    }


    // POR SOCIO

    public List<Turno> buscarPorSocio(Socio socio) {
        return coleccion.buscarPorSocio(socio);
    }


    // POR FECHA (todos los turnos de ese dia, ordenados por hora)

    public List<Turno> buscarPorFecha(LocalDate fecha) {
        return coleccion.buscarPorFecha(fecha);
    }


    // ENTRE DOS FECHAS (inclusive)

    public List<Turno> buscarEntreFechas(LocalDate desde, LocalDate hasta) {
        return coleccion.buscarEntreFechas(desde, hasta);
    }


    // DISPONIBLES

    public List<Turno> verDisponibles() {
        return coleccion.verDisponibles();
    }


    // RESERVADOS

    public List<Turno> verReservados() {
        return coleccion.verReservados();
    }


    // POR PRECIO MAXIMO

    public List<Turno> buscarPorPrecioMaximo(double precioMaximo) {
        return coleccion.buscarPorPrecioMaximo(precioMaximo);
    }


    // =========================
    // MODIFICACIONES INDIVIDUALES
    // =========================

    // METODO AUXILIAR PARA GUARDAR CAMBIOS

    private boolean guardarModificacion(Turno turno, Runnable restaurar) {

        if (turno == null) {
            return false;
        }

        if (dao.modificar(turno)) {
            return true;
        }

        // Si falla MySQL, restauramos el valor anterior en memoria
        restaurar.run();

        return false;
    }


    // MODIFICAR FECHA Y HORA (tiene que estar dentro del horario de atencion)

    public boolean modificarFecha(int idTurno, LocalDateTime nuevaFecha) {

        Turno turno = buscar(idTurno);

        if (turno == null || !Turno.esHorarioValido(nuevaFecha)) {
            return false;
        }

        LocalDateTime anterior = turno.getFechaTurno();

        Turno modificado = coleccion.modificarFecha(turno, nuevaFecha);

        return guardarModificacion(modificado, () -> turno.setFechaTurno(anterior));
    }


    // MODIFICAR PRECIO

    public boolean modificarPrecio(int idTurno, double precio) {

        Turno turno = buscar(idTurno);

        if (turno == null || precio < 0) {
            return false;
        }

        double anterior = turno.getPrecio();

        Turno modificado = coleccion.modificarPrecio(turno, precio);

        return guardarModificacion(modificado, () -> turno.setPrecio(anterior));
    }


    // MODIFICAR NUTRICIONISTA (pasa el turno de la lista de uno a la del otro)

    public boolean modificarNutricionista(int idTurno, Nutricionista nuevoNutricionista) {

        Turno turno = buscar(idTurno);

        if (turno == null || nuevoNutricionista == null) {
            return false;
        }

        Nutricionista anterior = turno.getNutricionista();

        Turno modificado = coleccion.modificarNutricionista(turno, nuevoNutricionista);

        return guardarModificacion(modificado, () -> {

            // Devolvemos el turno a la lista del nutricionista anterior
            if (anterior != null) {
                coleccion.modificarNutricionista(turno, anterior);
            } else {
                if (nuevoNutricionista.getTurnos() != null) {
                    nuevoNutricionista.getTurnos().remove(turno);
                }
                turno.setNutricionista(null);
            }
        });
    }


    // =========================
    // METODOS AUXILIARES
    // =========================

    // True si el nutricionista ya tiene otro turno que se pisa con esa fecha y hora (duran 30 minutos)
    private boolean nutricionistaOcupado(Nutricionista nutricionista, LocalDateTime fecha, Turno excluir) {

        if (nutricionista == null || fecha == null) {
            return false;
        }

        for (Turno turno : coleccion.buscarPorNutricionista(nutricionista)) {
            if (turno != excluir && Turno.seSuperponen(fecha, turno.getFechaTurno())) {
                return true;
            }
        }

        return false;
    }

}
