package com.example.trabajofinalprogramacion2.Menus;

import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Administrador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Usuarios;

import java.util.List;

// Arma los textos para mostrar entidades en los menus (una linea por objeto)

public final class Formato {

    private Formato() {
    }

    public static String usuario(Usuarios u) {
        String edad = u.getFechaDeNacimiento() != null ? u.calcularEdad() + " años" : "-";

        return "#" + u.getId() + " | " + u.getNombre() + " " + u.getApellido()
                + " | DNI: " + u.getDni()
                + " | Email: " + u.getEmail()
                + " | Edad: " + edad
                + " | " + u.verEstadoTexto();
    }

    public static String administrador(Administrador a) {
        String tipo = a.isEsAdminSistema() ? "Admin del sistema" : "Gimnasio #" + a.getIdGimnasio();
        return usuario(a) + " | " + tipo;
    }

    public static String entrenador(Entrenador e) {
        String sucursal = e.getSucursalAsignada() != null ? "#" + e.getSucursalAsignada().getId() : "sin asignar";

        return usuario(e)
                + " | Especialidad: " + (e.getEspecialidad() != null ? e.getEspecialidad() : "-")
                + " | Salario: $" + (e.getSalario() != null ? e.getSalario() : "-")
                + " | Sucursal: " + sucursal;
    }

    public static String nutricionista(Nutricionista n) {
        String sucursal = n.getSucursalAsignada() != null ? "#" + n.getSucursalAsignada().getId() : "sin asignar";

        return usuario(n)
                + " | Salario: $" + n.getSalario()
                + " | Comisión: $" + n.getComisionPorConsulta()
                + " | Sucursal: " + sucursal;
    }

    public static String turno(Turno t) {
        String fecha = t.getFechaTurno() != null ? t.getFechaTurno().format(Consola.FORMATO_FECHA_HORA) : "-";

        String nutricionista = "-";
        if (t.getNutricionista() != null) {
            Nutricionista n = t.getNutricionista();
            // Si no se vinculo con el objeto completo, solo tenemos el id
            nutricionista = n.getNombre() != null ? n.getNombre() + " " + n.getApellido() : "#" + n.getId();
        }

        String socio = t.getSocio() != null ? " | Socio #" + t.getSocio().getId() : "";

        return "Turno #" + t.getId() + " | " + fecha
                + " | Nutricionista: " + nutricionista
                + " | $" + t.getPrecio()
                + " | " + t.getEstado()
                + socio;
    }

    // Elige el formato segun el tipo real del usuario (instanceof + casteo)
    public static String describir(Usuarios u) {
        if (u instanceof Administrador) {
            return administrador((Administrador) u);
        }
        if (u instanceof Entrenador) {
            return entrenador((Entrenador) u);
        }
        if (u instanceof Nutricionista) {
            return nutricionista((Nutricionista) u);
        }
        return usuario(u);
    }

    // LISTAS
    // "List<? extends Usuarios>" acepta una List<Entrenador>, List<Nutricionista>, etc.
    // (una List<Entrenador> no se puede pasar donde se pide una List<Usuarios>)

    public static void mostrarUsuarios(List<? extends Usuarios> lista, String mensajeVacia) {
        if (lista == null || lista.isEmpty()) {
            System.out.println(mensajeVacia);
            return;
        }
        for (Usuarios u : lista) {
            System.out.println(describir(u));
        }
    }

    public static void mostrarTurnos(List<Turno> lista, String mensajeVacia) {
        if (lista == null || lista.isEmpty()) {
            System.out.println(mensajeVacia);
            return;
        }
        for (Turno t : lista) {
            System.out.println(turno(t));
        }
    }
}
