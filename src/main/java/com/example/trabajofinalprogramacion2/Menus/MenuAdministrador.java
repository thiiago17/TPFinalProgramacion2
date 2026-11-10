package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Service.TurnosService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.AdministradorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.EntrenadorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.NutricionistaService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.SocioService;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Menu del administrador de un gimnasio: maneja el dia a dia de SU gimnasio
// (personal, socios, turnos, sucursales, maquinas y gastos).
// TODO cuando este SucursalService: filtrar entrenadores y nutricionistas por las sucursales
// del gimnasio del admin (admin.getIdGimnasio()), hoy se listan todos.

public class MenuAdministrador {

    private final Administrador admin;
    private final AdministradorService administradorService;
    private final EntrenadorService entrenadorService;
    private final NutricionistaService nutricionistaService;
    private final TurnosService turnosService;
    private final SocioService socioService;

    public MenuAdministrador(Administrador admin, AdministradorService administradorService,
                             EntrenadorService entrenadorService, NutricionistaService nutricionistaService,
                             TurnosService turnosService, SocioService socioService) {
        this.admin = admin;
        this.administradorService = administradorService;
        this.entrenadorService = entrenadorService;
        this.nutricionistaService = nutricionistaService;
        this.turnosService = turnosService;
        this.socioService = socioService;
    }

    public void mostrar() {

        int opcion;

        do {
            Consola.titulo("ADMINISTRADOR - GIMNASIO #" + admin.getIdGimnasio() + " - " + admin.getNombre());
            System.out.println("1. Entrenadores");
            System.out.println("2. Nutricionistas");
            System.out.println("3. Turnos");
            System.out.println("4. Socios");
            System.out.println("5. Sucursales");
            System.out.println("6. Máquinas");
            System.out.println("7. Gastos");
            System.out.println("8. Mi perfil");
            System.out.println("0. Cerrar sesión");

            opcion = Consola.leerOpcion(0, 8);

            switch (opcion) {
                case 1:
                    menuEntrenadores();
                    break;
                case 2:
                    menuNutricionistas();
                    break;
                case 3:
                    menuTurnos();
                    break;
                case 4:
                    menuSocios();
                    break;
                case 5:
                    menuSucursales();
                    break;
                case 6:
                    menuMaquinas();
                    break;
                case 7:
                    menuGastos();
                    break;
                case 8:
                    MenuPerfil.mostrar(admin, administradorService);
                    break;
            }

        } while (opcion != 0);
    }

    // ENTRENADORES =================================================================================

    private void menuEntrenadores() {

        int opcion;

        do {
            Consola.titulo("ENTRENADORES");
            System.out.println("1. Ver entrenadores");
            System.out.println("2. Agregar entrenador");
            System.out.println("3. Modificar especialidad");
            System.out.println("4. Modificar salario");
            System.out.println("5. Asignar sucursal");
            System.out.println("6. Dar de baja (pierde a sus alumnos)");
            System.out.println("7. Reactivar entrenador");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 7);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(entrenadorService.verListado(), "No hay entrenadores.");
                    break;
                case 2:
                    agregarEntrenador();
                    break;
                case 3: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    String especialidad = Consola.leerTexto("Nueva especialidad: ");
                    Consola.resultado(entrenadorService.modificarEspecialidad(id, especialidad),
                            "Especialidad actualizada.", "No se encontró un entrenador activo con ese ID.");
                    break;
                }
                case 4: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    double salario = Consola.leerDouble("Nuevo salario: ");
                    Consola.resultado(entrenadorService.modificarSalario(id, salario),
                            "Salario actualizado.", "No se pudo actualizar (¿ID correcto? ¿salario negativo?).");
                    break;
                }
                case 5: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    Consola.resultado(entrenadorService.modificarSucursal(id, FormularioUsuario.leerSucursal()),
                            "Sucursal asignada.", "No se pudo asignar (¿existen el entrenador y la sucursal?).");
                    break;
                }
                case 6: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    if (Consola.confirmar("¿Seguro? El entrenador va a perder a todos sus alumnos")) {
                        Consola.resultado(entrenadorService.eliminar(id),
                                "Entrenador dado de baja.", "No se encontró un entrenador activo con ese ID.");
                    }
                    break;
                }
                case 7: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    Consola.resultado(entrenadorService.alta(id),
                            "Entrenador reactivado.", "No se encontró un entrenador dado de baja con ese ID.");
                    break;
                }
            }

            if (opcion != 0) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void agregarEntrenador() {

        Entrenador nuevo = new Entrenador();
        FormularioUsuario.cargarDatosBasicos(nuevo);
        nuevo.setEspecialidad(Consola.leerTexto("Especialidad: "));
        nuevo.setSalario(Consola.leerDouble("Salario: "));
        nuevo.setSucursalAsignada(FormularioUsuario.leerSucursal());

        Consola.resultado(entrenadorService.agregar(nuevo),
                "Entrenador agregado con ID " + nuevo.getId() + ".",
                "No se pudo agregar (¿DNI repetido o sucursal inexistente?).");
    }

    // NUTRICIONISTAS =================================================================================

    private void menuNutricionistas() {

        int opcion;

        do {
            Consola.titulo("NUTRICIONISTAS");
            System.out.println("1. Ver nutricionistas");
            System.out.println("2. Agregar nutricionista");
            System.out.println("3. Modificar salario");
            System.out.println("4. Modificar comisión por consulta");
            System.out.println("5. Asignar sucursal");
            System.out.println("6. Dar de baja");
            System.out.println("7. Reactivar nutricionista");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 7);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(nutricionistaService.verListado(), "No hay nutricionistas.");
                    break;
                case 2:
                    agregarNutricionista();
                    break;
                case 3: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    double salario = Consola.leerDouble("Nuevo salario: ");
                    Consola.resultado(nutricionistaService.modificarSalario(id, salario),
                            "Salario actualizado.", "No se pudo actualizar (¿ID correcto? ¿salario negativo?).");
                    break;
                }
                case 4: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    double comision = Consola.leerDouble("Nueva comisión por consulta: ");
                    Consola.resultado(nutricionistaService.modificarComisionPorConsulta(id, comision),
                            "Comisión actualizada.", "No se pudo actualizar (¿ID correcto? ¿comisión negativa?).");
                    break;
                }
                case 5: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    Consola.resultado(nutricionistaService.modificarSucursal(id, FormularioUsuario.leerSucursal()),
                            "Sucursal asignada.", "No se pudo asignar (¿existen el nutricionista y la sucursal?).");
                    break;
                }
                case 6: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    if (Consola.confirmar("¿Seguro que desea darlo de baja?")) {
                        Consola.resultado(nutricionistaService.eliminar(id),
                                "Nutricionista dado de baja.", "No se encontró un nutricionista activo con ese ID.");
                    }
                    break;
                }
                case 7: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    Consola.resultado(nutricionistaService.alta(id),
                            "Nutricionista reactivado.", "No se encontró un nutricionista dado de baja con ese ID.");
                    break;
                }
            }

            if (opcion != 0) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void agregarNutricionista() {

        Nutricionista nuevo = new Nutricionista();
        FormularioUsuario.cargarDatosBasicos(nuevo);
        nuevo.setSalario(Consola.leerDouble("Salario: "));
        nuevo.setComisionPorConsulta(Consola.leerDouble("Comisión por consulta: "));
        nuevo.setSucursalAsignada(FormularioUsuario.leerSucursal());

        Consola.resultado(nutricionistaService.agregar(nuevo),
                "Nutricionista agregado con ID " + nuevo.getId() + ".",
                "No se pudo agregar (¿DNI repetido o sucursal inexistente?).");
    }

    // TURNOS =================================================================================

    private void menuTurnos() {

        int opcion;

        do {
            Consola.titulo("TURNOS");
            System.out.println("1. Ver todos");
            System.out.println("2. Ver disponibles");
            System.out.println("3. Ver reservados");
            System.out.println("4. Crear turno");
            System.out.println("5. Modificar fecha y hora");
            System.out.println("6. Modificar precio");
            System.out.println("7. Dar de baja turno (solo disponibles)");
            System.out.println("8. Ver turnos dados de baja");
            System.out.println("9. Reactivar turno");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 9);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(turnosService.verListado(), "No hay turnos.");
                    break;
                case 2:
                    Consola.mostrarLista(turnosService.verDisponibles(), "No hay turnos disponibles.");
                    break;
                case 3:
                    Consola.mostrarLista(turnosService.verReservados(), "No hay turnos reservados.");
                    break;
                case 4:
                    crearTurno();
                    break;
                case 5: {
                    int id = Consola.leerEntero("ID del turno: ");
                    LocalDateTime fecha = Consola.leerFechaHora("Nueva fecha y hora");
                    Consola.resultado(turnosService.modificarFecha(id, fecha),
                            "Fecha actualizada.",
                            "No se pudo (horario de 10:00 a 18:30 cada 30 min, sin pisarse con otro turno).");
                    break;
                }
                case 6: {
                    int id = Consola.leerEntero("ID del turno: ");
                    double precio = Consola.leerDouble("Nuevo precio: ");
                    Consola.resultado(turnosService.modificarPrecio(id, precio),
                            "Precio actualizado.", "No se pudo actualizar (¿ID correcto? ¿precio negativo?).");
                    break;
                }
                case 7: {
                    int id = Consola.leerEntero("ID del turno: ");
                    Consola.resultado(turnosService.eliminar(id),
                            "Turno dado de baja.", "No se pudo: el turno no existe o está reservado.");
                    break;
                }
                case 8:
                    Consola.mostrarLista(turnosService.verDadosDeBaja(), "No hay turnos dados de baja.");
                    break;
                case 9: {
                    int id = Consola.leerEntero("ID del turno: ");
                    Consola.resultado(turnosService.alta(id),
                            "Turno reactivado.",
                            "No se pudo: no está dado de baja o ya hay otro turno en ese horario.");
                    break;
                }
            }

            if (opcion != 0) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    private void crearTurno() {

        Nutricionista nutricionista = nutricionistaService.buscar(Consola.leerEntero("ID del nutricionista: "));

        if (nutricionista == null) {
            System.out.println("No se encontró un nutricionista activo con ese ID.");
            return;
        }

        LocalDateTime fecha = Consola.leerFechaHora("Fecha y hora del turno");
        double precio = Consola.leerDouble("Precio: ");

        Turno turno = new Turno(null, nutricionista, fecha, precio);

        Consola.resultado(turnosService.agregar(turno),
                "Turno creado con ID " + turno.getId() + ".",
                "No se pudo crear (horario de 10:00 a 18:30 cada 30 min, sin pisarse con otro turno, precio >= 0).");
    }

    // SOCIOS =================================================================================

    private void menuSocios() {

        int opcion;

        do {
            Consola.titulo("SOCIOS");
            System.out.println("1. Ver socios");
            System.out.println("2. Agregar socio");
            System.out.println("3. Cambiar plan");
            System.out.println("4. Asignar sucursal");
            System.out.println("5. Registrar pago de cuota");
            System.out.println("6. Asignar entrenador");
            System.out.println("7. Dar de baja (pierde entrenador y turnos reservados)");
            System.out.println("8. Reactivar socio");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 8);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(socioService.verListado(), "No hay socios.");
                    break;
                case 2:
                    agregarSocio();
                    break;
                case 3: {
                    int id = Consola.leerEntero("ID del socio: ");
                    Plan plan = FormularioUsuario.leerPlan();
                    Consola.resultado(socioService.cambiarPlanMembresia(id, plan),
                            "Plan actualizado.", "No se encontró un socio activo con ese ID.");
                    break;
                }
                case 4: {
                    int id = Consola.leerEntero("ID del socio: ");
                    Consola.resultado(socioService.modificarSucursal(id, FormularioUsuario.leerSucursal()),
                            "Sucursal asignada.", "No se pudo asignar (¿existen el socio y la sucursal?).");
                    break;
                }
                case 5: {
                    int id = Consola.leerEntero("ID del socio: ");
                    System.out.println(socioService.abonarCuota(id));
                    break;
                }
                case 6: {
                    int idSocio = Consola.leerEntero("ID del socio: ");
                    int idEntrenador = Consola.leerEntero("ID del entrenador: ");
                    Consola.resultado(entrenadorService.agregarSocio(idEntrenador, idSocio),
                            "Entrenador asignado.",
                            "No se pudo: el socio o el entrenador no existen / están de baja, o ya estaban vinculados.");
                    break;
                }
                case 7:
                    darDeBajaSocio();
                    break;
                case 8: {
                    int id = Consola.leerEntero("ID del socio: ");
                    Consola.resultado(socioService.alta(id),
                            "Socio reactivado.", "No se encontró un socio con ese ID.");
                    break;
                }
            }

            if (opcion != 0) {
                Consola.pausa();
            }

        } while (opcion != 0);
    }

    // El socio nuevo paga el primer mes al inscribirse: la membresia arranca hoy y vence en un mes
    private void agregarSocio() {

        Socio nuevo = new Socio();
        FormularioUsuario.cargarDatosBasicos(nuevo);
        FormularioUsuario.pedirPesoYAltura(nuevo);
        nuevo.setFechaAlta(LocalDate.now());

        Plan plan = FormularioUsuario.leerPlan();
        nuevo.setMembresia(new Membresia(LocalDate.now(), LocalDate.now().plusMonths(1), plan));

        nuevo.setSucursalAsignada(FormularioUsuario.leerSucursal());

        Consola.resultado(socioService.agregar(nuevo),
                "Socio agregado con ID " + nuevo.getId() + ". Primer mes abonado: $" + plan.getPrecio(),
                "No se pudo agregar (¿DNI repetido o sucursal inexistente?).");
    }

    // Al darse de baja el socio pierde su entrenador y se le cancelan los turnos reservados
    // (vuelven a quedar disponibles). Se hace desde aca porque SocioService no conoce a los otros services
    private void darDeBajaSocio() {

        int id = Consola.leerEntero("ID del socio: ");
        Socio socio = socioService.buscar(id);

        if (socio == null) {
            System.out.println("No se encontró un socio activo con ese ID.");
            return;
        }

        if (!Consola.confirmar("¿Seguro? El socio va a perder su entrenador y sus turnos reservados")) {
            return;
        }

        if (!socioService.eliminar(id)) {
            System.out.println("No se pudo dar de baja al socio.");
            return;
        }

        entrenadorService.quitarSocioDeTodos(id);
        int cancelados = turnosService.cancelarTurnosDeSocio(socio);

        System.out.println("Socio dado de baja. Turnos cancelados: " + cancelados + ".");
    }

    // SUCURSALES (pendiente: SucursalService) =================================================================================

    private void menuSucursales() {

        int opcion;

        do {
            Consola.titulo("SUCURSALES");
            System.out.println("1. Ver sucursales del gimnasio");
            System.out.println("2. Agregar sucursal");
            System.out.println("3. Modificar dirección");
            System.out.println("4. Dar de baja sucursal");
            System.out.println("5. Reactivar sucursal");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    // aca iria sucursalService.buscarPorGimnasio(admin.getIdGimnasio());
                    break;
                case 2:
                    // aca iria sucursalService.agregar(new Sucursal(calle, altura, ciudad, admin.getIdGimnasio()));
                    break;
                case 3:
                    // aca iria sucursalService.modificarDireccion(id, calle, altura, ciudad);
                    break;
                case 4:
                    // aca iria sucursalService.eliminar(id);
                    break;
                case 5:
                    // aca iria sucursalService.alta(id);
                    break;
            }

            if (opcion != 0) {
                System.out.println("(Pendiente: falta SucursalService)");
            }

        } while (opcion != 0);
    }

    // MAQUINAS (pendiente: MaquinaService) =================================================================================

    private void menuMaquinas() {

        int opcion;

        do {
            Consola.titulo("MÁQUINAS");
            System.out.println("1. Ver máquinas");
            System.out.println("2. Agregar máquina");
            System.out.println("3. Modificar máquina");
            System.out.println("4. Dar de baja máquina");
            System.out.println("5. Reactivar máquina");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    // aca iria maquinaService.verListado();
                    break;
                case 2:
                    // aca iria maquinaService.agregar(new Maquina(nombre, descripcion, pesoLimite, grupoMuscular));
                    break;
                case 3:
                    // aca iria maquinaService.modificar(maquina);
                    break;
                case 4:
                    // aca iria maquinaService.eliminar(id);
                    break;
                case 5:
                    // aca iria maquinaService.alta(id);
                    break;
            }

            if (opcion != 0) {
                System.out.println("(Pendiente: falta MaquinaService)");
            }

        } while (opcion != 0);
    }

    // GASTOS (pendiente: GastoService) =================================================================================

    private void menuGastos() {

        int opcion;

        do {
            Consola.titulo("GASTOS");
            System.out.println("1. Ver gastos");
            System.out.println("2. Registrar gasto");
            System.out.println("3. Total del mes");
            System.out.println("4. Total del mes por tipo");
            System.out.println("5. Total histórico");
            System.out.println("6. Dar de baja gasto");
            System.out.println("0. Volver");

            opcion = Consola.leerOpcion(0, 6);

            switch (opcion) {
                case 1:
                    // aca iria gastoService.buscarPorGimnasio(admin.getIdGimnasio());
                    break;
                case 2:
                    // aca iria gastoService.agregar(new Gasto(detalles, monto, tipo, fecha, admin.getIdGimnasio()));
                    break;
                case 3:
                    // aca iria gastoService.calcularTotalMensual(admin.getIdGimnasio());
                    break;
                case 4:
                    // aca iria gastoService.calcularTotalMensualPorTipo(admin.getIdGimnasio(), tipo);
                    break;
                case 5:
                    // aca iria gastoService.calcularTotalHistorico(admin.getIdGimnasio());
                    break;
                case 6:
                    // aca iria gastoService.eliminar(id);
                    break;
            }

            if (opcion != 0) {
                System.out.println("(Pendiente: falta GastoService)");
            }

        } while (opcion != 0);
    }
}
