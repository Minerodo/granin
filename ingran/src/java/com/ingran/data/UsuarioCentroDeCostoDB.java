package com.ingran.data;

import com.ingran.model.CentroDeCosto;
import com.ingran.model.Usuario;
import com.ingran.model.UsuarioCentroDeCosto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCentroDeCostoDB {
    
    public static List<UsuarioCentroDeCosto> obtenerUsuarioCentroDeCosto(Usuario usuario) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        List<UsuarioCentroDeCosto> list_usuario_centro_de_costos = new ArrayList<>();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT ucdc.id, ucdc.usuario, ucdc.centro_costo FROM USUARIO_CENTRO_COSTO ucdc WHERE ucdc.usuario = ? ORDER BY ucdc.centro_costo ASC;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, usuario.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                UsuarioCentroDeCosto usuario_centro_de_costo = new UsuarioCentroDeCosto();

                usuario_centro_de_costo.setId(rs.getInt(1));
                usuario_centro_de_costo.setUsuario(usuario);
                CentroDeCosto cdc = new CentroDeCosto();
                cdc.setCentro_de_costo(rs.getString(3));
                usuario_centro_de_costo.setCentro_de_costo(CentroDeCostoDB.obtenerCentroDeCosto(cdc));
                
                list_usuario_centro_de_costos.add(usuario_centro_de_costo);
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
        return list_usuario_centro_de_costos;
    }

    public static Boolean asignarProyecto(Usuario usuario) {
        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "INSERT INTO USUARIO_CENTRO_COSTO(usuario, centro_costo) VALUES(?, ?);";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, usuario.getId());
            pst.setString(2, usuario.getCentro_de_costo().getCentro_de_costo());

            pst.executeUpdate();
        } catch (SQLException e) {
            creado = false;
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
        return creado;
    }
    
    public static Boolean desasignarProyecto(int id) {
        Boolean eliminado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "DELETE FROM USUARIO_CENTRO_COSTO WHERE id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, id);

            pst.executeUpdate();
        } catch (SQLException e) {
            eliminado = false;
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
        return eliminado;
    }
    
    public static Boolean existeProyectoAsignado(Usuario usuario) {
        Boolean bool_existe = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "SELECT COUNT(*) FROM dbo.USUARIO_CENTRO_COSTO ucdc WHERE ucdc.usuario = ? AND ucdc.CENTRO_COSTO = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, usuario.getId());
            pst.setString(2, usuario.getCentro_de_costo().getCentro_de_costo());
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                String str_existe = rs.getString(1);
                if(str_existe.equals("0"))
                    bool_existe = false;
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
        return bool_existe;
    }
}
