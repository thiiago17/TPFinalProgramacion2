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
    NutricionistaService nutricionistaService;

    // CONSTRUCTORES

    // Carga los turnos sin vincularlos a los nutricionistas de NutricionistaService
    public TurnosService() {
        this(null);
    }

    // Carga los turnos activos (no los dados de baja) y los vincula a los nutricionistas
    // reales de NutricionistaService, asi la lista de turnos de cada nutricionista queda cargada
    public TurnosService(NutricionistaService nutricionistaService) {

        dao = new TurnosDao();
        coleccion = new TurnosCollection();
        this.nutricionistaService = nutricionistaService;

        for (Turno turno : dao.listadoActivos()) {
            vincularNutricionista(turno);
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


    // ELIMINAR (BAJA LOGICA: estado = 0 en la BD y se saca de memoria)
    // Solo se pueden dar de baja turnos disponibles, para no dejar a un socio sin su reserva

    public boolean eliminar(int id) {

        Turno turno = buscar(id);

        if (turno == null || !turno.estaDisponible()) {
            return false;
        }

        if (dao.baja(turno)) {
            return coleccion.eliminar(turno);
        }

        return false;
    }


    // DAR DE ALTA (REACTIVAR UN TURNO DADO DE BAJA)
    // No se reactiva si mientras tanto se cargo otro turno del mismo nutricionista que se pisa con este

    public boolean alta(int id) {

        Turno turno = dao.consulta(id);

        if (turno == null || !turno.estaDadoDeBaja()) {
            return false;
        }

        vincularNutricionista(turno);

        if (!Turno.esHorarioValido(turno.getFechaTurno())
                || nutricionistaOcupado(turno.getNutricionista(), turno.getFechaTurno(), turno)) {
            return false;
        }

        if (!dao.alta(turno)) {
            return false;
        }

        return coleccion.agregar(turno);
    }


    // VER TURNOS DADOS DE BAJA (solo estan en la BD)

    public List<Turno> verDadosDeBaja() {

        List<Turno> turnos = dao.listadoDadosDeBaja();

        for (Turno turno : turnos) {
            vincularNutricionista(turno);
        }

        return turnos;
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
        return dao.consulta(id);
    }


    // =========================
    // RESERVAR / CANCELAR
    // =========================

    // RESERVAR (el DAO solo reserva si el turno sigue disponible en la BD,
    // y si sale bien actualiza tambien el objeto en memoria)

    public boolean reservar(int idTurno, Socio socio) {

        Turno turno = buscar(idTurno);

        if (turno == null || socio == null || !turno.estaDisponible()) {
            return false;
        }

        return dao.reservar(turno, socio);
    }


    // CANCELAR RESERVA (el turno vuelve a quedar disponible)

    public boolean cancelar(int idTurno) {

        Turno turno = buscar(idTurno);

        if (turno == null || !turno.estaReservado()) {
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

    // El DAO arma el nutricionista solo con su id: si tenemos el service,
    // lo reemplazamos por el objeto completo que ya esta en memoria
    private void vincularNutricionista(Turno turno) {

        if (nutricionistaService == null || turno.getNutricionista() == null) {
            return;
        }

        Nutricionista real = nutricionistaService.buscar(turno.getNutricionista().getId());

        if (real != null) {
            turno.setNutricionista(real);
        }
    }

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
