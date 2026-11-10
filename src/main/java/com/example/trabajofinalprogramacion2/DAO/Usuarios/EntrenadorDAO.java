package com.example.trabajofinalprogramacion2.DAO.Usuarios;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.DAO.FechasBD;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Entrenador;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO implements ABCMLDAO<Entrenador> {


    @Override
    public Entrenador agregar(Entrenador entrenador) {
        String sql = "INSERT INTO entrenador (dni, nombre, apellido, email, contrasenia, fechaDeNacimiento, activo, especialidad, salario, id_sucursal, fechaIngreso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, entrenador.getDni());
            pstmt.setString(2, entrenador.getNombre());
            pstmt.setString(3, entrenador.getApellido());
            pstmt.setString(4, entrenador.getEmail());
            pstmt.setString(5, entrenador.getPassword());
            FechasBD.setFecha(pstmt, 6, entrenador.getFechaDeNacimiento());
            pstmt.setBoolean(7, entrenador.isActivo());
            pstmt.setString(8, entrenador.getEspecialidad());
            setSalario(pstmt, 9, entrenador.getSalario());
            setSucursal(pstmt, 10, entrenador.getSucursalAsignada());
            FechasBD.setFecha(pstmt, 11, entrenador.getFechaIngreso());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entrenador.setId(generatedKeys.getInt(1)); // Asignar el ID generado por la BD
                    }
                }
                return entrenador;
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar entrenador: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean alta(Entrenador entrenador) {
        String sql = "UPDATE entrenador SET activo = true WHERE id_entrenador = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, entrenador.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al dar de alta entrenador: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean baja(Entrenador entrenador) {
        String sql = "UPDATE entrenador SET activo = false WHERE id_entrenador = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, entrenador.getId());


            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al dar de baja entrenador: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Entrenador consulta(int clavePrimaria) {
        String sql = "SELECT * FROM entrenador WHERE id_entrenador = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clavePrimaria);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEntrenador(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar entrenador: " + e.getMessage());
        }
        return null;
    }

    // CONSULTA POR DNI

    public Entrenador consultaPorDni(String dni) {
        String sql = "SELECT * FROM entrenador WHERE dni = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEntrenador(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar entrenador por DNI: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean modificar(Entrenador entrenador) {
        String sql = "UPDATE entrenador SET nombre = ?, apellido = ?, dni= ?, email = ?, contrasenia = ?, fechaDeNacimiento = ?, activo = ?, especialidad = ?, salario = ?, id_sucursal = ?, fechaIngreso = ? WHERE id_entrenador = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entrenador.getNombre());
            pstmt.setString(2, entrenador.getApellido());
            pstmt.setString(3,entrenador.getDni());
            pstmt.setString(4, entrenador.getEmail());
            pstmt.setString(5, entrenador.getPassword());
            FechasBD.setFecha(pstmt, 6, entrenador.getFechaDeNacimiento());
            pstmt.setBoolean(7, entrenador.isActivo());
            pstmt.setString(8, entrenador.getEspecialidad());
            setSalario(pstmt, 9, entrenador.getSalario());
            setSucursal(pstmt, 10, entrenador.getSucursalAsignada());
            FechasBD.setFecha(pstmt, 11, entrenador.getFechaIngreso());
            pstmt.setInt(12, entrenador.getId());


            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar entrenador: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Entrenador> listado() {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenador";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearEntrenador(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar entrenadores: " + e.getMessage());
        }
        return lista;
    }

    // ALUMNOS (tabla entrenador_socio) =================================================================================

    // VINCULAR un socio con un entrenador

    public boolean vincularSocio(int idEntrenador, int idSocio) {
        String sql = "INSERT INTO entrenador_socio (id_entrenador, id_socio) VALUES (?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEntrenador);
            pstmt.setInt(2, idSocio);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al vincular socio con entrenador: " + e.getMessage());
            return false;
        }
    }

    // DESVINCULAR un socio de un entrenador

    public boolean desvincularSocio(int idEntrenador, int idSocio) {
        String sql = "DELETE FROM entrenador_socio WHERE id_entrenador = ? AND id_socio = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEntrenador);
            pstmt.setInt(2, idSocio);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al desvincular socio del entrenador: " + e.getMessage());
            return false;
        }
    }

    // DESVINCULAR TODOS los socios de un entrenador (se usa al darlo de baja).
    // Devuelve true aunque no tuviera socios: lo importante es que no haya fallado

    public boolean desvincularTodosLosSocios(int idEntrenador) {
        String sql = "DELETE FROM entrenador_socio WHERE id_entrenador = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEntrenador);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error al desvincular los socios del entrenador: " + e.getMessage());
            return false;
        }
    }

    // DESVINCULAR un socio de TODOS sus entrenadores (se usa cuando el socio se da de baja).
    // Devuelve true aunque no tuviera entrenador: lo importante es que no haya fallado

    public boolean desvincularSocioDeTodos(int idSocio) {
        String sql = "DELETE FROM entrenador_socio WHERE id_socio = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSocio);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error al desvincular el socio de sus entrenadores: " + e.getMessage());
            return false;
        }
    }

    // LISTAR los ids de los socios de un entrenador

    public List<Integer> listarIdsSocios(int idEntrenador) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_socio FROM entrenador_socio WHERE id_entrenador = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEntrenador);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id_socio"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los socios del entrenador: " + e.getMessage());
        }
        return ids;
    }

    // Arma un Entrenador a partir de la fila actual del ResultSet
    private Entrenador mapearEntrenador(ResultSet rs) throws SQLException {
        Entrenador e = new Entrenador();
        e.setId(rs.getInt("id_entrenador"));
        e.setNombre(rs.getString("nombre"));
        e.setApellido(rs.getString("apellido"));
        e.setDni(rs.getString("dni"));
        e.setEmail(rs.getString("email"));
        e.setPassword(rs.getString("contrasenia"));
        e.setFechaDeNacimiento(FechasBD.leerFecha(rs, "fechaDeNacimiento"));
        e.setActivo(rs.getBoolean("activo"));
        e.setEspecialidad(rs.getString("especialidad"));
        // Si el salario esta en NULL, getDouble devuelve 0
        e.setSalario(rs.getDouble("salario"));
        e.setFechaIngreso(FechasBD.leerFecha(rs, "fechaIngreso"));

        // La sucursal viene solo con su id: si se necesitan los datos completos hay que buscarla con su DAO.
        // Si la columna esta en NULL, getInt devuelve 0 (los ids empiezan en 1)
        int idSucursal = rs.getInt("id_sucursal");
        if (idSucursal > 0) {
            Sucursal sucursal = new Sucursal();
            sucursal.setId(idSucursal);
            e.setSucursalAsignada(sucursal);
        }
        return e;
    }

    // La sucursal puede ser null: en ese caso se guarda NULL en la BD
    private void setSucursal(PreparedStatement pstmt, int posicion, Sucursal sucursal) throws SQLException {
        if (sucursal == null) {
            pstmt.setNull(posicion, Types.INTEGER);
        } else {
            pstmt.setInt(posicion, sucursal.getId());
        }
    }

    // El salario es Double y puede ser null: en ese caso se guarda NULL en la BD
    private void setSalario(PreparedStatement pstmt, int posicion, Double salario) throws SQLException {
        if (salario == null) {
            pstmt.setNull(posicion, Types.DOUBLE);
        } else {
            pstmt.setDouble(posicion, salario);
        }
    }
}
