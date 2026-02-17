package jm;

import java.sql.*;

/******************************************************************************
 * crea un tipo estructurado, una tabla con el tipo como columna, y una
 * función sobre el tipo que devuelve una cadena compuesta
 */

public class App {

    //URL de la base de datos anaconda
    static String url = "jdbc:postgresql://localhost/anaconda";
    //contraseña del usuario 'postgres' para acceder a la base de datos anaconda
    static String passwd = "admin";

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement sta = null;

        try {
            connection = DriverManager.getConnection(url, "admin", passwd);

            //comando
            sta = connection.createStatement();

            //elimina todo lo que va a crearse, si existe previamente. Como la función 
            //depende del tipo y se especificó CASCADE, se elimina con el tipo
            sta.execute("DROP TABLE IF EXISTS empleados");
            sta.execute("DROP TYPE IF EXISTS puesto CASCADE");

            //crea el tipo estructurado 
            sta.execute("CREATE TYPE puesto AS(nombre varchar,"
                    + "cargo varchar,sueldo numeric)");
            //crea la función que transforma el tipo estructurado en una cadena
            sta.execute("CREATE FUNCTION cadena_puesto(puesto) RETURNS varchar AS $$"
                    + "SELECT $1.nombre||' como '||$1.cargo||' tiene un sueldo de '"
                    + "||CAST(ROUND($1.sueldo,2) AS varchar)||'€';$$"
                    + "LANGUAGE SQL");
            //crea la tabla con una columna del tipo creado (columna ocupacion)
            sta.execute("CREATE TABLE empleados(empleado_id serial,"
                    + "ocupacion puesto)");
            //inserta un par de registros
            sta.executeUpdate("INSERT INTO empleados (ocupacion)"
                    + "VALUES(ROW('Antonio','Comercial',1450.32));"
                    + "INSERT INTO empleados (ocupacion)"
                    + "VALUES(ROW('Juan','Encargado',1230.48))");
            //Ejecuta la función mediante una consulta SELECT
            ResultSet rst = sta.executeQuery("SELECT cadena_puesto(ocupacion) "
                    + "FROM empleados");

            //muestra el resultado
            while (rst.next()) {
                System.out.println(rst.getString(1));
            }

        } catch (SQLException ex) {
            //imprime la excepción
            System.out.println(ex.toString());
        } finally {
            //
            sta.close();
            connection.close();
        }

    }

}
