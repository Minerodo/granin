package com.ingran.data;

import com.ingran.model.Laudo;
import com.ingran.model.Unidad_Medida;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaudoDB extends Conexion {

    private List<Laudo> laudos = new ArrayList<>();

    public List<Laudo> getLaudos() {
        return laudos;
    }

    public void setLaudos(List<Laudo> laudos) {
        this.laudos = laudos;
    }

    public void obtenerLaudos() {
        abrirConexion();

        getLaudos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT l.ID, l.DESCRIPCION, l.COSTO, u.NOMBRE, l.ESTADO FROM dbo.LAUDO l, dbo.UNIDAD_MEDIDA u WHERE l.TIPO_UNIDAD = u.id;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Laudo laudo = new Laudo();

                laudo.setId(rs.getInt(1));
                laudo.setDescripcion(rs.getString(2));
                laudo.setCosto(rs.getDouble(3));
                Unidad_Medida unidad_medida = new Unidad_Medida();
                unidad_medida.setNombre(rs.getString(4));
                laudo.setUnidad_medida(unidad_medida);
                laudo.setEstado(rs.getString(5));

                getLaudos().add(laudo);
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

    public static void obtenerLaudo(Laudo laudo) {

        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT l.ID, l.DESCRIPCION, l.COSTO,  u.NOMBRE, l.TIPO_UNIDAD, l.ESTADO FROM dbo.LAUDO l, dbo.UNIDAD_MEDIDA u WHERE l.TIPO_UNIDAD = u.id AND l.ID = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, laudo.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                laudo.setId(rs.getInt(1));
                laudo.setDescripcion(rs.getString(2));
                laudo.setCosto(rs.getDouble(3));
                
                Unidad_Medida udm = new Unidad_Medida();
                udm.setNombre(rs.getString(4));
                udm.setId(rs.getInt(5));
                laudo.setUnidad_medida(udm);
                
                laudo.setEstado(rs.getString(6));
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
    }

    public static void main(String[] args) {
        Laudo laudo = new Laudo();
        laudo.setCosto(2.2);
        laudo.setDescripcion("prueba");

        Unidad_Medida unidad_medida = new Unidad_Medida();

        unidad_medida.setId(12);

        laudo.setUnidad_medida(unidad_medida);
        laudo.setEstado("A");

        agregarLaudo(laudo);
    }

    public Double getMontoLaudo(Integer idLaudo) {
        Double costo = 0.0d;

        abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT l.COSTO FROM dbo.LAUDO l WHERE l.id = ?;";

            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, idLaudo);

            rs = pst.executeQuery();

            while (rs.next()) {
                costo = rs.getDouble(1);

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

        return costo;
    }

    public static Boolean agregarLaudo(Laudo laudo) {

        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "INSERT INTO LAUDO(descripcion, costo, tipo_unidad, estado) VALUES( ?, ?, ?, ?);";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, laudo.getDescripcion());
            pst.setDouble(2, laudo.getCosto());
            pst.setInt(3, laudo.getUnidad_medida().getId());
            pst.setString(4, laudo.getEstado());

            String test = String.valueOf(laudo.getUnidad_medida().getId());
            test = String.valueOf(laudo.getEstado());

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

    public static Boolean editarLaudo(Laudo laudo) {

        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "update LAUDO set descripcion = ?, costo = ?, tipo_unidad = ?, estado = ? WHERE id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, laudo.getDescripcion());
            pst.setDouble(2, laudo.getCosto());
            pst.setInt(3, laudo.getUnidad_medida().getId());
            pst.setString(4, laudo.getEstado());

            String test = String.valueOf(laudo.getUnidad_medida().getId());
            test = String.valueOf(laudo.getEstado());

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

}
