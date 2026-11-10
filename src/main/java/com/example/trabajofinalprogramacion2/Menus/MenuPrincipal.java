package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;
import com.example.trabajofinalprogramacion2.Service.TurnosService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.AdministradorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.EntrenadorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.NutricionistaService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.SocioService;

import java.time.LocalDate;

// Inicio de sesion: se elige el rol, se ingresa DNI y contraseña, y se deriva al menu de ese rol.
// Los services se crean una sola vez aca y se pasan a cada menu.

public class MenuPrincipal {

    private final AdministradorService administradorService;
    private final EntrenadorService entrenadorService;
    private final NutricionistaService nutricionistaService;
    private final TurnosService turnosService;
    private final SocioService socioService;
    // private final GimnasioService gimnasioService; -> cuando este hecho
    // private final SucursalService sucursalService; -> cuando este hecho

    public MenuPrincipal() {
        administradorService = new AdministradorService();
        // SocioService va antes: EntrenadorService lo necesita para cargar los alumnos de cada entrenador
        socioService = new SocioService();
        entrenadorService = new EntrenadorService(socioService);
        nutricionistaService = new NutricionistaService();
        // TurnosService necesita el de nutricionistas y el de socios para vincular cada turno con los objetos reales
        turnosService = new TurnosService(nutricionistaService, socioService);
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
                    ingresarComoSocio();
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
            new MenuAdministrador(admin, administradorService, entrenadorService, nutricionistaService,
                    turnosService, socioService).mostrar();
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

    // Metodo para el acceso de socios con opcion de Login o Registro
    private void ingresarComoSocio() {
        int opcion;
        do {
            Consola.titulo("ACCESO SOCIOS");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse (Nuevo Socio)");
            System.out.println("0. Volver al menú anterior");

            opcion = Consola.leerOpcion(0, 2);

            switch (opcion) {
                case 1:
                    loguearSocio();
                    break;
                case 2:
                    registrarNuevoSocio();
                    break;
            }
        } while (opcion != 0);
    }

    private void loguearSocio() {
        String dni = Consola.leerTexto("DNI: ");
        String password = Consola.leerTexto("Contraseña: ");

        try {
            Socio socio = validarCredenciales(socioService.buscarPorDni(dni), password);

            if (socio != null) {
                new MenuSocio(socio, socioService, turnosService).mostrar();
            }
        } catch (Exception e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    private void registrarNuevoSocio() {
        Consola.titulo("REGISTRO DE NUEVO SOCIO");

        try {
            String nombre = Consola.leerTexto("Nombre: ");
            String apellido = Consola.leerTexto("Apellido: ");
            String dni = Consola.leerTexto("DNI: ");
            String email = Consola.leerTexto("Email: ");
            String password = Consola.leerTexto("Contraseña: ");

            // Fecha de nacimiento
            System.out.println("--- Fecha de Nacimiento ---");
            int anioNac = Consola.leerEntero("Año: ");
            int mesNac = Consola.leerEntero("Mes (1-12): ");
            int diaNac = Consola.leerEntero("Día (1-31): ");
            LocalDate fechaNac = LocalDate.of(anioNac, mesNac, diaNac);

            // Datos fisicos
            double peso = Consola.leerDouble("Peso actual (kg): ");
            double altura = Consola.leerDouble("Altura (m, ej: 1.75): ");

            // Eleccion del plan inicial
            System.out.println("\n--- Seleccione su Plan Inicial ---");
            System.out.println("1. BASICO ($" + Plan.BASICO.getPrecio() + ")");
            System.out.println("2. LIBRE ($" + Plan.LIBRE.getPrecio() + ")");
            System.out.println("3. PRO ($" + Plan.PRO.getPrecio() + ")");
            int opPlan = Consola.leerOpcion(1, 3);

            Plan planElegido = (opPlan == 1) ? Plan.BASICO : (opPlan == 2) ? Plan.LIBRE : Plan.PRO;

            // Creamos la membresia inicial (hoy hasta en 1 mes)
            Membresia membresia = new Membresia(LocalDate.now(), LocalDate.now().plusMonths(1), planElegido);

            Socio nuevoSocio = new Socio(
                    nombre, apellido, dni, email, password,
                    fechaNac, true, peso, altura,
                    LocalDate.now(), membresia
            );

            boolean registrado = socioService.agregar(nuevoSocio);

            if (registrado) {
                System.out.println("\n¡Registro completado con éxito! Ya puede iniciar sesión.");
            } else {
                System.out.println("\nNo se pudo completar el registro.");
            }

        } catch (Exception e) {
            System.out.println("\nError en el registro: " + e.getMessage());
        }

        Consola.pausa();
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