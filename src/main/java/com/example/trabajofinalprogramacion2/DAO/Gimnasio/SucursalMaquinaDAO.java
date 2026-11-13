package com.example.trabajofinalprogramacion2.DAO.Gimnasio;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.SucursalMaquina;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SucursalMaquinaDAO implements ABCMLDAO<SucursalMaquina> {
    @Override
    public SucursalMaquina agregar(SucursalMaquina sucursalMaquina) {
        String sql = "INSERT INTO sucursal_maquina(id_sucursal, id_maquina, cantidad, estado) VALUES (?, ?, ?, ?)";
        String sqlId = "SELECT LAST_INSERT_ID()";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement psInsert = con.prepareStatement(sql)) {

            psInsert.setInt(1, sucursalMaquina.getIdSucursal());
            psInsert.setInt(2, sucursalMaquina.getIdMaquina());
            psInsert.setInt(3, sucursalMaquina.getCantidad());
            psInsert.setInt(4, sucursalMaquina.getEstado()); // Incluido según tu aclaración

            if (psInsert.executeUpdate() > 0) {
                try (PreparedStatement psId = con.prepareStatement(sqlId);
                     ResultSet rs = psId.executeQuery()) {

                    if (rs.next()) {
                        sucursalMaquina.setIdSucursalMaquina(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return sucursalMaquina;
    }

    @Override
    public boolean alta(SucursalMaquina sucursalMaquina) {
        String sql = "UPDATE sucursal_maquina SET estado = 1 WHERE id_sucursal_maquina = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sucursalMaquina.getIdSucursalMaquina());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean baja(SucursalMaquina sucursalMaquina) {
        String sql = "UPDATE sucursal_maquina SET estado = 0 WHERE id_sucursal_maquina = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sucursalMaquina.getIdSucursalMaquina());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public SucursalMaquina consulta(int clavePrimaria) {
        String sql = "SELECT * FROM sucursal_maquina WHERE id_sucursal_maquina = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clavePrimaria);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SucursalMaquina sucursalMaquina = new SucursalMaquina();
                    sucursalMaquina.setIdSucursalMaquina(rs.getInt("id_sucursal_maquina"));
                    sucursalMaquina.setIdSucursal(rs.getInt("id_sucursal"));
                    sucursalMaquina.setIdMaquina(rs.getInt("id_maquina"));
                    sucursalMaquina.setCantidad(rs.getInt("cantidad"));
                    sucursalMaquina.setEstado(rs.getInt("estado"));
                    return sucursalMaquina;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean modificar(SucursalMaquina sucursalMaquina) {
        String sql = "UPDATE sucursal_maquina SET id_sucursal = ?, id_maquina = ?, cantidad = ?, estado = ? WHERE id_sucursal_maquina = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sucursalMaquina.getIdSucursal());
            ps.setInt(2, sucursalMaquina.getIdMaquina());
            ps.setInt(3, sucursalMaquina.getCantidad());
            ps.setInt(4, sucursalMaquina.getEstado());
            ps.setInt(5, sucursalMaquina.getIdSucursalMaquina());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public List<SucursalMaquina> listado() {
        List<SucursalMaquina> lista = new ArrayList<>();
        String sql = "SELECT * FROM sucursal_maquina";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SucursalMaquina sucursalMaquina = new SucursalMaquina();
                sucursalMaquina.setIdSucursalMaquina(rs.getInt("id_sucursal_maquina"));
                sucursalMaquina.setIdSucursal(rs.getInt("id_sucursal"));
                sucursalMaquina.setIdMaquina(rs.getInt("id_maquina"));
                sucursalMaquina.setCantidad(rs.getInt("cantidad"));
                sucursalMaquina.setEstado(rs.getInt("estado"));

                lista.add(sucursalMaquina);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return lista;
    }
}
