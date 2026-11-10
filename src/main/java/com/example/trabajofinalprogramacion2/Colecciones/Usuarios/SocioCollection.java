package com.example.trabajofinalprogramacion2.Colecciones.Usuarios;

import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;

import java.util.ArrayList;
import java.util.List;

public class SocioCollection extends UsuariosCollection<Socio> {

    // MODIFICAR PESO ACTUAL
    public Socio modificarPeso(int id, double nuevoPeso) {
        Socio socio = buscar(id);

        if (socio == null || nuevoPeso <= 0) {
            return null;
        }

        socio.setPesoActual(nuevoPeso);
        return socio;
    }

    // MODIFICAR ALTURA
    public Socio modificarAltura(int id, double nuevaAltura) {
        Socio socio = buscar(id);

        if (socio == null || nuevaAltura <= 0) {
            return null;
        }

        socio.setAltura(nuevaAltura);
        return socio;
    }

    // MODIFICAR SUCURSAL ASIGNADA
    public Socio modificarSucursal(int id, Sucursal sucursal) {
        Socio socio = buscar(id);

        if (socio == null || sucursal == null) {
            return null;
        }

        socio.setSucursalAsignada(sucursal);
        return socio;
    }


    // CAMBIAR / ELEGIR PLAN DE MEMBRESÍA
    public Socio cambiarPlanMembresia(int id, Plan nuevoPlan) {
        Socio socio = buscar(id);

        if (socio == null || nuevoPlan == null) {
            return null;
        }

        // Si el socio no tenía membresía creada, se la instanciamos
        if (socio.getMembresia() == null) {
            socio.setMembresia(new Membresia());
        }

        socio.getMembresia().elegirPlan(nuevoPlan);
        return socio;
    }

    // ABONAR CUOTA DE LA MEMBRESÍA
    public String abonarCuotaSocio(int id) {
        Socio socio = buscar(id);

        if (socio == null) {
            return "Error: Socio no encontrado.";
        }

        if (socio.getMembresia() == null) {
            return "Error: El socio no tiene una membresía asignada.";
        }

        return socio.getMembresia().abonarCuota();
    }

    // VER ESTADO / DÍAS RESTANTES DE LA MEMBRESÍA
    public String verEstadoMembresia(int id) {
        Socio socio = buscar(id);

        if (socio == null) {
            return "Socio no encontrado.";
        }

        if (socio.getMembresia() == null) {
            return "El socio no posee membresía.";
        }

        return socio.getMembresia().verPlanActual() + " - " + socio.getMembresia().verDiasRestantes();
    }

    // LISTAR SOCIOS CUYA MEMBRESÍA ESTÁ VENCIDA
    public List<Socio> listarSociosVencidos() {
        List<Socio> vencidos = new ArrayList<>();

        // Usamos verListado() que es el método heredado de UsuariosCollection
        for (Socio socio : verListado()) {
            if (socio != null && socio.getMembresia() != null && socio.getMembresia().isVencida()) {
                vencidos.add(socio);
            }
        }
        return vencidos;
    }

    // VERIFICAR SI UN SOCIO PUEDE INGRESAR A UNA SUCURSAL ESPECÍFICA
    public boolean verificarAccesoSucursal(int idSocio, Sucursal sucursal) {
        Socio socio = buscar(idSocio);

        if (socio == null) {
            return false;
        }

        return socio.puedeIngresarA(sucursal);
    }
}