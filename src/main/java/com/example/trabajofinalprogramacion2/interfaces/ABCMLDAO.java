package com.example.trabajofinalprogramacion2.interfaces;

import java.util.List;

public interface ABCMLDAO<T> {
    boolean agregar(T objeto);
    boolean alta(T objeto);
    boolean baja(T objeto);
    T consulta(String dni);
    boolean modificacion(T objeto);
    List<T> listado();
}
