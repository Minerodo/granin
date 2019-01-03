package com.ingran.data;

import com.ingran.model.CentroDeCosto;
import com.ingran.model.Cliente;
import com.ingran.model.OrdenDeTrabajo;
import com.ingran.util.Fecha;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenDeTrabajoDB extends Conexion {

    private List<OrdenDeTrabajo> orden_de_trabajos = new ArrayList<>();

    public List<OrdenDeTrabajo> getOrden_de_trabajos() {
        return orden_de_trabajos;
    }

    public void setOrden_de_trabajos(List<OrdenDeTrabajo> orden_de_trabajos) {
        this.orden_de_trabajos = orden_de_trabajos;
    }

    public static boolean crearOrdenDeTrabajo(OrdenDeTrabajo odt) {
        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "INSERT INTO ORDEN_DE_TRABAJO(proyecto, propietario, titulo, fecha) VALUES(?, ?, ?, ?);";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, odt.getProyecto().getCentro_de_costo());
            pst.setString(2, odt.getPropietario().getCliente());
            pst.setString(3, odt.getTitulo());
            pst.setString(4, odt.getFecha());

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
    
    public static void obtenerOrdenDeTrabajo(OrdenDeTrabajo odt) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "SELECT odt.id, odt.proyecto, odt.propietario, odt.titulo, odt.fecha, odt.monto, odt.avance FROM ORDEN_DE_TRABAJO odt WHERE odt.id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, odt.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                odt.setId(rs.getInt(1));

                CentroDeCosto cdc = new CentroDeCosto();
                cdc.setCentro_de_costo(rs.getString(2));
                cdc = CentroDeCostoDB.obtenerCentroDeCosto(cdc);
                odt.setProyecto(cdc);

                Cliente cliente = ClienteDB.obtenerCliente(rs.getString(3));
                odt.setPropietario(cliente);

                odt.setTitulo(rs.getString(4));
                odt.setFecha(rs.getString(5));
                odt.setMonto(rs.getDouble(6));
                odt.setAvance(rs.getDouble(7));
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
    
    public void obtenerOrdenDeTrabajos() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getOrden_de_trabajos().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT odt.id, odt.proyecto, odt.propietario, odt.titulo, odt.fecha, odt.monto, odt.avance FROM ORDEN_DE_TRABAJO odt;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                OrdenDeTrabajo odt = new OrdenDeTrabajo();

                odt.setId(rs.getInt(1));

                CentroDeCosto cdc = new CentroDeCosto();
                cdc.setCentro_de_costo(rs.getString(2));
                cdc = CentroDeCostoDB.obtenerCentroDeCosto(cdc);
                odt.setProyecto(cdc);

                Cliente cliente = ClienteDB.obtenerCliente(rs.getString(3));
                odt.setPropietario(cliente);

                odt.setTitulo(rs.getString(4));
                odt.setFecha(rs.getString(5));
                odt.setMonto(rs.getDouble(6));
                odt.setAvance(rs.getDouble(7));

                orden_de_trabajos.add(odt);
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
    
    public static void main(String[] args) {
        OrdenDeTrabajo odt = new OrdenDeTrabajo();
        CentroDeCosto cdc = new CentroDeCosto();
        cdc.setCentro_de_costo("123.456");
        odt.setProyecto(cdc);
        Cliente cliente = new Cliente();
        cliente.setCliente("123-4");
        odt.setPropietario(cliente);
        odt.setTitulo("prueba desde consola");
//        odt.setFecha(Fecha.convertitTextoADate("01-01-2019"));
        OrdenDeTrabajoDB.crearOrdenDeTrabajo(odt);
    }
}
