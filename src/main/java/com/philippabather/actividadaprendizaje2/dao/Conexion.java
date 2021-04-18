package com.philippabather.actividadaprendizaje2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;

/**
 * Conexion establece la conexión con la BBDD y terminación de la conexión.
 * 
 * @author philippa bather
 */
public class Conexion {
    //declarar las propiedades de la instancia
    private Connection conexion;    
    private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL_CONEXION = "jdbc:oracle:thin:@//localhost:1521/XE";
    private final String USER = "ParksHR";
    private final String PASSWORD = "1234";
    private final String ERROR = "Error: ";

    //constructores
    public Conexion() {
        
    }

    public Connection getConexion() {
        return conexion;
    }

    /**
     * Se conecta a la BBDD.
     * 
     * @throws SQLException 
     */
    public void connect() throws SQLException {
        try {
            Class.forName(DRIVER);
            System.out.println("Driver loaded.");
            conexion = DriverManager.getConnection(URL_CONEXION, USER, PASSWORD);
            if (conexion != null) {
                System.out.println("Connection established.\n");
            } else {
                System.out.println("Connection failed.\n");
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println(ERROR + "driver not found.");
        } catch (SQLRecoverableException e) {
            System.out.println(ERROR + e.getErrorCode() + e.getMessage());
        }
    }

    /**
     * Se conecta desde la BBDD.
     */
    public void disconnect() {
        try {
            conexion.close();
            System.out.println("\n*Connection terminated.\n");
        } catch (SQLException sqle) {
            System.out.println(ERROR + "SQL Exception");
            sqle.printStackTrace();
        }
    }
}
