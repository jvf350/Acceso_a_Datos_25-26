/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad02_ej03_sol2;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author admin
 */
public class PA_BBDD {
       
    private static Connection conexion = null;
    private static CallableStatement proc = null;
    private static int resultado1 = 0, resultado2 = 0;
    

    public static int paUno(float mini, float maxi) {
                              
        try {
            conexion = AccesoBBDD.establecerConexion();
            
            String procedimiento2 = ("{call empleados_comision (?, ?, ?)}");
            proc = conexion.prepareCall(procedimiento2);
            
            //Establecemeos los valores de los parámetros de entrada
            proc.setFloat(1, mini);
            proc.setFloat(2, maxi);
            //Registramos el parámetro de salida
            proc.registerOutParameter(3, Types.INTEGER);
            proc.execute();
            resultado1 = proc.getInt(3);
                        
            System.out.println("El resultado de la ejecución del procedimiento almacenado con los parámetros"
                               + " introducidos es: " + resultado1);        
           
        } catch (SQLException e) {
            muestraErrorSQL(e);   
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (conexion != null){
                try {
                if (proc != null)
                    proc.close(); //Cerramos el PA1
                
            AccesoBBDD.cerrarConexion(conexion); //Llamada a método de cierre de conexión
                } catch (SQLException ex) {
                    muestraErrorSQL(ex);
                }
            }
        }
        return resultado1;
    }
    public static int paDos(float sal_min, float sal_max){
                      
        try {
            conexion = AccesoBBDD.establecerConexion();
            
            String procedimiento2 = ("{call cantidad_empl_rango_salarial (?, ?, ?)}");
            proc = conexion.prepareCall(procedimiento2);
            
            //Establecemeos los valores de los parámetros de entrada
            proc.setFloat(1, sal_min);
            proc.setFloat(2, sal_max);
            //Registramos el parámetro de salida
            proc.registerOutParameter(3, Types.INTEGER);
            proc.execute();
            resultado2 = proc.getInt(3);
                        
            System.out.println("El resultado de la ejecución del procedimiento almacenado con los parámetros"
                               + " introducidos es: " + resultado2);        
           
        } catch (SQLException e) {
            muestraErrorSQL(e);   
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (conexion != null){
                try {
                if (proc != null)
                    proc.close(); //Cerramos el PA2
                
            AccesoBBDD.cerrarConexion(conexion); //Llamada a método de cierre de conexión
                } catch (SQLException ex) {
                    muestraErrorSQL(ex);
                }
            }
        }
        return resultado2;
    }
    
    public static void paTres(int dep, int subida){
        
        try {
            conexion = AccesoBBDD.establecerConexion();
            
            String procedimiento3 = ("{call subida_salario (?, ?)}");
            proc = conexion.prepareCall(procedimiento3);
            
            //Establecemeos los valores de los parámetros de entrada
            proc.setInt(1, dep);
            proc.setInt(2, subida);
            proc.execute();
                                    
            System.out.println("El salario de los trabajadores del departamento nº: "+ dep
                               + " ha sido aumentado en un porcentaje de: "+ subida + "%");        
           
        } catch (SQLException e) {
            muestraErrorSQL(e);   
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (conexion != null){
                try {
                if (proc != null)
                    proc.close(); //Cerramos el PA2
                
            AccesoBBDD.cerrarConexion(conexion); //Llamada a método de cierre de conexión
                } catch (SQLException ex) {
                    muestraErrorSQL(ex);
                }
            }
        }
        }
    //Método que gestiona en profundidad los errores de SQL
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje: " + e.getMessage());
        System.err.println("SQL Estado: " + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }
}    
    


   
