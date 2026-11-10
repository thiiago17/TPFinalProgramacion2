package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;
import com.example.trabajofinalprogramacion2.interfaces.GestionPerfil;

// Submenu "Mi perfil", comun a todos los usuarios.
// Recibe el usuario y SU service. Como todos los services de usuarios implementan GestionPerfil,
// este menu sirve para cualquiera (polimorfismo): llama a modificarPassword / modificarEmail
// y cada service hace su propia version.

public final class MenuPerfil {

    private MenuPerfil() {
    }

    public static void mostrar(Usuarios usuario, GestionPerfil service) {

        int opcion;

        do {
            Consola.titulo("MI PERFIL");
            System.out.println(usuario);   // llama al toString del tipo real (polimorfismo)
            System.out.println("\n1. Cambiar contraseña");
            System.out.println("2. Cambiar email");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 2);

            switch (opcion) {
                case 1:
                    cambiarPassword(usuario, service);
                    break;
                case 2:
                    cambiarEmail(usuario, service);
                    break;
            }

        } while (opcion != 0);
    }

    private static void cambiarPassword(Usuarios usuario, GestionPerfil service) {

        String password = FormularioUsuario.leerPasswordNueva();

        if (service.modificarPassword(usuario.getId(), password)) {
            System.out.println("Contraseña actualizada.");
        } else {
            System.out.println("No se pudo actualizar la contraseña.");
        }
    }

    private static void cambiarEmail(Usuarios usuario, GestionPerfil service) {

        String email = Consola.leerTexto("Nuevo email: ");

        // setEmail tira ValorNoValidoException si el email no es valido (por ejemplo, sin @)
        try {
            if (service.modificarEmail(usuario.getId(), email)) {
                System.out.println("Email actualizado.");
            } else {
                System.out.println("No se pudo actualizar el email (¿ya lo usa otro usuario?).");
            }
        } catch (ValorNoValidoException e) {
            System.out.println(e.getMessage());
        }
    }
}
