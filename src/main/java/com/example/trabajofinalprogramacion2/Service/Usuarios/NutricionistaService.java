package com.example.trabajofinalprogramacion2.Service.Usuarios;

import com.example.trabajofinalprogramacion2.interfaces.GestionPerfil;
import com.example.trabajofinalprogramacion2.Colecciones.Usuarios.NutricionistaCollection;
import com.example.trabajofinalprogramacion2.DAO.Usuarios.NutricionistaDAO;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;

import java.time.LocalDate;
import java.util.List;

public class NutricionistaService implements GestionPerfil {
    NutricionistaDAO dao;
    NutricionistaCollection coleccion;

    // CONSTRUCTOR

    public NutricionistaService() {

        dao = new NutricionistaDAO();
        coleccion = new NutricionistaCollection();

        // Cargar los nutricionistas activos de MySQL en Collection
        // (los dados de baja quedan en la BD y se recuperan con alta)
        List<Nutricionista> nutricionistas = dao.listado();

        for (Nutricionista nutricionista : nutricionistas) {
            if (nutricionista.isActivo()) {
                coleccion.agregar(nutricionista);
            }
        }
    }


    // =========================
    // ABCML
    // =========================

    // VER LISTADO

    public List<Nutricionista> verListado() {
        return coleccion.verListado();
    }


    // AGREGAR

    public boolean agregar(Nutricionista nutricionista) {

        if (nutricionista == null || nutricionista.getFechaDeNacimiento() == null
                || coleccion.buscarPorDni(nutricionista.getDni()) != null) {
            return false;
        }

        // Si no se indico fecha de ingreso, se toma la fecha de hoy (igual que en AdministradorService)
        if (nutricionista.getFechaIngreso() == null) {
            nutricionista.setFechaIngreso(LocalDate.now());
        }

        Nutricionista nuevo = dao.agregar(nutricionista);

        if (nuevo != null) {
            return coleccion.agregar(nuevo);
        }

        return false;
    }


    // MODIFICAR OBJETO COMPLETO

    public boolean modificar(Nutricionista nutricionista) {

        if (nutricionista == null || coleccion.buscar(nutricionista.getId()) == null) {
            return false;
        }

        if (dao.modificar(nutricionista)) {
            return coleccion.modificar(nutricionista);
        }

        return false;
    }


    // ELIMINAR (BAJA LOGICA)

    public boolean eliminar(int id) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null) {
            return false;
        }

        if (dao.baja(nutricionista)) {
            return coleccion.eliminar(id);
        }

        return false;
    }


    // DAR DE ALTA (REACTIVAR UN NUTRICIONISTA DADO DE BAJA)

    public boolean alta(int id) {

        Nutricionista nutricionista = dao.consulta(id);

        if (nutricionista == null) {
            return false;
        }

        if (!dao.alta(nutricionista)) {
            return false;
        }

        nutricionista.setActivo(true);

        // Si ya estaba en memoria lo actualizamos, si no lo agregamos
        Nutricionista enMemoria = coleccion.buscar(id);

        if (enMemoria != null) {
            enMemoria.setActivo(true);
            return true;
        }

        return coleccion.agregar(nutricionista);
    }


    // CONSULTA EN BASE DE DATOS (POR ID)

    public Nutricionista consultar(int id) {
        return dao.consulta(id);
    }


    // CONSULTA EN BASE DE DATOS (POR DNI)

    public Nutricionista consultarPorDni(String dni) {

        if (dni == null || dni.isBlank()) {
            return null;
        }

        return dao.consultaPorDni(dni);
    }


    // BUSCAR POR ID

    public Nutricionista buscar(int id) {
        return coleccion.buscar(id);
    }


    // =========================
    // BUSQUEDAS ESPECIFICAS
    // =========================

    // BUSCAR POR DNI

    public Nutricionista buscarPorDni(String dni) {
        return coleccion.buscarPorDni(dni);
    }


    // BUSCAR POR NOMBRE

    public List<Nutricionista> buscarPorNombre(String nombre) {
        return coleccion.buscarPorNombre(nombre);
    }


    // BUSCAR POR APELLIDO

    public List<Nutricionista> buscarPorApellido(String apellido) {
        return coleccion.buscarPorApellido(apellido);
    }


    // BUSCAR POR EMAIL

    public Nutricionista buscarPorEmail(String email) {
        return coleccion.buscarPorEmail(email);
    }


    // =========================
    // MODIFICACIONES INDIVIDUALES
    // =========================

    // METODO AUXILIAR PARA GUARDAR CAMBIOS
    // Guarda en MySQL el objeto ya modificado en memoria. Si devuelve false, cada metodo
    // "modificarX" vuelve a poner el valor anterior para que memoria y BD no queden distintas.

    private boolean guardarModificacion(Nutricionista nutricionista) {

        if (nutricionista == null) {
            return false;
        }

        return dao.modificar(nutricionista);
    }


    // MODIFICAR NOMBRE

    public boolean modificarNombre(int id, String nombre) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || nombre == null || nombre.isBlank()) {
            return false;
        }

        String anterior = nutricionista.getNombre();

        Nutricionista modificado = coleccion.modificarNombre(id, nombre);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setNombre(anterior);
        return false;
    }


    // MODIFICAR APELLIDO

    public boolean modificarApellido(int id, String apellido) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || apellido == null || apellido.isBlank()) {
            return false;
        }

        String anterior = nutricionista.getApellido();

        Nutricionista modificado = coleccion.modificarApellido(id, apellido);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setApellido(anterior);
        return false;
    }


    // MODIFICAR DNI

    public boolean modificarDni(int id, String dni) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || dni == null || dni.isBlank()) {
            return false;
        }

        Nutricionista existente = coleccion.buscarPorDni(dni);

        if (existente != null && existente.getId() != id) {
            return false;
        }

        String anterior = nutricionista.getDni();

        Nutricionista modificado = coleccion.modificarDni(id, dni);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setDni(anterior);
        return false;
    }


    // MODIFICAR EMAIL

    public boolean modificarEmail(int id, String email) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || email == null || email.isBlank()) {
            return false;
        }

        Nutricionista existente = coleccion.buscarPorEmail(email);

        if (existente != null && existente.getId() != id) {
            return false;
        }

        String anterior = nutricionista.getEmail();

        Nutricionista modificado = coleccion.modificarEmail(id, email);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setEmail(anterior);
        return false;
    }


    // MODIFICAR PASSWORD

    public boolean modificarPassword(int id, String password) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || password == null || password.isBlank()) {
            return false;
        }

        String anterior = nutricionista.getPassword();

        Nutricionista modificado = coleccion.modificarPassword(id, password);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setPassword(anterior);
        return false;
    }


    // MODIFICAR FECHA DE NACIMIENTO

    public boolean modificarFechaNacimiento(int id, LocalDate fecha) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || fecha == null) {
            return false;
        }

        LocalDate anterior = nutricionista.getFechaDeNacimiento();

        Nutricionista modificado = coleccion.modificarFechaNacimiento(id, fecha);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setFechaDeNacimiento(anterior);
        return false;
    }


    // MODIFICAR ESTADO

    public boolean modificarActivo(int id, boolean activo) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null) {
            return false;
        }

        boolean anterior = nutricionista.isActivo();

        Nutricionista modificado = coleccion.modificarActivo(id, activo);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setActivo(anterior);
        return false;
    }


    // MODIFICAR SALARIO

    public boolean modificarSalario(int id, double salario) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || salario < 0) {
            return false;
        }

        double anterior = nutricionista.getSalario();

        Nutricionista modificado = coleccion.modificarSalario(id, salario);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setSalario(anterior);
        return false;
    }


    // MODIFICAR SUCURSAL (trabaja en una sola; si la sucursal no existe falla la FK y se restaura)

    public boolean modificarSucursal(int id, Sucursal sucursal) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || sucursal == null || sucursal.getId() <= 0) {
            return false;
        }

        Sucursal anterior = nutricionista.getSucursalAsignada();

        Nutricionista modificado = coleccion.modificarSucursal(id, sucursal);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setSucursalAsignada(anterior);
        return false;
    }


    // MODIFICAR COMISION POR CONSULTA

    public boolean modificarComisionPorConsulta(int id, double comisionPorConsulta) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || comisionPorConsulta < 0) {
            return false;
        }

        double anterior = nutricionista.getComisionPorConsulta();

        Nutricionista modificado = coleccion.modificarComisionPorConsulta(id, comisionPorConsulta);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si fallo MySQL, se vuelve al valor anterior en memoria
        nutricionista.setComisionPorConsulta(anterior);
        return false;
    }


    // =========================
    // TURNOS (solo consulta)
    // Para agregar, eliminar, reservar o cancelar turnos se usa TurnosService,
    // que los guarda en MySQL y mantiene actualizada esta lista
    // =========================

    // VER TODOS LOS TURNOS (ordenados por fecha)

    public List<Turno> verTurnos(int idNutricionista) {
        return coleccion.verTurnos(idNutricionista);
    }


    // VER TURNOS DISPONIBLES (ordenados por fecha)

    public List<Turno> verTurnosDisponibles(int idNutricionista) {
        return coleccion.verTurnosDisponibles(idNutricionista);
    }


    // VER TURNOS OCUPADOS (ordenados por fecha)

    public List<Turno> verTurnosOcupados(int idNutricionista) {
        return coleccion.verTurnosOcupados(idNutricionista);
    }

}
