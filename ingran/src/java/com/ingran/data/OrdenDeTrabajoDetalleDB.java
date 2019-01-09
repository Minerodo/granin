package com.ingran.data;

import com.ingran.model.Fase;
import com.ingran.model.Laudo;
import com.ingran.model.OrdenDeTrabajo;
import com.ingran.model.OrdenDeTrabajoDetalle;
import com.ingran.model.Unidad_Medida;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenDeTrabajoDetalleDB extends Conexion {

    private List<OrdenDeTrabajoDetalle> ordene_de_trabajos_detalle = new ArrayList<>();

    public List<OrdenDeTrabajoDetalle> getOrdene_de_trabajos_detalle() {
        return ordene_de_trabajos_detalle;
    }

    public void setOrdene_de_trabajos_detalle(List<OrdenDeTrabajoDetalle> ordene_de_trabajos_detalle) {
        this.ordene_de_trabajos_detalle = ordene_de_trabajos_detalle;
    }

    public void obtenerOrdenDeTrabajoDetalle(OrdenDeTrabajo odt) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getOrdene_de_trabajos_detalle().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT odtd.id, l.id, l.descripcion, um.nombre, odtd.cantidad, odtd.precio_unitario, (odtd.cantidad * odtd.precio_unitario), odtd.fase FROM ORDEN_DE_TRABAJO_DETALLE odtd INNER JOIN LAUDO l ON odtd.laudo = l.id INNER JOIN UNIDAD_MEDIDA um ON l.tipo_unidad = um.id WHERE odtd.orden_de_trabajo = ?;";

            pst = getConexion().prepareStatement(consultaSQL);
            
            pst.setInt(1, odt.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                OrdenDeTrabajoDetalle odtd = new OrdenDeTrabajoDetalle();

                odtd.setId(rs.getInt(1));
                
                Laudo laudo = new Laudo();
                laudo.setId(rs.getInt(2));
                laudo.setDescripcion(rs.getString(3));

                Unidad_Medida um = new Unidad_Medida();
                um.setNombre(rs.getString(4));

                laudo.setUnidad_medida(um);

                odtd.setLaudo(laudo);

                odtd.setCantidad(rs.getDouble(5));
                odtd.setPrecio_unitario(rs.getDouble(6));
                odtd.setSubtotal(rs.getDouble(7));
                
                Fase fase = new Fase();
                fase.setFase(rs.getString(8));
                //FaseDB.

                ordene_de_trabajos_detalle.add(odtd);
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

    public static boolean crearOrdenDeTrabajoDetalle(OrdenDeTrabajo odt, OrdenDeTrabajoDetalle odtd) {
        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "INSERT INTO ORDEN_DE_TRABAJO_DETALLE(orden_de_trabajo, laudo, cantidad, precio_unitario) VALUES(?, ?, ?, ?);";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, odt.getId());
            pst.setInt(2, odtd.getLaudo().getId());
            pst.setDouble(3, odtd.getCantidad());
            pst.setDouble(4, odtd.getLaudo().getCosto());

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
