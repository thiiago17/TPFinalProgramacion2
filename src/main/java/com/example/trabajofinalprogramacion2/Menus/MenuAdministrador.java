package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.TipoGasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.SucursalMaquina;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.Excepciones.ObjetoNuloException;
import com.example.trabajofinalprogramacion2.Excepciones.ValorNoValidoException;
import com.example.trabajofinalprogramacion2.Service.Gimnasio.GastoService;
import com.example.trabajofinalprogramacion2.Service.Gimnasio.SucursalMaquinaService;
import com.example.trabajofinalprogramacion2.Service.Gimnasio.SucursalService;
import com.example.trabajofinalprogramacion2.Service.TurnosService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.AdministradorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.EntrenadorService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.NutricionistaService;
import com.example.trabajofinalprogramacion2.Service.Usuarios.SocioService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

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
    private final SucursalService sucursalService;
    private final SucursalMaquinaService sucursalMaquinaService;
    private final GastoService gastoService;

    public MenuAdministrador(Administrador admin, AdministradorService administradorService,
                             EntrenadorService entrenadorService, NutricionistaService nutricionistaService,
                             TurnosService turnosService, SocioService socioService, SucursalService sucursalService, SucursalMaquinaService sucursalMaquinaService, GastoService gastoService) {
        this.admin = admin;
        this.administradorService = administradorService;
        this.entrenadorService = entrenadorService;
        this.nutricionistaService = nutricionistaService;
        this.turnosService = turnosService;
        this.socioService = socioService;
        this.sucursalService = sucursalService;
        this.sucursalMaquinaService = sucursalMaquinaService;
        this.gastoService = gastoService;
    }

    public void mostrar() {

        int opcion;

        do {
            Consola.titulo("ADMINISTRADOR - GIMNASIO #" + admin.getIdGimnasio() + " - " + admin.getNombre());
            System.out.println("1 - Entrenadores");
            System.out.println("2 - Nutricionistas");
            System.out.println("3 - Turnos");
            System.out.println("4 - Socios");
            System.out.println("5 - Sucursales");
            System.out.println("6 - Máquinas");
            System.out.println("7 - Gastos");
            System.out.println("8 - Mi perfil");
            System.out.println("0 - Cerrar sesión");

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
            System.out.println("1 - Ver entrenadores");
            System.out.println("2 - Agregar entrenador");
            System.out.println("3 - Modificar especialidad");
            System.out.println("4 - Modificar salario");
            System.out.println("5 - Asignar sucursal");
            System.out.println("6 - Dar de baja (pierde a sus alumnos)");
            System.out.println("7 - Reactivar entrenador");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 7);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(entrenadorService.verListado(), "No hay entrenadores");
                    break;
                case 2:
                    agregarEntrenador();
                    break;
                case 3: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    String especialidad = Consola.leerTexto("Nueva especialidad: ");
                    Consola.resultado(entrenadorService.modificarEspecialidad(id, especialidad),
                            "Especialidad actualizada", "No se encontró un entrenador activo con ese ID");
                    break;
                }
                case 4: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    double salario = Consola.leerDouble("Nuevo salario: ");
                    Consola.resultado(entrenadorService.modificarSalario(id, salario),
                            "Salario actualizado", "No se pudo actualizar (ID incorrecto - salario negativo)");
                    break;
                }
                case 5: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    Consola.resultado(entrenadorService.modificarSucursal(id, FormularioUsuario.leerSucursal()),
                            "Sucursal asignada", "No se pudo asignar");
                    break;
                }
                case 6: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    if (Consola.confirmar("¿Seguro? El entrenador va a perder a todos sus alumnos")) {
                        Consola.resultado(entrenadorService.eliminar(id),
                                "Entrenador dado de baja", "No se encontró un entrenador activo con ese ID");
                    }
                    break;
                }
                case 7: {
                    int id = Consola.leerEntero("ID del entrenador: ");
                    Consola.resultado(entrenadorService.alta(id),
                            "Entrenador reactivado", "No se encontró un entrenador dado de baja con ese ID");
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
                "Entrenador agregado con ID " + nuevo.getId(),
                "No se pudo agregar (DNI repetido - sucursal inexistente)");
    }

    // NUTRICIONISTAS =================================================================================

    private void menuNutricionistas() {

        int opcion;

        do {
            Consola.titulo("NUTRICIONISTAS");
            System.out.println("1 - Ver nutricionistas");
            System.out.println("2 - Agregar nutricionista");
            System.out.println("3 - Modificar salario");
            System.out.println("4 - Modificar comisión por consulta");
            System.out.println("5 - Asignar sucursal");
            System.out.println("6 - Dar de baja");
            System.out.println("7 - Reactivar nutricionista");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 7);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(nutricionistaService.verListado(), "No hay nutricionistas");
                    break;
                case 2:
                    agregarNutricionista();
                    break;
                case 3: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    double salario = Consola.leerDouble("Nuevo salario: ");
                    Consola.resultado(nutricionistaService.modificarSalario(id, salario),
                            "Salario actualizado", "No se pudo actualizar (ID correcto - salario negativo)");
                    break;
                }
                case 4: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    double comision = Consola.leerDouble("Nueva comisión por consulta: ");
                    Consola.resultado(nutricionistaService.modificarComisionPorConsulta(id, comision),
                            "Comisión actualizada", "No se pudo actualizar (ID correcto - comisión negativa)");
                    break;
                }
                case 5: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    Consola.resultado(nutricionistaService.modificarSucursal(id, FormularioUsuario.leerSucursal()),
                            "Sucursal asignada", "No se pudo asignar");
                    break;
                }
                case 6: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    if (Consola.confirmar("¿Seguro que desea darlo de baja?")) {
                        Consola.resultado(nutricionistaService.eliminar(id),
                                "Nutricionista dado de baja", "No se encontró un nutricionista activo con ese ID");
                    }
                    break;
                }
                case 7: {
                    int id = Consola.leerEntero("ID del nutricionista: ");
                    Consola.resultado(nutricionistaService.alta(id),
                            "Nutricionista reactivado", "No se encontró un nutricionista dado de baja con ese ID");
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
                "Nutricionista agregado con ID " + nuevo.getId(),
                "No se pudo agregar (DNI repetido - sucursal inexistente)");
    }

    // TURNOS =================================================================================

    private void menuTurnos() {

        int opcion;

        do {
            Consola.titulo("TURNOS");
            System.out.println("1 - Ver todos");
            System.out.println("2 - Ver disponibles");
            System.out.println("3 - Ver reservados");
            System.out.println("4 - Crear turno");
            System.out.println("5 - Modificar fecha y hora");
            System.out.println("6 - Modificar precio");
            System.out.println("7 - Dar de baja turno (solo disponibles)");
            System.out.println("8 - Ver turnos dados de baja");
            System.out.println("9 - Reactivar turno");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 9);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(turnosService.verListado(), "No hay turnos");
                    break;
                case 2:
                    Consola.mostrarLista(turnosService.verDisponibles(), "No hay turnos disponibles");
                    break;
                case 3:
                    Consola.mostrarLista(turnosService.verReservados(), "No hay turnos reservados");
                    break;
                case 4:
                    crearTurno();
                    break;
                case 5: {
                    int id = Consola.leerEntero("ID del turno: ");
                    LocalDateTime fecha = Consola.leerFechaHora("Nueva fecha y hora");
                    Consola.resultado(turnosService.modificarFecha(id, fecha),
                            "Fecha actualizada.",
                            "No se pudo (horario de 10:00 a 18:30 cada 30 min, sin pisarse con otro turno)");
                    break;
                }
                case 6: {
                    int id = Consola.leerEntero("ID del turno: ");
                    double precio = Consola.leerDouble("Nuevo precio: ");
                    Consola.resultado(turnosService.modificarPrecio(id, precio),
                            "Precio actualizado", "No se pudo actualizar (ID incorrecto - precio negativo)");
                    break;
                }
                case 7: {
                    int id = Consola.leerEntero("ID del turno: ");
                    Consola.resultado(turnosService.eliminar(id),
                            "Turno dado de baja", "No se pudo: el turno no existe o está reservado");
                    break;
                }
                case 8:
                    Consola.mostrarLista(turnosService.verDadosDeBaja(), "No hay turnos dados de baja");
                    break;
                case 9: {
                    int id = Consola.leerEntero("ID del turno: ");
                    Consola.resultado(turnosService.alta(id),
                            "Turno reactivado",
                            "No se pudo: no está dado de baja o ya hay otro turno en ese horario");
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
            System.out.println("No se encontró un nutricionista activo con ese ID");
            return;
        }

        LocalDateTime fecha = Consola.leerFechaHora("Fecha y hora del turno");
        double precio = Consola.leerDouble("Precio: ");

        Turno turno = new Turno(null, nutricionista, fecha, precio);

        Consola.resultado(turnosService.agregar(turno),
                "Turno creado con ID " + turno.getId(),
                "No se pudo crear (horario de 10:00 a 18:30 cada 30 min, sin pisarse con otro turno, precio >= 0)");
    }

    // SOCIOS =================================================================================

    private void menuSocios() {

        int opcion;

        do {
            Consola.titulo("SOCIOS");
            System.out.println("1 - Ver socios");
            System.out.println("2 - Agregar socio");
            System.out.println("3 - Cambiar plan");
            System.out.println("4 - Asignar sucursal");
            System.out.println("5 - Registrar pago de cuota");
            System.out.println("6 - Asignar entrenador");
            System.out.println("7 - Dar de baja (pierde entrenador y turnos reservados)");
            System.out.println("8 - Reactivar socio");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 8);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(socioService.verListado(), "No hay socios");
                    break;
                case 2:
                    agregarSocio();
                    break;
                case 3: {
                    int id = Consola.leerEntero("ID del socio: ");
                    Plan plan = FormularioUsuario.leerPlan();
                    Consola.resultado(socioService.cambiarPlanMembresia(id, plan),
                            "Plan actualizado", "No se encontró un socio activo con ese ID");
                    break;
                }
                case 4: {
                    int id = Consola.leerEntero("ID del socio: ");
                    Consola.resultado(socioService.modificarSucursal(id, FormularioUsuario.leerSucursal()),
                            "Sucursal asignada", "No se pudo asignar");
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
                            "Entrenador asignado",
                            "No se pudo: el socio o el entrenador no existen / están de baja, o ya estaban vinculados");
                    break;
                }
                case 7:
                    darDeBajaSocio();
                    break;
                case 8: {
                    int id = Consola.leerEntero("ID del socio: ");
                    Consola.resultado(socioService.alta(id),
                            "Socio reactivado", "No se encontró un socio con ese ID");
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
                "No se pudo agregar (DNI repetido - sucursal inexistente)");
    }

    // Al darse de baja el socio pierde su entrenador y se le cancelan los turnos reservados
    // (vuelven a quedar disponibles). Se hace desde aca porque SocioService no conoce a los otros services
    private void darDeBajaSocio() {

        int id = Consola.leerEntero("ID del socio: ");
        Socio socio = socioService.buscar(id);

        if (socio == null) {
            System.out.println("No se encontró un socio activo con ese ID");
            return;
        }

        if (!Consola.confirmar("¿Seguro? El socio va a perder su entrenador y sus turnos reservados")) {
            return;
        }

        if (!socioService.eliminar(id)) {
            System.out.println("No se pudo dar de baja al socio");
            return;
        }

        entrenadorService.quitarSocioDeTodos(id);
        int cancelados = turnosService.cancelarTurnosDeSocio(socio);

        System.out.println("Socio dado de baja - Turnos cancelados: " + cancelados);
    }

    // SUCURSALES =================================================================================

    private void menuSucursales() {

        int opcion;

        do {
            Consola.titulo("SUCURSALES");
            System.out.println("1 - Ver sucursales del gimnasio");
            System.out.println("2 - Buscar sucursales");
            System.out.println("3 - Agregar sucursal");
            System.out.println("4 - Modificar sucursales");
            System.out.println("5 - Dar de baja sucursal");
            System.out.println("6 - Reactivar sucursal");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    Consola.mostrarLista(sucursalService.buscarPorGimnasio(admin.getIdGimnasio()),"No hay sucursales");
                    break;
                case 2:
                    menuBusquedaSucursales();
                case 3:
                    String calle=Consola.leerTexto("Calle: ");
                    int altura=Consola.leerEntero("Altura: ");
                    String ciudad=Consola.leerTexto("Ciudad: ");

                    try{
                        Sucursal sucursal= new Sucursal(calle, altura, ciudad, admin.getIdGimnasio());
                        sucursal.setEstado(1);

                       Consola.resultado(sucursalService.agregar(sucursal),"Agregado correctamente","No se pudo agregar");
                    }catch(SQLException | ObjetoNuloException e){
                        System.err.println("Error al intentar agregar: " + e.getMessage());
                    }
                    break;
                case 4:
                    menuModificacionSucursales();
                    break;
                case 5:
                    int idBaja = Consola.leerEntero("ID de la sucursal a modificar: ");

                    try {
                        Sucursal sucursalABajar = sucursalService.buscarPorId(idBaja);

                        if (sucursalABajar != null) {
                            Consola.resultado(sucursalService.baja(sucursalABajar), "Sucursal dada de baja correctamente", "No se pudo dar de baja");
                        } else {
                            System.out.println("No existe ninguna sucursal con ese ID");
                        }

                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error al intentar dar de baja: " + e.getMessage());
                    }
                    break;
                case 6:
                    int idAlta = Consola.leerEntero("ID de la sucursal a dar de alta: ");

                    try {
                        Sucursal sucursalAAlta = sucursalService.buscarPorId(idAlta);

                        if (sucursalAAlta != null) {
                            Consola.resultado(sucursalService.alta(sucursalAAlta), "Sucursal dada de alta correctamente", "No se pudo dar de alta");
                        } else {
                            System.out.println("No existe ninguna sucursal con ese ID");
                        }

                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error al intentar dar de alta: " + e.getMessage());
                    }
                    break;
            }


        } while (opcion != 0);
    }

    private void menuBusquedaSucursales(){
        int opcion;

        do {
            Consola.titulo("BUSQUEDA DE SUCURSALES");
            System.out.println("1 - Buscar por direccion");
            System.out.println("2 - Buscar por ciudad");
            System.out.println("3 - Buscar por activos");
            System.out.println("4 - Buscar por inactivos");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    int idTodo = Consola.leerEntero("ID de la sucursal a modificar: ");
                    try {
                        Sucursal sucursalExistente = sucursalService.buscarPorId(idTodo);

                        if (sucursalExistente != null) {
                            if (sucursalExistente.getIdGimnasio() == admin.getIdGimnasio()) {
                                String calleModificada = Consola.leerTexto("Nueva calle: ");
                                int alturaModificada = Consola.leerEntero("Nueva altura: ");
                                String ciudadModificada = Consola.leerTexto("Nueva ciudad: ");

                                sucursalExistente.setCalle(calleModificada);
                                sucursalExistente.setAltura(alturaModificada);
                                sucursalExistente.setCiudad(ciudadModificada);

                                Consola.resultado(sucursalService.modificar(sucursalExistente), "Sucursal modificada completamente", "No se pudo modificar la sucursal");
                            } else {
                                System.out.println("Acceso denegado: No puede modificar esta sucursal.");
                            }
                        } else {
                            System.out.println("No existe ninguna sucursal con ese ID.");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    int idDir = Consola.leerEntero("ID de la sucursal a modificar: ");
                    String nuevaCalle = Consola.leerTexto("Nueva calle: ");
                    int nuevaAltura = Consola.leerEntero("Nueva altura: ");
                    try {
                        boolean okCalle = sucursalService.modificarCalle(idDir, nuevaCalle, admin.getIdGimnasio());
                        boolean okAltura = sucursalService.modificarAltura(idDir, nuevaAltura, admin.getIdGimnasio());

                        Consola.resultado(okCalle, "Calle modificada correctamente", "No se pudo modificar la calle");
                        Consola.resultado(okAltura,"Altura modificada correctamente", "No se pudo modificar la altura");
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    int idCiu = Consola.leerEntero("ID de la sucursal a modificar: ");
                    String nuevaCiudad = Consola.leerTexto("Nueva ciudad: ");
                    try {
                        Consola.resultado(sucursalService.modificarCiudad(idCiu, nuevaCiudad, admin.getIdGimnasio()), "Ciudad modificada correctamente", "No se pudo modificar la ciudad");
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }


        } while (opcion != 0);
    }

    private void menuModificacionSucursales(){
        int opcion;

        do {
            Consola.titulo("MODIFICACION DE SUCURSALES");
            System.out.println("1 - Modificar todo");
            System.out.println("2 - Modificar direccion");
            System.out.println("3 - Modificar ciudad");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    String calle = Consola.leerTexto("Ingrese la calle a buscar: ");
                    int altura = Consola.leerEntero("Ingrese la altura: ");
                    try {
                        Sucursal sucursalEncontrada = sucursalService.buscarPorGimnasioYDireccion(admin.getIdGimnasio(), calle, altura);

                        if (sucursalEncontrada != null) {
                            System.out.println("--- SUCURSAL ENCONTRADA ---");
                            System.out.println(sucursalEncontrada.toString());
                        } else {
                            System.out.println("No se encontró ninguna sucursal en " + calle + " " + altura + " para tu gimnasio.");
                        }
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    String ciudad = Consola.leerTexto("Ingrese la ciudad a buscar: ");
                    try {
                        System.out.println("--- SUCURSALES ENCONTRADAS ---");
                        Consola.mostrarLista(sucursalService.buscarPorGimnasioYCiudad(admin.getIdGimnasio(), ciudad), "No se encontraron sucursales en esa ciudad para tu gimnasio.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("--- SUCURSALES ACTIVAS ---");
                    try {
                        Consola.mostrarLista(sucursalService.buscarActivosPorGimnasio(admin.getIdGimnasio()), "No tenés sucursales activas en este gimnasio.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("--- SUCURSALES INACTIVAS ---");
                    try {
                        Consola.mostrarLista(sucursalService.buscarInactivosPorGimnasio(admin.getIdGimnasio()), "No tenés sucursales inactivas en este gimnasio.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }


        } while (opcion != 0);
    }

    // MAQUINAS =================================================================================

    private void menuMaquinas() {

        int opcion;

        do {
            Consola.titulo("MÁQUINAS");
            System.out.println("1 - Ver máquinas");
            System.out.println("2 - Agregar máquina");
            System.out.println("3 - Modificar cantidad");
            System.out.println("4 - Dar de baja máquina");
            System.out.println("5 - Reactivar máquina");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    int idSucursalVer = Consola.leerEntero("Ingrese el ID de su Sucursal: ");
                    try {
                        Sucursal sucursalVer = sucursalService.buscarPorId(idSucursalVer);

                        if (sucursalVer != null && sucursalVer.getIdGimnasio() == admin.getIdGimnasio()) { //que coincida con el gimnasio del admin
                            Consola.mostrarLista(sucursalMaquinaService.buscarActivosPorIdSucursal(idSucursalVer), "No hay máquinas activas en esta sucursal");
                        } else {
                            System.out.println("Esa sucursal no existe o no pertenece a tu gimnasio");
                        }
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    int idSucursalAgregar = Consola.leerEntero("Ingrese el ID de su Sucursal: ");
                    try {
                        Sucursal sucursalAgregar = sucursalService.buscarPorId(idSucursalAgregar);

                        if (sucursalAgregar != null && sucursalAgregar.getIdGimnasio() == admin.getIdGimnasio()) {
                            int idMaquina = Consola.leerEntero("ID de la Máquina a agregar: ");
                            int cantidad = Consola.leerEntero("Cantidad: ");

                            SucursalMaquina sucursalMaquina = new SucursalMaquina();
                            sucursalMaquina.setIdSucursal(idSucursalAgregar);
                            sucursalMaquina.setIdMaquina(idMaquina);
                            sucursalMaquina.setCantidad(cantidad);
                            sucursalMaquina.setEstado(1);

                            Consola.resultado(sucursalMaquinaService.agregar(sucursalMaquina), "Máquina agregada a la sucursal correctamente", "No se pudo agregar");
                        } else {
                            System.out.println("Esa sucursal no existe o no pertenece a tu gimnasio");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    int idRelacionModificar = Consola.leerEntero("ID del registro a modificar: ");
                    try {
                        SucursalMaquina relacionBuscada = sucursalMaquinaService.buscarPorId(idRelacionModificar);

                        if (relacionBuscada != null) { //si existe el registro
                            Sucursal sucMod = sucursalService.buscarPorId(relacionBuscada.getIdSucursal()); //toma el id de la sucursal y la busca

                            if (sucMod != null && sucMod.getIdGimnasio() == admin.getIdGimnasio()) { //verifica que la sucursal pertenezca al gimnasio del admin
                                int nuevaCantidad = Consola.leerEntero("Nueva cantidad: ");
                                Consola.resultado(sucursalMaquinaService.modificarCantidad(idRelacionModificar, nuevaCantidad), "Cantidad modificada correctamente", "No se pudo modificar");
                            } else {
                                System.out.println("No tenés permisos para modificar registros de otro gimnasio");
                            }
                        } else {
                            System.out.println("No existe ese registro");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    int idRelacionBaja = Consola.leerEntero("ID del registro a dar de baja: ");
                    try {
                        SucursalMaquina relacionABajar = sucursalMaquinaService.buscarPorId(idRelacionBaja);

                        if (relacionABajar != null) {
                            Sucursal sucursalBaja = sucursalService.buscarPorId(relacionABajar.getIdSucursal());

                            if (sucursalBaja != null && sucursalBaja.getIdGimnasio() == admin.getIdGimnasio()) {
                                Consola.resultado(sucursalMaquinaService.baja(relacionABajar), "Máquina dada de baja en esta sucursal", "No se pudo dar de baja");
                            } else {
                                System.out.println("No tenés permisos para dar de baja registros de otro gimnasio");
                            }
                        } else {
                            System.out.println("No existe ese registro");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    int idRelacionAlta = Consola.leerEntero("ID del registro a reactivar: ");
                    try {
                        SucursalMaquina relacionAAlta = sucursalMaquinaService.buscarPorId(idRelacionAlta);

                        if (relacionAAlta != null) {
                            Sucursal sucursalAlta = sucursalService.buscarPorId(relacionAAlta.getIdSucursal());

                            if (sucursalAlta != null && sucursalAlta.getIdGimnasio() == admin.getIdGimnasio()) {
                                Consola.resultado(sucursalMaquinaService.alta(relacionAAlta), "Máquina reactivada correctamente", "No se pudo reactivar");
                            } else {
                                System.out.println("No tenés permisos para reactivar registros de otro gimnasio");
                            }
                        } else {
                            System.out.println("No existe ese registro");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }

        } while (opcion != 0);
    }

    // GASTOS =================================================================================

    private void menuGastos() {

        int opcion;

        do {
            Consola.titulo("GASTOS");
            System.out.println("1 - Ver gastos");
            System.out.println("2 - Registrar gasto");
            System.out.println("3 - Total del mes");
            System.out.println("4 - Total del mes por tipo");
            System.out.println("5 - Filtrar gastos");
            System.out.println("6 - Modificar gastos");
            System.out.println("7 - Dar de baja gasto");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 6);

            switch (opcion) {
                case 1:
                    menuVerGastos();
                    break;
                case 2:
                    try {
                        String detalles = Consola.leerTexto("Detalles del gasto: ");
                        double monto = Consola.leerDouble("Monto: $");

                        System.out.println("--- Tipo de Gasto ---");
                        System.out.println("1 - Luz");
                        System.out.println("2 - Agua");
                        System.out.println("3 - Gas");
                        System.out.println("4 - Mantenimiento");
                        System.out.println("5 - Internet");
                        System.out.println("6 - Empleados");
                        System.out.println("7 - Alquiler");
                        System.out.println("8 - Otros");
                        int opcionTipo = Consola.leerEntero("Seleccione una opción: ");

                        TipoGasto tipo = null;

                        switch (opcionTipo) {
                            case 1: tipo = TipoGasto.LUZ; break;
                            case 2: tipo = TipoGasto.AGUA; break;
                            case 3: tipo = TipoGasto.GAS; break;
                            case 4: tipo = TipoGasto.MANTENIMIENTO; break;
                            case 5: tipo = TipoGasto.INTERNET; break;
                            case 6: tipo = TipoGasto.EMPLEADOS; break;
                            case 7: tipo = TipoGasto.ALQUILER; break;
                            case 8: tipo = TipoGasto.OTROS; break;
                            default:
                                System.out.println("Opción de tipo de gasto inválida -> Operación cancelada");
                                break;
                        }

                        // Avanza solo si el usuario eligio una opcion correcta
                        if (tipo != null) {
                            Gasto nuevoGasto = new Gasto();
                            nuevoGasto.setDetalles(detalles);
                            nuevoGasto.setMonto(monto);
                            nuevoGasto.setTipoGasto(tipo);
                            nuevoGasto.setFecha(LocalDate.now());
                            nuevoGasto.setIdGimnasio(admin.getIdGimnasio());
                            nuevoGasto.setEstado(1);

                            Consola.resultado(gastoService.agregar(nuevoGasto), "Gasto registrado correctamente", "No se pudo registrar");
                        }

                    } catch (SQLException | ObjetoNuloException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    menuCalcularTotales();
                    break;
                case 4:
                    menuCalcularTotalesPorTipo();
                    break;
                case 5:
                    menuBusquedaGastos();
                    break;
                case 6:
                    //aca va otro menu que llame a los metodos de modificacion
                    break;
                case 7:
                    int idBaja = Consola.leerEntero("ID del gasto a dar de baja: ");
                    try {
                        Gasto gastoABajar = gastoService.buscarPorId(idBaja);

                        if (gastoABajar != null) {
                            if (gastoABajar.getIdGimnasio() == admin.getIdGimnasio()) {
                                Consola.resultado(gastoService.baja(gastoABajar), "Gasto dado de baja correctamente", "No se pudo dar de baja");
                            } else {
                                System.out.println("Acceso denegado: Este gasto no pertenece a tu gimnasio");
                            }
                        } else {
                            System.out.println("No existe ningún gasto con ese ID");
                        }
                    } catch (SQLException | ObjetoNuloException | ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }

        } while (opcion != 0);
    }

    private void menuVerGastos(){
        int opcion;

        do {
            Consola.titulo("VER GASTOS");
            System.out.println("1 - Ver gastos historicos");
            System.out.println("2 - Ver gastos anuales");
            System.out.println("3 - Ver gastos mensuales");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 6);

            switch (opcion) {
                case 1:
                    menuVerGastosHistoricos();
                    break;
                case 2:
                    menuVerGastosAnuales();
                    break;
                case 3:
                    menuVerGastosMensuales();
                    break;
            }

        } while (opcion != 0);
    }

    private void menuVerGastosHistoricos(){
        int opcion;

        do {
            Consola.titulo("VER GASTOS HISTORICOS");
            System.out.println("1 - Ver por monto ascendente");
            System.out.println("2 - Ver por monto descendente");
            System.out.println("3 - Ver por fecha ascendente");
            System.out.println("4 - Ver por fecha descendente");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    try {
                        Consola.mostrarLista(gastoService.verListadoHistoricoOrdenadoPorMontoAscendente(admin.getIdGimnasio()), "No hay gastos registrados para ordenar.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        Consola.mostrarLista(gastoService.verListadoHistoricoOrdenadoPorMontoDescendente(admin.getIdGimnasio()), "No hay gastos registrados para ordenar.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        Consola.mostrarLista(gastoService.verListadoHistoricoOrdenadoPorFechaAscendente(admin.getIdGimnasio()), "No hay gastos registrados para ordenar.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        Consola.mostrarLista(gastoService.verListadoHistoricoOrdenadoPorFechaDescendente(admin.getIdGimnasio()), "No hay gastos registrados para ordenar.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }
        } while (opcion != 0);
    }

    private void menuVerGastosAnuales(){
        int opcion;

        do {
            Consola.titulo("VER GASTOS ANUALES");
            System.out.println("1 - Ver por monto ascendente");
            System.out.println("2 - Ver por monto descendente");
            System.out.println("3 - Ver por fecha ascendente");
            System.out.println("4 - Ver por fecha descendente");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    int anioMontoAsc = Consola.leerEntero("Ingrese el año a consultar: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoAnualOrdenadoPorMontoAscendente(admin.getIdGimnasio(), anioMontoAsc), "No hay gastos en ese año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    int anioMontoDesc = Consola.leerEntero("Ingrese el año a consultar: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoAnualOrdenadoPorMontoDescendente(admin.getIdGimnasio(), anioMontoDesc), "No hay gastos en ese año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    int anioFechaAsc = Consola.leerEntero("Ingrese el año a consultar: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoAnualOrdenadoPorFechaAscendente(admin.getIdGimnasio(), anioFechaAsc), "No hay gastos en ese año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    int anioFechaDesc = Consola.leerEntero("Ingrese el año a consultar: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoAnualOrdenadoPorFechaDescendente(admin.getIdGimnasio(), anioFechaDesc), "No hay gastos en ese año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }

        } while (opcion != 0);
    }

    private void menuVerGastosMensuales(){
        int opcion;

        do {
            Consola.titulo("VER GASTOS MENSUALES");
            System.out.println("1 - Ver por monto ascendente");
            System.out.println("2 - Ver por monto descendente");
            System.out.println("3 - Ver por fecha ascendente");
            System.out.println("4 - Ver por fecha descendente");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    int mesMontoAsc = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioMontoAsc = Consola.leerEntero("Ingrese el año: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoMensualOrdenadoPorMontoAscendente(admin.getIdGimnasio(), mesMontoAsc, anioMontoAsc), "No hay gastos en ese mes/año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    int mesMontoDesc = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioMontoDesc = Consola.leerEntero("Ingrese el año: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoMensualOrdenadoPorMontoDescendente(admin.getIdGimnasio(), mesMontoDesc, anioMontoDesc), "No hay gastos en ese mes/año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    int mesFechaAsc = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioFechaAsc = Consola.leerEntero("Ingrese el año: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoMensualOrdenadoPorFechaAscendente(admin.getIdGimnasio(), mesFechaAsc, anioFechaAsc), "No hay gastos en ese mes/año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    int mesFechaDesc = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioFechaDesc = Consola.leerEntero("Ingrese el año: ");
                    try {
                        Consola.mostrarLista(gastoService.verListadoMensualOrdenadoPorFechaDescendente(admin.getIdGimnasio(), mesFechaDesc, anioFechaDesc), "No hay gastos en ese mes/año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }
        } while (opcion != 0);
    }

    private void menuCalcularTotales(){
        int opcion;

        do {
            Consola.titulo("CALCULAR TOTAL");
            System.out.println("1 - Total historico");
            System.out.println("2 - Total anual");
            System.out.println("3 - Total mensual");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 3);

            switch (opcion) {
                case 1: // Total histórico
                    try {
                        double totalHistorico = gastoService.calcularTotalHistorico(admin.getIdGimnasio());
                        System.out.println("Total histórico de gastos: $" + totalHistorico);
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 2: // Total anual
                    int anioTotal = Consola.leerEntero("Ingrese el año a calcular: ");
                    try {
                        double totalAnual = gastoService.calcularTotalAnual(admin.getIdGimnasio(), anioTotal);
                        System.out.println("Total de gastos en el año " + anioTotal + ": $" + totalAnual);
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 3: // Total mensual
                    int mesTotal = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioMesTotal = Consola.leerEntero("Ingrese el año: ");
                    try {
                        double totalMensual = gastoService.calcularTotalMensual(admin.getIdGimnasio(), mesTotal, anioMesTotal);
                        System.out.println("Total de gastos en " + mesTotal + "/" + anioMesTotal + ": $" + totalMensual);
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }
        } while (opcion != 0);
    }

    private void menuCalcularTotalesPorTipo(){
        int opcion;

        do {
            Consola.titulo("CALCULAR TOTAL POR TIPO");
            System.out.println("1 - Total historico");
            System.out.println("2 - Total anual");
            System.out.println("3 - Total mensual");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 3);

            switch (opcion) {
                case 1: // Total histórico por tipo
                    TipoGasto tipoHistorico = seleccionarTipoGasto();
                    if (tipoHistorico != null) {
                        try {
                            double total = gastoService.calcularTotalHistoricoPorTipo(admin.getIdGimnasio(), tipoHistorico);
                            System.out.println("Total histórico en " + tipoHistorico + ": $" + total);
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 2: // Total anual por tipo
                    TipoGasto tipoAnual = seleccionarTipoGasto();
                    if (tipoAnual != null) {
                        int anio = Consola.leerEntero("Ingrese el año a calcular: ");
                        try {
                            double total = gastoService.calcularTotalAnualPorTipo(admin.getIdGimnasio(), anio, tipoAnual);
                            System.out.println("Total gastado en " + tipoAnual + " durante el año " + anio + ": $" + total);
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 3: // Total mensual por tipo
                    TipoGasto tipoMensual = seleccionarTipoGasto();
                    if (tipoMensual != null) {
                        int mes = Consola.leerEntero("Ingrese el mes (1-12): ");
                        int anioMensual = Consola.leerEntero("Ingrese el año: ");
                        try {
                            double total = gastoService.calcularTotalMensualPorTipo(admin.getIdGimnasio(), mes, anioMensual, tipoMensual);
                            System.out.println("Total gastado en " + tipoMensual + " en " + mes + "/" + anioMensual + ": $" + total);
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    break;
            }
        } while (opcion != 0);
    }

    private TipoGasto seleccionarTipoGasto() {
        System.out.println("--- Tipo de Gasto ---");
        System.out.println("1-Luz | 2-Agua | 3-Gas | 4-Mantenimiento | 5-Internet | 6-Empleados | 7-Alquiler | 8-Otros");
        int opcionTipo = Consola.leerEntero("Seleccione una opción: ");

        switch(opcionTipo) {
            case 1: return TipoGasto.LUZ;
            case 2: return TipoGasto.AGUA;
            case 3: return TipoGasto.GAS;
            case 4: return TipoGasto.MANTENIMIENTO;
            case 5: return TipoGasto.INTERNET;
            case 6: return TipoGasto.EMPLEADOS;
            case 7: return TipoGasto.ALQUILER;
            case 8: return TipoGasto.OTROS;
            default:
                System.out.println("Opción inválida. Operación cancelada.");
                return null;
        }
    }

    private void menuBusquedaGastos(){
        int opcion;

        do {
            Consola.titulo("BUSQUEDA DE GASTOS");
            System.out.println("1 - Gastos historicos");
            System.out.println("2 - Gastos anuales");
            System.out.println("3 - Gastos mensuales");
            System.out.println("4 - Gastos inactivos");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 3);

            switch (opcion) {
                case 1:
                    menuBusquedaGastosHistoricos();
                    break;
                case 2:
                    menuBusquedaGastosAnuales();
                    break;

                case 3:
                    menuBusquedaGastosMensuales();
                    break;

                case 4:
                    try {
                        Consola.mostrarLista(gastoService.buscarPorInactivos(admin.getIdGimnasio()), "No hay gastos inactivos registrados en tu gimnasio.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }
        } while (opcion != 0);
    }

    private void menuBusquedaGastosHistoricos(){
        int opcion;

        do {
            Consola.titulo("BUSQUEDA DE GASTOS HISTORICOS");
            System.out.println("1 - Busqueda general");
            System.out.println("2 - Busqueda por tipo");
            System.out.println("3 - Busqueda por monto menor a");
            System.out.println("4 - Busqueda por monto mayor a");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    try {
                        Consola.mostrarLista(gastoService.buscarGastosHistoricos(admin.getIdGimnasio()), "No se encontraron gastos históricos.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    TipoGasto tipoBusqueda = seleccionarTipoGasto();
                    if (tipoBusqueda != null) {
                        try {
                            Consola.mostrarLista(gastoService.buscarTipoDeGastoHistorico(admin.getIdGimnasio(), tipoBusqueda), "No se encontraron gastos históricos de ese tipo.");
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    double montoMenor = Consola.leerDouble("Buscar gastos con monto menor a: $");
                    try {
                        Consola.mostrarLista(gastoService.buscarHistoricoPorMontoMenorA(admin.getIdGimnasio(), montoMenor), "No se encontraron gastos menores a ese monto.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    double montoMayor = Consola.leerDouble("Buscar gastos con monto mayor a: $");
                    try {
                        Consola.mostrarLista(gastoService.buscarHistoricoPorMontoMayorA(admin.getIdGimnasio(), montoMayor), "No se encontraron gastos mayores a ese monto.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }
        } while (opcion != 0);
    }

    private void menuBusquedaGastosAnuales(){
        int opcion;

        do {
            Consola.titulo("BUSQUEDA DE GASTOS ANUALES");
            System.out.println("1 - Busqueda general");
            System.out.println("2 - Busqueda por tipo");
            System.out.println("3 - Busqueda por monto menor a");
            System.out.println("4 - Busqueda por monto mayor a");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    int anioGeneral = Consola.leerEntero("Ingrese el año a buscar: ");
                    try {
                        Consola.mostrarLista(gastoService.buscarGastosAnuales(admin.getIdGimnasio(), anioGeneral), "No se encontraron gastos en ese año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    TipoGasto tipoAnual = seleccionarTipoGasto();
                    if (tipoAnual != null) {
                        int anioTipo = Consola.leerEntero("Ingrese el año a buscar: ");
                        try {
                            Consola.mostrarLista(gastoService.buscarTipoDeGastoAnual(admin.getIdGimnasio(), tipoAnual, anioTipo), "No se encontraron gastos de ese tipo en ese año.");
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    double montoMenorAnual = Consola.leerDouble("Buscar gastos con monto menor a: $");
                    int anioMenor = Consola.leerEntero("Ingrese el año a buscar: ");
                    try {
                        Consola.mostrarLista(gastoService.buscarAnualPorMontoMenorA(admin.getIdGimnasio(), montoMenorAnual, anioMenor), "No se encontraron gastos menores a ese monto en ese año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    double montoMayorAnual = Consola.leerDouble("Buscar gastos con monto mayor a: $");
                    int anioMayor = Consola.leerEntero("Ingrese el año a buscar: ");
                    try {
                        Consola.mostrarLista(gastoService.buscarAnualPorMontoMayorA(admin.getIdGimnasio(), montoMayorAnual, anioMayor), "No se encontraron gastos mayores a ese monto en ese año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }
        } while (opcion != 0);
    }

    private void menuBusquedaGastosMensuales(){
        int opcion;

        do {
            Consola.titulo("BUSQUEDA DE GASTOS MENSUALES");
            System.out.println("1 - Busqueda general");
            System.out.println("2 - Busqueda por tipo");
            System.out.println("3 - Busqueda por monto menor a");
            System.out.println("4 - Busqueda por monto mayor a");
            System.out.println("0 - Volver");

            opcion = Consola.leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    int mesGeneral = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioGeneral = Consola.leerEntero("Ingrese el año: ");
                    try {
                        Consola.mostrarLista(gastoService.buscarGastosMensuales(admin.getIdGimnasio(), mesGeneral, anioGeneral), "No se encontraron gastos en ese mes/año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    TipoGasto tipoMensual = seleccionarTipoGasto();
                    if (tipoMensual != null) {
                        int mesTipo = Consola.leerEntero("Ingrese el mes (1-12): ");
                        int anioTipo = Consola.leerEntero("Ingrese el año: ");
                        try {
                            Consola.mostrarLista(gastoService.buscarTipoDeGastoMensual(admin.getIdGimnasio(), tipoMensual, mesTipo, anioTipo), "No se encontraron gastos de ese tipo en ese mes/año.");
                        } catch (ValorNoValidoException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    double montoMenorMensual = Consola.leerDouble("Buscar gastos con monto menor a: $");
                    int mesMenor = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioMenor = Consola.leerEntero("Ingrese el año: ");
                    try {
                        Consola.mostrarLista(gastoService.buscarMensualPorMontoMenorA(admin.getIdGimnasio(), montoMenorMensual, mesMenor, anioMenor), "No se encontraron gastos menores a ese monto en ese mes/año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    double montoMayorMensual = Consola.leerDouble("Buscar gastos con monto mayor a: $");
                    int mesMayor = Consola.leerEntero("Ingrese el mes (1-12): ");
                    int anioMayor = Consola.leerEntero("Ingrese el año: ");
                    try {
                        Consola.mostrarLista(gastoService.buscarMensualPorMontoMayorA(admin.getIdGimnasio(), montoMayorMensual, mesMayor, anioMayor), "No se encontraron gastos mayores a ese monto en ese mes/año.");
                    } catch (ValorNoValidoException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
            }
        } while (opcion != 0);
    }
}
