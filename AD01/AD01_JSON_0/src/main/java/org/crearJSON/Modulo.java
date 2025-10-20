package org.crearJSON;

import org.json.JSONObject;

// Clase que representa un módulo
public class Modulo {

    private String nombre;
    private int horas;
    private double nota;

    // Constructor
    public Modulo(String nombre, int horas, double nota) {
        this.nombre = nombre;
        this.horas = horas;
        this.nota = nota;
    }

    // Método para convertir el objeto Modulo a JSON
    public JSONObject getAsJSON() {
        JSONObject modulo = new JSONObject();

        modulo.put("nombre", this.nombre);
        modulo.put("horas", this.horas);
        modulo.put("nota", this.nota);

        // Si algún atributo pudiera ser nulo, usar:
        // modulo.put("atributo", this.atributo != null ? this.atributo : JSONObject.NULL);

        return modulo;
    }
}
