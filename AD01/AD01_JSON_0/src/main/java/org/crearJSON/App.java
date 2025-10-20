package org.crearJSON;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Curso curso = new Curso();
        curso.addModulo(new Modulo("Acceso a Datos", 60, 9.5));
        curso.addModulo(new Modulo("Entornos de Desarrollo", 45, 8.7));
        curso.addModulo(new Modulo("Programación", 80, 10.0));

        // Guardamos el curso en un archivo JSON
        curso.guardarCursoEnArchivo("curso.json");
    }
}
