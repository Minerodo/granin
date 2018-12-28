package com.ingran.data.reportes;

import com.ingran.data.Conexion;
import com.ingran.model.Catorcena;
import com.ingran.model.reportes.PlanillaHoraReportes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanillaHoraReportesDB extends Conexion {

    private List<PlanillaHoraReportes> planilla_hora_reportes = new ArrayList<>();

    public void obtenerHorasPorPersonaBodeguero(FechasDB fechas, Catorcena catorcena, int bodeguero) {
        String from = " FROM(SELECT e.NOMBRE, p.DESCRIPCION, phd.tipo_de_hora, phe.fecha, phd.horas, phd.administrador_estado as estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p ON e.PUESTO = p.PUESTO JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO tdh ON phd.tipo_de_hora = tdh.CONCEPTO WHERE phe.catorcena = ? AND phe.bodeguero = ? GROUP BY e.NOMBRE, p.DESCRIPCION, phd.tipo_de_hora, phe.fecha, phd.horas, phd.administrador_estado) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerHorasPorPersona(from, fechas, catorcena, bodeguero);
    }

    public void obtenerHorasPorPersonaIngeniero(FechasDB fechas, Catorcena catorcena, int ingeniero) {
        String from = " FROM(SELECT e.NOMBRE, p.DESCRIPCION, phd.tipo_de_hora, phe.fecha, phd.horas, phd.ingeniero_estado as estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p ON e.PUESTO = p.PUESTO JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO tdh ON phd.tipo_de_hora = tdh.CONCEPTO WHERE phe.catorcena = ? AND phe.enviado = 1 AND phe.centro_de_costo IN (SELECT centro_costo FROM USUARIO_CENTRO_COSTO WHERE usuario = ? ) GROUP BY e.NOMBRE, p.DESCRIPCION, phd.tipo_de_hora, phe.fecha, phd.horas, phd.ingeniero_estado ) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerHorasPorPersona(from, fechas, catorcena, ingeniero);
    }
    
    public void obtenerHorasPorPersonaAdministrador(FechasDB fechas, Catorcena catorcena) {
        String from = " FROM(SELECT e.NOMBRE, p.DESCRIPCION, phd.tipo_de_hora, phe.fecha, phd.horas, phd.administrador_estado AS estado FROM PLANILLA_HORA_DETALLE phd JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p ON e.PUESTO = p.PUESTO WHERE phe.catorcena = ? AND phe.enviado = ? AND phd.ingeniero_enviado = 1 AND phd.ingeniero_estado = 'APROBADO' GROUP BY e.NOMBRE, p.DESCRIPCION, phd.tipo_de_hora, phe.fecha, phd.horas, phd.administrador_estado ) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerHorasPorPersona(from, fechas, catorcena, 1);
    }

    private void obtenerHorasPorPersona(String from, FechasDB fechas, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getPlanilla_hora_reportes().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        String consultaSQL = "SELECT nombre, DESCRIPCION, tipo_de_hora, estado";

        String titulos = "";
        String datos = "";
        int i = 0;
        for (String fecha : fechas.getFechas()) {
            titulos += ", COALESCE(\"" + fecha + "\", 0) \"" + fecha + "\"";
            if (i == 0) {
                datos += "\"" + fecha + "\"";
                i++;
            } else {
                datos += ", \"" + fecha + "\"";
            }
        }

        consultaSQL += titulos;
        consultaSQL += from;
        consultaSQL += datos;
        consultaSQL += ")) AS PIVOTABLE ORDER BY nombre ASC, tipo_de_hora ASC, estado ASC;";

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setInt(2, usuario);

            rs = pst.executeQuery();

            while (rs.next()) {
                PlanillaHoraReportes horas_por_persona = new PlanillaHoraReportes();

                horas_por_persona.setNombre(rs.getString(1));
                horas_por_persona.setCargo(rs.getString(2));
                horas_por_persona.setTipo_de_hora(rs.getString(3));
                horas_por_persona.setEstado(rs.getString(4));

                List<String> dato = new ArrayList<>();
                double total = 0;
                for (i = 0; i < fechas.getFechas().size(); i++) {
                    String dat = "";
                    dat = rs.getString(i + 5);
                    dato.add(dat);
                    total += Double.parseDouble(dat);
                }

                horas_por_persona.setFechas(dato);
                horas_por_persona.setTotal_general(String.valueOf(total));

                planilla_hora_reportes.add(horas_por_persona);
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

    public void obtenerActividadPorPersonaBodeguero(FechasDB fechas, Catorcena catorcena, int bodeguero) {
        String from = " FROM(SELECT e.NOMBRE, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(phd.fase, '.00',''), LEN(REPLACE(phd.fase, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = py.PROYECTO) AS descripcion_de_fase, phe.fecha, phd.horas, phd.administrador_estado AS estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO WHERE phe.catorcena = ? AND phe.bodeguero = ? GROUP BY e.NOMBRE, phd.fase, phd.horas, py.PROYECTO, phe.fecha, phd.administrador_estado ) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerActividadPorPersona(from, fechas, catorcena, bodeguero);
    }

    public void obtenerActividadPorPersonaIngeniero(FechasDB fechas, Catorcena catorcena, int ingeniero) {
        String from = " FROM(SELECT e.NOMBRE, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(phd.fase, '.00',''), LEN(REPLACE(phd.fase, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = py.PROYECTO) AS descripcion_de_fase, phe.fecha, phd.horas, phd.ingeniero_estado AS estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO WHERE phe.catorcena = ? AND phe.enviado = 1 AND phe.centro_de_costo IN (SELECT centro_costo FROM USUARIO_CENTRO_COSTO WHERE usuario = ?) GROUP BY e.NOMBRE, phd.fase, py.PROYECTO, phe.fecha, phd.horas, phd.ingeniero_estado) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerActividadPorPersona(from, fechas, catorcena, ingeniero);
    }
    
    public void obtenerActividadPorPersonaAdministrador(FechasDB fechas, Catorcena catorcena) {
        String from = " FROM(SELECT e.NOMBRE, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(phd.fase, '.00',''), LEN(REPLACE(phd.fase, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = py.PROYECTO) AS descripcion_de_fase, phe.fecha, phd.horas, phd.administrador_estado AS estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO WHERE phe.catorcena = ? AND phe.enviado = ? AND phd.ingeniero_enviado = 1 AND phd.ingeniero_estado = 'APROBADO' GROUP BY e.NOMBRE, phd.fase, py.PROYECTO, phe.fecha, phd.horas, phd.administrador_estado) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerActividadPorPersona(from, fechas, catorcena, 1);
    }

    private void obtenerActividadPorPersona(String from, FechasDB fechas, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getPlanilla_hora_reportes().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        String consultaSQL = "SELECT nombre, descripcion_de_fase, estado";

        String titulos = "";
        String datos = "";
        int i = 0;
        for (String fecha : fechas.getFechas()) {
            titulos += ", COALESCE(\"" + fecha + "\", 0) \"" + fecha + "\"";
            if (i == 0) {
                datos += "\"" + fecha + "\"";
                i++;
            } else {
                datos += ", \"" + fecha + "\"";
            }
        }

        consultaSQL += titulos;
        consultaSQL += from;
        consultaSQL += datos;
        consultaSQL += ")) AS PIVOTABLE ORDER BY nombre ASC, descripcion_de_fase ASC, estado ASC;";

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setInt(2, usuario);

            rs = pst.executeQuery();

            while (rs.next()) {
                PlanillaHoraReportes horas_por_persona = new PlanillaHoraReportes();

                horas_por_persona.setNombre(rs.getString(1));
                horas_por_persona.setActividad(rs.getString(2));
                horas_por_persona.setEstado(rs.getString(3));

                List<String> dato = new ArrayList<>();
                double total = 0;
                for (i = 0; i < fechas.getFechas().size(); i++) {
                    String dat = "";
                    dat = rs.getString(i + 4);
                    dato.add(dat);
                    total += Double.parseDouble(dat);
                }

                horas_por_persona.setFechas(dato);
                horas_por_persona.setTotal_general(String.valueOf(total));

                planilla_hora_reportes.add(horas_por_persona);
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

    public void obtenerPersonaPorActividadBodeguero(FechasDB fechas, Catorcena catorcena, int bodeguero) {
        String from = " FROM(SELECT e.NOMBRE, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(phd.fase, '.00',''), LEN(REPLACE(phd.fase, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = py.PROYECTO) AS descripcion_de_fase, phe.fecha, phd.horas, phd.administrador_estado AS estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO WHERE phe.catorcena = ? AND phe.bodeguero = ? GROUP BY e.NOMBRE, phd.fase, phd.horas, py.PROYECTO, phe.fecha, phd.administrador_estado) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerPersonasPorActividad(from, fechas, catorcena, bodeguero);
    }

    public void obtenerPersonaPorActividadIngeniero(FechasDB fechas, Catorcena catorcena, int ingeniero) {
        String from = " FROM(SELECT e.NOMBRE, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(phd.fase, '.00',''), LEN(REPLACE(phd.fase, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = py.PROYECTO) AS descripcion_de_fase, phe.fecha, phd.horas, phd.ingeniero_estado AS estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO WHERE phe.catorcena = ? AND phe.enviado = 1 AND phe.centro_de_costo IN (SELECT centro_costo FROM USUARIO_CENTRO_COSTO WHERE usuario = ?) GROUP BY e.NOMBRE, phd.fase, py.PROYECTO, phe.fecha, phd.horas, phd.ingeniero_estado) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerPersonasPorActividad(from, fechas, catorcena, ingeniero);
    }
    
    public void obtenerPersonaPorActividadAdministrador(FechasDB fechas, Catorcena catorcena) {
        String from = " FROM(SELECT e.NOMBRE, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(phd.fase, '.00',''), LEN(REPLACE(phd.fase, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = py.PROYECTO) AS descripcion_de_fase, phe.fecha, phd.horas, phd.administrador_estado AS estado FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO WHERE phe.catorcena = ? AND phe.enviado = ? AND phd.ingeniero_enviado = 1 AND phd.ingeniero_estado = 'APROBADO' GROUP BY e.NOMBRE, phd.fase, py.PROYECTO, phe.fecha, phd.horas, phd.administrador_estado) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        obtenerPersonasPorActividad(from, fechas, catorcena, 1);
    }

    private void obtenerPersonasPorActividad(String from, FechasDB fechas, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getPlanilla_hora_reportes().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        String consultaSQL = "SELECT descripcion_de_fase, nombre, estado";

        String titulos = "";
        String datos = "";
        int i = 0;
        for (String fecha : fechas.getFechas()) {
            titulos += ", COALESCE(\"" + fecha + "\", 0) \"" + fecha + "\"";
            if (i == 0) {
                datos += "\"" + fecha + "\"";
                i++;
            } else {
                datos += ", \"" + fecha + "\"";
            }
        }

        consultaSQL += titulos;
        consultaSQL += from;
        consultaSQL += datos;
        consultaSQL += ")) AS PIVOTABLE ORDER BY descripcion_de_fase ASC, nombre ASC, estado ASC;";

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setInt(2, usuario);

            rs = pst.executeQuery();

            while (rs.next()) {
                PlanillaHoraReportes horas_por_persona = new PlanillaHoraReportes();

                horas_por_persona.setActividad(rs.getString(1));
                horas_por_persona.setNombre(rs.getString(2));
                horas_por_persona.setEstado(rs.getString(3));

                List<String> dato = new ArrayList<>();
                double total = 0;
                for (i = 0; i < fechas.getFechas().size(); i++) {
                    String dat = "";
                    dat = rs.getString(i + 4);
                    dato.add(dat);
                    total += Double.parseDouble(dat);
                }

                horas_por_persona.setFechas(dato);
                horas_por_persona.setTotal_general(String.valueOf(total));

                planilla_hora_reportes.add(horas_por_persona);
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
    
    //Reportes de Administrador
    
    public void obtenerConsolidadoTiempoEfectivo(FechasDB fechas, Catorcena catorcena){
        if (getConexion() == null) {
            abrirConexion();
        }

        getPlanilla_hora_reportes().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        String consultaSQL = "SELECT nombre, EMPLEADO, tipo_de_hora, administrador_estado";

        String titulos = "";
        String datos = "";
        int i = 0;
        for (String fecha : fechas.getFechas()) {
            titulos += ", COALESCE(\"" + fecha + "\", 0) \"" + fecha + "\"";
            if (i == 0) {
                datos += "\"" + fecha + "\"";
                i++;
            } else {
                datos += ", \"" + fecha + "\"";
            }
        }

        consultaSQL += titulos;
        consultaSQL += " FROM(SELECT e.NOMBRE, e.EMPLEADO, tdh.CONCEPTO AS tipo_de_hora, phd.administrador_estado, phe.fecha, phd.horas FROM PLANILLA_HORA_ENCABEZADO phe JOIN PLANILLA_HORA_DETALLE phd ON phe.id = phd.planilla_hora_encabezado JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p ON e.PUESTO = p.PUESTO JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO tdh ON phd.tipo_de_hora = tdh.CONCEPTO WHERE phd.ingeniero_enviado = 1 AND phe.catorcena = ? AND phe.enviado = 1) SOURCE PIVOT( SUM(SOURCE.horas) FOR SOURCE.fecha in(";
        consultaSQL += datos;
        consultaSQL += ")) AS PIVOTABLE ORDER BY nombre ASC, tipo_de_hora ASC;";

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                PlanillaHoraReportes planilla_hora_reporte = new PlanillaHoraReportes();

                planilla_hora_reporte.setNombre(rs.getString(1));
                planilla_hora_reporte.setCargo(rs.getString(2));
                planilla_hora_reporte.setTipo_de_hora(rs.getString(3));
                planilla_hora_reporte.setActividad(rs.getString(4));

                List<String> dato = new ArrayList<>();
                double total = 0;
                for (i = 0; i < fechas.getFechas().size(); i++) {
                    String dat = "";
                    dat = rs.getString(i + 5);
                    dato.add(dat);
                    total += Double.parseDouble(dat);
                }

                planilla_hora_reporte.setFechas(dato);
                planilla_hora_reporte.setTotal_general(String.valueOf(total));

                planilla_hora_reportes.add(planilla_hora_reporte);
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
    
    public void obtenerConsolidadoTiempoPagado(Catorcena catorcena){
        if (getConexion() == null) {
            abrirConexion();
        }

        getPlanilla_hora_reportes().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        String consultaSQL = "SELECT em.NOMBRE, pu.DESCRIPCION, co.CONCEPTO, SUM(phd.horas) AS Horas, (SUM(phd.horas) * (SELECT cs.numerador/cs.denominador FROM CONCEPTO_SALARIO cs WHERE cs.concepto = co.CONCEPTO)) AS HORAS, (SUM(phd.horas) * (SELECT cs.numerador/cs.denominador FROM CONCEPTO_SALARIO cs WHERE cs.concepto = co.CONCEPTO) * (SELECT ps.hora_normal FROM PUESTO_SALARIO ps WHERE ps.puesto = pu.PUESTO)) AS HORAS FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO em ON phe.empleado = em.EMPLEADO INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO pu ON em.PUESTO = pu.PUESTO INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO co ON phd.tipo_de_hora = co.CONCEPTO WHERE phd.administrador_estado = 'APROBADO' AND phe.catorcena = ? GROUP BY em.NOMBRE, pu.DESCRIPCION, co.DESCRIPCION, co.CONCEPTO, pu.PUESTO;";

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                PlanillaHoraReportes planilla_hora_reporte = new PlanillaHoraReportes();

                planilla_hora_reporte.setNombre(rs.getString(1));
                planilla_hora_reporte.setCargo(rs.getString(2));
                planilla_hora_reporte.setTipo_de_hora(rs.getString(3));
                planilla_hora_reporte.setHoras_efectivas(rs.getString(4));
                planilla_hora_reporte.setHoras_pagadas(rs.getString(5));
                planilla_hora_reporte.setTotal_general(rs.getString(6));

                planilla_hora_reportes.add(planilla_hora_reporte);
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

    public List<PlanillaHoraReportes> getPlanilla_hora_reportes() {
        return planilla_hora_reportes;
    }

    public void setPlanilla_hora_reportes(List<PlanillaHoraReportes> planilla_hora_reportes) {
        this.planilla_hora_reportes = planilla_hora_reportes;
    }
}
