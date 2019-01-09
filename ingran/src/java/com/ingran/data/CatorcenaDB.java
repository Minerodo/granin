package com.ingran.data;

import com.ingran.model.Catorcena;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatorcenaDB extends Conexion {

    private List<Catorcena> catorcenas = new ArrayList<>();

    public void obtenerCatorcenas() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCatorcenas().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT id, descripcion, fecha_inicio, fecha_fin, activo FROM CATORCENA ORDER BY fecha_inicio DESC, fecha_fin DESC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Catorcena catorcena = new Catorcena();

                catorcena.setId(rs.getInt(1));
                catorcena.setDescripcion(rs.getString(2));
                catorcena.setFecha_inicio(rs.getString(3));
                catorcena.setFecha_fin(rs.getString(4));
                catorcena.setActivo(rs.getBoolean(5));

                catorcenas.add(catorcena);
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

    public static Boolean agregarCatorcena(Catorcena catorcena) {
        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "INSERT INTO CATORCENA(descripcion, fecha_inicio, fecha_fin, activo) VALUES(?, ?, ?, ?);";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, catorcena.getDescripcion());
            pst.setString(2, catorcena.getFecha_inicio());
            pst.setString(3, catorcena.getFecha_fin());
            pst.setBoolean(4, catorcena.getActivo());

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

    public static Boolean actualizarCatorcena(Catorcena catorcena) {
        Boolean actualizado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "UPDATE CATORCENA SET descripcion = ?, fecha_inicio = ?, fecha_fin = ?, activo = ? WHERE id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, catorcena.getDescripcion());
            pst.setString(2, catorcena.getFecha_inicio());
            pst.setString(3, catorcena.getFecha_fin());
            pst.setBoolean(4, catorcena.getActivo());
            pst.setInt(5, catorcena.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
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
        return actualizado;
    }

    public static void obtenerCatorcena(Catorcena catorcena) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "SELECT id, descripcion, fecha_inicio, fecha_fin, activo FROM CATORCENA WHERE id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                catorcena.setId(rs.getInt(1));
                catorcena.setDescripcion(rs.getString(2));
                catorcena.setFecha_inicio(rs.getString(3));
                catorcena.setFecha_fin(rs.getString(4));
                catorcena.setActivo(rs.getBoolean(5));
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

    public static void obtenerCatorcenaActiva(Catorcena catorcena) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "SELECT id, descripcion, fecha_inicio, fecha_fin, activo FROM CATORCENA WHERE activo = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setBoolean(1, true);

            rs = pst.executeQuery();

            while (rs.next()) {
                catorcena.setId(rs.getInt(1));
                catorcena.setDescripcion(rs.getString(2));
                catorcena.setFecha_inicio(rs.getString(3));
                catorcena.setFecha_fin(rs.getString(4));
                catorcena.setActivo(rs.getBoolean(5));
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

    public void obtenerCatorcenasBodeguero() {
        String consultaSQL = "SELECT TOP 2 c.id, c.descripcion, c.fecha_inicio, c.fecha_fin, c.activo FROM CATORCENA c WHERE c.fecha_inicio < (SELECT fecha_fin FROM CATORCENA WHERE activo = 1) ORDER BY c.fecha_inicio DESC, c.fecha_fin DESC;";
        obtenerCatorcenas(consultaSQL);
    }

    public void obtenerCatorcenasIngeniero() {
        String consultaSQL = "SELECT c.id, c.descripcion, c.fecha_inicio, c.fecha_fin, c.activo FROM CATORCENA c WHERE c.fecha_inicio < (SELECT fecha_fin FROM CATORCENA WHERE activo = 1) ORDER BY c.fecha_inicio DESC, c.fecha_fin DESC;";
        obtenerCatorcenas(consultaSQL);
    }

    public void obtenerCatorcenasAdministrador() {
        String consultaSQL = "SELECT c.id, c.descripcion, c.fecha_inicio, c.fecha_fin, c.activo FROM CATORCENA c WHERE c.fecha_inicio < (SELECT fecha_fin FROM CATORCENA WHERE activo = 1) ORDER BY c.fecha_inicio DESC, c.fecha_fin DESC;";
        obtenerCatorcenas(consultaSQL);
    }

    private void obtenerCatorcenas(String consultaSQL) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCatorcenas().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Catorcena catorcena = new Catorcena();

                catorcena.setId(rs.getInt(1));
                catorcena.setDescripcion(rs.getString(2));
                catorcena.setFecha_inicio(rs.getString(3));
                catorcena.setFecha_fin(rs.getString(4));
                catorcena.setActivo(rs.getBoolean(5));

                catorcenas.add(catorcena);
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

    public List<Catorcena> getCatorcenas() {
        return catorcenas;
    }

    public void setCatorcenas(List<Catorcena> catorcenas) {
        this.catorcenas = catorcenas;
    }
    
//    public static void main(String args[]){
//        Catorcena c = new Catorcena();
//        c.setActivo(false);
//        c.setFecha_inicio(Fecha.convertitTextoAString("01-01-2019"));
//        c.setFecha_fin(Fecha.convertitTextoAString("14-01-2019"));
//        c.setDescripcion(c.getFecha_inicio_formato() + " al " + c.getFecha_fin_formato());
//        System.out.println(CatorcenaDB.agregarCatorcena(c));
//    }
}
