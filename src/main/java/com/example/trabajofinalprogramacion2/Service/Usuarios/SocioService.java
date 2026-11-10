package com.example.trabajofinalprogramacion2.Service.Usuarios;

import com.example.trabajofinalprogramacion2.Colecciones.Usuarios.SocioCollection;
import com.example.trabajofinalprogramacion2.DAO.Usuarios.SocioDAO;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.interfaces.GestionPerfil;

import java.time.LocalDate;
import java.util.List;

public class SocioService implements GestionPerfil {
    SocioDAO dao;
    SocioCollection coleccion;

    public SocioService() {
        dao = new SocioDAO();
        coleccion = new SocioCollection();

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
        if (socio == null) {
            throw new ObjetoNuloException("El socio no puede ser nulo.");
        }
        if (socio.getFechaDeNacimiento() == null) {
            throw new ObjetoNuloException("La fecha de nacimiento del socio no puede ser nula.");
        }
        if (coleccion.buscarPorDni(socio.getDni()) != null) {
            throw new ValorNoValidoException("Ya existe un socio registrado con el DNI: " + socio.getDni());
        }

        Socio nuevo = dao.agregar(socio);

        if (nuevo != null) {
            return coleccion.agregar(nuevo);
        }

        return false;
    }

    // MODIFICAR OBJETO COMPLETO

    public boolean modificar(Socio socio) {
        if (socio == null) {
            throw new ObjetoNuloException("El socio a modificar no puede ser nulo.");
        }
        if (coleccion.buscar(socio.getId()) == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + socio.getId());
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
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
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
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id + " en la base de datos.");
        }

        if (!dao.alta(socio)) {
            return false;
        }

        socio.setActivo(true);

        Socio enMemoria = coleccion.buscar(id);

        if (enMemoria != null) {
            enMemoria.setActivo(true);
            return true;
        }

        return coleccion.agregar(socio);
    }

    // CONSULTA EN BASE DE DATOS (POR ID)

    public Socio consultar(int id) {
        Socio socio = dao.consulta(id);
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        return socio;
    }

    // CONSULTA EN BASE DE DATOS (POR DNI)

    public Socio consultarPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new ValorNoValidoException("El DNI a consultar no puede estar vacio.");
        }
        Socio socio = dao.consultaPorDni(dni);
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro ningún socio con el DNI: " + dni);
        }
        return socio;
    }

    // BUSCAR POR ID (EN MEMORIA)

    public Socio buscar(int id) {
        Socio socio = coleccion.buscar(id);
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id + " en memoria.");
        }
        return socio;
    }

    // =========================
    // BÚSQUEDAS ESPECÍFICAS
    // =========================

    public Socio buscarPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new ValorNoValidoException("El DNI de busqueda no puede estar vacio.");
        }
        return coleccion.buscarPorDni(dni);
    }

    public List<Socio> buscarPorNombre(String nombre) {
        if (nombre == null) {
            throw new ObjetoNuloException("El nombre de busqueda no puede ser nulo.");
        }
        return coleccion.buscarPorNombre(nombre);
    }

    public List<Socio> buscarPorApellido(String apellido) {
        if (apellido == null) {
            throw new ObjetoNuloException("El apellido de busqueda no puede ser nulo.");
        }
        return coleccion.buscarPorApellido(apellido);
    }

    public Socio buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValorNoValidoException("El email de busqueda no puede estar vacio.");
        }
        return coleccion.buscarPorEmail(email);
    }

    // =========================
    // MODIFICACIONES INDIVIDUALES
    // =========================

    private boolean guardarModificacion(Socio socio) {
        if (socio == null) {
            throw new ObjetoNuloException("El socio modificado no puede ser nulo.");
        }
        return dao.modificar(socio);
    }

    public boolean modificarNombre(int id, String nombre) {
        Socio socio = coleccion.buscar(id);
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (nombre == null || nombre.isBlank()) {
            throw new ValorNoValidoException("El nuevo nombre no puede estar vacio.");
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (apellido == null || apellido.isBlank()) {
            throw new ValorNoValidoException("El nuevo apellido no puede estar vacio.");
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (dni == null || dni.isBlank()) {
            throw new ValorNoValidoException("El nuevo DNI no puede estar vacio.");
        }
        Socio existente = coleccion.buscarPorDni(dni);
        if (existente != null && existente.getId() != id) {
            throw new ValorNoValidoException("Ya existe otro socio registrado con el DNI: " + dni);
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (email == null || email.isBlank()) {
            throw new ValorNoValidoException("El nuevo email no puede estar vacio.");
        }
        Socio existente = coleccion.buscarPorEmail(email);
        if (existente != null && existente.getId() != id) {
            throw new ValorNoValidoException("Ya existe otro socio registrado con el email: " + email);
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (password == null || password.isBlank()) {
            throw new ValorNoValidoException("La nueva contraseña no puede estar vacia.");
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (fecha == null) {
            throw new ObjetoNuloException("La fecha de nacimiento no puede ser nula.");
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
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (peso <= 0) {
            throw new ValorNoValidoException("El peso debe ser mayor a 0.");
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (altura <= 0) {
            throw new ValorNoValidoException("La altura debe ser mayor a 0.");
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (sucursal == null) {
            throw new ObjetoNuloException("La sucursal no puede ser nula.");
        }
        if (sucursal.getId() <= 0) {
            throw new ValorNoValidoException("El ID de la sucursal no es valido.");
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
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        if (nuevoPlan == null) {
            throw new ObjetoNuloException("El nuevo plan no puede ser nulo.");
        }

        var membresiaAnterior = socio.getMembresia();

        Socio modificado = coleccion.cambiarPlanMembresia(id, nuevoPlan);

        if (guardarModificacion(modificado)) {
            return true;
        }

        socio.setMembresia(membresiaAnterior);
        return false;
    }

    public String abonarCuota(int id) {
        Socio socio = coleccion.buscar(id);
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }

        String resultadoMemoria = coleccion.abonarCuotaSocio(id);

        if (resultadoMemoria.contains("exito")) {
            if (!guardarModificacion(socio)) {
                return "Error al sincronizar el pago con la base de datos.";
            }
        }

        return resultadoMemoria;
    }

    public String verEstadoMembresia(int id) {
        Socio socio = coleccion.buscar(id);
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + id);
        }
        return coleccion.verEstadoMembresia(id);
    }

    public List<Socio> listarSociosVencidos() {
        return coleccion.listarSociosVencidos();
    }

    public boolean verificarAccesoSucursal(int idSocio, Sucursal sucursal) {
        Socio socio = coleccion.buscar(idSocio);
        if (socio == null) {
            throw new ObjetoNuloException("No se encontro el socio con ID: " + idSocio);
        }
        if (sucursal == null) {
            throw new ObjetoNuloException("La sucursal a verificar no puede ser nula.");
        }
        return coleccion.verificarAccesoSucursal(idSocio, sucursal);
    }
}