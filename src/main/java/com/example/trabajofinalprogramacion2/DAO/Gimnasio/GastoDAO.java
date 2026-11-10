package com.example.trabajofinalprogramacion2.DAO.Gimnasio;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.TipoGasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GastoDAO implements ABCMLDAO<Gasto> {
    public Gasto agregar(Gasto gasto) {
        String sql="INSERT INTO Gasto(detalles,monto,gasto,fecha,id_gimnasio) VALUES (?,?,?,?,?)"; //AGREGARLE "OTROS" AL ENUM DE LA BASE DE DATOS
        String sqlId="SELECT LAST_INSERT_ID()";

        try(Connection con= ConexionBD.conectar();
            PreparedStatement psInsert=con.prepareStatement(sql)){

            psInsert.setString(1,gasto.getDetalles());
            psInsert.setDouble(2,gasto.getMonto());
            psInsert.setString(3,gasto.getTipoGasto().name());
            psInsert.setDate(4,java.sql.Date.valueOf(gasto.getFecha()));
            psInsert.setInt(5,gasto.getIdGimnasio());

            if(psInsert.executeUpdate() > 0){
                try(PreparedStatement psId= con.prepareStatement(sqlId);
                    ResultSet rs=psId.executeQuery()){

                    if(rs.next()){
                        gasto.setId(rs.getInt(1));
                    }

                }catch (SQLException e2){
                    e2.printStackTrace();
                    System.err.println(e2.getMessage());
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return gasto;
    }

    @Override
    public boolean alta(Gasto gasto) {
        String sql="UPDATE Gasto SET estado=1 WHERE id_gasto= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,gasto.getId());

            if(ps.executeUpdate() > 0){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean baja(Gasto gasto) {
        String sql="UPDATE Gasto SET estado=0 WHERE id_gasto= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,gasto.getId());

            if(ps.executeUpdate() > 0){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public Gasto consulta(int clavePrimaria) {
        String sql="SELECT * FROM Gasto WHERE id_gasto= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,clavePrimaria);

            try(ResultSet rs= ps.executeQuery()){

                if(rs.next()){
                    return new Gasto(
                            rs.getInt("id_gasto"),
                            rs.getString("detalles"),
                            rs.getDouble("monto"),
                            TipoGasto.valueOf(rs.getString("gasto")),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getInt("id_gimnasio"),
                            rs.getInt("estado")
                    );
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean modificar(Gasto gasto) {
        String sql="UPDATE Gasto SET detalles=?, monto=?, gasto=?, fecha=?, id_gimnasio=? WHERE id_gasto=?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,gasto.getDetalles());
            ps.setDouble(2,gasto.getMonto());
            ps.setString(3,gasto.getTipoGasto().name());
            ps.setDate(4,java.sql.Date.valueOf(gasto.getFecha()));
            ps.setInt(5,gasto.getIdGimnasio());
            ps.setInt(6,gasto.getId());

            if(ps.executeUpdate() > 0){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public List<Gasto> listado() {
        String sql="SELECT * FROM Gasto";
        List<Gasto> lista=new ArrayList<>();

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){
                Gasto gastoEncontrado= new Gasto(
                        rs.getInt("id_gasto"),
                        rs.getString("detalles"),
                        rs.getDouble("monto"),
                        TipoGasto.valueOf(rs.getString("gasto")),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getInt("id_gimnasio"),
                        rs.getInt("estado")
                );
                lista.add(gastoEncontrado);
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return lista;
    }
}
