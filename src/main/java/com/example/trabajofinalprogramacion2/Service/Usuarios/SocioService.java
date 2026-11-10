package com.example.trabajofinalprogramacion2.Service.Usuarios;

import com.example.trabajofinalprogramacion2.Colecciones.Usuarios.SocioCollection;
import com.example.trabajofinalprogramacion2.DAO.Usuarios.SocioDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.interfaces.GestionPerfil;

import java.time.LocalDate;
import java.util.List;

public class SocioService implements GestionPerfil {
    SocioDAO dao;
    SocioCollection coleccion;

    public SocioService() {
        dao = new SocioDAO();
        coleccion = new SocioCollection();

        // Carga los socios activos de workbench en la colección
        List<Socio> socios = dao.listado();

        for (Socio socio : socios) {
            if (socio.isActivo()) {
                coleccion.agregar(socio);
            }
        }
    }

    // VER LISTADO

    public List<Socio> verListado() {
        return coleccion.verListado();
    }

    // AGREGAR

    public boolean agregar(Socio socio) {
        if (socio == null || socio.getFechaDeNacimiento() == null
                || coleccion.buscarPorDni(socio.getDni()) != null) {
            return false;
        }

        Socio nuevo = dao.agregar(socio);

        if (nuevo != null) {
            return coleccion.agregar(nuevo);
        }

        return false;
    }

    // MODIFICAR OBJETO COMPLETO

    public boolean modificar(Socio socio) {
        if (socio == null || coleccion.buscar(socio.getId()) == null) {
            return false;
        }

        if (dao.modificar(socio)) {
            return coleccion.modificar(socio);
        }

        return false;
    }

    // ELIMINAR (BAJA LÓGICA)

    public boolean eliminar(int id) {
        Socio socio = coleccion.buscar(id);

        if (socio == null) {
            return false;
        }

        if (dao.baja(socio)) {
            return coleccion.eliminar(id);
        }

        return false;
    }

    // DAR DE ALTA (REACTIVAR UN SOCIO DADO DE BAJA)

    public boolean alta(int id) {
        Socio socio = dao.consulta(id);

        if (socio == null) {
            return false;
        }

        if (!dao.alta(socio)) {
            return false;
        }

        socio.setActivo(true);

        // Si ya estaba en memoria lo actualizamos, si no lo agregamos
        Socio enMemoria = coleccion.buscar(id);

        if (enMemoria != null) {
            enMemoria.setActivo(true);
            return true;
        }

        return coleccion.agregar(socio);
    }

    // CONSULTA EN BASE DE DATOS (POR ID)

    public Socio consultar(int id) {
        return dao.consulta(id);
    }

    // CONSULTA EN BASE DE DATOS (POR DNI)

    public Socio consultarPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            return null;
        }
        return dao.consultaPorDni(dni);
    }

    // BUSCAR POR ID (EN MEMORIA)

    public Socio buscar(int id) {
        return coleccion.buscar(id);
    }

    // =========================
    // BÚSQUEDAS ESPECÍFICAS
    // =========================

    public Socio buscarPorDni(String dni) {
        return coleccion.buscarPorDni(dni);
    }

    public List<Socio> buscarPorNombre(String nombre) {
        return coleccion.buscarPorNombre(nombre);
    }

    public List<Socio> buscarPorApellido(String apellido) {
        return coleccion.buscarPorApellido(apellido);
    }

    public Socio buscarPorEmail(String email) {
        return coleccion.buscarPorEmail(email);
    }

    // =========================
    // MODIFICACIONES INDIVIDUALES
    // =========================

    private boolean guardarModificacion(Socio socio) {
        if (socio == null) {
            return false;
        }
        return dao.modificar(socio);
    }

    public boolean modificarNombre(int id, String nombre) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || nombre == null || nombre.isBlank()) {
            return false;
        }
        String anterior = socio.getNombre();
        Socio modificado = coleccion.modificarNombre(id, nombre);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setNombre(anterior);
        return false;
    }

    public boolean modificarApellido(int id, String apellido) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || apellido == null || apellido.isBlank()) {
            return false;
        }
        String anterior = socio.getApellido();
        Socio modificado = coleccion.modificarApellido(id, apellido);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setApellido(anterior);
        return false;
    }

    public boolean modificarDni(int id, String dni) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || dni == null || dni.isBlank()) {
            return false;
        }
        Socio existente = coleccion.buscarPorDni(dni);
        if (existente != null && existente.getId() != id) {
            return false;
        }
        String anterior = socio.getDni();
        Socio modificado = coleccion.modificarDni(id, dni);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setDni(anterior);
        return false;
    }

    public boolean modificarEmail(int id, String email) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || email == null || email.isBlank()) {
            return false;
        }
        Socio existente = coleccion.buscarPorEmail(email);
        if (existente != null && existente.getId() != id) {
            return false;
        }
        String anterior = socio.getEmail();
        Socio modificado = coleccion.modificarEmail(id, email);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setEmail(anterior);
        return false;
    }

    public boolean modificarPassword(int id, String password) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || password == null || password.isBlank()) {
            return false;
        }
        String anterior = socio.getPassword();
        Socio modificado = coleccion.modificarPassword(id, password);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setPassword(anterior);
        return false;
    }

    public boolean modificarFechaNacimiento(int id, LocalDate fecha) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || fecha == null) {
            return false;
        }
        LocalDate anterior = socio.getFechaDeNacimiento();
        Socio modificado = coleccion.modificarFechaNacimiento(id, fecha);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setFechaDeNacimiento(anterior);
        return false;
    }

    public boolean modificarActivo(int id, boolean activo) {
        Socio socio = coleccion.buscar(id);
        if (socio == null) {
            return false;
        }
        boolean anterior = socio.isActivo();
        Socio modificado = coleccion.modificarActivo(id, activo);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setActivo(anterior);
        return false;
    }

    // =========================
    // MÉTODOS ESPECÍFICOS DE SOCIO
    // =========================

    public boolean modificarPeso(int id, double peso) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || peso <= 0) {
            return false;
        }
        Double anterior = socio.getPesoActual();
        Socio modificado = coleccion.modificarPeso(id, peso);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setPesoActual(anterior);
        return false;
    }

    public boolean modificarAltura(int id, double altura) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || altura <= 0) {
            return false;
        }
        Double anterior = socio.getAltura();
        Socio modificado = coleccion.modificarAltura(id, altura);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setAltura(anterior);
        return false;
    }

    public boolean modificarSucursal(int id, Sucursal sucursal) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || sucursal == null || sucursal.getId() <= 0) {
            return false;
        }
        Sucursal anterior = socio.getSucursalAsignada();
        Socio modificado = coleccion.modificarSucursal(id, sucursal);

        if (guardarModificacion(modificado)) {
            return true;
        }
        socio.setSucursalAsignada(anterior);
        return false;
    }

    // GESTIÓN DE MEMBRESÍA Y PAGOS

    public boolean cambiarPlanMembresia(int id, Plan nuevoPlan) {
        Socio socio = coleccion.buscar(id);
        if (socio == null || nuevoPlan == null) {
            return false;
        }

        // Guardamos el estado anterior por si falla la base de datos
        // (clonamos o guardamos la referencia de la membresía previa)
        var membresiaAnterior = socio.getMembresia();

        Socio modificado = coleccion.cambiarPlanMembresia(id, nuevoPlan);

        if (guardarModificacion(modificado)) {
            return true;
        }

        // Si falla MySQL, restauramos la membresía anterior en memoria
        socio.setMembresia(membresiaAnterior);
        return false;
    }

    public String abonarCuota(int id) {
        Socio socio = coleccion.buscar(id);
        if (socio == null) {
            return "Error: Socio no encontrado.";
        }

        // Intentamos abonar en memoria (aplica la regla de negocio de si está vencida)
        String resultadoMemoria = coleccion.abonarCuotaSocio(id);

        // Si el abono fue exitoso, actualizamos los cambios en la base de datos
        // Membresia.abonarCuota devuelve "Cuota abonada con exito" (sin tilde)
        if (resultadoMemoria.contains("exito")) {
            if (!guardarModificacion(socio)) {
                return "Error al sincronizar el pago con la base de datos.";
            }
        }

        return resultadoMemoria;
    }

    public String verEstadoMembresia(int id) {
        return coleccion.verEstadoMembresia(id);
    }

    public List<Socio> listarSociosVencidos() {
        return coleccion.listarSociosVencidos();
    }

    public boolean verificarAccesoSucursal(int idSocio, Sucursal sucursal) {
        return coleccion.verificarAccesoSucursal(idSocio, sucursal);
    }
}