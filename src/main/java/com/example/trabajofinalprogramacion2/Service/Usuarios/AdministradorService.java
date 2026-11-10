package com.example.trabajofinalprogramacion2.Service.Usuarios;

import com.example.trabajofinalprogramacion2.Colecciones.Usuarios.AdministradorCollection;
import com.example.trabajofinalprogramacion2.DAO.Usuarios.AdministradorDAO;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdministradorService {
    AdministradorDAO dao;
    AdministradorCollection coleccion;

    // CONSTRUCTOR

    public AdministradorService() {

        dao = new AdministradorDAO();
        coleccion = new AdministradorCollection();

        // Cargar los administradores activos de MySQL en Collection
        // (los dados de baja quedan en la BD y se recuperan con alta)
        List<Administrador> administradores = dao.listado();

        for (Administrador admin : administradores) {
            if (admin.isActivo()) {
                coleccion.agregar(admin);
            }
        }
    }


    // =========================
    // ABCML
    // =========================

    // VER LISTADO

    public List<Administrador> verListado() {
        return coleccion.verListado();
    }


    // AGREGAR

    public boolean agregar(Administrador admin) {

        // El del sistema no lleva gimnasio, el de gimnasio tiene que tener uno
        if (admin == null || admin.getFechaDeNacimiento() == null || !admin.tieneGimnasioValido()
                || coleccion.buscarPorDni(admin.getDni()) != null) {
            return false;
        }

        // Si no se indico fecha de ingreso, se toma la fecha de hoy
        if (admin.getFechaIngreso() == null) {
            admin.setFechaIngreso(LocalDate.now());
        }

        Administrador nuevo = dao.agregar(admin);

        if (nuevo != null) {
            return coleccion.agregar(nuevo);
        }

        return false;
    }


    // MODIFICAR OBJETO COMPLETO

    public boolean modificar(Administrador admin) {

        if (admin == null || !admin.tieneGimnasioValido() || coleccion.buscar(admin.getId()) == null) {
            return false;
        }

        if (dao.modificar(admin)) {
            return coleccion.modificar(admin);
        }

        return false;
    }


    // ELIMINAR (BAJA LOGICA)

    public boolean eliminar(int id) {

        if (coleccion.buscar(id) == null) {
            return false;
        }

        Administrador admin = dao.consulta(id);

        if (dao.baja(admin)) {
            return coleccion.eliminar(id);
        }

        return false;
    }


    // DAR DE ALTA (REACTIVAR UN ADMINISTRADOR DADO DE BAJA)

    public boolean alta(int id) {

        Administrador admin = dao.consulta(id);

        if (admin == null) {
            return false;
        }

        if (!dao.alta(admin)) {
            return false;
        }

        admin.setActivo(true);

        // Si ya estaba en memoria lo actualizamos, si no lo agregamos
        Administrador enMemoria = coleccion.buscar(id);

        if (enMemoria != null) {
            enMemoria.setActivo(true);
            return true;
        }

        return coleccion.agregar(admin);
    }


    // BUSCAR POR ID

    public Administrador buscar(int id) {
        return coleccion.buscar(id);
    }


    // =========================
    // BUSQUEDAS ESPECIFICAS
    // =========================

    // BUSCAR POR DNI

    public Administrador buscarPorDni(String dni) {
        return coleccion.buscarPorDni(dni);
    }


    // BUSCAR POR NOMBRE

    public List<Administrador> buscarPorNombre(String nombre) {
        return coleccion.buscarPorNombre(nombre);
    }


    // BUSCAR POR APELLIDO

    public List<Administrador> buscarPorApellido(String apellido) {
        return coleccion.buscarPorApellido(apellido);
    }


    // BUSCAR POR EMAIL

    public Administrador buscarPorEmail(String email) {
        return coleccion.buscarPorEmail(email);
    }


    // ADMINISTRADORES DE UN GIMNASIO (normalmente uno, a veces dos)

    public List<Administrador> buscarPorGimnasio(int idGimnasio) {

        List<Administrador> encontrados = new ArrayList<>();

        for (Administrador admin : coleccion.verListado()) {
            if (!admin.isEsAdminSistema() && admin.getIdGimnasio() != null && admin.getIdGimnasio() == idGimnasio) {
                encontrados.add(admin);
            }
        }

        return encontrados;
    }


    // =========================
    // MODIFICACIONES INDIVIDUALES
    // =========================

    // METODO AUXILIAR PARA GUARDAR CAMBIOS

    private boolean guardarModificacion(Administrador admin, Runnable restaurar) {

        if (admin == null) {
            return false;
        }

        if (dao.modificar(admin)) {
            return true;
        }

        // Si falla MySQL, restauramos el valor anterior en memoria
        restaurar.run();

        return false;
    }


    // MODIFICAR NOMBRE

    public boolean modificarNombre(int id, String nombre) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null || nombre == null || nombre.isBlank()) {
            return false;
        }

        String anterior = admin.getNombre();

        Administrador modificado = coleccion.modificarNombre(id, nombre);

        return guardarModificacion(modificado, () -> admin.setNombre(anterior));
    }


    // MODIFICAR APELLIDO

    public boolean modificarApellido(int id, String apellido) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null || apellido == null || apellido.isBlank()) {
            return false;
        }

        String anterior = admin.getApellido();

        Administrador modificado = coleccion.modificarApellido(id, apellido);

        return guardarModificacion(modificado, () -> admin.setApellido(anterior));
    }


    // MODIFICAR DNI

    public boolean modificarDni(int id, String dni) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null || dni == null || dni.isBlank()) {
            return false;
        }

        Administrador existente = coleccion.buscarPorDni(dni);

        if (existente != null && existente.getId() != id) {
            return false;
        }

        String anterior = admin.getDni();

        Administrador modificado = coleccion.modificarDni(id, dni);

        return guardarModificacion(modificado, () -> admin.setDni(anterior));
    }


    // MODIFICAR EMAIL

    public boolean modificarEmail(int id, String email) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null || email == null || email.isBlank()) {
            return false;
        }

        Administrador existente = coleccion.buscarPorEmail(email);

        if (existente != null && existente.getId() != id) {
            return false;
        }

        String anterior = admin.getEmail();

        Administrador modificado = coleccion.modificarEmail(id, email);

        return guardarModificacion(modificado, () -> admin.setEmail(anterior));
    }


    // MODIFICAR PASSWORD

    public boolean modificarPassword(int id, String password) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null || password == null || password.isBlank()) {
            return false;
        }

        String anterior = admin.getPassword();

        Administrador modificado = coleccion.modificarPassword(id, password);

        return guardarModificacion(modificado, () -> admin.setPassword(anterior));
    }


    // MODIFICAR FECHA DE NACIMIENTO

    public boolean modificarFechaNacimiento(int id, LocalDate fecha) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null || fecha == null) {
            return false;
        }

        LocalDate anterior = admin.getFechaDeNacimiento();

        Administrador modificado = coleccion.modificarFechaNacimiento(id, fecha);

        return guardarModificacion(modificado, () -> admin.setFechaDeNacimiento(anterior));
    }


    // MODIFICAR FECHA DE INGRESO

    public boolean modificarFechaIngreso(int id, LocalDate fecha) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null || fecha == null) {
            return false;
        }

        LocalDate anterior = admin.getFechaIngreso();

        Administrador modificado = coleccion.modificarFechaIngreso(id, fecha);

        return guardarModificacion(modificado, () -> admin.setFechaIngreso(anterior));
    }


    // MODIFICAR ESTADO

    public boolean modificarActivo(int id, boolean activo) {

        Administrador admin = coleccion.buscar(id);

        if (admin == null) {
            return false;
        }

        boolean anterior = admin.isActivo();

        Administrador modificado = coleccion.modificarActivo(id, activo);

        return guardarModificacion(modificado, () -> admin.setActivo(anterior));
    }

}
