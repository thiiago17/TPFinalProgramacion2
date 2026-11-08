package com.example.trabajofinalprogramacion2.interfaces;

import java.util.List;

public interface ABCMLCollection<T> {
    List<T> verListado();
    boolean agregar(T objeto);
    boolean modificar(T objeto);
    boolean eliminar(int id);
    T buscar(int id);
}
