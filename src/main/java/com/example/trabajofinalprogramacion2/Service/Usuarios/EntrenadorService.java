package com.example.trabajofinalprogramacion2.Service.Usuarios;

import com.example.trabajofinalprogramacion2.Colecciones.Usuarios.EntrenadorCollection;
import com.example.trabajofinalprogramacion2.DAO.Usuarios.EntrenadorDAO;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;

import java.util.List;

public class EntrenadorService {
    private final EntrenadorDAO entrenadorDAO;
    private final EntrenadorCollection entrenadorCollection;

    public EntrenadorService() {
        this.entrenadorDAO = new EntrenadorDAO();
        this.entrenadorCollection = new EntrenadorCollection();
        sincronizarConBD();
    }

    public void sincronizarConBD() {
        List<Entrenador> listaBD = entrenadorDAO.listado();

        for (Entrenador e : listaBD) {
            entrenadorCollection.agregar(e);
        }
    }

    public boolean registrarEntrenador(Entrenador entrenador) {
        if (entrenador == null) {
            System.out.println("Error: El entrenador no puede ser nulo.");
            return false;
        }


        if (entrenadorCollection.buscarPorDni(entrenador.getDni()) != null) {
            System.out.println("Error: Ya existe un entrenador con el DNI " + entrenador.getDni());
            return false;
        }


        Entrenador entrenadorGuardado = entrenadorDAO.agregar(entrenador);

        if (entrenadorGuardado != null) {
            entrenadorCollection.agregar(entrenadorGuardado);
            System.out.println("¡Entrenador registrado con éxito!");
            return true;
        }

        System.out.println("Error al registrar en la base de datos.");
        return false;
    }

    public boolean eliminarEntrenador(int id) {
        Entrenador entrenador = entrenadorCollection.buscar(id);
        if (entrenador == null) {
            System.out.println("No se encontró el entrenador con ID: " + id);
            return false;
        }


        boolean eliminadoEnBD = entrenadorDAO.baja(entrenador);

        if (eliminadoEnBD) {
            entrenadorCollection.eliminar(id);
            System.out.println("Entrenador eliminado correctamente.");
            return true;
        }

        return false;
    }

    public Entrenador buscarPorId(int id) {
        return entrenadorCollection.buscar(id);
    }

    public Entrenador buscarPorDni(String dni) {
        return entrenadorCollection.buscarPorDni(dni);
    }

    public boolean actualizarEntrenador(Entrenador entrenador) {
        if (entrenador == null) return false;

        boolean modificadoEnBD = entrenadorDAO.modificar(entrenador);
        if (modificadoEnBD) {

            entrenadorCollection.modificar(entrenador);
            System.out.println("Entrenador actualizado con éxito.");
            return true;
        }
        return false;
    }

    public List<Entrenador> obtenerListado() {
        return entrenadorCollection.verListado();
    }
}
