package com.ingran.data;

import com.ingran.model.Rol;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDB extends Conexion {

    private List<Rol> roles = new ArrayList<>();

    public void obtenerRoles() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getRoles().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT r.id, r.nombre, r.descripcion FROM ROL r;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Rol rol = new Rol();

                rol.setId(rs.getInt(1));
                rol.setNombre(rs.getString(2));
                rol.setDescripcion(rs.getString(3));

                getRoles().add(rol);
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

    public static Rol obtenerRol(Integer id) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Rol rol = null;

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT r.nombre, r.descripcion FROM ROL r WHERE r.id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                rol = new Rol();

                rol.setId(id);
                rol.setNombre(rs.getString(1));
                rol.setDescripcion(rs.getString(2));
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
        return rol;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

//    public static void main(String[] args) {
//        RolDB roles = new RolDB();
//        roles.obtenerRoles();
//
//        for (Rol rol : roles.getRoles()) {
//            System.out.println(rol.getNombre());
//        }
//
//        System.out.println(RolDB.obtenerRol(1).getNombre());
//    }
}
