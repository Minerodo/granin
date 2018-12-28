package com.ingran.data;

import com.ingran.model.CentroDeCosto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CentroDeCostoDB extends Conexion {

    private List<CentroDeCosto> centro_de_costos = new ArrayList<>();

    public void obtenerCentroDeCostos() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCentro_de_costos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT cdc.CENTRO_COSTO, cdc.DESCRIPCION, cdc.TIPO FROM " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY p ON cdc.CENTRO_COSTO = p.CENTRO_COSTO ORDER BY cdc.CENTRO_COSTO ASC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                CentroDeCosto centro_de_costo = new CentroDeCosto();

                centro_de_costo.setCentro_de_costo(rs.getString(1));
                centro_de_costo.setDescripcion(rs.getString(2));
                centro_de_costo.setTipo(rs.getString(3));
                centro_de_costo.setProyecto(ProyectoDB.obtenerProyecto(centro_de_costo));

                getCentro_de_costos().add(centro_de_costo);
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
    
    public void obtenerCentroDeCostosSinUsuario() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCentro_de_costos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT cdc.CENTRO_COSTO, cdc.DESCRIPCION, cdc.TIPO FROM " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY p ON cdc.CENTRO_COSTO = p.CENTRO_COSTO ORDER BY cdc.CENTRO_COSTO ASC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                CentroDeCosto centro_de_costo = new CentroDeCosto();

                centro_de_costo.setCentro_de_costo(rs.getString(1));
                centro_de_costo.setDescripcion(rs.getString(2));
                centro_de_costo.setTipo(rs.getString(3));
                centro_de_costo.setProyecto(ProyectoDB.obtenerProyecto(centro_de_costo));

                getCentro_de_costos().add(centro_de_costo);
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
    
    public void obtenerProyectosSinHorario() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCentro_de_costos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT cdc.CENTRO_COSTO, cdc.DESCRIPCION FROM " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc LEFT JOIN PROYECTO_HORARIO ph ON cdc.CENTRO_COSTO = ph.proyecto INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY p ON cdc.CENTRO_COSTO = p.CENTRO_COSTO WHERE p.ESTADO = 'A' AND ph.id IS NULL AND cdc.ACEPTA_DATOS = 'S' ORDER BY cdc.CENTRO_COSTO;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                CentroDeCosto centro_de_costo = new CentroDeCosto();
                
                centro_de_costo.setCentro_de_costo(rs.getString(1));
                centro_de_costo.setDescripcion(rs.getString(2));
                
                getCentro_de_costos().add(centro_de_costo);
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
    
    public static CentroDeCosto obtenerCentroDeCosto(CentroDeCosto centro_de_costos) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT cdc.CENTRO_COSTO, cdc.DESCRIPCION, cdc.TIPO FROM " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc WHERE cdc.CENTRO_COSTO = ?";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, centro_de_costos.getCentro_de_costo());

            rs = pst.executeQuery();

            while (rs.next()) {
                centro_de_costos.setCentro_de_costo(rs.getString(1));
                centro_de_costos.setDescripcion(rs.getString(2));
                centro_de_costos.setTipo(rs.getString(3));
                centro_de_costos.setProyecto(ProyectoDB.obtenerProyecto(centro_de_costos));
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
        return centro_de_costos;
    }

    public List<CentroDeCosto> getCentro_de_costos() {
        return centro_de_costos;
    }

    public void setCentro_de_costos(List<CentroDeCosto> centro_de_costos) {
        this.centro_de_costos = centro_de_costos;
    }

//    public static void main(String[] args) {
//        CentroDeCostoDB centro_de_costos = new CentroDeCostoDB();
//        centro_de_costos.obtenerCentroDeCostos();
//        int i = 0;
//        for (CentroDeCosto centro_de_costo : centro_de_costos.getCentro_de_costos()) {
//            System.out.println(centro_de_costo.getCentro_de_costo());
//            Proyecto proyecto = centro_de_costo.getProyecto();
//            System.out.println(proyecto.getProyecto());
//            i ++;
//        }
//        System.out.println("Proyectos: " + i);
//    }
}
