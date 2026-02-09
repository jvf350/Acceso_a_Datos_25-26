package jm;

import java.sql.*;

/**
 * Ilustra característica meramente relacionales de PostgreSQL
 *
 */
public class App 
{
    public static void main(String[] args) throws SQLException {
        //cadena url de la base de datos temperaturas en el servidor local
        String url = "jdbc:postgresql://localhost:5432/Temperaturas";
        //conexión con la base de datos
        Connection connection = null;
        try {
            //abre la conexión con la base de datos a la que apunta el url
            //mediante el usuario postgres
            connection = DriverManager.getConnection(url, "admin", "admin");
            //elimina las tablas (si existen)
            dropTablas(connection);
            //vuelve a crear las tablas de ejemplo
            crearTablas(connection);
            //inserta algunos regitros
            insertarRegistros(connection);
            //realiza alguna consultas
            consulta1(connection);
            consulta2(connection);
            //modifica algunos registros
            updateProvincia(connection);
            //borra algunos registros
            deleteProvincia(connection);
        } catch (SQLException ex) {
            //imprime la excepción
            System.out.println(ex.toString());
        } finally {
            //cierra la conexión
            //assert connection != null;
            connection.close();
        }
    }

    /****************************************************************************
     * borra todas las tablas del ejemplo (si es que existen)
     */
    private static void dropTablas(Connection connection) throws SQLException {
        //sentencia SQL de eliminación
        String sentSql = "DROP TABLE IF EXISTS datos_meteo;"
                + "DROP TABLE IF EXISTS meses;"
                + "DROP TABLE IF EXISTS provincias;";

        //objeto Statement
        Statement sta = connection.createStatement();
        //ejecuta la sentencia SQL
        sta.execute(sentSql);
        //cierra sentencia
        sta.close();
    }

    /****************************************************************************
     * creamos las tablas ('meses', 'provincias' y 'datos_meteo') y establecemos
     * tanto las claves primarias ('mes_id', 'provincia_id' y 'datos_meteo_id'),
     * como las foráneas (columnas 'mes_id' y 'provincia_id' de la tabla
     * 'datos_meteo')
     *
     * el tipo de las claves primarias "mes_id" y "provincia_id" es entero. El
     * tipo de la clave primaria de la tabla 'datos_meteo' es serial (entero
     * autoincremental en PostgreSQL). El resto son cadenas de longitud variable
     * , o numeric (el tipo numérico para cálculos exactos).
     *
     * la tabla 'meses' tiene un relacion de uno a varios con la tabla
     * 'datos_meteo', mediante el campo 'mes_id' de la segunda
     *
     * la tabla 'provincias' tiene un relacion de uno a varios con la tabla
     * 'datos_meteo', mediante el campo 'provincia_id' de la segunda
     *
     * ambas relaciones implementan actualizaciones y eliminaciones en cascada
     */
    private static void crearTablas(Connection connection) throws SQLException {
        //sentSql
        String sql = "CREATE TABLE meses("
                + "mes_id integer NOT NULL,"
                + "mes varchar(25),"
                + "CONSTRAINT mes_id PRIMARY KEY (mes_id )"
                + ");"

                + "CREATE TABLE provincias("
                + "provincia_id integer NOT NULL,"
                + "provincia varchar(25),"
                + "CONSTRAINT provincia_id PRIMARY KEY (provincia_id )"
                + ");"

                + "CREATE TABLE datos_meteo("
                + "datos_meteo_id serial NOT NULL,"
                + "provincia_id integer,"
                + "mes_id integer,"
                + "temp_min numeric,"
                + "temp_max numeric,"
                + "precipitaciones integer,"
                + "CONSTRAINT datos_meteo_id PRIMARY KEY (datos_meteo_id ),"
                + "CONSTRAINT mes_id FOREIGN KEY (mes_id) REFERENCES "
                + "meses (mes_id) MATCH SIMPLE ON UPDATE CASCADE ON "
                + "DELETE CASCADE,"
                + "CONSTRAINT provincia_id FOREIGN KEY (provincia_id) REFERENCES "
                + "provincias (provincia_id) MATCH SIMPLE ON UPDATE CASCADE "
                + "ON DELETE CASCADE"
                + ");";

        //objeto Statement
        Statement sta = connection.createStatement();
        //ejecuta la sentencia SQL
        sta.execute(sql);
        //cierra el objeto Statement
        if (sta != null) {
            sta.close();
        }
    }


    /***********************************************************************
     * insertamos algunos registros en las tablas (por suspuesto, respetando
     * la integridad de las relaciones)
     */
    private static void insertarRegistros(Connection connection) throws SQLException {
        //sentSql
        String consulta = "INSERT INTO meses VALUES (1, 'enero'),(2, 'febrero'), "
                + "(3, 'marzo'),(4, 'abril'),(5, 'mayo'),(6, 'junio'),(7, 'julio'),"
                + "(8, 'agosto'), (9, 'septiembre'), (10, 'octubre'),"
                + "(11, 'noviembre'),(12, 'diciembre');"

                + "INSERT INTO provincias VALUES (1, 'Barcelona'),(2, 'Madrid'),"
                + "(3, 'Murcia'),(4, 'Valencia');"

                + "INSERT INTO datos_meteo (provincia_id, mes_id, temp_min, "
                + "temp_max, precipitaciones) VALUES (1, 1, 4.4, 13.4, 41),"
                + "(1, 4, 8.5, 17.6, 49),(1, 7, 18.6, 27.5, 20),"
                + "(1, 10, 12.6, 21.5, 91),(2, 1, 0.3, 10.6, 33),"
                + "(2, 4, 5.4, 18.0, 39),(2, 7, 16.1, 33.0, 11),"
                + "(2, 10, 8.3, 20.6, 39),(3, 1, 5.12, 15.82, 38),"
                + "(3, 4, 9.3, 19.9, 25),(3, 7, 19.9, 28.4, 6),"
                + "(3, 10, 18.7, 23.4, 14),(4, 1, 5.0, 15.5, 38),"
                + "(4, 4, 9.4, 20.6, 38),(4, 7, 19.8, 30.9, 14),"
                + "(4, 10, 13.3, 23.4, 74);";

        //sentencia Statement
        Statement sta = connection.createStatement();
        //ejecuta la sentencia SQL
        sta.execute(consulta);
        //cierra el objeto Statement
        sta.close();
    }

    /**************************************************************************
     * mostramos todos los registros de la tabla 'datos_meteo'. Tanto en bruto,
     * como mostrando los nombres de los meses y de la provincias. En el segundo
     * caso, redondeamos las columnas 'temp_min' y 'temp_max"
     */
    private static void consulta1(Connection connection) throws SQLException {
        //
        System.out.print("Todos los datos meteorológicos, tanto en bruto:\n");
        //sentencia SQL
        String sql = "SELECT * FROM datos_meteo";
        //objeto Statement
        Statement sta = connection.createStatement();
        //ejecuta la sentencia SQL.
        //Al ser una SELECT, devolverá un objeto ResultSet
        ResultSet res = sta.executeQuery(sql);
        //imprime el resultado de la sql
        imprimir_ResultSet(res);
        //
        System.out.print("\ncomo con los valores tomados de las "
                + "tablas relacionadas:\n");

        //sentencia  SQL
        sql = "SELECT dm.datos_meteo_id, p.provincia, m.mes, "
                + "round(dm.temp_min), "
                + "round(dm.temp_max), "
                + "dm.precipitaciones "
                + "FROM meses m "
                + "INNER JOIN  datos_meteo dm ON m.mes_id=dm.mes_id "
                + "INNER JOIN provincias p ON  p.provincia_id=dm.provincia_id";

        //objeto Statement
        sta = connection.createStatement();
        //ejecuta la sentencia
        res = sta.executeQuery(sql);
        //imprime el resultado
        imprimir_ResultSet(res);
        //cierra los objetos auxiliares
        res.close();
        sta.close();
    }

    /*************************************************************************
     * calcula la temperatura media por mes de Murcia (provincia_id=3)
     */
    private static void consulta2(Connection connection) throws SQLException {

        //
        System.out.print("\nTemperatura media por mes de Murcia:\n");
        //sentencia SQL
        String consulta = "SELECT m.mes, "
                + "ROUND((dm.temp_max+dm.temp_min)/2,2) "
                + "FROM meses m  "
                + "INNER JOIN  datos_meteo dm ON m.mes_id=dm.mes_id "
                + "WHERE dm.provincia_id=3";
        //comando SQL
        Statement sta = connection.createStatement();
        //ejecuta la sentSql para que devuelva un conjunto de registros
        ResultSet res = sta.executeQuery(consulta);
        //imprime el resultado
        imprimir_ResultSet(res);
        //cierra los objetos auxiliares
        res.close();
        sta.close();
    }

    /****************************************************************************
     * cambia la clave principal del registro 'Valencia' en la tabla 'provincias'
     * (de 4 a 5), y comprueba la actualización en cascada de los registros
     * relacionados en la tabla 'datos_meteo'
     */
    private static void updateProvincia(Connection connection) throws SQLException {    //
        System.out.print("\nCambia la clave principal de 'Valencia' en la tabla "
                + "'provincias'(de 4 a 5).");
        //sentencia SQL
        String consulta = "UPDATE provincias "
                + "SET provincia_id=5 "
                + "WHERE "
                + "provincia_id=4";
        //objeto Statement
        Statement sta = connection.createStatement();
        //ejecuta la sentencia SQL
        System.out.print("\nComo resultado, " + sta.executeUpdate(consulta)
                + " fila actualizada:\n");

        //sentencia SQL
        consulta = "SELECT * FROM provincias";
        //devuelva un ResultSet
        ResultSet res = sta.executeQuery(consulta);
        //imprime el resultado
        imprimir_ResultSet(res);

        //
        System.out.print("\ny muestra la actualización en cascada de los registros "
                + "relacionados en la tabla 'datos_meteo':\n");

        //sentencia SQL
        consulta = "SELECT * FROM datos_meteo WHERE provincia_id=5";
        //ejecuta la sentencia SQL
        res = sta.executeQuery(consulta);
        //imprime el resultado
        imprimir_ResultSet(res);
        //cierra los objetos auxiliares
        res.close();
        sta.close();
    }

    /****************************************************************************
     * borra la provincia de 'Valencia' en la tabla 'provincias', y comprueba el
     * borrado en cascada de los registros relacionados en la tabla 'datos_meteo'
     */
    private static void deleteProvincia(Connection connection) throws SQLException {

        //
        System.out.print("\nBorra la provincia 'Valencia' en la tabla "
                + "'provincias':\n");

        //sentencia SQL
        String sql = "DELETE FROM provincias WHERE provincia = 'Valencia'";

        //objeto Statement
        Statement sta = connection.createStatement();

        //ejecuta la sentencia sql
        //el valor devuelto, son las filas eliminadas
        System.out.print("\nComo resultado, " + sta.executeUpdate(sql)
                + " fila eliminada:\n");

        //sentencia SQL
        sql = "SELECT * FROM provincias";
        //ejecuta la sentencia SQL
        ResultSet res = sta.executeQuery(sql);
        //imprime el resultado
        imprimir_ResultSet(res);


        //
        System.out.print("\ny Datos tras el borrado en cascada de los registros "
                + "relacionados en la tabla 'datos_meteo' "
                + "con la provincia Valencia:\n");
        //nueva sentSql
        sql = "SELECT dm.datos_meteo_id, p.provincia, m.mes, "
                + "round(dm.temp_min), "
                + "round(dm.temp_max), "
                + "dm.precipitaciones "
                + "FROM meses m "
                + "INNER JOIN datos_meteo dm ON  m.mes_id=dm.mes_id "
                + "INNER JOIN provincias p ON  p.provincia_id=dm.provincia_id";

        //ejecuta la sentencia SQL
        res = sta.executeQuery(sql);
        //imprime el resultado
        imprimir_ResultSet(res);
        //cierra los objetos auxiliares
        res.close();
        sta.close();
    }

    /****************************************************************************
     * imprime en la Salida el resultSet especificado, con los datos de cada
     * columna tabulados
     */
    private static void imprimir_ResultSet(ResultSet resultSet) throws
            SQLException {
        //número de columnas del resultset
        ResultSetMetaData metaDatos = resultSet.getMetaData();
        int columnas = metaDatos.getColumnCount();
        //mientras quedan registros por leer en el ResultSet
        while (resultSet.next()) {
            for (int i = 1; i <= columnas; i++) {
                //imprime cada columna seguido de un tabulador
                System.out.print(resultSet.getString(i) + "\t");
            }
            //línea en blanco
            System.out.println();
        }
    }
}
