package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Service.Usuarios.AdministradorService;

// Menu del administrador del sistema: maneja todos los gimnasios y a los administradores
// (quien entra y quien sale). No maneja el dia a dia de cada gimnasio: eso es del admin de gimnasio.

public class MenuAdministradorSistema {

    private final Administrador admin;
    private final AdministradorService administradorService;
    // private final GimnasioService gimnasioService; -> cuando este hecho

    public MenuAdministradorSistema(Administrador admin, AdministradorService administradorService) {
        this.admin = admin;
        this.administradorService = administradorService;
    }

    public void mostrar() {

        int opcion;

        do {
            Consola.titulo("ADMINISTRADOR DEL SISTEMA - " + admin.getNombre() + " " + admin.getApellido());
            System.out.println("1 - Gestionar gimnasios");
            System.out.println("2 - Gestionar administradores");
            System.out.println("3 - Mi perfil");
            System.out.println("0 - Cerrar sesión");

            opcion = Consola.leerOpcion(0, 3);

            switch (opcion) {
                case 1:
                    menuGimnasios();
                    break;
                case 2:
                    menuAdministradores();
                    break;
                case 3:
                    menuPerfil();
                    break;
            }

        } while (opcion != 0);
    }

    // GIMNASIOS =================================================================================

    private void menuGimnasios() {

        int opcion;

        do {
            Consola.titulo("GIMNASIOS");
            System.out.println("1 - Ver gimnasios");
            System.out.println("2 - Agregar gimnasio");
            System.out.println("3 - Modificar nombre");
            System.out.println("4 - Dar de baja gimnasio");
            System.out.println("5 - Reactivar gimnasio");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    // aca iria gimnasioService.verListado();
                    break;
                case 2:
                    // aca iria gimnasioService.agregar(new Gimnasio(nombre));
                    break;
                case 3:
                    // aca iria gimnasioService.modificarNombre(id, nombre);
                    break;
                case 4:
                    // aca iria gimnasioService.eliminar(id);
                    break;
                case 5:
                    // aca iria gimnasioService.alta(id);
                    break;
            }

        } while (opcion != 0);
    }

    // ADMINISTRADORES =================================================================================

    private void menuAdministradores() {

        int opcion;

        do {
            Consola.titulo("ADMINISTRADORES");
            System.out.println("1 - Ver todos");
            System.out.println("2 - Ver administradores de un gimnasio");
            System.out.println("3 - Agregar administrador de gimnasio");
            System.out.println("4 - Agregar administrador del sistema");
            System.out.println("5 - Dar de baja administrador");
            System.out.println("6 - Reactivar administrador");
            System.out.println("7 - Cambiar de gimnasio a un administrador");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 7);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(administradorService.verListado(), "No hay administradores");
                    break;
                case 2:
                    int idGimnasio = Consola.leerEntero("ID del gimnasio: ");
                    Consola.mostrarLista(administradorService.buscarPorGimnasio(idGimnasio),
                            "Ese gimnasio no tiene administradores");
                    break;
                case 3:
                    agregarAdministrador(false);
                    break;
                case 4:
                    agregarAdministrador(true);
                    break;
                case 5:
                    darDeBajaAdministrador();
                    break;
                case 6:
                    int idReactivar = Consola.leerEntero("ID del administrador a reactivar: ");
                    Consola.resultado(administradorService.alta(idReactivar),
                            "Administrador reactivado", "No se encontró un administrador dado de baja con ese ID");
                    break;
                case 7:
                    cambiarGimnasio();
                    break;
            }

            if (opcion != 0) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void agregarAdministrador(boolean esAdminSistema) {

        Administrador nuevo = new Administrador();
        FormularioUsuario.cargarDatosBasicos(nuevo);
        nuevo.setEsAdminSistema(esAdminSistema);

        if (!esAdminSistema) {
            // Cuando este GimnasioService, validar que el gimnasio exista y este activo
            nuevo.setIdGimnasio(Consola.leerEntero("ID del gimnasio que va a administrar: "));
        }

        Consola.resultado(administradorService.agregar(nuevo),
                "Administrador agregado con ID " + nuevo.getId() + ".",
                "No se pudo agregar (DNI repetido - gimnasio inexistente)");
    }

    private void darDeBajaAdministrador() {

        int id = Consola.leerEntero("ID del administrador a dar de baja: ");

        // No se puede dar de baja a uno mismo: el sistema se quedaria sin quien lo maneje
        if (id == admin.getId()) {
            System.out.println("No podés darte de baja a vos mismo");
            return;
        }

        if (Consola.confirmar("¿Seguro que desea dar de baja al administrador #" + id + "?")) {
            Consola.resultado(administradorService.eliminar(id),
                    "Administrador dado de baja", "No se encontró un administrador activo con ese ID");
        }
    }

    private void cambiarGimnasio() {

        int id = Consola.leerEntero("ID del administrador: ");
        Administrador otro = administradorService.buscar(id);

        if (otro == null || otro.isEsAdminSistema()) {
            System.out.println("No existe un administrador de gimnasio activo con ese ID");
            return;
        }

        Integer anterior = otro.getIdGimnasio();
        otro.setIdGimnasio(Consola.leerEntero("ID del nuevo gimnasio: "));

        if (administradorService.modificar(otro)) {
            System.out.println("Gimnasio actualizado");
        } else {
            otro.setIdGimnasio(anterior);
            System.out.println("No se pudo cambiar el gimnasio");
        }
    }

    // PERFIL =================================================================================

    private void menuPerfil() {
        MenuPerfil.mostrar(admin, administradorService);
    }
}
