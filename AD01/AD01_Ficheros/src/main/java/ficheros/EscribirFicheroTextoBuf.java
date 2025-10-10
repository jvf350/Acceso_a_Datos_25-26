package ficheros;

import java.io.*;

public class EscribirFicheroTextoBuf {

    public static void main(String[] args) {

        BufferedWriter fichero = null;

        try {
            fichero = new BufferedWriter (new FileWriter("src/main/java/ficheros/FicheroTexto1.txt"));
            for (int i = 1; i<11; i++) {
                fichero.write("Fila numero:" + i); //escribe una línea
                fichero.newLine(); //salto de línea
            }
        }catch (FileNotFoundException fn) {
            System.out.println("No se encuentra el fichero");
        }catch (IOException io) {
            System.out.println("Error de E/S");
        }finally {
            try {
                fichero.close();
            }catch (IOException ex) {}
        }

    }

}
