package com.ingran.data;

import com.ingran.model.OrdenDeTrabajo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenDeTrabajoDetalleDB extends Conexion {

    private List<OrdenDeTrabajo> ordene_de_trabajos = new ArrayList<>();

    public List<OrdenDeTrabajo> getOrdene_de_trabajos() {
        return ordene_de_trabajos;
    }

    public void setOrdene_de_trabajos(List<OrdenDeTrabajo> ordene_de_trabajos) {
        this.ordene_de_trabajos = ordene_de_trabajos;
    }

    public void obtenerCatorcenas() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getOrdene_de_trabajos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT id, descripcion, fecha_inicio, fecha_fin, activo FROM CATORCENA ORDER BY fecha_inicio DESC, fecha_fin DESC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
//                Catorcena catorcena = new Catorcena();
//
//                catorcena.setId(rs.getInt(1));
//                catorcena.setDescripcion(rs.getString(2));
//                catorcena.setFecha_inicio(rs.getDate(3));
//                catorcena.setFecha_fin(rs.getDate(4));
//                catorcena.setActivo(rs.getBoolean(5));
//
//                catorcenas.add(catorcena);
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
}
