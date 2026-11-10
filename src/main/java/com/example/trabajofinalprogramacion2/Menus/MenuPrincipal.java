package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;
import com.example.trabajofinalprogramacion2.Service.TurnosService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.AdministradorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.EntrenadorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.NutricionistaService;

// Inicio de sesion: se elige el rol, se ingresa DNI y contraseña, y se deriva al menu de ese rol.
// Los services se crean una sola vez aca y se pasan a cada menu.

public class MenuPrincipal {

    private final AdministradorService administradorService;
    private final EntrenadorService entrenadorService;
    private final NutricionistaService nutricionistaService;
    private final TurnosService turnosService;
    // private final SocioService socioService;       -> cuando este hecho
    // private final GimnasioService gimnasioService; -> cuando este hecho
    // private final SucursalService sucursalService; -> cuando este hecho

    public MenuPrincipal() {
        administradorService = new AdministradorService();
        entrenadorService = new EntrenadorService();
        nutricionistaService = new NutricionistaService();
        // TurnosService necesita el de nutricionistas para vincular cada turno con su nutricionista
        turnosService = new TurnosService(nutricionistaService);
    }

    public void iniciar() {

        int opcion;

        do {
            Consola.titulo("INICIO DE SESIÓN");
            System.out.println("1. Administrador");
            System.out.println("2. Entrenador");
            System.out.println("3. Nutricionista");
            System.out.println("4. Socio");
            System.out.println("0. Salir");

            opcion = Consola.leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    ingresarComoAdministrador();
                    break;
                case 2:
                    ingresarComoEntrenador();
                    break;
                case 3:
                    ingresarComoNutricionista();
                    break;
                case 4:
                    // aca iria ingresarComoSocio(); cuando este SocioService:
                    // Socio socio = validarCredenciales(socioService.buscarPorDni(dni), password);
                    // if (socio != null) new MenuSocio(socio, turnosService).mostrar();
                    System.out.println("El ingreso de socios todavía no está disponible.");
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
            }

        } while (opcion != 0);
    }

    // INGRESOS POR ROL =================================================================================

    private void ingresarComoAdministrador() {
        String dni = Consola.leerTexto("DNI: ");
        String password = Consola.leerTexto("Contraseña: ");

        Administrador admin = validarCredenciales(administradorService.buscarPorDni(dni), password);

        if (admin == null) {
            return;
        }

        if (admin.isEsAdminSistema()) {
            new MenuAdministradorSistema(admin, administradorService).mostrar();
        } else {
            new MenuAdministrador(admin, administradorService, entrenadorService, nutricionistaService, turnosService).mostrar();
        }
    }

    private void ingresarComoEntrenador() {
        String dni = Consola.leerTexto("DNI: ");
        String password = Consola.leerTexto("Contraseña: ");

        Entrenador entrenador = validarCredenciales(entrenadorService.buscarPorDni(dni), password);

        if (entrenador != null) {
            new MenuEntrenador(entrenador, entrenadorService).mostrar();
        }
    }

    private void ingresarComoNutricionista() {
        String dni = Consola.leerTexto("DNI: ");
        String password = Consola.leerTexto("Contraseña: ");

        Nutricionista nutricionista = validarCredenciales(nutricionistaService.buscarPorDni(dni), password);

        if (nutricionista != null) {
            new MenuNutricionista(nutricionista, nutricionistaService, turnosService).mostrar();
        }
    }

    // Devuelve el usuario si existe, esta activo y la contraseña coincide; si no, null.
    // El mensaje de error es el mismo en todos los casos para no dar pistas de cual dato fallo.
    private <T extends Usuarios> T validarCredenciales(T usuario, String password) {

        if (usuario == null || !usuario.isActivo() || !password.equals(usuario.getPassword())) {
            System.out.println("DNI o contraseña incorrectos.");
            return null;
        }

        System.out.println("Bienvenido/a, " + usuario.getNombre() + ".");
        return usuario;
    }
}
