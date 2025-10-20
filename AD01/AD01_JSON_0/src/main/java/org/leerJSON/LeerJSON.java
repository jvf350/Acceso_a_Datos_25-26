package org.leerJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;

public class LeerJSON {

    public static void main(String[] args) {

        String contenido = ""; // Inicializamos un String vacío

        // Bloque try-catch para manejar errores de lectura
        try (FileReader fr = new FileReader("curso.json")) {
            int c;
            // Leer carácter a carácter hasta el final del archivo
            while ((c = fr.read()) != -1) {
                contenido += (char) c; // Concatenamos cada carácter al String
            }

            // Convertir el contenido a JSONObject
            JSONObject cursoJson = new JSONObject(contenido);

            // Obtener el array de módulos
            JSONArray modulos = cursoJson.getJSONArray("curso");

            // Iterar sobre los módulos y mostrar sus datos
            for (int i = 0; i < modulos.length(); i++) {
                JSONObject modulo = modulos.getJSONObject(i);
                System.out.println("Nombre: " + modulo.getString("nombre"));
                System.out.println("Horas: " + modulo.getInt("horas"));
                System.out.println("Nota: " + modulo.getDouble("nota"));
                System.out.println("---------------------");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error al procesar el contenido JSON.");
            e.printStackTrace();
        }
    }
}
