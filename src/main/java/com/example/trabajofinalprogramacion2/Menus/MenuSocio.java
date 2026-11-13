package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.Service.Gimnasio.SucursalService;
import com.example.trabajofinalprogramacion2.Service.TurnosService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.SocioService;

import java.util.ArrayList;
import java.util.List;

public class MenuSocio {

    private final Socio socio;
    private final SocioService socioService;
    private final TurnosService turnosService;
    private final SucursalService sucursalService;

    public MenuSocio(Socio socio, SocioService socioService, TurnosService turnosService, SucursalService sucursalService) {
        this.socio = socio;
        this.socioService = socioService;
        this.turnosService = turnosService;
        this.sucursalService = sucursalService;
    }

    public void mostrar() {

        int opcion;

        do {
            Consola.titulo("SOCIO - " + socio.getNombre() + " " + socio.getApellido());
            System.out.println("1 - Ver turnos disponibles con nutricionista");
            System.out.println("2 - Reservar turno");
            System.out.println("3 - Ver mis turnos");
            System.out.println("4 - Cancelar un turno");
            System.out.println("5 - Ver mi membresía");
            System.out.println("6 - Pagar cuota");
            System.out.println("7 - Ver sucursales a las que puedo ir");
            System.out.println("8 - Ver mi IMC");
            System.out.println("9 - Mi perfil");
            System.out.println("0 - Cerrar sesión");

            opcion = Consola.leerOpcion(0, 9);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(turnosService.verDisponibles(), "No hay turnos disponibles");
                    break;
                case 2: {
                    int id = Consola.leerEntero("ID del turno a reservar: ");
                    Consola.resultado(turnosService.reservar(id, socio),
                            "Turno reservado", "No se pudo: el turno no existe o ya está reservado");
                    break;
                }
                case 3:
                    Consola.mostrarLista(turnosService.buscarPorSocio(socio), "No tiene turnos reservados");
                    break;
                case 4:
                    cancelarTurno();
                    break;
                case 5:
                    System.out.println(socioService.verEstadoMembresia(socio.getId()));
                    break;
                case 6:
                    System.out.println(socioService.abonarCuota(socio.getId()));
                    break;
                case 7:
                    verSucursalesHabilitadas();
                    break;
                case 8:
                    System.out.printf("IMC: %.2f - %s%n", socio.calcularIMC(), socio.verEstadoIMC());
                    break;
                case 9:
                    MenuPerfil.mostrar(socio, socioService);
                    break;
            }

            if (opcion != 0 && opcion != 9) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void cancelarTurno() {

        Turno turno = turnosService.buscar(Consola.leerEntero("ID del turno a cancelar: "));

        // Solo puede cancelar sus propios turnos
        if (turno == null || turno.getSocio() == null || !turno.getSocio().equals(socio)) {
            System.out.println("No tiene un turno reservado con ese ID");
            return;
        }

        Consola.resultado(turnosService.cancelar(turno.getId()),
                "Turno cancelado", "No se pudo cancelar el turno");
    }

    private void verSucursalesHabilitadas()
    {
        if(socio.getSucursalAsignada()==null)
        {
            System.out.println("No tiene sucursal asignada");
            return;
        }
        try {
            Sucursal real = sucursalService.buscarPorId(socio.getSucursalAsignada().getId());
            if(real == null)
            {
                System.out.println("Su sucursal asignada ya no existe");
                return;
            }
            socio.setSucursalAsignada(real);

            List<Sucursal> habilitadas = new ArrayList<>();
            for(Sucursal s : sucursalService.verListado())
            {
                if(socio.puedeIngresarA(s))
                {
                    habilitadas.add(s);
                }
            }
            Consola.mostrarLista(habilitadas, "No puede ingresar a ninguna sucursal (Membresia vencida)");
        } catch (ValorNoValidoException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
