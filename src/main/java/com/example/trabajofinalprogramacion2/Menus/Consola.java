package com.example.trabajofinalprogramacion2.Menus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

// Lectura de datos por teclado con validacion.
// Todos los metodos leen la linea entera (nextLine), asi no quedan "enters" colgados en el buffer.

public final class Consola {

    private static final Scanner scanner = new Scanner(System.in);

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Consola() {
    }

    // TEXTO (no puede quedar vacio)

    public static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El dato no puede quedar vacío.");
        }
    }

    // NUMEROS

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    public static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    // Opcion de menu entre min y max (inclusive)
    public static int leerOpcion(int min, int max) {
        while (true) {
            int opcion = leerEntero("Opción: ");

            if (opcion >= min && opcion <= max) {
                return opcion;
            }
            System.out.println("Opción inválida, elija entre " + min + " y " + max + ".");
        }
    }

    // FECHAS

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (dd/MM/aaaa): ");
            try {
                return LocalDate.parse(scanner.nextLine().trim(), FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida.");
            }
        }
    }

    public static LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (dd/MM/aaaa HH:mm): ");
            try {
                return LocalDateTime.parse(scanner.nextLine().trim(), FORMATO_FECHA_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha y hora inválidas.");
            }
        }
    }

    // CONFIRMACION (s/n)

    public static boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                return true;
            }
            if (respuesta.equals("n")) {
                return false;
            }
            System.out.println("Responda 's' o 'n'.");
        }
    }

    // Ejecuta la carga hasta que no tire IllegalArgumentException
    // (los setters de las entidades validan y tiran esa excepcion)
    public static void repetirHastaValido(Runnable carga) {
        while (true) {
            try {
                carga.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Ejecuta una accion de un service y si un setter tira IllegalArgumentException
    // muestra el motivo y devuelve false, en vez de cortar el programa
    public static boolean intentar(BooleanSupplier accion) {
        try {
            return accion.getAsBoolean();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // MOSTRAR

    public static void titulo(String texto) {
        System.out.println("\n===== " + texto + " =====");
    }

    public static void resultado(boolean ok, String mensajeOk, String mensajeError) {
        System.out.println(ok ? mensajeOk : mensajeError);
    }

    public static void pausa() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static <T> void mostrarLista(List<T> lista, String mensajeVacia) {
        if (lista == null || lista.isEmpty()) {
            System.out.println(mensajeVacia);
            return;
        }
        for (T elemento : lista) {
            System.out.println(elemento);
        }
    }
}
