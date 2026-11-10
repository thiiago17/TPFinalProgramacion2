package com.example.trabajofinalprogramacion2.interfaces;

// La implementan los services de usuarios (Administrador, Entrenador, Nutricionista y, cuando este, Socio).
// Asi MenuPerfil puede cambiar la contraseña o el email de cualquier usuario sin saber de que tipo es.

public interface GestionPerfil {
    boolean modificarPassword(int id, String password);
    boolean modificarEmail(int id, String email);
}
