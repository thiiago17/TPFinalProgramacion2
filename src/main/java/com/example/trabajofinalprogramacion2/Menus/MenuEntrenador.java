package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Service.Usuarios.EntrenadorService;

import java.util.List;

public class MenuEntrenador {

    private final Entrenador entrenador;
    private final EntrenadorService entrenadorService;

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
                case 2: {
                    int idSocio = Consola.leerEntero("ID del socio a agregar: ");
                    Consola.resultado(entrenadorService.agregarSocio(entrenador.getId(), idSocio),
                            "Alumno agregado.", "No se pudo: el socio no existe, está dado de baja o ya es su alumno.");
                    break;
                }
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
                    MenuPerfil.mostrar(entrenador, entrenadorService);
                    break;
            }

            if (opcion != 0 && opcion != 5) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void verAlumnos() {

        List<Socio> alumnos = entrenadorService.verAlumnos(entrenador.getId());

        // Los socios vienen completos desde SocioService: se muestran con su toString
        Consola.mostrarLista(alumnos, "No tiene alumnos asignados.");
    }
}
