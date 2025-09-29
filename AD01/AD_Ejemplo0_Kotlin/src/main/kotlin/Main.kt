import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.Scanner

fun main() {
    // Crear el directorio
    val directorio = File("directorio/")

    if (!directorio.exists()) {
        directorio.mkdir()
        println("directorio creado: ${directorio.absolutePath}")
    } else {
        println("El directorio ya existe")
    }

    // Crear el archivo de texto
    val archivo = File(directorio, "prueba.txt")
    try {
        archivo.createNewFile()
        println("Archivo creado: ${archivo.absolutePath}")

        // Escribir contenido en el archivo
        val writer = FileWriter(archivo)
        writer.write("Hola clase de Acceso a datos de DAM!")
        writer.close()
    } catch (e: IOException) {
        println("Error al crear o escribir en el archivo: ${e.message}")
    }

    // Leer el archivo caracter a caracter
    /*try {
        val scanner = Scanner(archivo)

        while (scanner.hasNext()) {
            val caracter = scanner.next()
            println("Caracter: $caracter")
        }
        scanner.close()
    } catch (e: IOException) {
        println("Error al leer el archivo: ${e.message}")
    }*/

    // Leer el archivo caracter a caracter
    try {
        val reader = FileReader(archivo)
        var caracter: Int

        while (reader.read().also { caracter = it } != -1) {
            print("${caracter.toChar()}")
        }
        reader.close()
    } catch (e: IOException) {
        System.out.println("Error al leer el archivo: ${e.message}")
    }
}