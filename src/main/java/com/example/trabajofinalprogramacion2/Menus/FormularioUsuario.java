package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;

// Carga por teclado los datos que tienen todos los usuarios.
// Los setters de Usuarios validan y tiran IllegalArgumentException si el dato no sirve:
// cada dato se pide dentro de un while hasta que el setter lo acepte.

public final class FormularioUsuario {

    private FormularioUsuario() {
    }

    // Sirve para cualquier hijo de Usuarios (Entrenador, Nutricionista, Administrador, Socio)
    public static void cargarDatosBasicos(Usuarios u) {
        pedirNombre(u);
        pedirApellido(u);
        pedirDni(u);
        pedirEmail(u);
        pedirPassword(u);
        pedirFechaDeNacimiento(u);
        u.setActivo(true);
    }

    private static void pedirNombre(Usuarios u) {
        boolean valido = false;

        while (!valido) {
            try {
                u.setNombre(Consola.leerTexto("Nombre: "));
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void pedirApellido(Usuarios u) {
        boolean valido = false;

        while (!valido) {
            try {
                u.setApellido(Consola.leerTexto("Apellido: "));
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void pedirDni(Usuarios u) {
        boolean valido = false;

        while (!valido) {
            try {
                u.setDni(Consola.leerTexto("DNI: "));
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void pedirEmail(Usuarios u) {
        boolean valido = false;

        while (!valido) {
            try {
                u.setEmail(Consola.leerTexto("Email: "));
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void pedirPassword(Usuarios u) {
        boolean valido = false;

        while (!valido) {
            try {
                u.setPassword(Consola.leerTexto("Contraseña (mínimo 4 caracteres y un número): "));
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void pedirFechaDeNacimiento(Usuarios u) {
        boolean valido = false;

        while (!valido) {
            try {
                u.setFechaDeNacimiento(Consola.leerFecha("Fecha de nacimiento"));
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Pide una contraseña nueva hasta que cumpla las mismas reglas que setPassword
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
