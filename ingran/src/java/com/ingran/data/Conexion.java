package com.ingran.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private String USERNAME = "ingrandbc";
    private String PASSWORD = "Pa$$w0rd";
    private String HOST = "localhost";
    //private String HOST = "192.168.1.224";
    //private String HOST = "localhost";
    private String PORT = "1433";
    //private String PORT = "61101";
    private String DATABASE = "PLANILLA";
    private String CLASSNAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String URL = "jdbc:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DATABASE;
    //private String URL = "jdbc:sqlserver://" + HOST + ":" + PORT + ";";

    private static String DATABASE_EXACTUS = "PRUEBA";
    //private static String DATABASE_EXACTUS = "EXACTUS";
    
    public static String getDBEXACTUS(){
        return DATABASE_EXACTUS;
    }
    
    private Connection conexion;

    public Conexion() {
    }
    
    public void abrirConexion(){
        try {
            Class.forName(CLASSNAME);
            conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("ERROR: " + e);
        }
    }
    
    public void cerrarConexion(){
        try {
            if(getConexion() != null){
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        }
    }

    public Connection getConexion() {
        return conexion;
    }
    
    public static void main(String[] args) {
        Conexion con = new Conexion();
        con.abrirConexion();
        System.out.println(con.URL);
    }
}
