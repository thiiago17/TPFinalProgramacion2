package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLCollection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuariosCollection<T extends Usuarios> implements ABCMLCollection<T> {

    protected List<T> usuarios = new ArrayList<>();

    public List<T> verListado() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public boolean eliminar(int id) {

        T usuario = buscar(id);

        if (usuario != null) {
            usuarios.remove(usuario);
            return true;
        }

        return false;
    }

    @Override
    public boolean agregar(T usuario) {
        if (usuario == null) {
            return false;
        }

        usuarios.add(usuario);
        return true;
    }

    // MODIFICACIONES =================================================================================

    // ENTERO

    @Override
    public boolean modificar(T usuario) {

        if (usuario == null) {
            return false;
        }

        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).getDni().equals(usuario.getDni()))
            {
                usuarios.set(i, usuario);
                return true;
            }
        }

        return false;
    }

    // MODIFICAR DNI

    public T modificarDni(int id, String dni) {

        T usuario = buscar(id);

        if (usuario == null || dni == null || dni.isBlank()) {
            return null;
        }

        usuario.setDni(dni);

        return usuario;
    }

    // MODIFICAR NOMBRE

    public T modificarNombre(int id, String nombre) {

        T usuario = buscar(id);

        if (usuario == null || nombre == null || nombre.isBlank()) {
            return null;
        }

        usuario.setNombre(nombre);

        return usuario;
    }

    // MODIFICAR APELLIDO

    public T modificarApellido(int id, String apellido) {

        T usuario = buscar(id);

        if (usuario == null || apellido == null || apellido.isBlank()) {
            return null;
        }

        usuario.setApellido(apellido);

        return usuario;
    }

    // MODIFICAR EMAIL

    public T modificarEmail(int id, String email) {

        T usuario = buscar(id);

        if (usuario == null || email == null || email.isBlank()) {
            return null;
        }

        usuario.setEmail(email);

        return usuario;
    }

    // MODIFICAR CONTRASEÑA

    public T modificarPassword(int id, String password) {

        T usuario = buscar(id);

        if (usuario == null || password == null || password.isBlank()) {
            return null;
        }

        usuario.setPassword(password);

        return usuario;
    }

    // MODIFICAR FECHA DE NACIMIENTO

    public T modificarFechaNacimiento(int id, LocalDate fechaNacimiento) {

        T usuario = buscar(id);

        if (usuario == null || fechaNacimiento == null) {
            return null;
        }

        usuario.setFechaDeNacimiento(fechaNacimiento);

        return usuario;
    }

    // MODIFICAR ACTIVO

    public T modificarActivo(int id, boolean activo) {

        T usuario = buscar(id);

        if (usuario == null) {
            return null;
        }

        usuario.setActivo(activo);

        return usuario;
    }


    // BUSQUEDA =================================================================================

    public T buscar(int id) {
        for (T usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }

        return null;
    }

    // BUSCAR POR DNI

    public T buscarPorDni(String dni) {

        if (dni == null) {
            return null;
        }

        for (T usuario : usuarios) {

            if (usuario.getDni().equalsIgnoreCase(dni)) {
                return usuario;
            }
        }

        return null;
    }

    // BUSCAR POR NOMBRE

    public List<T> buscarPorNombre(String nombre) {

        List<T> encontrados = new ArrayList<>();

        if (nombre == null) {
            return encontrados;
        }

        for (T usuario : usuarios) {

            if (usuario.getNombre().toLowerCase().contains(nombre.toLowerCase())) {

                encontrados.add(usuario);
            }
        }

        return encontrados;
    }

    // BUSCAR POR APELLIDO

    public List<T> buscarPorApellido(String apellido) {

        List<T> encontrados = new ArrayList<>();

        if (apellido == null) {
            return encontrados;
        }

        for (T usuario : usuarios) {

            if (usuario.getApellido().toLowerCase().contains(apellido.toLowerCase())) {

                encontrados.add(usuario);
            }
        }

        return encontrados;
    }

    // BUSCAR POR EMAIL

    public T buscarPorEmail(String email) {

        if (email == null) {
            return null;
        }

        for (T usuario : usuarios) {

            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }

        return null;
    }

}
