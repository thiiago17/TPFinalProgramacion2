package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;

// Carga por teclado los datos que tienen todos los usuarios.
// Cada dato se vuelve a pedir hasta que el setter lo acepte (los setters validan).

public final class FormularioUsuario {

    private FormularioUsuario() {
    }

    public static void cargarDatosBasicos(Usuarios u) {
        Consola.repetirHastaValido(() -> u.setNombre(Consola.leerTexto("Nombre: ")));
        Consola.repetirHastaValido(() -> u.setApellido(Consola.leerTexto("Apellido: ")));
        Consola.repetirHastaValido(() -> u.setDni(Consola.leerTexto("DNI: ")));
        Consola.repetirHastaValido(() -> u.setEmail(Consola.leerTexto("Email: ")));
        Consola.repetirHastaValido(() -> u.setPassword(Consola.leerTexto("Contraseña (mínimo 4 caracteres y un número): ")));
        Consola.repetirHastaValido(() -> u.setFechaDeNacimiento(Consola.leerFecha("Fecha de nacimiento")));
        u.setActivo(true);
    }

    // Pide una contraseña nueva hasta que sea valida (sin modificar al usuario)
    public static String leerPasswordNueva() {
        while (true) {
            String password = Consola.leerTexto("Nueva contraseña (mínimo 4 caracteres y un número): ");

            if (password.length() >= 4 && password.matches(".*\\d.*")) {
                return password;
            }
            System.out.println("La contraseña debe tener al menos 4 caracteres y contener al menos un número.");
        }
    }

    // Por ahora la sucursal se arma solo con el id (la FK de la BD valida que exista).
    // Cuando este SucursalService, buscarla ahi y validar que sea del gimnasio del administrador.
    public static Sucursal leerSucursal() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(Consola.leerEntero("ID de la sucursal: "));
        return sucursal;
    }
}
