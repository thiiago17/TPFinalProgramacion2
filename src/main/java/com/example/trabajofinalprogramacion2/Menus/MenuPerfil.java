package com.example.trabajofinalprogramacion2.Menus;

import java.util.function.Predicate;
import java.util.function.Supplier;

// Submenu "Mi perfil", comun a todos los usuarios.
// Cada menu le pasa como mostrar sus datos y que metodo de su service usar para cada cambio.

public final class MenuPerfil {

    private MenuPerfil() {
    }

    public static void mostrar(Supplier<String> datos, Predicate<String> cambiarPassword, Predicate<String> cambiarEmail) {

        int opcion;

        do {
            Consola.titulo("MI PERFIL");
            System.out.println(datos.get());
            System.out.println("\n1. Cambiar contraseña");
            System.out.println("2. Cambiar email");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 2);

            switch (opcion) {
                case 1:
                    String password = FormularioUsuario.leerPasswordNueva();
                    Consola.resultado(Consola.intentar(() -> cambiarPassword.test(password)),
                            "Contraseña actualizada.", "No se pudo actualizar la contraseña.");
                    break;
                case 2:
                    String email = Consola.leerTexto("Nuevo email: ");
                    Consola.resultado(Consola.intentar(() -> cambiarEmail.test(email)),
                            "Email actualizado.", "No se pudo actualizar el email (¿ya lo usa otro usuario?).");
                    break;
            }

        } while (opcion != 0);
    }
}
