package com.ingran.data;


import com.ingran.model.CentroDeCosto;
import com.ingran.model.LaudoCCosto;
import com.ingran.model.Unidad_Medida;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author admin
 */
public class LaudoCCostoDB  extends Conexion {
    private List<LaudoCCosto> laudos = new ArrayList<>();

    public List<LaudoCCosto> getLaudosCCosto() {
        return laudos;
    }
    
    
    
    public void setLaudosCCosto(List<LaudoCCosto> laudos) {
        this.laudos = laudos;
    }
    
    

    public void obtenerLaudosCCostos(Integer idLaudo) {
        abrirConexion();
        
        

        getLaudosCCosto().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            
            
            
            String consultaSQL = "SELECT laudocc.id, laudocc.idcentro_costo, laudocc.id_laudo_general, laudocc.costo, laudocc.estado, (SELECT u.NOMBRE FROM dbo.LAUDO l, dbo.UNIDAD_MEDIDA u WHERE l.TIPO_UNIDAD = u.id and l.id = laudocc.id_laudo_general) unidad,  (SELECT l.DESCRIPCION FROM dbo.LAUDO l WHERE l.id = laudocc.id_laudo_general) descripcion FROM [PLANILLA].[dbo].[LAUDO_CCOSTO] laudocc where laudocc.id_laudo_general = ?;";

            pst = getConexion().prepareStatement(consultaSQL);
            
            pst.setInt(1, idLaudo);

            rs = pst.executeQuery();

            while (rs.next()) {
                LaudoCCosto laudo = new LaudoCCosto();
                
                laudo.setId(rs.getInt(1));
                
                CentroDeCosto centro = new CentroDeCosto();
                
                centro.setCentro_de_costo(rs.getString(2));
                
                laudo.setCentro_costo(centro);
                
                laudo.setId_laudo_general(rs.getInt(3));
                
                laudo.setCosto(rs.getDouble(4));
                laudo.setEstado(rs.getString(5));
                
                Unidad_Medida unidad_medida = new Unidad_Medida();
                unidad_medida.setNombre(rs.getString(6));
                laudo.setUnidad_medida(unidad_medida);
                
                laudo.setDescripcion(rs.getString(7));

                getLaudosCCosto().add(laudo);
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
    
    
    public static Boolean agregarLaudoCCosto(LaudoCCosto laudoccosto) {
        

        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
             String consultaSQL = "INSERT INTO LAUDO_CCOSTO(idcentro_costo, id_laudo_general, costo, estado) VALUES( ?, ?, ?, ?);";

            pst = conexion.getConexion().prepareStatement(consultaSQL);
            


            pst.setString(1, laudoccosto.getCentro_costo().getCentro_de_costo());
            pst.setInt(2, laudoccosto.getIdlaudo());
            pst.setDouble(3, laudoccosto.getCosto());
            pst.setString(4, laudoccosto.getEstado());
            
//            String test = String.valueOf(laudoccosto.getUnidad_medida().getId());
//            test = String.valueOf(laudoccosto.getEstado());


            pst.executeUpdate();
        } catch (SQLException e) {
            creado = false;
            System.err.println("ERROR: " + e);
            System.out.println("ERROR: " + e);
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
    
    
//    
//    public static void main(String[] args) {
//         Laudo laudo = new Laudo();
//         laudo.setCosto(2.2);
//         laudo.setDescripcion("prueba");
//         
//         Unidad_Medida unidad_medida = new Unidad_Medida();
//         
//         unidad_medida.setId(12);
//         
//         laudo.setUnidad_medida(unidad_medida);
//         laudo.setEstado("A");
//         
//         agregarLaudo(laudo);
//    }
//    
//    public static Boolean agregarLaudo(Laudo laudo) {
//        
//
//        Boolean creado = true;
//        Conexion conexion = new Conexion();
//        conexion.abrirConexion();
//
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//        try {
//             String consultaSQL = "INSERT INTO LAUDO(descripcion, costo, tipo_unidad, estado) VALUES( ?, ?, ?, ?);";
//
//            pst = conexion.getConexion().prepareStatement(consultaSQL);
//
//            pst.setString(1, laudo.getDescripcion());
//            pst.setDouble(2, laudo.getCosto());
//            pst.setInt(3, laudo.getUnidad_medida().getId());
//            pst.setString(4, laudo.getEstado());
//            
//            String test = String.valueOf(laudo.getUnidad_medida().getId());
//            test = String.valueOf(laudo.getEstado());
//
//
//            pst.executeUpdate();
//        } catch (SQLException e) {
//            creado = false;
//            System.err.println("ERROR: " + e);
//            System.out.println("ERROR: " + e);
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
//        return creado;
//    }
    
    
}
