package com.example.trabajofinalprogramacion2.DAO;

import com.example.trabajofinalprogramacion2.Entidades.Turno.Turno;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Nutricionista;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Tabla esperada:
// Turno(id_turno PK autoincremental, id_socio NULL, id_nutricionista, fechaTurno DATE, estado BOOLEAN, precio DOUBLE)
// estado = true -> turno disponible | estado = false -> turno reservado

public class TurnosDao implements ABCMLDAO<Turno> {

    // AGREGAR

    @Override
    public Turno agregar(Turno turno) {

        String sql = "INSERT INTO Turno (id_socio, id_nutricionista, fecha_turno, estado, precio) VALUES (?, ?, ?, ?, ?)";

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

    // ALTA

    @Override
    public boolean alta(Turno turno) {
        return agregar(turno) != null;
    }

    // BAJA (el turno no tiene "activo", asi que la baja lo elimina)

    @Override
    public boolean baja(Turno turno) {

        String sql = "UPDATE Turno SET estado=false WHERE id_turno = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, turno.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al dar de baja el turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // CONSULTA

    @Override
    public Turno consulta(Turno turno) {

        String sql = "SELECT * FROM Turno WHERE id_turno = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, turno.getId());

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

        String sql = "UPDATE Turno SET id_socio = ?, id_nutricionista = ?, fechaTurno = ?, estado = ?, precio = ? WHERE id_turno = ?";

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

    // LISTADO (ordenado por fecha)

    @Override
    public List<Turno> listado() {
        return listar("SELECT * FROM Turno ORDER BY fechaTurno", null);
    }

    // LISTADOS FILTRADOS =================================================================================

    // Todos los turnos de un nutricionista
    public List<Turno> listadoPorNutricionista(int idNutricionista) {
        return listar("SELECT * FROM Turno WHERE id_nutricionista = ? ORDER BY fechaTurno", idNutricionista);
    }

    // Solo los turnos disponibles (no tomados) de un nutricionista
    public List<Turno> listadoDisponiblesPorNutricionista(int idNutricionista) {
        return listar("SELECT * FROM Turno WHERE id_nutricionista = ? AND estado = true ORDER BY fechaTurno", idNutricionista);
    }

    // Solo los turnos reservados de un nutricionista
    public List<Turno> listadoReservadosPorNutricionista(int idNutricionista) {
        return listar("SELECT * FROM Turno WHERE id_nutricionista = ? AND estado = false ORDER BY fechaTurno", idNutricionista);
    }

    // Turnos reservados por un socio
    public List<Turno> listadoPorSocio(int idSocio) {
        return listar("SELECT * FROM Turno WHERE id_socio = ? ORDER BY fechaTurno", idSocio);
    }

    // Todos los turnos disponibles
    public List<Turno> listadoDisponibles() {
        return listar("SELECT * FROM Turno WHERE estado = true ORDER BY fechaTurno", null);
    }

    // RESERVAR / CANCELAR =================================================================================

    // Reserva solo si el turno sigue disponible (evita que dos socios tomen el mismo turno)
    public boolean reservar(Turno turno, Socio socio) {

        if (turno == null || socio == null) {
            return false;
        }

        String sql = "UPDATE Turno SET id_socio = ?, estado = false WHERE id_turno = ? AND estado = true";

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

        String sql = "UPDATE Turno SET id_socio = NULL, estado = true WHERE id_turno = ? AND estado = false";

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

        ps.setDate(3, turno.getFechaTurno() != null ? Date.valueOf(turno.getFechaTurno()) : null);
        ps.setBoolean(4, turno.isEstado());
        ps.setDouble(5, turno.getPrecio());
    }

    // Convierte la fila actual del ResultSet en un Turno.
    // Socio y Nutricionista vienen solo con su id cargado; si se necesitan
    // los datos completos hay que buscarlos con su DAO.
    private Turno mapearTurno(ResultSet rs) throws SQLException {

        Turno turno = new Turno();

        turno.setId(rs.getInt("id_turno"));

        int idSocio = rs.getInt("id_socio");
        if (!rs.wasNull()) {
            Socio socio = new Socio();
            socio.setId(idSocio);
            turno.setSocio(socio);
        }

        int idNutricionista = rs.getInt("id_nutricionista");
        if (!rs.wasNull()) {
            Nutricionista nutricionista = new Nutricionista();
            nutricionista.setId(idNutricionista);
            turno.setNutricionista(nutricionista);
        }

        Date fecha = rs.getDate("fechaTurno");
        turno.setFechaTurno(fecha != null ? fecha.toLocalDate() : null);
        turno.setEstado(rs.getBoolean("estado"));
        turno.setPrecio(rs.getDouble("precio"));

        return turno;
    }
}
