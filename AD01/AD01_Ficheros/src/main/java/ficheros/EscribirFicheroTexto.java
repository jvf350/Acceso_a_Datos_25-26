package ficheros;

import java.io.*;

public class EscribirFicheroTexto {

    public static void main(String[] args) throws IOException {

        //File fichero = new File ("src/main/java/ficheros/FicheroTexto.txt");

        File fichero = new File ("C:\\tmp\\FicheroTexto2.txt");
        FileWriter fw = new FileWriter(fichero, true); //creando el flujo de salida

        String cadena = "Esto es un ejemplo version 0";

        char[] cad = cadena.toCharArray();

        //for(int i=0; i<cad.length; i++) {
            fw.write(cadena); //se va escribiendo un caracter
        //}

        fw.append('#'); //añade el caracter final
        fw.close(); //cierra fichero
    }

}
