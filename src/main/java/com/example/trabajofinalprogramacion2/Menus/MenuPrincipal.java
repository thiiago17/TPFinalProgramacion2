package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.Service.Gimnasio.*;
import com.example.trabajofinalprogramacion2.Service.TurnosService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.AdministradorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.EntrenadorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.NutricionistaService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.SocioService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

// Inicio de sesion: se elige el rol, se ingresa DNI y contraseña, y se deriva al menu de ese rol.
// Los services se crean una sola vez aca y se pasan a cada menu.

public class MenuPrincipal {

    private final AdministradorService administradorService;
    private final EntrenadorService entrenadorService;
    private final NutricionistaService nutricionistaService;
    private final TurnosService turnosService;
    private final SocioService socioService;
    private final GimnasioService gimnasioService;
    private final SucursalService sucursalService;
    private final SucursalMaquinaService sucursalMaquinaService;
    private final GastoService gastoService;
    private final MaquinaService maquinaService;

    public MenuPrincipal() {
        administradorService = new AdministradorService();
        // SocioService va antes: EntrenadorService lo necesita para cargar los alumnos de cada entrenador
        socioService = new SocioService();
        entrenadorService = new EntrenadorService(socioService);
        nutricionistaService = new NutricionistaService();
        // TurnosService necesita el de nutricionistas y el de socios para vincular cada turno con los objetos reales
        turnosService = new TurnosService(nutricionistaService, socioService);
        gastoService = new GastoService();

        // Estos constructores leen la BD y pueden tirar SQLException.
        // Si fallan, el programa no puede funcionar, asi que se corta con un mensaje claro.
        try {
            gimnasioService = new GimnasioService();
            sucursalService = new SucursalService();
            sucursalMaquinaService = new SucursalMaquinaService();
            maquinaService = new MaquinaService();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron cargar gimnasios/sucursales de la BD: " + e.getMessage());
        }
    }

    public void iniciar() {

        int opcion;

        do {
            Consola.titulo("INICIO DE SESIÓN");
            System.out.println("1 - Administrador");
            System.out.println("2 - Entrenador");
            System.out.println("3 - Nutricionista");
            System.out.println("4 - Socio");
            System.out.println("0 - Salir");

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
            new MenuAdministradorSistema(admin, administradorService, gimnasioService, sucursalService, maquinaService).mostrar();
        } else {
            new MenuAdministrador(admin, administradorService, entrenadorService, nutricionistaService,
                    turnosService, socioService, sucursalService, sucursalMaquinaService, gastoService, gimnasioService, maquinaService).mostrar();
        }
    }

    private void ingresarComoEntrenador() {
        String dni = Consola.leerTexto("DNI: ");
        String password = Consola.leerTexto("Contraseña: ");

        Entrenador entrenador = validarCredenciales(entrenadorService.buscarPorDni(dni), password);

        if (entrenador != null) {
            new MenuEntrenador(entrenador, entrenadorService, sucursalService).mostrar();
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
            System.out.println("1 - Iniciar Sesión");
            System.out.println("2 - Registrarse (Nuevo Socio)");
            System.out.println("0 - Volver al menú anterior");

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
                new MenuSocio(socio, socioService, turnosService, sucursalService).mostrar();
            }
        } catch (Exception e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    private void registrarNuevoSocio() {
        Consola.titulo("REGISTRO DE NUEVO SOCIO");

        // Creamos un socio vacío (o usamos setters provisionales)
        Socio tempSocio = new Socio();

        // 1. Validar Nombre
        while (true) {
            try {
                tempSocio.setNombre(Consola.leerTexto("Nombre: "));
                break; // Si el setter no tiró excepción, el dato es válido y salimos del while
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 2. Validar Apellido
        while (true) {
            try {
                tempSocio.setApellido(Consola.leerTexto("Apellido: "));
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 3. Validar DNI
        while (true) {
            try {
                String dni = Consola.leerTexto("DNI: ");

                // Chequeo si ya existe en la base
                if (socioService.buscarPorDni(dni) != null) {
                    throw new ValorNoValidoException("Ya existe un socio registrado con ese DNI.");
                }

                tempSocio.setDni(dni);
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 4. Validar Email
        while (true) {
            try {
                tempSocio.setEmail(Consola.leerTexto("Email: "));
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 5. Validar Contraseña
        while (true) {
            try {
                tempSocio.setPassword(Consola.leerTexto("Contraseña: "));
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 6. Validar Fecha de Nacimiento
        LocalDate fechaNac = null;
        while (fechaNac == null) {
            try {
                System.out.println("--- Fecha de Nacimiento ---");
                int anioNac = Consola.leerEntero("Año: ");
                int mesNac = Consola.leerEntero("Mes (1-12): ");
                int diaNac = Consola.leerEntero("Día (1-31): ");

                LocalDate tempFecha = LocalDate.of(anioNac, mesNac, diaNac);
                tempSocio.setFechaDeNacimiento(tempFecha); // Usa el setter heredado que valida edad/futuro
                fechaNac = tempFecha;
            } catch (Exception e) {
                System.out.println("Error en la fecha: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 7. Validar Peso
        while (true) {
            try {
                tempSocio.setPesoActual(Consola.leerDouble("Peso actual (kg): "));
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 8. Validar Altura
        while (true) {
            try {
                tempSocio.setAltura(Consola.leerDouble("Altura (m, ej: 1.75): "));
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }

        // 9. Fecha de Alta (se asigna directo porque es interna de hoy)
        try {
            tempSocio.setFechaAlta(LocalDate.now());
        } catch (Exception e) {
            // Manejo por seguridad
        }

        // 10. Elección del plan inicial
        System.out.println("\n--- Seleccione su Plan Inicial ---");
        System.out.println("1 - BASICO ($" + Plan.BASICO.getPrecio() + ")");
        System.out.println("2 - LIBRE ($" + Plan.LIBRE.getPrecio() + ")");
        System.out.println("3 - PRO ($" + Plan.PRO.getPrecio() + ")");
        int opPlan = Consola.leerOpcion(1, 3);

        Plan planElegido = (opPlan == 1) ? Plan.BASICO : (opPlan == 2) ? Plan.LIBRE : Plan.PRO;
        Membresia membresia = new Membresia(LocalDate.now(), LocalDate.now().plusMonths(1), planElegido);

        try {
            tempSocio.setMembresia(membresia);
        } catch (Exception e) {
            // Validado por setter
        }

        // 11. Elección del gimnasio y despues de una sucursal de ese gimnasio
        List<Gimnasio> gimnasios = gimnasioService.verActivosOrdenadoPorNombre();
        if (gimnasios.isEmpty()) {
            System.out.println("\nNo hay gimnasios disponibles. No se puede completar el registro");
            Consola.pausa();
            return;
        }

        Sucursal sucursalElegida = null;
        while (sucursalElegida == null) {

            // 11.a Gimnasio
            System.out.println("\n--- Gimnasios disponibles ---");
            Consola.mostrarLista(gimnasios, "No hay gimnasios disponibles");

            Gimnasio gimnasioElegido = null;
            try {
                gimnasioElegido = gimnasioService.buscarPorId(Consola.leerEntero("ID del gimnasio: "));
            } catch (ValorNoValidoException e) {
                // ID negativo o 0: se trata igual que un gimnasio inexistente
            }

            if (gimnasioElegido == null || gimnasioElegido.getEstado() != 1) {
                System.out.println("\nEl gimnasio elegido no existe o no está activo. Intente de nuevo.");
                continue;
            }

            // 11.b Sucursal (solo las activas de ese gimnasio)
            List<Sucursal> sucursales = sucursalService.buscarActivosPorGimnasio(gimnasioElegido.getId());
            if (sucursales.isEmpty()) {
                System.out.println("\nEse gimnasio no tiene sucursales activas. Elija otro.");
                continue;
            }

            System.out.println("\n--- Sucursales de " + gimnasioElegido.getNombre() + " ---");
            Consola.mostrarLista(sucursales, "No hay sucursales disponibles");

            Sucursal tempSuc = null;
            try {
                tempSuc = sucursalService.buscarPorId(Consola.leerEntero("ID de la sucursal: "));
            } catch (ValorNoValidoException e) {
                // ID negativo o 0: se trata igual que una sucursal inexistente
            }

            // Tiene que ser una de las listadas (activa y de ese gimnasio)
            if (tempSuc == null || tempSuc.getEstado() != 1 || tempSuc.getIdGimnasio() != gimnasioElegido.getId()) {
                System.out.println("\nLa sucursal elegida no es una sucursal activa de ese gimnasio. Intente de nuevo.");
                continue;
            }

            tempSocio.setSucursalAsignada(tempSuc);
            sucursalElegida = tempSuc;
        }

        // Marcamos como activo por defecto al registrarse
        tempSocio.setActivo(true);

        // Finalmente, guardamos el objeto ya totalmente validado campo por campo
        try {
            boolean registrado = socioService.agregar(tempSocio);

            if (registrado) {
                System.out.println("\n¡Registro completado con éxito! Ya puede iniciar sesión");
            } else {
                System.out.println("\nNo se pudo completar el registro");
            }
        } catch (Exception e) {
            System.out.println("\nError al guardar en la base de datos: " + e.getMessage());
        }

        Consola.pausa();
    }

    // Devuelve el usuario si existe, esta activo y la contraseña coincide; si no, null.
    // El mensaje de error es el mismo en todos los casos para no dar pistas de cual dato fallo.
    private <T extends Usuarios> T validarCredenciales(T usuario, String password) {

        if (usuario == null || !usuario.isActivo() || !password.equals(usuario.getPassword())) {
            System.out.println("DNI o contraseña incorrectos");
            return null;
        }

        System.out.println("Bienvenido/a, " + usuario.getNombre());
        return usuario;
    }

}