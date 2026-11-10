package com.example.trabajofinalprogramacion2.DAO;

import com.example.trabajofinalprogramacion2.Entidades.Turno.EstadoTurno;
import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Tabla esperada:
// turno(id_turno PK autoincremental, id_socio NULL, id_nutricionista, fecha_turno DATETIME, estado TINYINT, precio DECIMAL)
// estado: 0 -> dado de baja | 1 -> reservado | 2 -> disponible (ver EstadoTurno)

public class TurnosDao implements ABCMLDAO<Turno> {

    // AGREGAR

    @Override
    public Turno agregar(Turno turno) {

        String sql = "INSERT INTO turno (id_socio, id_nutricionista, fecha_turno, estado, precio) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            cargarParametros(ps, turno);

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        turno.setId(rs.getInt(1));
                    }
                }
                return turno;
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar turno: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // ALTA (reactiva un turno dado de baja: vuelve a reservado si tiene socio, si no a disponible)

    @Override
    public boolean alta(Turno turno) {

        String sql = "UPDATE turno SET estado = CASE WHEN id_socio IS NULL THEN ? ELSE ? END WHERE id_turno = ? AND estado = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, EstadoTurno.DISPONIBLE.getCodigo());
            ps.setInt(2, EstadoTurno.RESERVADO.getCodigo());
            ps.setInt(3, turno.getId());
            ps.setInt(4, EstadoTurno.DADO_DE_BAJA.getCodigo());

            if (ps.executeUpdate() > 0) {
                turno.setEstado(turno.getSocio() == null ? EstadoTurno.DISPONIBLE : EstadoTurno.RESERVADO);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al dar de alta el turno: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // BAJA LOGICA (estado = 0, el turno queda en la BD)

    @Override
    public boolean baja(Turno turno) {

        String sql = "UPDATE turno SET estado = ? WHERE id_turno = ? AND estado <> ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, EstadoTurno.DADO_DE_BAJA.getCodigo());
            ps.setInt(2, turno.getId());
            ps.setInt(3, EstadoTurno.DADO_DE_BAJA.getCodigo());

            if (ps.executeUpdate() > 0) {
                turno.setEstado(EstadoTurno.DADO_DE_BAJA);
                return true;
            }

            return false;

        } catch (SQLException e) {
            System.out.println("Error al dar de baja el turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // CONSULTA

    @Override
    public Turno consulta(int clavePrimaria) {

        String sql = "SELECT * FROM turno WHERE id_turno = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, clavePrimaria);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearTurno(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar turno: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // MODIFICACION

    @Override
    public boolean modificar(Turno turno) {

        String sql = "UPDATE turno SET id_socio = ?, id_nutricionista = ?, fecha_turno = ?, estado = ?, precio = ? WHERE id_turno = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            cargarParametros(ps, turno);
            ps.setInt(6, turno.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // LISTADO (todos, incluidos los dados de baja, ordenado por fecha)

    @Override
    public List<Turno> listado() {
        return listar("SELECT * FROM turno ORDER BY fecha_turno", null);
    }

    // LISTADOS FILTRADOS =================================================================================
    // Los codigos de estado van fijos en el SQL: 0 = baja, 1 = reservado, 2 = disponible

    // Turnos activos (disponibles o reservados)
    public List<Turno> listadoActivos() {
        return listar("SELECT * FROM turno WHERE estado <> 0 ORDER BY fecha_turno", null);
    }

    // Turnos dados de baja
    public List<Turno> listadoDadosDeBaja() {
        return listar("SELECT * FROM turno WHERE estado = 0 ORDER BY fecha_turno", null);
    }

    // Turnos activos de un nutricionista
    public List<Turno> listadoPorNutricionista(int idNutricionista) {
        return listar("SELECT * FROM turno WHERE id_nutricionista = ? AND estado <> 0 ORDER BY fecha_turno", idNutricionista);
    }

    // Solo los turnos disponibles (no tomados) de un nutricionista
    public List<Turno> listadoDisponiblesPorNutricionista(int idNutricionista) {
        return listar("SELECT * FROM turno WHERE id_nutricionista = ? AND estado = 2 ORDER BY fecha_turno", idNutricionista);
    }

    // Solo los turnos reservados de un nutricionista
    public List<Turno> listadoReservadosPorNutricionista(int idNutricionista) {
        return listar("SELECT * FROM turno WHERE id_nutricionista = ? AND estado = 1 ORDER BY fecha_turno", idNutricionista);
    }

    // Turnos reservados por un socio
    public List<Turno> listadoPorSocio(int idSocio) {
        return listar("SELECT * FROM turno WHERE id_socio = ? AND estado = 1 ORDER BY fecha_turno", idSocio);
    }

    // Todos los turnos disponibles
    public List<Turno> listadoDisponibles() {
        return listar("SELECT * FROM turno WHERE estado = 2 ORDER BY fecha_turno", null);
    }

    // RESERVAR / CANCELAR =================================================================================

    // Reserva solo si el turno sigue disponible (evita que dos socios tomen el mismo turno)
    public boolean reservar(Turno turno, Socio socio) {

        if (turno == null || socio == null) {
            return false;
        }

        String sql = "UPDATE turno SET id_socio = ?, estado = 1 WHERE id_turno = ? AND estado = 2";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, socio.getId());
            ps.setInt(2, turno.getId());

            if (ps.executeUpdate() > 0) {
                turno.sacarTurno(socio);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al reservar turno: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean cancelar(Turno turno) {

        if (turno == null) {
            return false;
        }

        String sql = "UPDATE turno SET id_socio = NULL, estado = 2 WHERE id_turno = ? AND estado = 1";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, turno.getId());

            if (ps.executeUpdate() > 0) {
                turno.cancelarTurno();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al cancelar turno: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // METODOS AUXILIARES =================================================================================

    // Ejecuta un SELECT con un parametro entero opcional
    private List<Turno> listar(String sql, Integer parametro) {

        List<Turno> turnos = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setInt(1, parametro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    turnos.add(mapearTurno(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar turnos: " + e.getMessage());
            e.printStackTrace();
        }

        return turnos;
    }

    // Carga los parametros 1 a 5 (comunes a INSERT y UPDATE)
    private void cargarParametros(PreparedStatement ps, Turno turno) throws SQLException {

        if (turno.getSocio() != null) {
            ps.setInt(1, turno.getSocio().getId());
        } else {
            ps.setNull(1, Types.INTEGER);
        }

        if (turno.getNutricionista() != null) {
            ps.setInt(2, turno.getNutricionista().getId());
        } else {
            ps.setNull(2, Types.INTEGER);
        }

        FechasBD.setFechaHora(ps, 3, turno.getFechaTurno());
        ps.setInt(4, turno.getEstado().getCodigo());
        ps.setDouble(5, turno.getPrecio());
    }

    // Convierte la fila actual del ResultSet en un Turno.
    // Socio y Nutricionista vienen solo con su id cargado; si se necesitan
    // los datos completos hay que buscarlos con su DAO.
    private Turno mapearTurno(ResultSet rs) throws SQLException {

        Turno turno = new Turno();

        turno.setId(rs.getInt("id_turno"));

        // Si la columna esta en NULL, getInt devuelve 0. Como los ids empiezan en 1,
        // un id mayor a 0 significa que el turno tiene socio / nutricionista
        int idSocio = rs.getInt("id_socio");
        if (idSocio > 0) {
            Socio socio = new Socio();
            socio.setId(idSocio);
            turno.setSocio(socio);
        }

        int idNutricionista = rs.getInt("id_nutricionista");
        if (idNutricionista > 0) {
            Nutricionista nutricionista = new Nutricionista();
            nutricionista.setId(idNutricionista);
            turno.setNutricionista(nutricionista);
        }

        turno.setFechaTurno(FechasBD.leerFechaHora(rs, "fecha_turno"));
        turno.setEstado(EstadoTurno.desdeCodigo(rs.getInt("estado")));
        turno.setPrecio(rs.getDouble("precio"));

        return turno;
    }
}
