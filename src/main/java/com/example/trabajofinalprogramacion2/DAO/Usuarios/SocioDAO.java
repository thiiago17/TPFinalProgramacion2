package com.example.trabajofinalprogramacion2.DAO.Usuarios;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.DAO.FechasBD;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Membresia;
import com.example.trabajofinalprogramacion2.Entidades.Membresias.Plan;
import com.example.trabajofinalprogramacion2.Entidades.Usuarios.Socio;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO implements ABCMLDAO<Socio> {

    @Override
    public Socio agregar(Socio socio) {
        // Primero manejamos la membresia: si el socio tiene una, la guardamos en la BD para obtener su ID
        Integer idMembresia = guardarOActualizarMembresia(socio.getMembresia());

        String sql = "INSERT INTO socio (nombre, apellido, dni, email, contrasenia, fechaDeNacimiento, activo, pesoActual, altura, fechaAlta, id_membresia, id_sucursal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellido());
            ps.setString(3, socio.getDni());
            ps.setString(4, socio.getEmail());
            ps.setString(5, socio.getPassword());
            FechasBD.setFecha(ps, 6, socio.getFechaDeNacimiento());
            ps.setBoolean(7, socio.isActivo());
            ps.setDouble(8, socio.getPesoActual());
            ps.setDouble(9, socio.getAltura());
            FechasBD.setFecha(ps, 10, socio.getFechaAlta());

            // Settear id_membresia (puede ser null)
            if (idMembresia != null) {
                ps.setInt(11, idMembresia);
            } else {
                ps.setNull(11, Types.INTEGER);
            }

            // Settear id_sucursal
            setSucursal(ps, 12, socio.getSucursalAsignada());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        socio.setId(generatedKeys.getInt(1)); // Asignar el ID generado al socio
                    }
                }
                return socio;
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar socio: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean alta(Socio socio) {
        String sql = "UPDATE socio SET activo = true WHERE id_socio = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, socio.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al dar de alta al socio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean baja(Socio socio) {
        String sql = "UPDATE socio SET activo = false WHERE id_socio = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, socio.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al dar de baja al socio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Socio consulta(int clavePrimaria) {
        String sql = "SELECT * FROM socio WHERE id_socio = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, clavePrimaria);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearSocio(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar socio por ID: " + e.getMessage());
        }
        return null;
    }

    // CONSULTA POR DNI
    public Socio consultaPorDni(String dni) {
        String sql = "SELECT * FROM socio WHERE dni = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearSocio(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar socio por DNI: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean modificar(Socio socio) {
        // Actualizamos o guardamos la membresía primero
        Integer idMembresia = guardarOActualizarMembresia(socio.getMembresia());

        String sql = "UPDATE socio SET nombre = ?, apellido = ?, dni = ?, email = ?, contrasenia = ?, fechaDeNacimiento = ?, activo = ?, pesoActual = ?, altura = ?, fechaAlta = ?, id_membresia = ?, id_sucursal = ? WHERE id_socio = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellido());
            ps.setString(3, socio.getDni());
            ps.setString(4, socio.getEmail());
            ps.setString(5, socio.getPassword());
            FechasBD.setFecha(ps, 6, socio.getFechaDeNacimiento());
            ps.setBoolean(7, socio.isActivo());
            ps.setDouble(8, socio.getPesoActual());
            ps.setDouble(9, socio.getAltura());
            FechasBD.setFecha(ps, 10, socio.getFechaAlta());

            if (idMembresia != null) {
                ps.setInt(11, idMembresia);
            } else {
                ps.setNull(11, Types.INTEGER);
            }

            setSucursal(ps, 12, socio.getSucursalAsignada());
            ps.setInt(13, socio.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar socio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Socio> listado() {
        List<Socio> lista = new ArrayList<>();
        String sql = "SELECT * FROM socio";

        try (Connection conexion = ConexionBD.conectar();
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearSocio(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar socios: " + e.getMessage());
        }
        return lista;
    }

    // --- MÉTODOS AUXILIARES Y DE MAPEO ---

    private Socio mapearSocio(ResultSet rs) throws SQLException {
        Socio s = new Socio();
        s.setId(rs.getInt("id_socio"));
        s.setNombre(rs.getString("nombre"));
        s.setApellido(rs.getString("apellido"));
        s.setDni(rs.getString("dni"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("contrasenia"));
        s.setFechaDeNacimiento(FechasBD.leerFecha(rs, "fechaDeNacimiento"));
        s.setActivo(rs.getBoolean("activo"));
        s.setPesoActual(rs.getDouble("pesoActual"));
        s.setAltura(rs.getDouble("altura"));
        s.setFechaAlta(FechasBD.leerFecha(rs, "fechaAlta"));

        // Mapear Membresía si tiene ID asociado en la BD
        int idMembresia = rs.getInt("id_membresia");
        if (idMembresia > 0) {
            Membresia membresia = buscarMembresiaPorId(idMembresia);
            s.setMembresia(membresia);
        }

        // Mapear Sucursal asignada
        int idSucursal = rs.getInt("id_sucursal");
        if (idSucursal > 0) {
            Sucursal sucursal = new Sucursal();
            sucursal.setId(idSucursal);
            s.setSucursalAsignada(sucursal);
        }

        return s;
    }

    // Método auxiliar para buscar la membresía en su propia tabla al levantar el socio
    private Membresia buscarMembresiaPorId(int idMembresia) {
        String sql = "SELECT * FROM membresia WHERE id_membresia = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idMembresia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LocalDate inicio = FechasBD.leerFecha(rs, "fechaInicio");
                    LocalDate vencimiento = FechasBD.leerFecha(rs, "fechaVencimiento");
                    Plan plan = Plan.valueOf(rs.getString("plan"));

                    return new Membresia(inicio, vencimiento, plan);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la membresía del socio: " + e.getMessage());
        }
        return null;
    }

    // Método auxiliar para guardar una membresía nueva en su tabla y retornar su ID generado
    private Integer guardarOActualizarMembresia(Membresia membresia) {
        if (membresia == null) {
            return null;
        }

        String sql = "INSERT INTO membresia (fechaInicio, fechaVencimiento, plan) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            FechasBD.setFecha(ps, 1, membresia.getFechaInicio());
            FechasBD.setFecha(ps, 2, membresia.getFechaVencimiento());
            ps.setString(3, membresia.getPlan().name());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar la membresía: " + e.getMessage());
        }
        return null;
    }

    private void setSucursal(PreparedStatement ps, int posicion, Sucursal sucursal) throws SQLException {
        if (sucursal == null) {
            ps.setNull(posicion, Types.INTEGER);
        } else {
            ps.setInt(posicion, sucursal.getId());
        }
    }
}