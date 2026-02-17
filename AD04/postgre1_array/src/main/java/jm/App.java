package jm;

import java.sql.*;

public class App {

    //URL de la base de datos anaconda
    static String url = "jdbc:postgresql://localhost:5432/anaconda";
    //contraseña del usuario 'postgres' para acceder a la base de datos anaconda
    static String passwd = "admin";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        //conexión
        Connection connection = null;
        Statement sta = null;

        try {
            //abre la conexión con la base de datos a la que apunta el url
            //mediante la contraseña del usuario postgres
            connection = DriverManager.getConnection(url, "admin", passwd);

            //comando
            sta = connection.createStatement();
            sta.execute("DROP TABLE IF EXISTS tareas");
            //crea una tabla con una columna matricial de tipo varchar
            sta.execute("CREATE TABLE tareas(comercial_id integer,"
                    + "agenda varchar[][])");
            //inserta un registro de dos tareas por día para el comercial número 3
            //durante dos días (día 1: [0][0],[0][1]; día 2:[1][0],[1][1])
            sta.executeUpdate("INSERT INTO tareas VALUES(3,"
                    + "'{{\"reunión 9:30\",\"comida 14:30\"},"
                    + "{\"reunión 8:30\",\"cena 22:30\"}}')");
            //consulta todas las tareas del segundo día del comercial número 3
            ResultSet rst = sta.executeQuery("SELECT agenda[1:1] "
                    + "FROM tareas WHERE comercial_id=3");
            //muestra el resultado
            System.out.println("Todas las tareas del segundo día del comercial 3: ");
            while (rst.next()) {
                System.out.println(rst.getString(1));
            }
            //consulta la segunda tarea del primer día del comercial número 3
            rst = sta.executeQuery("SELECT agenda[1][2] "
                    + "FROM tareas WHERE comercial_id=3");

            //muestra el resultado
            System.out.println("Segunda tarea del primer día del comercial 3: ");
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
