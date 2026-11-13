package com.example.trabajofinalprogramacion2.DAO.Gimnasio;

import com.example.trabajofinalprogramacion2.DAO.ConexionBD;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.Gasto;
import com.example.trabajofinalprogramacion2.Entidades.Gastos.TipoGasto;
import com.example.trabajofinalprogramacion2.Entidades.Gimnasio.Maquina;
import com.example.trabajofinalprogramacion2.interfaces.ABCMLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO implements ABCMLDAO<Maquina> {
    @Override
    public Maquina agregar(Maquina maquina) {
        String sql="INSERT INTO Maquina(nombre,descripcion,pesoLimite,grupoMuscular) VALUES (?,?,?,?)";
        String sqlId="SELECT LAST_INSERT_ID()";

        try(Connection con= ConexionBD.conectar();
            PreparedStatement psInsert=con.prepareStatement(sql)){

            psInsert.setString(1,maquina.getNombre());
            psInsert.setString(2,maquina.getDescripcion());
            psInsert.setDouble(3,maquina.getPesoLimite());
            psInsert.setString(4,maquina.getGrupoMuscular());

            if(psInsert.executeUpdate() > 0){
                try(PreparedStatement psId= con.prepareStatement(sqlId);
                    ResultSet rs=psId.executeQuery()){

                    if(rs.next()){
                        maquina.setId(rs.getInt(1));
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

        return maquina;
    }

    @Override
    public boolean alta(Maquina maquina) {
        String sql="UPDATE Maquina SET estado=1 WHERE id_maquina= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,maquina.getId());

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
    public boolean baja(Maquina maquina) {
        String sql="UPDATE Maquina SET estado=0 WHERE id_maquina= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,maquina.getId());

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
    public Maquina consulta(int clavePrimaria) {
        String sql="SELECT * FROM Maquina WHERE id_maquina= ?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,clavePrimaria);

            try(ResultSet rs= ps.executeQuery()){

                if(rs.next()){
                    return new Maquina(
                            rs.getInt("id_maquina"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("pesoLimite"),
                            rs.getString("grupoMuscular"),
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
    public boolean modificar(Maquina maquina) {
        String sql="UPDATE Maquina SET nombre=?, descripcion=?, pesoLimite=?, grupoMuscular=? WHERE id_maquina=?";

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,maquina.getNombre());
            ps.setString(2,maquina.getDescripcion());
            ps.setDouble(3,maquina.getPesoLimite());
            ps.setString(4,maquina.getGrupoMuscular());
            ps.setInt(5,maquina.getId());

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
    public List<Maquina> listado() {
        String sql="SELECT * FROM Maquina";
        List<Maquina> lista=new ArrayList<>();

        try(Connection con=ConexionBD.conectar();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){
                Maquina maquinaEncontrada= new Maquina(
                        rs.getInt("id_maquina"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("pesoLimite"),
                        rs.getString("grupoMuscular"),
                        rs.getInt("estado")
                );
                lista.add(maquinaEncontrada);
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return lista;
    }
}

