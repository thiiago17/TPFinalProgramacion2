package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Service.TurnosService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.NutricionistaService;

import java.time.LocalDateTime;

public class MenuNutricionista {

    private final Nutricionista nutricionista;
    private final NutricionistaService nutricionistaService;
    private final TurnosService turnosService;

    public MenuNutricionista(Nutricionista nutricionista, NutricionistaService nutricionistaService, TurnosService turnosService) {
        this.nutricionista = nutricionista;
        this.nutricionistaService = nutricionistaService;
        this.turnosService = turnosService;
    }

    public void mostrar() {

        int opcion;

        do {
            Consola.titulo("NUTRICIONISTA - " + nutricionista.getNombre() + " " + nutricionista.getApellido());
            System.out.println("1. Ver todos mis turnos");
            System.out.println("2. Ver mis turnos disponibles");
            System.out.println("3. Ver mis turnos reservados");
            System.out.println("4. Crear turno");
            System.out.println("5. Dar de baja un turno (solo disponibles)");
            System.out.println("6. Ver comisión, sueldo anual y antigüedad");
            System.out.println("7. Mi perfil");
            System.out.println("0. Cerrar sesión");

            opcion = Consola.leerOpcion(0, 7);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(nutricionistaService.verTurnos(nutricionista.getId()), "No tiene turnos.");
                    break;
                case 2:
                    Consola.mostrarLista(nutricionistaService.verTurnosDisponibles(nutricionista.getId()),
                            "No tiene turnos disponibles.");
                    break;
                case 3:
                    Consola.mostrarLista(nutricionistaService.verTurnosOcupados(nutricionista.getId()),
                            "No tiene turnos reservados.");
                    break;
                case 4:
                    crearTurno();
                    break;
                case 5:
                    darDeBajaTurno();
                    break;
                case 6:
                    System.out.println("Extra por comisión (turnos reservados): $" + nutricionista.calcularExtraPorComision());
                    System.out.println("Sueldo anual (salario x 12 + comisión): $" + nutricionista.calcularSueldoAnual());
                    System.out.println("Antigüedad: " + (int) nutricionista.calcularAniosAntiguedad() + " años");
                    break;
                case 7:
                    MenuPerfil.mostrar(nutricionista, nutricionistaService);
                    break;
            }

            if (opcion != 0 && opcion != 7) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void crearTurno() {

        LocalDateTime fecha = Consola.leerFechaHora("Fecha y hora del turno");
        double precio = Consola.leerDouble("Precio: ");

        Turno turno = new Turno(null, nutricionista, fecha, precio);

        Consola.resultado(turnosService.agregar(turno),
                "Turno creado con ID " + turno.getId() + ".",
                "No se pudo crear (horario de 10:00 a 18:30 cada 30 min, sin pisarse con otro turno, precio >= 0).");
    }

    private void darDeBajaTurno() {

        Turno turno = turnosService.buscar(Consola.leerEntero("ID del turno: "));

        // Solo puede dar de baja sus propios turnos
        if (turno == null || turno.getNutricionista() == null
                || turno.getNutricionista().getId() != nutricionista.getId()) {
            System.out.println("No tiene un turno con ese ID.");
            return;
        }

        Consola.resultado(turnosService.eliminar(turno.getId()),
                "Turno dado de baja.", "No se pudo: el turno está reservado.");
    }
}
