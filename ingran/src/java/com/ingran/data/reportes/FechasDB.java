package com.ingran.data.reportes;

import com.ingran.data.Conexion;
import com.ingran.model.Catorcena;
import com.ingran.util.Fecha;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FechasDB extends Conexion {

    private List<String> fechas = new ArrayList<>();

    public void obtenerFechasBodeguero(Catorcena catorcena, int bodeguero) {
        String consultaSQL = "SELECT phe.fecha FROM PLANILLA_HORA_ENCABEZADO phe WHERE phe.catorcena = ? AND phe.bodeguero = ? GROUP BY phe.fecha ORDER BY phe.fecha ASC;";
        obtenerFechas(consultaSQL, catorcena, bodeguero);
    }

    public void obtenerFechasIngeniero(Catorcena catorcena, int ingeniero) {
        String consultaSQL = "SELECT phe.fecha FROM PLANILLA_HORA_ENCABEZADO phe WHERE phe.catorcena = ? AND phe.centro_de_costo IN (SELECT centro_costo FROM USUARIO_CENTRO_COSTO WHERE usuario = ?) GROUP BY phe.fecha ORDER BY phe.fecha ASC;";
        obtenerFechas(consultaSQL, catorcena, ingeniero);
    }

    private void obtenerFechas(String consultaSQL, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getFechas().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setInt(2, usuario);

            rs = pst.executeQuery();

            while (rs.next()) {
                String fecha;

                fecha = rs.getString(1);

                fechas.add(Fecha.convertirFecha(fecha));
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
    
    public void obtenerFechasAdministrador(Catorcena catorcena){
        String consultaSQL  = "SELECT phe.fecha FROM PLANILLA_HORA_ENCABEZADO phe WHERE phe.catorcena = ? GROUP BY phe.fecha ORDER BY phe.fecha ASC;";
        obtenerFechas(consultaSQL, catorcena);
    }
    
    private void obtenerFechas(String consultaSQL, Catorcena catorcena) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getFechas().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                String fecha;

                fecha = rs.getString(1);

                fechas.add(Fecha.convertirFecha(fecha));
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

    public List<String> getFechas() {
        return fechas;
    }

    public void setFechas(List<String> fechas) {
        this.fechas = fechas;
    }
}
