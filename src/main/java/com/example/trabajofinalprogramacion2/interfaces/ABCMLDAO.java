package com.example.trabajofinalprogramacion2.interfaces;

import java.util.List;

public interface ABCMLDAO<T> {
    T agregar(T objeto);
    boolean alta(T objeto);
    boolean baja(T objeto);
    T consulta(T objeto);
    boolean modificar(T objeto);
    List<T> listado();
}
