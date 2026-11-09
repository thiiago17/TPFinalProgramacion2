package com.example.trabajofinalprogramacion2.DAO.Gimnasio;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Gimnasio;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GimnasioDAO implements ABCMLDAO<Gimnasio> {
    @Override
    public Gimnasio agregar(Gimnasio gimnasio) {
        String sql="INSERT INTO Gimnasio(nombre) VALUES (?)";
        String sqlId="SELECT LAST_INSERT_ID()";

        try(Connection con= ConexionBD.conectar();
            PreparedStatement psInsert=con.prepareStatement(sql)){

            psInsert.setString(1,gimnasio.getNombre());
            if(psInsert.executeUpdate() > 0){
                try(PreparedStatement psId= con.prepareStatement(sqlId);
                    ResultSet rs=psId.executeQuery()){

                    if(rs.next()){
                        gimnasio.setId(rs.getInt(1));
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

        return gimnasio;
    }

    @Override
    public boolean alta(Gimnasio gimnasio) {
        String sql="UPDATE Gimnasio SET estado=1 WHERE id_gimnasio= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,gimnasio.getId());

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
    public boolean baja(Gimnasio gimnasio) {
        String sql="UPDATE Gimnasio SET estado=0 WHERE id_gimnasio= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,gimnasio.getId());

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
    public Gimnasio consulta(Gimnasio gimnasio) { //ESTE METODO NO TIENE MUCHO SENTIDO, DEBERIA SER CON EL ID, HAY Q MODIFICAR LA INTERFAZ
        String sql="SELECT * FROM Gimnasio WHERE id_gimnasio= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,gimnasio.getId());

            ResultSet rs= ps.executeQuery();

            if(rs.next()){
                Gimnasio gymConsultado= new Gimnasio();

                gymConsultado.setNombre(rs.getString("nombre"));

                return gymConsultado;
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean modificar(Gimnasio gimnasio) {
        String sql="UPDATE Gimnasio SET nombre=? WHERE id_gimnasio=?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,gimnasio.getNombre());
            ps.setInt(2,gimnasio.getId());

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
    public List<Gimnasio> listado() {
        String sql="SELECT * FROM Gimnasio";
        List<Gimnasio> lista=new ArrayList<>();

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){
                Gimnasio gimnasioEncontrado= new Gimnasio(rs.getInt("id_gimnasio"), rs.getString("nombre"), rs.getInt("estado"));
                lista.add(gimnasioEncontrado);
            }


        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return lista;
    }
}
