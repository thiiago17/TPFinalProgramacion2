package com.example.trabajofinalprogramacion2.Entidades.Turno;

// Estado de un turno. En la BD se guarda el codigo (columna turno.estado TINYINT):
// 0 -> dado de baja | 1 -> reservado (ocupado) | 2 -> disponible (desocupado)

public enum EstadoTurno {
    DADO_DE_BAJA(0),
    RESERVADO(1),
    DISPONIBLE(2);

    private final int codigo;

    EstadoTurno(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    // Convierte el codigo guardado en la BD al enum
    public static EstadoTurno desdeCodigo(int codigo) {
        for (EstadoTurno estado : values()) {
            if (estado.codigo == codigo) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Codigo de estado de turno invalido: " + codigo);
    }
}
