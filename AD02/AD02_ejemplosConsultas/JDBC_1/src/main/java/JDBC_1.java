
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC_1 {

    public static void conectaBd() throws SQLException {

        ResultSet rs = null;
        //String prueba;

        // Declaración/Inicialización flujo entrada:
        InputStream entrada = null;
        // Definición del objeto Properties:
        Properties config = null;
        // Creación del objeto mediante constructor:
        config = new Properties();

        // Carga de las propiedades que componen la url de la BD
        // Controlar potenciales excepciones/errores.
        /*try {
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
                //Connection con = DriverManager.getConnection(config.getProperty("connector"), config);
                //prueba = config.getProperty("connector/dbengine").toString;
                Connection con = DriverManager.getConnection(config.getProperty("db.connector/dbengine"), config);
                //Connection con = DriverManager.getConnection(config.getProperty ("db.connector/dbengine").toString(), config);
                PreparedStatement Consulta = con.prepareStatement("SELECT * FROM departamentos");
        ) {
            rs = Consulta.executeQuery();
            while (rs.next()) {
                System.out.printf("%2d %-15s %s\n", rs.getInt(1),
                        rs.getString("dnombre"), rs.getString(3));
            }
            System.out.println("\nTabla 'departamentos' consultada correctamente");
        } catch (SQLException e) {
            muestraErrorSQL(e);
        }*/


        // Otra alternativa:

        Properties p = new Properties();

        try (InputStream in = new FileInputStream("urlBd.properties")) {
            p.load(in);
        } catch (FileNotFoundException fEx) {
            System.err.println("Fichero de configuración perdido.");
            fEx.printStackTrace();
        } catch (IOException ioEx) {
            System.err.println("Error accediendo al fichero de configuración.");
            ioEx.printStackTrace();
        }

        String url = String.format("%s%s:%s/%s",
                p.getProperty("db.driverPrefix"),   // jdbc:mysql://
                p.getProperty("db.host"),           // localhost
                p.getProperty("db.port"),           // 3306
                p.getProperty("db.name"));          // consultorait

        Properties credenciales = new Properties();
        credenciales.put("user", p.getProperty("db.user"));
        credenciales.put("password", p.getProperty("db.password"));

        try (Connection con = DriverManager.getConnection(url, credenciales);
             PreparedStatement st = con.prepareStatement("SELECT * FROM departamentos");
             ResultSet rs1 = st.executeQuery()) {

            while (rs1.next()) {
                System.out.printf("%2d %-15s %s%n",
                        rs1.getInt(1), rs1.getString("dnombre"), rs1.getString(3));
            }
        } catch (SQLException e) {
            muestraErrorSQL(e);
        }
    }


    /**
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        conectaBd();
    }


    public static void muestraErrorSQL(SQLException e) {

        System.err.println("SQL ERROR mensaje: " + e.getMessage());
        System.err.println("SQL Estado: " + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }

}

