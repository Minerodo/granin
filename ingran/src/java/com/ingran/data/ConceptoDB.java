package com.ingran.data;

import com.ingran.model.Concepto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConceptoDB extends Conexion {

    private List<Concepto> conceptos = new ArrayList<>();

    public void obtenerConceptos() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getConceptos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT CONCEPTO, DESCRIPCION FROM " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO WHERE CONCEPTO LIKE 'BB%' ORDER BY CONCEPTO ASC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Concepto concepto = new Concepto();

                concepto.setConcepto(rs.getString(1));
                concepto.setDescripcion(rs.getString(2));

                getConceptos().add(concepto);
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

    public static Concepto obtenerConcepto(Concepto concepto) {
        Concepto concept = new Concepto();

        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT CONCEPTO, DESCRIPCION FROM " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO WHERE CONCEPTO = ? OR DESCRIPCION = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, concepto.getConcepto());
            pst.setString(2, concepto.getDescripcion());

            rs = pst.executeQuery();

            while (rs.next()) {
                concept.setConcepto(rs.getString(1));
                concept.setDescripcion(rs.getString(2));
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
        return concept;
    }

    public List<Concepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<Concepto> conceptos) {
        this.conceptos = conceptos;
    }
}
