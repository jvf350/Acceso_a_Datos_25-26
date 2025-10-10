package ficheros;

import java.io.*;

public class LeerFicheroTextoBuf {

    public static void main(String[] args) {

        BufferedReader fichero = null;

        try {
            // montamos un buffer sobre FileReader
            fichero = new BufferedReader(new FileReader("src/main/java/ficheros/FicheroTexto1.txt"));
            String linea;

            while ((linea = fichero.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (FileNotFoundException fn) {
            System.out.println("No se encuentra el fichero");
        } catch (IOException io) {
            System.out.println("Error de E/S");
        } finally {
            try {
                fichero.close(); // cierre del fichero
            } catch (IOException ex) {
            }
        }

    }

}
