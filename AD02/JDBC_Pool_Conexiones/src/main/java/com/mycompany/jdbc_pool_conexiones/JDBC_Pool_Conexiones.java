// Creación y uso de un pool de conexiones utilizando Apache Commons DBCP

package com.mycompany.jdbc_pool_conexiones;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author jm
 */
public class JDBC_Pool_Conexiones {

    private static final String BASEDATOS ="consultorait";
    private static final String HOST ="localhost";
    private static final String PORT ="3306";
    private static final String URL_CONNECTION = "jdbc:mysql://" + HOST + ":" + PORT + "/" + BASEDATOS;
    //private static final String USER =...;
    //private static final String PWD =...;
    
    private static BasicDataSource dataSource;
    
    private static BasicDataSource getDataSource(){
        
        if (dataSource == null){
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl(URL_CONNECTION);
            ds.setUsername("root");
            ds.setPassword("mysql2324");
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxTotal(25);
            ds.setMaxOpenPreparedStatements(100);
            dataSource = ds;
        }
        return dataSource;
    }

        public static void main(String[] args) throws SQLException{
            
        try (
           BasicDataSource ds = JDBC_Pool_Conexiones.getDataSource();
           Connection con = ds.getConnection();
           Statement stc = con.createStatement();
           ResultSet rs = stc.executeQuery("SELECT * FROM departamentos")) {
            //int i = 1;
            while (rs.next()) {
                System.out.printf("%2d %-15s %s\n", rs.getInt(1),
                        rs.getString("dnombre"), rs.getString(3));
            }
            System.out.println("\nTabla 'departamentos' consultada correctamente");
        }catch (SQLException e) {
            muestraErrorSQL(e);
        }catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje: " + e.getMessage());
        System.err.println("SQL Estado: " + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }
}
