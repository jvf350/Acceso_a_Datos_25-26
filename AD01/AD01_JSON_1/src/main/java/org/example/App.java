package org.example;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Programa principal: Se crean algunos alumnos de ejemplo y se genera el fichero JSON
 *
 */
public class App 
{
    public static void main(String[] args) {

        AlumnoTel a1 = new AlumnoTel(1000L, "Jose Andres Perez", 22, false, new String[]{"222333444", "555666777", "888999000"});
        AlumnoTel a2 = new AlumnoTel(1001L, "Ana Sánchez Martínez", 21, false, new String[]{"222333444", "555666777", "888999000", "333421039"});
        AlumnoTel a3 = new AlumnoTel(1002L, "Adriana Gómez López", 23, true, new String[]{});
        AlumnoTel a4 = new AlumnoTel(1003L, "Ainara Vicente", 24, false, new String[]{"222111214", "515626677"});

        // Creo elemento raíz
        JSONObject clase = new JSONObject();

        // Creo el array que contendrá a los alumnos
        JSONArray alumnos = new JSONArray();
        clase.append("alumnos", alumnos);

        // Añado los elementos al array
        alumnos.put(a1.asJSON());
        alumnos.put(a2.asJSON());
        alumnos.put(a3.asJSON());
        alumnos.put(a4.asJSON());

        // Genero el fichero
        FileWriter file;
        try {
            file = new FileWriter("alumnos.json");
            file.write(clase.toString(4)); // 4 espacios de indentación
            file.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
