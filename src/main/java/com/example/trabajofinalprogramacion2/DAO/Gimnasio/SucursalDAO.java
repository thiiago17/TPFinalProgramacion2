package com.example.trabajofinalprogramacion2.DAO.Gimnasio;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Sucursal;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SucursalDAO implements ABCMLDAO<Sucursal> {
    public Sucursal agregar(Sucursal sucursal) {
        String sql="INSERT INTO Sucursal(id_gimnasio,calle,altura,ciudad) VALUES (?,?,?,?)";
        String sqlId="SELECT LAST_INSERT_ID()";

        try(Connection con= ConexionBD.conectar();
            PreparedStatement psInsert=con.prepareStatement(sql)){

            psInsert.setInt(1,sucursal.getIdGimnasio());
            psInsert.setString(2,sucursal.getCalle());
            psInsert.setInt(3,sucursal.getAltura());
            psInsert.setString(4,sucursal.getCiudad());

            if(psInsert.executeUpdate() > 0){
                try(PreparedStatement psId= con.prepareStatement(sqlId);
                    ResultSet rs=psId.executeQuery()){

                    if(rs.next()){
                        sucursal.setId(rs.getInt(1));
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

        return sucursal;
    }

    @Override
    public boolean alta(Sucursal sucursal) {
        String sql="UPDATE Sucursal SET estado=1 WHERE id_sucursal= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,sucursal.getId());

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
    public boolean baja(Sucursal sucursal) {
        String sql="UPDATE Sucursal SET estado=0 WHERE id_sucursal= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,sucursal.getId());

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
    public Sucursal consulta(Sucursal sucursal) { //ESTE METODO NO TIENE MUCHO SENTIDO, DEBERIA SER CON EL ID, HAY Q MODIFICAR LA INTERFAZ
        //
        return null;
    }

    @Override
    public boolean modificar(Sucursal sucursal) {
        String sql="UPDATE Sucursal SET id_gimnasio=?, calle=?, altura=?, ciudad=? WHERE id_sucursal=?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,sucursal.getIdGimnasio());
            ps.setString(2,sucursal.getCalle());
            ps.setInt(3,sucursal.getAltura());
            ps.setString(4,sucursal.getCiudad());
            ps.setInt(5,sucursal.getId());

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
    public List<Sucursal> listado() {
        String sql="SELECT * FROM Sucursal";
        List<Sucursal> lista=new ArrayList<>();

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){
                Sucursal sucursalEncontrada= new Sucursal(
                        rs.getInt("id_sucursal"),
                        rs.getInt("estado"),
                        rs.getString("calle"),
                        rs.getInt("altura"),
                        rs.getString("ciudad"),
                        rs.getInt("id_gimnasio")
                );
                lista.add(sucursalEncontrada);
            }


        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return lista;
    }
}
