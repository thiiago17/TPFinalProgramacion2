package com.example.trabajofinalprogramacion2.Service.Usuarios;

import com.example.trabajofinalprogramacion2.Colecciones.Usuarios.NutricionistaCollection;
import com.example.trabajofinalprogramacion2.DAO.Usuarios.NutricionistaDAO;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;

import java.time.LocalDate;
import java.util.List;

public class NutricionistaService {
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

        Nutricionista buscado = new Nutricionista();
        buscado.setId(id);

        Nutricionista nutricionista = dao.consulta(buscado.getId());

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


    // CONSULTA EN BASE DE DATOS (POR ID O DNI)

    public Nutricionista consultar(Nutricionista nutricionista) {

        if (nutricionista == null) {
            return null;
        }

        return dao.consulta(nutricionista.getId());
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

    private boolean guardarModificacion(Nutricionista nutricionista, Runnable restaurar) {

        if (nutricionista == null) {
            return false;
        }

        if (dao.modificar(nutricionista)) {
            return true;
        }

        // Si falla MySQL, restauramos el valor anterior en memoria
        restaurar.run();

        return false;
    }


    // MODIFICAR NOMBRE

    public boolean modificarNombre(int id, String nombre) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || nombre == null || nombre.isBlank()) {
            return false;
        }

        String anterior = nutricionista.getNombre();

        Nutricionista modificado = coleccion.modificarNombre(id, nombre);

        return guardarModificacion(modificado, () -> nutricionista.setNombre(anterior));
    }


    // MODIFICAR APELLIDO

    public boolean modificarApellido(int id, String apellido) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || apellido == null || apellido.isBlank()) {
            return false;
        }

        String anterior = nutricionista.getApellido();

        Nutricionista modificado = coleccion.modificarApellido(id, apellido);

        return guardarModificacion(modificado, () -> nutricionista.setApellido(anterior));
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

        return guardarModificacion(modificado, () -> nutricionista.setDni(anterior));
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

        return guardarModificacion(modificado, () -> nutricionista.setEmail(anterior));
    }


    // MODIFICAR PASSWORD

    public boolean modificarPassword(int id, String password) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || password == null || password.isBlank()) {
            return false;
        }

        String anterior = nutricionista.getPassword();

        Nutricionista modificado = coleccion.modificarPassword(id, password);

        return guardarModificacion(modificado, () -> nutricionista.setPassword(anterior));
    }


    // MODIFICAR FECHA DE NACIMIENTO

    public boolean modificarFechaNacimiento(int id, LocalDate fecha) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || fecha == null) {
            return false;
        }

        LocalDate anterior = nutricionista.getFechaDeNacimiento();

        Nutricionista modificado = coleccion.modificarFechaNacimiento(id, fecha);

        return guardarModificacion(modificado, () -> nutricionista.setFechaDeNacimiento(anterior));
    }


    // MODIFICAR ESTADO

    public boolean modificarActivo(int id, boolean activo) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null) {
            return false;
        }

        boolean anterior = nutricionista.isActivo();

        Nutricionista modificado = coleccion.modificarActivo(id, activo);

        return guardarModificacion(modificado, () -> nutricionista.setActivo(anterior));
    }


    // MODIFICAR SALARIO

    public boolean modificarSalario(int id, double salario) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || salario < 0) {
            return false;
        }

        double anterior = nutricionista.getSalario();

        Nutricionista modificado = coleccion.modificarSalario(id, salario);

        return guardarModificacion(modificado, () -> nutricionista.setSalario(anterior));
    }


    // MODIFICAR COMISION POR CONSULTA

    public boolean modificarComisionPorConsulta(int id, double comisionPorConsulta) {

        Nutricionista nutricionista = coleccion.buscar(id);

        if (nutricionista == null || comisionPorConsulta < 0) {
            return false;
        }

        double anterior = nutricionista.getComisionPorConsulta();

        Nutricionista modificado = coleccion.modificarComisionPorConsulta(id, comisionPorConsulta);

        return guardarModificacion(modificado, () -> nutricionista.setComisionPorConsulta(anterior));
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
