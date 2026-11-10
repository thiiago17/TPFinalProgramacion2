package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;

import java.util.HashSet;

public class EntrenadorCollection extends UsuariosCollection<Entrenador> {


    public Entrenador modificarEspecialidad(int id, String especialidad) {
        Entrenador entrenador = buscar(id);

        if (entrenador == null || especialidad == null || especialidad.isBlank()) {
            return null;
        }

        entrenador.setEspecialidad(especialidad);
        return entrenador;
    }


    public Entrenador modificarSalario(int id, Double salario) {
        Entrenador entrenador = buscar(id);

        if (entrenador == null || salario == null || salario < 0) {
            return null;
        }

        entrenador.setSalario(salario);
        return entrenador;
    }


    public Entrenador modificarSucursal(int id, Sucursal sucursal) {
        Entrenador entrenador = buscar(id);

        if (entrenador == null || sucursal == null) {
            return null;
        }

        entrenador.setSucursalAsignada(sucursal);
        return entrenador;
    }


    // ALUMNOS =================================================================================

    // AGREGAR SOCIO (no se repite: Socio no tiene equals, asi que se compara por id)

    public boolean agregarSocio(int idEntrenador, Socio socio) throws ObjetoNuloException {

        if (socio == null) {
            throw new ObjetoNuloException("No se puede agregar un socio nulo al entrenador");
        }

        Entrenador entrenador = buscar(idEntrenador);

        if (entrenador == null || tieneSocio(idEntrenador, socio.getId())) {
            return false;
        }

        if (entrenador.getAlumnosAsociados() == null) {
            entrenador.setAlumnosAsociados(new HashSet<>());
        }

        return entrenador.getAlumnosAsociados().add(socio);
    }

    // QUITAR SOCIO

    public boolean quitarSocio(int idEntrenador, int idSocio) {
        Entrenador entrenador = buscar(idEntrenador);

        if (entrenador == null || entrenador.getAlumnosAsociados() == null) {
            return false;
        }

        // Primero se busca el socio con ese id y despues se lo saca
        // (no se puede sacar elementos de un Set mientras se lo recorre con for-each)
        Socio encontrado = null;

        for (Socio socio : entrenador.getAlumnosAsociados()) {
            if (socio.getId() == idSocio) {
                encontrado = socio;
                break;
            }
        }

        if (encontrado == null) {
            return false;
        }

        return entrenador.getAlumnosAsociados().remove(encontrado);
    }

    // QUITAR TODOS LOS SOCIOS (cuando el entrenador se da de baja)

    public void quitarTodosLosSocios(int idEntrenador) {
        Entrenador entrenador = buscar(idEntrenador);

        if (entrenador != null && entrenador.getAlumnosAsociados() != null) {
            entrenador.getAlumnosAsociados().clear();
        }
    }

    // QUITAR UN SOCIO DE TODOS LOS ENTRENADORES (cuando el socio se da de baja)

    public void quitarSocioDeTodos(int idSocio) {
        for (Entrenador entrenador : usuarios) {
            quitarSocio(entrenador.getId(), idSocio);
        }
    }

    // TIENE SOCIO

    public boolean tieneSocio(int idEntrenador, int idSocio) {
        Entrenador entrenador = buscar(idEntrenador);

        if (entrenador == null || entrenador.getAlumnosAsociados() == null) {
            return false;
        }

        for (Socio socio : entrenador.getAlumnosAsociados()) {
            if (socio.getId() == idSocio) {
                return true;
            }
        }

        return false;
    }

}