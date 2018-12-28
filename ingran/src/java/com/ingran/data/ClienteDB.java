package com.ingran.data;

import com.ingran.model.Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDB extends Conexion {

    private List<Cliente> clientes = new ArrayList<>();

    public void obtenerClientes() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getClientes().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT CLIENTE, NOMBRE, ALIAS FROM " + Conexion.getDBEXACTUS() + ".ingran.CLIENTE c WHERE c.ACTIVO = 'S';";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setCliente(rs.getString(1));
                cliente.setNombre(rs.getString(2));
                cliente.setAlias(rs.getString(3));

                getClientes().add(cliente);
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

    public static Cliente obtenerCliente(String str_cliente) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Cliente cliente = null;

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT CLIENTE, NOMBRE, ALIAS FROM " + Conexion.getDBEXACTUS() + ".ingran.CLIENTE c WHERE c.ACTIVO = 'S' AND c.CLIENTE = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, str_cliente);

            rs = pst.executeQuery();

            while (rs.next()) {
                cliente = new Cliente();

                cliente.setCliente(rs.getString(1));
                cliente.setNombre(rs.getString(2));
                cliente.setAlias(rs.getString(3));
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
        return cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

//    public static void main(String[] args) {
//        ClienteDB clientes = new ClienteDB();
//        clientes.obtenerClientes();
//
//        for (Cliente cliente : clientes.getClientes()) {
//            System.out.println(cliente.getNombre());
//        }
//
//        System.out.println(ClienteDB.obtenerCliente("158366-3").getNombre());
//    }
}
