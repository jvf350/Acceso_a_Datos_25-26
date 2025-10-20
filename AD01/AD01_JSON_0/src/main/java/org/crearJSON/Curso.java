package org.crearJSON;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase que representa un curso que contiene varios módulos
public class Curso {

    private List<Modulo> modulos;

    public Curso() {
        this.modulos = new ArrayList<>();
    }

    public void addModulo(Modulo m) {
        modulos.add(m);
    }

    // Genera un objeto JSON que representa todo el curso
    public JSONObject getCursoAsJSON() {
        JSONObject curso = new JSONObject();
        JSONArray jsarray = new JSONArray();

        for (Modulo m : modulos) {
            jsarray.put(m.getAsJSON());
        }

        curso.put("curso", jsarray);
        return curso;
    }

    // Guarda el JSON del curso en un archivo
    public void guardarCursoEnArchivo(String nombreArchivo) {
        try (FileWriter file = new FileWriter(nombreArchivo)) {
            file.write(getCursoAsJSON().toString(4)); // indentación de 4 espacios
            System.out.println("Archivo JSON creado correctamente: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error en la creación del fichero JSON");
            e.printStackTrace();
        }
    }
}
