package com.ingran.data;

import com.ingran.model.Catorcena;
import com.ingran.model.CentroDeCosto;
import com.ingran.model.Cliente;
import com.ingran.model.Empleado;
import com.ingran.model.PlanillaHoraDetalle;
import com.ingran.model.PlanillaHoraEncabezado;
import com.ingran.model.Proyecto;
import com.ingran.model.Puesto;
import com.ingran.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlanillaHoraEncabezadoDB extends Conexion {

    public static Boolean agregarPlanillaEncabezado(Connection connect, PreparedStatement pst, PlanillaHoraEncabezado planilla_hora_encabezado) {
        Boolean creado = true;

        planilla_hora_encabezado.calcularDatos();

        try {
            String consultaSQL = "INSERT INTO PLANILLA_HORA_ENCABEZADO(bodeguero, catorcena, fecha, cliente, centro_de_costo, empleado, enviado, horario, fecha_real, cantidad_de_horas, horas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?);";

            pst = connect.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, planilla_hora_encabezado.getBodeguero().getId());
            pst.setInt(2, planilla_hora_encabezado.getCatorcena().getId());
            pst.setString(3, planilla_hora_encabezado.getFecha());
            pst.setString(4, planilla_hora_encabezado.getCliente().getCliente());
            pst.setString(5, planilla_hora_encabezado.getCentro_de_costo().getCentro_de_costo());
            pst.setString(6, planilla_hora_encabezado.getEmpleado().getEmpleado());
            pst.setBoolean(7, false);
            pst.setInt(8, planilla_hora_encabezado.getId_horario());

            pst.setString(9, planilla_hora_encabezado.getCantidad_de_horas());
            pst.setDouble(10, planilla_hora_encabezado.getHoras());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                creado = false;
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    planilla_hora_encabezado.setId(generatedKeys.getInt(1));
                } else {
                    creado = false;
                }
            }

        } catch (SQLException e) {
            creado = false;
        }

        if (creado) {
            for (PlanillaHoraDetalle planilla_hora_detalle : planilla_hora_encabezado.getPlanilla_hora_detalle()) {
                if (creado) {
                    creado = creado && PlanillaHoraDetalleDB.agregarPlanillaDetalle(connect, pst, planilla_hora_encabezado.getId(), planilla_hora_detalle);
                }
            }
        }

        return creado;
    }
    
    public static Boolean actualizarPlanillaEncabezado(Connection connect, PreparedStatement pst, PlanillaHoraEncabezado planilla_hora_encabezado) {
        Boolean creado = true;

        planilla_hora_encabezado.calcularDatos();

        if (creado) {
            for (PlanillaHoraDetalle planilla_hora_detalle : planilla_hora_encabezado.getPlanilla_hora_detalle()) {
                if (creado) {
                    creado = creado && PlanillaHoraDetalleDB.agregarPlanillaDetalle(connect, pst, planilla_hora_encabezado.getId(), planilla_hora_detalle);
                }
            }
        }

        return creado;
    }

    public static void obtenerPlanillaEncabezado(PlanillaHoraEncabezado phe) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        
        phe.getPlanilla_hora_detalle().clear();
        
        try {
            String consultaSQL = "SELECT bo.NOMBRE, cat.descripcion, phe.fecha, cl.NOMBRE, cdc.DESCRIPCION, emp.NOMBRE, ph.hora_entrada, ph.hora_salida, ph.receso_hora, ph.receso_minutos, pu.DESCRIPCION, cdc.CENTRO_COSTO FROM PLANILLA.dbo.PLANILLA_HORA_ENCABEZADO phe JOIN CATORCENA cat ON phe.catorcena = cat.id JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO emp ON phe.empleado = emp.EMPLEADO JOIN USUARIO ubo ON phe.bodeguero = ubo.id JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO bo ON ubo.empleado = bo.EMPLEADO JOIN " + Conexion.getDBEXACTUS() + ".ingran.CLIENTE cl ON phe.cliente = cl.CLIENTE JOIN " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc ON phe.centro_de_costo = cdc.CENTRO_COSTO JOIN PROYECTO_HORARIO ph ON phe.horario = ph.id JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO pu ON emp.PUESTO = pu.PUESTO JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON cdc.CENTRO_COSTO = py.CENTRO_COSTO WHERE phe.id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, phe.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                Empleado empBodeguero = new Empleado();
                empBodeguero.setNombre(rs.getString(1));
                Usuario bodeguero = new Usuario();
                bodeguero.setEmpleado(empBodeguero);
                phe.setBodeguero(bodeguero);
                
                Catorcena catorcena = new Catorcena();
                catorcena.setDescripcion(rs.getString(2));
                phe.setCatorcena(catorcena);
                
                phe.setFecha(rs.getString(3));
                
                Cliente cliente = new Cliente();
                cliente.setNombre(rs.getString(4));
                phe.setCliente(cliente);
                
                Proyecto proyecto = new Proyecto();
                proyecto.setDescripcion(rs.getString(5));
                CentroDeCosto cdc = new CentroDeCosto();
                cdc.setProyecto(proyecto);
                
                Empleado empleado = new Empleado();
                empleado.setNombre(rs.getString(6));
                Puesto puesto = new Puesto();
                puesto.setDescripcion(rs.getString(11));
                empleado.setPuesto(puesto);
                phe.setEmpleado(empleado);
                
                phe.setHora_entrada(rs.getString(7));
                phe.setHora_salida(rs.getString(8));
                phe.setTiempo_receso_horas(rs.getInt(9));
                phe.setTiempo_receso_minutos(rs.getInt(10));
                phe.calcularDatos();
                
                cdc.setCentro_de_costo(rs.getString(12));
                phe.setCentro_de_costo(cdc);
            }
            
            PlanillaHoraDetalleDB.obtenerPlanillaEncabezado(phe);
            
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
}
