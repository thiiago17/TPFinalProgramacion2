package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.Service.Gimnasio.GimnasioService;
import com.example.trabajofinalprogramacion2.Service.Gimnasio.SucursalService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.AdministradorService;

import java.sql.SQLException;
import java.util.List;

// Menu del administrador del sistema: maneja todos los gimnasios y a los administradores
// (quien entra y quien sale). No maneja el dia a dia de cada gimnasio: eso es del admin de gimnasio.

public class MenuAdministradorSistema {

    private final Administrador admin;
    private final AdministradorService administradorService;
    private final GimnasioService gimnasioService;
    private final SucursalService sucursalService;

    public MenuAdministradorSistema(Administrador admin, AdministradorService administradorService, GimnasioService gimnasioService, SucursalService sucursalService) {
        this.admin = admin;
        this.administradorService = administradorService;
        this.gimnasioService = gimnasioService;
        this.sucursalService = sucursalService;
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
            System.out.println("2 - Busqueda de gimnasios");
            System.out.println("3 - Agregar gimnasio");
            System.out.println("4 - Modificar nombre");
            System.out.println("5 - Dar de baja gimnasio");
            System.out.println("6 - Reactivar gimnasio");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    menuListadoGimnasios();
                    break;
                case 2:
                    menuBusquedaGimnasios();
                    break;
                case 3:
                    String nombre = Consola.leerTexto("Nombre del nuevo gimnasio: ");
                    try {
                        Gimnasio nuevoGimnasio = new Gimnasio();
                        nuevoGimnasio.setNombre(nombre);
                        nuevoGimnasio.setEstado(1);

                        Consola.resultado(gimnasioService.agregar(nuevoGimnasio), "Gimnasio agregado correctamente", "No se pudo agregar el gimnasio");
                    } catch (SQLException | ObjetoNuloException e) {
                        System.err.println("Error al agregar: " + e.getMessage());
                    }
                    break;
                case 4:
                    int idModificar = Consola.leerEntero("ID del gimnasio a modificar: ");
                    String nuevoNombre = Consola.leerTexto("Nuevo nombre: ");
                    try {
                        Consola.resultado(gimnasioService.modificarNombre(idModificar, nuevoNombre), "Nombre modificado correctamente", "No se pudo modificar el nombre");
                    } catch (SQLException | ValorNoValidoException | ObjetoNuloException e) {
                        System.err.println("Error al modificar: " + e.getMessage());
                    }
                    break;
                case 5:
                    int idBaja = Consola.leerEntero("ID del gimnasio a dar de baja: ");
                    try {
                        Gimnasio gimnasioBaja = gimnasioService.buscarPorId(idBaja);

                        if (gimnasioBaja != null) {
                            if (gimnasioService.baja(gimnasioBaja)) { //da de baja el gimnasio

                                List<Sucursal> sucursalesActivas = sucursalService.buscarActivosPorGimnasio(idBaja);
                                for (Sucursal suc : sucursalesActivas) {
                                    sucursalService.baja(suc); //da de baja todas las sucursales vinculadas a ese id
                                }

                                System.out.println("Gimnasio y sus " + sucursalesActivas.size() + " sucursales activas dados de baja correctamente.");
                            } else {
                                System.out.println("No se pudo dar de baja el gimnasio.");
                            }
                        } else {
                            System.out.println("No existe ningún gimnasio con ese ID.");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error al dar de baja: " + e.getMessage());
                    }
                    break;
                case 6:
                    int idAlta = Consola.leerEntero("ID del gimnasio a reactivar: ");
                    try {
                        Gimnasio gimnasioAlta = gimnasioService.buscarPorId(idAlta);

                        if (gimnasioAlta != null) {
                            Consola.resultado(gimnasioService.alta(gimnasioAlta), "Gimnasio reactivado correctamente", "No se pudo reactivar");
                        } else {
                            System.out.println("No existe ningún gimnasio con ese ID.");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error al reactivar: " + e.getMessage());
                    }
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

    //LISTADOS DE GIMNASIOS
    private void menuListadoGimnasios(){
        int opcion;

        do {
            Consola.titulo("LISTADO DE GIMNASIOS");
            System.out.println("1 - Listado general");
            System.out.println("2 - Listado general ordenado por nombre");
            System.out.println("3 - Listado de activos ordenado por nombre");
            System.out.println("4 - Listado de inactivos ordenado por nombre");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    System.out.println("--- LISTADO GENERAL ---");
                    Consola.mostrarLista(gimnasioService.verListado(), "No hay gimnasios registrados en el sistema.");
                    break;

                case 2:
                    System.out.println("--- LISTADO GENERAL (ORDENADO POR NOMBRE) ---");
                    Consola.mostrarLista(gimnasioService.verListadoOrdenadoPorNombre(), "No hay gimnasios registrados en el sistema.");
                    break;

                case 3:
                    System.out.println("--- GIMNASIOS ACTIVOS (ORDENADO POR NOMBRE) ---");
                    Consola.mostrarLista(gimnasioService.verActivosOrdenadoPorNombre(), "No hay gimnasios activos registrados.");
                    break;

                case 4:
                    System.out.println("--- GIMNASIOS INACTIVOS (ORDENADO POR NOMBRE) ---");
                    Consola.mostrarLista(gimnasioService.verInactivosOrdenadoPorNombre(), "No hay gimnasios inactivos registrados.");
                    break;
            }

        } while (opcion != 0);
    }

    //BUSQUEDA DE GIMNASIOS
    private void menuBusquedaGimnasios(){
            int opcion;

            do {
                Consola.titulo("BUSQUEDA DE GIMNASIOS");
                System.out.println("1 - Buscar por ID");
                System.out.println("2 - Buscar por nombre");
                System.out.println("3 - Buscar por activos");
                System.out.println("4 - Buscar por inactivos");
                System.out.println("0 - Volver");

                opcion = Consola.leerOpcion(0, 5);

                switch (opcion) {
                    case 1:
                        int idGim = Consola.leerEntero("Ingrese el ID del gimnasio: ");
                        try {
                            Gimnasio gimEncontrado = gimnasioService.buscarPorId(idGim);
                            if (gimEncontrado != null) {
                                System.out.println("Gimnasio encontrado:");
                                System.out.println(gimEncontrado.toString());
                            } else {
                                System.out.println("No se encontró ningún gimnasio con el ID: " + idGim);
                            }
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        String nombreABuscar = Consola.leerTexto("Ingrese el nombre del gimnasio a buscar: ");
                        try {
                            Consola.mostrarLista(gimnasioService.buscarPorNombre(nombreABuscar), "No se encontraron gimnasios con ese nombre.");
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("--- GIMNASIOS ACTIVOS ---");
                        Consola.mostrarLista(gimnasioService.buscarPorActivos(), "No hay gimnasios activos en el sistema.");
                        break;

                    case 4:
                        System.out.println("--- GIMNASIOS INACTIVOS ---");
                        Consola.mostrarLista(gimnasioService.buscarPorInactivos(), "No hay gimnasios inactivos en el sistema.");
                        break;
                }

            } while (opcion != 0);
        }
}
