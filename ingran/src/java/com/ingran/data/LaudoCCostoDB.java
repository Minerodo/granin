package com.ingran.data;

import com.ingran.model.CentroDeCosto;
import com.ingran.model.Laudo;
import com.ingran.model.LaudoCCosto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaudoCCostoDB extends Conexion {

    private List<LaudoCCosto> laudos = new ArrayList<>();

    public List<LaudoCCosto> getLaudosCCosto() {
        return laudos;
    }

    public void setLaudosCCosto(List<LaudoCCosto> laudos) {
        this.laudos = laudos;
    }

    public void obtenerLaudosCCostos(Laudo laudo) {
        abrirConexion();

        getLaudosCCosto().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            String consultaSQL = "SELECT lcc.id, lcc.idcentro_costo, lcc.estado, lcc.costo FROM LAUDO_CCOSTO lcc inner join LAUDO l on lcc.id_laudo_general = l.id where l.id = ?;";

            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, laudo.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                LaudoCCosto lcc = new LaudoCCosto();

                lcc.setId_laudo(rs.getInt(1));

                CentroDeCosto cdc = new CentroDeCosto();
                cdc.setCentro_de_costo(rs.getString(2));
                CentroDeCostoDB.obtenerCentroDeCosto(cdc);
                lcc.setCentro_costo(cdc);

                lcc.setEstado(rs.getString(3));
                lcc.setCosto(rs.getDouble(4));

                getLaudosCCosto().add(lcc);
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
            pst.setInt(2, laudoccosto.getId());
            pst.setDouble(3, laudoccosto.getCosto());
            pst.setString(4, laudoccosto.getEstado());

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
}
