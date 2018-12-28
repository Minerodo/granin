package com.ingran.data;

import com.ingran.model.Concepto;
import com.ingran.model.Empleado;
import com.ingran.model.Fase;
import com.ingran.model.PlanillaHoraDetalle;
import com.ingran.model.PlanillaHoraEncabezado;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlanillaHoraDetalleDB {

    public static Boolean agregarPlanillaDetalle(Connection connect, PreparedStatement pst, int planilla_hora_encabezado, PlanillaHoraDetalle planilla_hora_detalle) {
        Boolean creado = true;

        planilla_hora_detalle.calcularDatos();

        try {
            String consultaSQL = "INSERT INTO PLANILLA_HORA_DETALLE (planilla_hora_encabezado, fase, hora_inicio, hora_fin, tipo_de_hora, cantidad_de_horas, horas) VALUES (?, ?, ?, ?, ?, ?, ?);";

            pst = connect.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, planilla_hora_encabezado);
            pst.setString(2, planilla_hora_detalle.getFase().getFase());
            pst.setString(3, planilla_hora_detalle.getHora_inicio());
            pst.setString(4, planilla_hora_detalle.getHora_fin());

            pst.setString(5, ConceptoDB.obtenerConcepto(planilla_hora_detalle.getConcepto()).getConcepto());

            pst.setString(6, planilla_hora_detalle.getCantidad_hora());
            pst.setDouble(7, planilla_hora_detalle.getHoras());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                creado = false;
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    planilla_hora_detalle.setId(generatedKeys.getInt(1));
                } else {
                    creado = false;
                }
            }

        } catch (SQLException e) {
            creado = false;
        }
        return creado;
    }

    public static void obtenerPlanillaEncabezado(PlanillaHoraEncabezado phe) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT phd.fase, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(phd.fase, '.00',''), LEN(REPLACE(phd.fase, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = py.PROYECTO) AS DESCRIPCION, phd.hora_inicio, phd.hora_fin, tdh.DESCRIPCION FROM PLANILLA_HORA_DETALLE phd JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO JOIN " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY fpy ON phd.fase = fpy.FASE JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO tdh ON phd.tipo_de_hora = tdh.CONCEPTO WHERE fpy.PROYECTO = py.PROYECTO AND phd.planilla_hora_encabezado = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, phe.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                PlanillaHoraDetalle phd = new PlanillaHoraDetalle();

                Fase fase = new Fase();
                fase.setFase(rs.getString(1));
                fase.setNombre(rs.getString(2));
                phd.setFase(fase);

                phd.setHora_inicio(rs.getString(3));
                phd.setHora_fin(rs.getString(4));
                phd.calcularDatos();

                Concepto concepto = new Concepto();
                concepto.setDescripcion(rs.getString(5));
                phd.setConcepto(concepto);

                phe.getPlanilla_hora_detalle().add(phd);
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

    public static void eliminarPlanillaDetalle(PlanillaHoraEncabezado phe) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "UPDATE PLANILLA_HORA_ENCABEZADO SET enviado = 0, enviado_fecha = NULL, cantidad_de_horas = NULL, horas = 0 WHERE id = ?;DELETE FROM PLANILLA_HORA_DETALLE WHERE planilla_hora_encabezado = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, phe.getId());
            pst.setInt(2, phe.getId());

            pst.executeUpdate();

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
    
    public static String validarSiExiste(Empleado empleado, Date fecha, PlanillaHoraDetalle phd){
        String retorno = "";
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT hora_inicio, hora_fin, fecha FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id WHERE phe.empleado = ? AND phe.fecha = ? AND ((phd.hora_inicio < ? AND phd.hora_fin > ?) OR (phd.hora_inicio < ? AND phd.hora_fin > ?) OR (phd.hora_inicio >= ? AND phd.hora_fin <= ?));";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, empleado.getEmpleado());
            pst.setDate(2, fecha);
            pst.setString(3, phd.getHora_inicio());
            pst.setString(4, phd.getHora_inicio());
            pst.setString(5, phd.getHora_fin());
            pst.setString(6, phd.getHora_fin());
            pst.setString(7, phd.getHora_inicio());
            pst.setString(8, phd.getHora_fin());
//            pst.setString(9, phd.getHora_inicio());
//            pst.setString(10, phd.getHora_fin());

            rs = pst.executeQuery();
            int i = 0;
            while (rs.next()) {
                retorno+= "Hora inicio: " + rs.getString(1).substring(0, 5) + " Hora fin: " + rs.getString(2).substring(0, 5) + "<br>";
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
        return retorno;
    }
}
