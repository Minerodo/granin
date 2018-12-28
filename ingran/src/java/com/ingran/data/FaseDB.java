package com.ingran.data;

import com.ingran.model.Fase;
import com.ingran.model.Proyecto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaseDB {

    public static List<Fase> obtenerFases(Proyecto proyecto) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        List<Fase> list_fases = new ArrayList<>();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //String consultaSQL = "SELECT f.PROYECTO, f.FASE, f.NOMBRE FROM " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY f WHERE f.PROYECTO = ? AND f.ACTIVO = 'S' AND f.DESCRIPCION = 'Mano de obra' ORDER BY f.FASE ASC;";

            String consultaSQL = "SELECT f.PROYECTO, f.FASE, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(f.FASE, '.00',''), LEN(REPLACE(f.FASE, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = ?) AS NOMBRE FROM " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY f WHERE f.PROYECTO = ? AND f.ACTIVO = 'S' AND f.DESCRIPCION = 'Mano de obra' ORDER BY f.FASE ASC;";
            
            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, proyecto.getProyecto());
            pst.setString(2, proyecto.getProyecto());

            rs = pst.executeQuery();

            while (rs.next()) {
                Fase fase = new Fase();
                
                fase.setProyecto(rs.getString(1));
                fase.setFase(rs.getString(2));
                fase.setNombre(rs.getString(3));

                list_fases.add(fase);
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
        return list_fases;
    }
}
