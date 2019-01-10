package com.ingran.data;

import com.ingran.model.Unidad_Medida;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Unidad_MedidaDB extends Conexion {

    private List<Unidad_Medida> unidades = new ArrayList<>();

    public List<Unidad_Medida> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad_Medida> unidades) {
        this.unidades = unidades;
    }

    public void obtenerUnidades() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getUnidades().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT r.id, r.nombre FROM UNIDAD_MEDIDA r;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Unidad_Medida unidad = new Unidad_Medida();

                unidad.setId(rs.getInt(1));
                unidad.setNombre(rs.getString(2));

                getUnidades().add(unidad);
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

    public static Unidad_Medida obtenerUnidad(Integer id) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Unidad_Medida unidad = null;

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT r.id, r.nombre FROM UNIDAD_MEDIDA r  WHERE r.id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {

                unidad.setId(rs.getInt(1));
                unidad.setNombre(rs.getString(2));

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
        return unidad;
    }

//    public static Rol obtenerRol(Integer id) {
//        Conexion conexion = new Conexion();
//        conexion.abrirConexion();
//
//        Rol rol = null;
//
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//
//        try {
//            String consultaSQL = "SELECT r.nombre, r.descripcion FROM ROL r WHERE r.id = ?;";
//
//            pst = conexion.getConexion().prepareStatement(consultaSQL);
//
//            pst.setInt(1, id);
//
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                rol = new Rol();
//
//                rol.setId(id);
//                rol.setNombre(rs.getString(1));
//                rol.setDescripcion(rs.getString(2));
//            }
//
//        } catch (SQLException e) {
//            System.err.println("ERROR: " + e);
//        } finally {
//            try {
//                if (conexion.getConexion() != null) {
//                    conexion.cerrarConexion();
//                }
//                if (pst != null) {
//                    pst.close();
//                }
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                System.err.println("ERROR: " + e);
//            }
//        }
//        return rol;
//    }
}
