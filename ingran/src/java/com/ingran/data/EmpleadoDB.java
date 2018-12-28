package com.ingran.data;

import com.ingran.model.Empleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDB extends Conexion {

    private List<Empleado> empleados = new ArrayList<>();

    public void obtenerEmpleados() {
        abrirConexion();

        empleados.clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT e.EMPLEADO, e.PRIMER_APELLIDO, ISNULL(e.SEGUNDO_APELLIDO, '') AS SEGUNDO_APELLIDO, e.NOMBRE_PILA, e.NOMBRE, e.PUESTO FROM " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e WHERE e.ACTIVO = 'S' ORDER BY e.PRIMER_APELLIDO ASC, e.SEGUNDO_APELLIDO ASC, e.NOMBRE_PILA ASC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setEmpleado(rs.getString(1));
                empleado.setPrimer_apellido(rs.getString(2));
                empleado.setSegundo_apellido(rs.getString(3));
                empleado.setNombre_pila(rs.getString(4));
                empleado.setNombre(rs.getString(5));
                empleado.setPuesto(PuestoDB.obtenerPuesto(rs.getString(6)));

                empleados.add(empleado);
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
    
    public void obtenerEmpleadosSinUsuario() {
        abrirConexion();

        empleados.clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT e.EMPLEADO, e.PRIMER_APELLIDO, ISNULL(e.SEGUNDO_APELLIDO, '') AS SEGUNDO_APELLIDO, e.NOMBRE_PILA, e.NOMBRE, e.PUESTO FROM " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e LEFT JOIN USUARIO u ON e.EMPLEADO = u.empleado WHERE e.ACTIVO = 'S' AND u.id IS NULL ORDER BY e.PRIMER_APELLIDO ASC, e.SEGUNDO_APELLIDO ASC, e.NOMBRE_PILA ASC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setEmpleado(rs.getString(1));
                empleado.setPrimer_apellido(rs.getString(2));
                empleado.setSegundo_apellido(rs.getString(3));
                empleado.setNombre_pila(rs.getString(4));
                empleado.setNombre(rs.getString(5));
                empleado.setPuesto(PuestoDB.obtenerPuesto(rs.getString(6)));

                empleados.add(empleado);
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

    public static Empleado obtenerEmpleado(String dui) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Empleado empleado = new Empleado();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT e.EMPLEADO, e.PRIMER_APELLIDO, ISNULL(e.SEGUNDO_APELLIDO, '') AS SEGUNDO_APELLIDO, e.NOMBRE_PILA, e.NOMBRE, e.PUESTO FROM " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e WHERE e.EMPLEADO = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, dui);

            rs = pst.executeQuery();

            while (rs.next()) {
                empleado.setEmpleado(rs.getString(1));
                empleado.setPrimer_apellido(rs.getString(2));
                empleado.setSegundo_apellido(rs.getString(3));
                empleado.setNombre_pila(rs.getString(4));
                empleado.setNombre(rs.getString(5));
                empleado.setPuesto(PuestoDB.obtenerPuesto(rs.getString(6)));
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
        return empleado;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

//    public static void main(String[] args) {
//        EmpleadoDB empleados = new EmpleadoDB();
//        empleados.obtenerEmpleados();
//
//        for (Empleado empleado : empleados.getEmpleados()) {
//            System.out.println(empleado.getEmpleado());
//        }
//
//        System.out.println(EmpleadoDB.obtenerEmpleado("03463218-0").getNombre_pila());
//    }
}
