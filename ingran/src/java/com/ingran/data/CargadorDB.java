package com.ingran.data;

import com.ingran.model.Catorcena;
import com.ingran.model.reportes.Cargador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargadorDB extends Conexion {

    private List<Cargador> cargadors = new ArrayList<>();

    public void obtenerCargadorCatorcenal(Catorcena catorcena) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCargadors().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT phe.empleado, phd.tipo_de_hora, phe.centro_de_costo, phd.fase, py.PROYECTO, SUM(phd.horas), (SELECT pus.hora_normal FROM PUESTO_SALARIO pus WHERE pus.puesto = em.PUESTO) FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO em ON phe.empleado = em.EMPLEADO WHERE phe.catorcena = ? GROUP BY phe.empleado, phd.tipo_de_hora, phe.centro_de_costo, phd.fase, py.PROYECTO, em.PUESTO;";

            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                Cargador cargador = new Cargador();

                cargador.setEmpleado(rs.getString(1));
                cargador.setConcepto(rs.getString(2));
                cargador.setCentro_de_costo(rs.getString(3));
                cargador.setNomina("PROY");
                cargador.setFase(rs.getString(4));
                cargador.setProyecto(rs.getString(5));
                cargador.setCantidad(rs.getString(6));
                cargador.setMonto(rs.getString(7));

                cargadors.add(cargador);
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

    public void obtenerCargadorSemanal(Catorcena catorcena, int semana) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCargadors().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "";
            if (semana == 1) {
                consultaSQL = "SELECT phe.empleado, phd.tipo_de_hora, phe.centro_de_costo, phd.fase, py.PROYECTO, SUM(phd.horas), (SELECT pus.hora_normal FROM PUESTO_SALARIO pus WHERE pus.puesto = em.PUESTO) FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN PRUEBA.ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO INNER JOIN PRUEBA.ingran.EMPLEADO em ON phe.empleado = em.EMPLEADO WHERE phe.catorcena = ? AND phe.fecha BETWEEN CAST(? AS DATETIME) AND CAST(DATEADD(DAY, 6, ?) AS DATETIME) GROUP BY phe.empleado, phd.tipo_de_hora, phe.centro_de_costo, phd.fase, py.PROYECTO, em.PUESTO;";
            }
            if (semana == 2) {
                consultaSQL = "SELECT phe.empleado, phd.tipo_de_hora, phe.centro_de_costo, phd.fase, py.PROYECTO, SUM(phd.horas), (SELECT pus.hora_normal FROM PUESTO_SALARIO pus WHERE pus.puesto = em.PUESTO) FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN PRUEBA.ingran.PROYECTO_PY py ON phe.centro_de_costo = py.CENTRO_COSTO INNER JOIN PRUEBA.ingran.EMPLEADO em ON phe.empleado = em.EMPLEADO WHERE phe.catorcena = ? AND phe.fecha BETWEEN CAST(DATEADD(DAY, 7, ?) AS DATETIME) AND CAST(? AS DATETIME) GROUP BY phe.empleado, phd.tipo_de_hora, phe.centro_de_costo, phd.fase, py.PROYECTO, em.PUESTO;";
            }

            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setString(2, catorcena.getFecha_inicio_formato());
            if (semana == 1) {
                pst.setString(3, catorcena.getFecha_inicio_formato());
            }
            if (semana == 2) {
                pst.setString(3, catorcena.getFecha_fin_formato());
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                Cargador cargador = new Cargador();

                cargador.setEmpleado(rs.getString(1));
                cargador.setConcepto(rs.getString(2));
                cargador.setCentro_de_costo(rs.getString(3));
                cargador.setNomina("PROY");
                cargador.setFase(rs.getString(4));
                cargador.setProyecto(rs.getString(5));
                cargador.setCantidad(rs.getString(6));
                cargador.setMonto(rs.getString(7));

                cargadors.add(cargador);
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

    public List<Cargador> getCargadors() {
        return cargadors;
    }

    public void setCargadors(List<Cargador> cargadors) {
        this.cargadors = cargadors;
    }
}
