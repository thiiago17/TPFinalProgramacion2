package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Service.TurnosService;

// Listo para usar cuando exista SocioService (el login de socio todavia esta comentado en MenuPrincipal).
// Lo que solo usa TurnosService o metodos de la propia entidad Socio ya funciona.

public class MenuSocio {

    private final Socio socio;
    private final TurnosService turnosService;
    // private final SocioService socioService;        cuando este hecho descomenta
    // private final SucursalService sucursalService;  lo mismo que arriba

    public MenuSocio(Socio socio, TurnosService turnosService) {
        this.socio = socio;
        this.turnosService = turnosService;
    }

    public void mostrar() {

        int opcion;

        do {
            Consola.titulo("SOCIO - " + socio.getNombre() + " " + socio.getApellido());
            System.out.println("1. Ver turnos disponibles con nutricionista");
            System.out.println("2. Reservar turno");
            System.out.println("3. Ver mis turnos");
            System.out.println("4. Cancelar un turno");
            System.out.println("5. Ver mi membresía");
            System.out.println("6. Pagar cuota");
            System.out.println("7. Ver sucursales a las que puedo ir");
            System.out.println("8. Ver mi IMC");
            System.out.println("9. Mi perfil");
            System.out.println("0. Cerrar sesión");

            opcion = Consola.leerOpcion(0, 9);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(turnosService.verDisponibles(), "No hay turnos disponibles.");
                    break;
                case 2: {
                    int id = Consola.leerEntero("ID del turno a reservar: ");
                    Consola.resultado(turnosService.reservar(id, socio),
                            "Turno reservado.", "No se pudo: el turno no existe o ya está reservado.");
                    break;
                }
                case 3:
                    Consola.mostrarLista(turnosService.buscarPorSocio(socio), "No tiene turnos reservados.");
                    break;
                case 4:
                    cancelarTurno();
                    break;
                case 5:
                    System.out.println(socio.getMembresia() != null
                            ? socio.getMembresia().verPlanActual() + " | Vence: " + socio.getMembresia().getFechaVencimiento()
                            : "No tiene membresía.");
                    break;
                case 6:
                    // aca iria socioService.abonarCuota(socio.getId()); (tiene que guardar el nuevo vencimiento en la BD)
                    System.out.println("(Pendiente: falta SocioService)");
                    break;
                case 7:
                    // aca iria: for (Sucursal s : sucursalService.verListado()) if (socio.puedeIngresarA(s)) mostrar(s);
                    System.out.println("(Pendiente: falta SucursalService)");
                    break;
                case 8:
                    System.out.printf("IMC: %.2f - %s%n", socio.calcularIMC(), socio.verEstadoIMC());
                    break;
                case 9:
                    // aca iria MenuPerfil.mostrar(socio, socioService);
                    // (SocioService tiene que implementar GestionPerfil, como los otros services)
                    System.out.println(socio);
                    break;
            }

            if (opcion != 0) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void cancelarTurno() {

        Turno turno = turnosService.buscar(Consola.leerEntero("ID del turno a cancelar: "));

        // Solo puede cancelar sus propios turnos
        if (turno == null || turno.getSocio() == null || !turno.getSocio().equals(socio)) {
            System.out.println("No tiene un turno reservado con ese ID.");
            return;
        }

        Consola.resultado(turnosService.cancelar(turno.getId()),
                "Turno cancelado.", "No se pudo cancelar el turno.");
    }
}
