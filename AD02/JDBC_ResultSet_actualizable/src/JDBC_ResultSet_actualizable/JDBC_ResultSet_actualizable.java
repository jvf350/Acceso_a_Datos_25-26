package JDBC_ResultSet_actualizable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;


public class JDBC_ResultSet_actualizable {
  
    
    private static void conectaBd() throws SQLException {
      
        ResultSet rs = null;   
            
        // Declaración/Inicialización flujo entrada:
        InputStream entrada = null;
        // Definición del objeto Properties:
        Properties config = null;
        // Creación del objeto mediante constructor:
        config = new Properties();
                    
        // Carga de las propiedades que componen la url de la BD
        // Controlar potenciales excepciones/errores.
            try {
                entrada = new FileInputStream("urlBd.properties");
                config.load(entrada);
            } catch (FileNotFoundException fnfex) {
                System.err.println("Fichero de configuración perdido.");
                System.err.println(fnfex.getMessage());
            } catch (IOException ioex) {
                System.err.println("Error accediendo al fichero de configuración.");
                System.err.println(ioex.getMessage());
            }
          
            try (
                Connection con = DriverManager.getConnection(config.getProperty("dbengine"), config)) {
            try (
                Statement Consulta = con.createStatement(
                      // ResultSetType
                      // TYPE_SCROLL_INSENSITIVE: Permite scroll o rebobinado a posición absoluta o relativa. 
                      // Los datos son los que se cargan en la apertura. MySQL solo soporta este.
                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                      // ResultSetConcurrency
                      // CONCUR_UPDATABLE: Permite actualizar el ResultSet.
                      ResultSet.CONCUR_UPDATABLE,
                      // Mejora el rendimiento, el RS se cierra.
                      ResultSet.CLOSE_CURSORS_AT_COMMIT)) {

                rs = Consulta.executeQuery("SELECT * FROM EMPLEADOS WHERE comision = 0");

                con.setAutoCommit(false);

                rs.last();  // Modifica último empleado que cumple la condición de la consulta
                rs.updateString("comision", "15");
                rs.updateRow();
        
                //rs.previous();  // Borrar penúltimo empleado que cumple la condición de la consulta
                //rs.deleteRow();
                
                rs.moveToInsertRow();  // Inserta nueva fila
                rs.updateString("emp_no", "9500");
                rs.updateString("apellido", "MORALES");
                rs.updateString("oficio", "CALLCENTER");
                rs.updateString("dir", "9");
                rs.updateString("fecha_alt", "2022-11-14");
                rs.updateString("salario", "1900");
                rs.updateString("comision", "0");
                rs.updateString("dept_no", "30");
                rs.insertRow();
                

                con.commit();

            } catch (SQLException e) {
                muestraErrorSQL(e);
            
                try {
                    con.rollback();
                    System.err.println("Se hace ROLLBACK");
                } catch (Exception er) {
                    System.err.println("ERROR haciendo ROLLBACK");
                    er.printStackTrace(System.err);
                }
            }
                } catch (Exception e) {
                    System.err.println("ERROR de conexión");
                    e.printStackTrace(System.err);
                }
       
    }

  public static void main(String[] args) throws SQLException {

    //Properties config = null;
    //ResultSet rs = null; 
    conectaBd();
  
  }
  
  
  public static void muestraErrorSQL(SQLException e) {
      
    System.err.println("SQL ERROR mensaje: " + e.getMessage());
    System.err.println("SQL Estado: " + e.getSQLState());
    System.err.println("SQL código específico: " + e.getErrorCode());
  }  

}
