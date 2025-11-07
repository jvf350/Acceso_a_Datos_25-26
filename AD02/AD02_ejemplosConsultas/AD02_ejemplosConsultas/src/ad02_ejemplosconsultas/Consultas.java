package ad02_ejemplosconsultas;

import java.sql.CallableStatement;
import java.sql.Connection; // Clase que centraliza todas las operaciones con la base de datos.
import java.sql.DriverManager; // Clase necesaria para gestionar el driver.
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;


public class Consultas {

    static Connection conBd;
    static final String DIR_URL = "jdbc:mysql://localhost:3306/instituto_ejemplo?user=root&password=mysql2324";
    private static CallableStatement proc = null;
    private static int resultado = 0;

    // El sistema intenta cargar los drivers al leer la propiedad jdbc.drivers
    // Indicamos que el driver JDBC está cargado:
    //Class.forName("com.mysql.cj.jdbc.Driver");


    // SENTENCIA FIJA (Consulta)
    public static void uno_SelectConStatement() throws ClassNotFoundException, SQLException {

        //Conexión a la BBDD
        try {
            conBd = DriverManager.getConnection(DIR_URL);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        //String SQL = "Select * from Persona where apellidos = ?";
        //String apellido = "Mira Pons";
        String SQL = "Select * from Persona where apellidos = ?";

        //Statement sentencia = conBd.createStatement();
        PreparedStatement stm = conBd.prepareStatement(SQL);
        stm.setString(1, "O'connor");

        ResultSet rst = stm.executeQuery();
        //ResultSet rst = sentencia.executeQuery(SQL);



        while (rst.next()) {
            //System.out.println(rst.getObject(1) + " " + rst.getString(2) + " " + rst.getString(3)+ ": " + rst.getInt(4));
            System.out.println(rst.getString("nombre") + " " + rst.getString("apellidos") + ": " + rst.getInt("edad"));
        }

        //Desconexión a la BBDD
        try {
            conBd.close();
            conBd = null;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    // SENTENCIA FIJA (Inserción)
    public static void dos_InsertConStatement() throws ClassNotFoundException, SQLException {

        //Conexión a la BBDD
        try {
            conBd = DriverManager.getConnection(DIR_URL);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        String SQL = "Insert into Persona(nombre,apellidos,edad) values ('Elena','García Sánchez',33);";

        Statement stm = conBd.createStatement();

        int filas = stm.executeUpdate(SQL);

        if (filas == 1) {
            System.out.println("Inserción realizada con éxito");
        } else {
            System.out.println("Error en la inserción");
        }
    }

    // SENTENCIA VARIABLE (Inserción)
    public static void tres_InsertConStatementVariable() throws ClassNotFoundException, SQLException {

        //Conexión a la BBDD
        try {
            conBd = DriverManager.getConnection(DIR_URL);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        String nombre = Leer.leerTexto("Dime el nombre: ");
        String apellidos = Leer.leerTexto("Dime los apellidos: ");
        int edad = Leer.leerEntero("Dime la edad: ");
        String SQL = "Insert into Persona(nombre,apellidos,edad)"
                + " values ('" + nombre + "','" + apellidos + "'," + edad + ")";

        Statement stm = conBd.createStatement();

        int filas = stm.executeUpdate(SQL);

        if (filas == 1) {
            System.out.println("Inserción realizada con éxito");
        } else {
            System.out.println("Error en la inserción");
        }
    }

    // SENTENCIA VARIABLE (Consulta)
    public static void cuatro_SelectConStatementVariable() throws ClassNotFoundException, SQLException {

        //Conexión a la BBDD
        try {
            conBd = DriverManager.getConnection(DIR_URL);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        String idPersona = Leer.leerTexto("Dime el id a consultar: ");

        String SQL = "Select * from Persona where idPersona=" + idPersona;

        System.out.println(SQL);
        Statement stm = conBd.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        while (rst.next()) {
            System.out.println(rst.getInt("idPersona") + " " + rst.getString("nombre")
                    + " " + rst.getString("apellidos") + ": " + rst.getInt("edad"));
        }
    }

    // SENTENCIA PREPARADA (Insert)
    public static void cinco_SelectConSentenciaPreparada() throws ClassNotFoundException, SQLException {

        //Conexión a la BBDD
        try {
            conBd = DriverManager.getConnection(DIR_URL);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        String nombre = Leer.leerTexto("Dime el nombre: ");
        String apellidos = Leer.leerTexto("Dime los apellidos: ");
        int edad = Leer.leerEntero("Dime la edad: ");
        String SQL = "Insert into Persona(nombre,apellidos,edad) values (?,?,?)";

        PreparedStatement pstm = conBd.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        pstm.setString(1, nombre);
        pstm.setString(2, apellidos);
        pstm.setInt(3, edad);

        int filas = pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();

        if (filas > 0) {
            System.out.print("Inserción realizada con éxito del registro." + "\n");
            if (rs.next()) {
                System.out.println("La clave de la fila insertada es " + rs.getLong(1));
            }
        } else {
            System.out.println("Error en la inserción");
        }
    }

    // ResultSet (Metadatos)
    public static void seis_ResultSetConMetaDAta() throws ClassNotFoundException, SQLException {

        //Conexión a la BBDD
        try {
            conBd = DriverManager.getConnection(DIR_URL);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        String SQL = "Select * from Persona";
        Statement stm = conBd.createStatement();
        ResultSet rst = stm.executeQuery(SQL);          // los datos
        ResultSetMetaData rstmd = rst.getMetaData();    // los metadatos
        int cols = rstmd.getColumnCount();              // cuantas columnas hay

        //imprimimos cabecera
        for (int i = 1; i <= cols; i++) {
            System.out.print(String.format("%20s", rstmd.getColumnName(i)));
        }
        System.out.println("");

        //imprimimos datos
        while (rst.next()) {
            for (int i = 1; i <= cols; i++) {
                switch (rstmd.getColumnType(i)) {   // segun el tipo formateamos
                    case java.sql.Types.VARCHAR:
                        System.out.print(String.format("%20s", rst.getString(i)));
                        break;
                    case java.sql.Types.INTEGER:
                        System.out.print(String.format("%20d", rst.getInt(i)));
                        break;
                    case java.sql.Types.DOUBLE,java.sql.Types.FLOAT:
                        System.out.print(String.format("%20lf", rst.getInt(i)));
                        break;
                    default:    // cualquier otro tipo. El polimorfismo efectua su magia
                        System.out.print(String.format("%20s", rst.getObject(i)));
                }
            }
            System.out.println("");
        }
    }

    // Ejemplo de Procedimiento Almacenado
    public static int siete_PAConCallableStatement (int min, int max) {

        try {
            conBd = DriverManager.getConnection(DIR_URL);

            String procedimiento = ("{call personas_edad (?, ?, ?)}");
            proc = conBd.prepareCall(procedimiento);

            //Establecemeos los valores de los parámetros de entrada
            proc.setInt(1, min);
            proc.setInt(2, max);
            //Registramos el parámetro de salida
            proc.registerOutParameter(3, Types.INTEGER);
            proc.execute();
            resultado = proc.getInt(3);

            System.out.println("El resultado de la ejecución del procedimiento almacenado con los parámetros"
                    + " introducidos es: " + resultado);

        } catch (SQLException e) {
            System.err.println("SQL ERROR mensaje: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (proc != null){
                try {
                    if (proc != null)
                        proc.close(); //Cerramos el PA
                } catch (SQLException e) {
                    System.err.println("SQL ERROR mensaje: " + e.getMessage());
                }
            }
            if (conBd != null){
                try {
                    conBd.close(); //Cerramos la conexión a la BD
                } catch (SQLException ex) {
                    System.err.println("No se puede cerrar la conexion ; " + ex);
                }
            }
        }
        return resultado;
    }

}
