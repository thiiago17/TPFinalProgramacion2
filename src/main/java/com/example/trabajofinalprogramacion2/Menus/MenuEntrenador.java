package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Service.Usuarios.EntrenadorService;

import java.util.List;

public class MenuEntrenador {

    private final Entrenador entrenador;
    private final EntrenadorService entrenadorService;
    // private final SocioService socioService; -> cuando este hecho

    public MenuEntrenador(Entrenador entrenador, EntrenadorService entrenadorService) {
        this.entrenador = entrenador;
        this.entrenadorService = entrenadorService;
    }

    public void mostrar() {

        int opcion;

        do {
            Consola.titulo("ENTRENADOR - " + entrenador.getNombre() + " " + entrenador.getApellido());
            System.out.println("1. Ver mis alumnos");
            System.out.println("2. Agregar alumno");
            System.out.println("3. Quitar alumno");
            System.out.println("4. Ver mi sucursal");
            System.out.println("5. Mi perfil");
            System.out.println("0. Cerrar sesión");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    verAlumnos();
                    break;
                case 2:
                    // aca iria entrenadorService.agregarSocio(entrenador.getId(), socioService.buscar(idSocio));
                    System.out.println("(Pendiente: falta SocioService)");
                    break;
                case 3: {
                    int idSocio = Consola.leerEntero("ID del socio a quitar: ");
                    Consola.resultado(entrenadorService.quitarSocio(entrenador.getId(), idSocio),
                            "Alumno quitado.", "Ese socio no es alumno suyo.");
                    break;
                }
                case 4:
                    // aca iria sucursalService.buscar(id) para mostrar la direccion completa
                    System.out.println(entrenador.getSucursalAsignada() != null
                            ? "Sucursal #" + entrenador.getSucursalAsignada().getId()
                            : "No tiene sucursal asignada.");
                    break;
                case 5:
                    MenuPerfil.mostrar(
                            () -> Formato.entrenador(entrenador),
                            password -> entrenadorService.modificarPassword(entrenador.getId(), password),
                            email -> entrenadorService.modificarEmail(entrenador.getId(), email));
                    break;
            }

            if (opcion != 0 && opcion != 5) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void verAlumnos() {

        List<Socio> alumnos = entrenadorService.verAlumnos(entrenador.getId());

        if (alumnos.isEmpty()) {
            System.out.println("No tiene alumnos asignados.");
            return;
        }

        // Por ahora los socios vienen solo con su id (los carga EntrenadorService desde entrenador_socio).
        // Cuando este SocioService se pueden mostrar con Formato.usuario(socio)
        for (Socio socio : alumnos) {
            System.out.println(socio.getNombre() != null ? Formato.usuario(socio) : "Socio #" + socio.getId());
        }
    }
}
