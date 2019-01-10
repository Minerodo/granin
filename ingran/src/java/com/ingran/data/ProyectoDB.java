package com.ingran.data;

import com.ingran.model.CentroDeCosto;
import com.ingran.model.Proyecto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDB extends Conexion {

    private List<Proyecto> proyectos = new ArrayList<>();

    public void obtenerProyectos() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getProyectos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT p.PROYECTO, p.DESCRIPCION, p.CENTRO_COSTO, p.ESTADO FROM " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY p;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Proyecto proyecto = new Proyecto();

                proyecto.setProyecto(rs.getString(1));
                proyecto.setDescripcion(rs.getString(2));
                proyecto.setCentro_de_costo(rs.getString(3));
                proyecto.setEstado(rs.getString(4));

                getProyectos().add(proyecto);
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

    public static Proyecto obtenerProyecto(CentroDeCosto centro_de_costo) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Proyecto proyecto = new Proyecto();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT p.PROYECTO, p.DESCRIPCION, p.CENTRO_COSTO, p.ESTADO FROM " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY p WHERE p.CENTRO_COSTO = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, centro_de_costo.getCentro_de_costo());

            rs = pst.executeQuery();

            while (rs.next()) {
                proyecto.setProyecto(rs.getString(1));
                proyecto.setDescripcion(rs.getString(2));
                proyecto.setCentro_de_costo(rs.getString(3));
                proyecto.setEstado(rs.getString(4));
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
        return proyecto;
    }

    public static Proyecto obtenerProyecto(Proyecto proy) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Proyecto proyecto = null;

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT p.PROYECTO, p.DESCRIPCION, p.CENTRO_COSTO, p.ESTADO FROM " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY p WHERE p.PROYECTO = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, proy.getProyecto());

            rs = pst.executeQuery();

            while (rs.next()) {
                proyecto = new Proyecto();

                proyecto.setProyecto(rs.getString(1));
                proyecto.setDescripcion(rs.getString(2));
                proyecto.setCentro_de_costo(rs.getString(3));
                proyecto.setEstado(rs.getString(4));
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
        return proyecto;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}
