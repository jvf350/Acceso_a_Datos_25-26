package org.leerJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerJSONLinea {
    public static void main(String[] args) {

        String archivo = "curso.json"; // Nombre del archivo JSON
        StringBuilder contenido = new StringBuilder();

        // Leer el archivo de forma eficiente
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            // Leemos línea a línea hasta el final
            while ((linea = br.readLine()) != null) {
                contenido.append(linea);
            }

            // Verificamos que el contenido no esté vacío
            if (contenido.length() == 0) {
                System.err.println("El archivo JSON está vacío.");
                return;
            }

            // Convertir el contenido a JSONObject
            JSONObject cursoJson = new JSONObject(contenido.toString());

            // Obtener el array de módulos
            JSONArray modulos = cursoJson.getJSONArray("curso");

            // Iterar sobre los módulos y mostrar sus datos
            for (int i = 0; i < modulos.length(); i++) {
                JSONObject modulo = modulos.getJSONObject(i);
                System.out.println("Nombre: " + modulo.optString("nombre", "Desconocido"));
                System.out.println("Horas: " + modulo.optInt("horas", 0));
                System.out.println("Nota: " + modulo.optDouble("nota", 0.0));
                System.out.println("---------------------");
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar el contenido JSON: " + e.getMessage());
        }
    }

}
