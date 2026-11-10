package com.example.trabajofinalprogramacion2.Service.Usuarios;

import com.example.trabajofinalprogramacion2.Colecciones.Usuarios.EntrenadorCollection;
import com.example.trabajofinalprogramacion2.DAO.Usuarios.EntrenadorDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorService {
    EntrenadorDAO dao;
    EntrenadorCollection coleccion;

    // CONSTRUCTOR

    public EntrenadorService() {

        dao = new EntrenadorDAO();
        coleccion = new EntrenadorCollection();

        // Cargar los entrenadores activos de MySQL en Collection
        // (los dados de baja quedan en la BD y se recuperan con alta)
        List<Entrenador> entrenadores = dao.listado();

        for (Entrenador entrenador : entrenadores) {
            if (entrenador.isActivo()) {
                coleccion.agregar(entrenador);
                cargarAlumnos(entrenador.getId());
            }
        }
    }


    // Carga los alumnos del entrenador desde la tabla entrenador_socio.
    // Por ahora cada Socio viene solo con su id: cuando exista SocioService
    // se reemplazan por los objetos completos (igual que en TurnosService)

    private void cargarAlumnos(int idEntrenador) {

        for (int idSocio : dao.listarIdsSocios(idEntrenador)) {
            Socio socio = new Socio();
            socio.setId(idSocio);
            coleccion.agregarSocio(idEntrenador, socio);
        }
    }


    // =========================
    // ABCML
    // =========================

    // VER LISTADO

    public List<Entrenador> verListado() {
        return coleccion.verListado();
    }


    // AGREGAR

    public boolean agregar(Entrenador entrenador) {

        if (entrenador == null || entrenador.getFechaDeNacimiento() == null
                || coleccion.buscarPorDni(entrenador.getDni()) != null) {
            return false;
        }

        Entrenador nuevo = dao.agregar(entrenador);

        if (nuevo != null) {
            return coleccion.agregar(nuevo);
        }

        return false;
    }


    // MODIFICAR OBJETO COMPLETO

    public boolean modificar(Entrenador entrenador) {

        if (entrenador == null || coleccion.buscar(entrenador.getId()) == null) {
            return false;
        }

        if (dao.modificar(entrenador)) {
            return coleccion.modificar(entrenador);
        }

        return false;
    }


    // ELIMINAR (BAJA LOGICA)
    // Al darse de baja, el entrenador pierde a todos sus alumnos (en la BD y en memoria)

    public boolean eliminar(int id) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null) {
            return false;
        }

        if (!dao.baja(entrenador)) {
            return false;
        }

        dao.desvincularTodosLosSocios(id);
        coleccion.quitarTodosLosSocios(id);

        return coleccion.eliminar(id);
    }


    // DAR DE ALTA (REACTIVAR UN ENTRENADOR DADO DE BAJA)

    public boolean alta(int id) {

        Entrenador entrenador = dao.consulta(id);

        // Si no existe o ya esta activo, no hay nada que reactivar
        if (entrenador == null || entrenador.isActivo()) {
            return false;
        }

        if (!dao.alta(entrenador)) {
            return false;
        }

        entrenador.setActivo(true);

        // Si ya estaba en memoria lo actualizamos, si no lo agregamos
        Entrenador enMemoria = coleccion.buscar(id);

        if (enMemoria != null) {
            enMemoria.setActivo(true);
            return true;
        }

        return coleccion.agregar(entrenador);
    }


    // CONSULTA EN BASE DE DATOS (POR ID)

    public Entrenador consultar(int id) {
        return dao.consulta(id);
    }


    // CONSULTA EN BASE DE DATOS (POR DNI)

    public Entrenador consultarPorDni(String dni) {

        if (dni == null || dni.isBlank()) {
            return null;
        }

        return dao.consultaPorDni(dni);
    }


    // BUSCAR POR ID

    public Entrenador buscar(int id) {
        return coleccion.buscar(id);
    }


    // =========================
    // BUSQUEDAS ESPECIFICAS
    // =========================

    // BUSCAR POR DNI

    public Entrenador buscarPorDni(String dni) {
        return coleccion.buscarPorDni(dni);
    }


    // BUSCAR POR NOMBRE

    public List<Entrenador> buscarPorNombre(String nombre) {
        return coleccion.buscarPorNombre(nombre);
    }


    // BUSCAR POR APELLIDO

    public List<Entrenador> buscarPorApellido(String apellido) {
        return coleccion.buscarPorApellido(apellido);
    }


    // BUSCAR POR EMAIL

    public Entrenador buscarPorEmail(String email) {
        return coleccion.buscarPorEmail(email);
    }


    // =========================
    // MODIFICACIONES INDIVIDUALES
    // =========================

    // METODO AUXILIAR PARA GUARDAR CAMBIOS

    private boolean guardarModificacion(Entrenador entrenador, Runnable restaurar) {

        if (entrenador == null) {
            return false;
        }

        if (dao.modificar(entrenador)) {
            return true;
        }

        // Si falla MySQL, restauramos el valor anterior en memoria
        restaurar.run();

        return false;
    }


    // MODIFICAR NOMBRE

    public boolean modificarNombre(int id, String nombre) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || nombre == null || nombre.isBlank()) {
            return false;
        }

        String anterior = entrenador.getNombre();

        Entrenador modificado = coleccion.modificarNombre(id, nombre);

        return guardarModificacion(modificado, () -> entrenador.setNombre(anterior));
    }


    // MODIFICAR APELLIDO

    public boolean modificarApellido(int id, String apellido) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || apellido == null || apellido.isBlank()) {
            return false;
        }

        String anterior = entrenador.getApellido();

        Entrenador modificado = coleccion.modificarApellido(id, apellido);

        return guardarModificacion(modificado, () -> entrenador.setApellido(anterior));
    }


    // MODIFICAR DNI

    public boolean modificarDni(int id, String dni) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || dni == null || dni.isBlank()) {
            return false;
        }

        Entrenador existente = coleccion.buscarPorDni(dni);

        if (existente != null && existente.getId() != id) {
            return false;
        }

        String anterior = entrenador.getDni();

        Entrenador modificado = coleccion.modificarDni(id, dni);

        return guardarModificacion(modificado, () -> entrenador.setDni(anterior));
    }


    // MODIFICAR EMAIL

    public boolean modificarEmail(int id, String email) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || email == null || email.isBlank()) {
            return false;
        }

        Entrenador existente = coleccion.buscarPorEmail(email);

        if (existente != null && existente.getId() != id) {
            return false;
        }

        String anterior = entrenador.getEmail();

        Entrenador modificado = coleccion.modificarEmail(id, email);

        return guardarModificacion(modificado, () -> entrenador.setEmail(anterior));
    }


    // MODIFICAR PASSWORD

    public boolean modificarPassword(int id, String password) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || password == null || password.isBlank()) {
            return false;
        }

        String anterior = entrenador.getPassword();

        Entrenador modificado = coleccion.modificarPassword(id, password);

        return guardarModificacion(modificado, () -> entrenador.setPassword(anterior));
    }


    // MODIFICAR FECHA DE NACIMIENTO

    public boolean modificarFechaNacimiento(int id, LocalDate fecha) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || fecha == null) {
            return false;
        }

        LocalDate anterior = entrenador.getFechaDeNacimiento();

        Entrenador modificado = coleccion.modificarFechaNacimiento(id, fecha);

        return guardarModificacion(modificado, () -> entrenador.setFechaDeNacimiento(anterior));
    }


    // MODIFICAR ESTADO
    // Si se da de baja (activo = false) pierde a todos sus alumnos, en la BD y en memoria

    public boolean modificarActivo(int id, boolean activo) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null) {
            return false;
        }

        boolean anterior = entrenador.isActivo();

        Entrenador modificado = coleccion.modificarActivo(id, activo);

        if (!guardarModificacion(modificado, () -> entrenador.setActivo(anterior))) {
            return false;
        }

        if (!activo) {
            dao.desvincularTodosLosSocios(id);
            coleccion.quitarTodosLosSocios(id);
        }

        return true;
    }


    // MODIFICAR ESPECIALIDAD

    public boolean modificarEspecialidad(int id, String especialidad) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || especialidad == null || especialidad.isBlank()) {
            return false;
        }

        String anterior = entrenador.getEspecialidad();

        Entrenador modificado = coleccion.modificarEspecialidad(id, especialidad);

        return guardarModificacion(modificado, () -> entrenador.setEspecialidad(anterior));
    }


    // MODIFICAR SALARIO

    public boolean modificarSalario(int id, Double salario) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || salario == null || salario < 0) {
            return false;
        }

        Double anterior = entrenador.getSalario();

        Entrenador modificado = coleccion.modificarSalario(id, salario);

        return guardarModificacion(modificado, () -> entrenador.setSalario(anterior));
    }


    // MODIFICAR SUCURSAL (trabaja en una sola; si la sucursal no existe falla la FK y se restaura)

    public boolean modificarSucursal(int id, Sucursal sucursal) {

        Entrenador entrenador = coleccion.buscar(id);

        if (entrenador == null || sucursal == null || sucursal.getId() <= 0) {
            return false;
        }

        Sucursal anterior = entrenador.getSucursalAsignada();

        Entrenador modificado = coleccion.modificarSucursal(id, sucursal);

        return guardarModificacion(modificado, () -> entrenador.setSucursalAsignada(anterior));
    }


    // =========================
    // ALUMNOS
    // =========================

    // AGREGAR SOCIO (ALUMNO) AL ENTRENADOR (se guarda en entrenador_socio)

    public boolean agregarSocio(int idEntrenador, Socio socio) {

        if (socio == null || coleccion.buscar(idEntrenador) == null
                || coleccion.tieneSocio(idEntrenador, socio.getId())) {
            return false;
        }

        if (dao.vincularSocio(idEntrenador, socio.getId())) {
            return coleccion.agregarSocio(idEntrenador, socio);
        }

        return false;
    }


    // QUITAR SOCIO (ALUMNO) DEL ENTRENADOR

    public boolean quitarSocio(int idEntrenador, int idSocio) {

        if (!coleccion.tieneSocio(idEntrenador, idSocio)) {
            return false;
        }

        if (dao.desvincularSocio(idEntrenador, idSocio)) {
            return coleccion.quitarSocio(idEntrenador, idSocio);
        }

        return false;
    }


    // VER ALUMNOS DEL ENTRENADOR

    public List<Socio> verAlumnos(int idEntrenador) {

        Entrenador entrenador = coleccion.buscar(idEntrenador);

        if (entrenador == null || entrenador.getAlumnosAsociados() == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(entrenador.getAlumnosAsociados());
    }

}
