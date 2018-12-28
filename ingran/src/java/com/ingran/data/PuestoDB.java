package com.ingran.data;

import com.ingran.model.Puesto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PuestoDB extends Conexion {

    private List<Puesto> puestos = new ArrayList<>();

    public void obtenerPuestos() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getPuestos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT p.PUESTO, p.DESCRIPCION FROM " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Puesto puesto = new Puesto();

                puesto.setPuesto(rs.getString(1));
                puesto.setDescripcion(rs.getString(2));

                getPuestos().add(puesto);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    cerrarConexion();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            }
        }
    }

    public static Puesto obtenerPuesto(String id) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Puesto puesto = null;

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT ISNULL(p.DESCRIPCION,'') AS DESCRIPCION FROM " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p WHERE p.PUESTO = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                puesto = new Puesto();

                puesto.setPuesto(id);
                puesto.setDescripcion(rs.getString(1));
            }

        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        } finally {
            try {
                if (conexion.getConexion() != null) {
                    conexion.cerrarConexion();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            }
        }
        return puesto;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<Puesto> puestos) {
        this.puestos = puestos;
    }
    
//    public static void main(String[] args) {
//        PuestoDB puestos = new PuestoDB();
//        puestos.obtenerPuestos();
//        
//        for(Puesto puesto : puestos.getPuestos()){
//            System.out.println(puesto.getDescripcion());
//        }
//        
//        System.out.println(PuestoDB.obtenerPuesto("001").getDescripcion());
//    }
}
