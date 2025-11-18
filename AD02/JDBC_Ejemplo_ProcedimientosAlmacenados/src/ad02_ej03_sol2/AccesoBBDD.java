/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad02_ej03_sol2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class AccesoBBDD {
    
    private static final String url = "jdbc:mysql://localhost/consultorait?";
    private static final String user = "root";
    private static final String password = "mysql2324";
    private static Connection conexion = null;

        
    //Método que permite conectarnos con la base de datos.
    public static Connection establecerConexion() {
        
        try {
            //Cargar el driver de mysql
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Establecemos la conexion
            conexion = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            System.err.println("Excepcion; " + ex);
        } catch (SQLException ex) {
            System.err.println("SQL Excepcion conexion; " + ex);
        }
        return conexion;
    }

    //Método creado para cerrar la conexión con la base de datos
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("No se pude cerrar la conexion ; " + e);
        }
        }
    }
}
