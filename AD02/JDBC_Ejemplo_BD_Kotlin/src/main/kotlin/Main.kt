import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.Properties
import java.io.FileInputStream

fun main() {
    val properties = Properties()
    FileInputStream("database.properties").use { properties.load(it) }

    val url = properties.getProperty("db.url")
    val user = properties.getProperty("db.user")
    val password = properties.getProperty("db.password")

    var con: Connection? = null
    var preparedStatement: PreparedStatement? = null
    var rs: ResultSet? = null

    try {
        con = DriverManager.getConnection(url, user, password)

        val sql = "SELECT * FROM departamentos"
        preparedStatement = con.prepareStatement(sql)
        //preparedStatement.setString(1, "valor")

        rs = preparedStatement.executeQuery()

        while (rs.next()) {
            System.out.printf("%2d %-15s %s\n", rs.getInt(1),
                rs.getString("dnombre"), rs.getString(3));
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        // Cerrar los recursos
        rs?.close()
        preparedStatement?.close()
        con?.close()
    }
}