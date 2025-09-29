package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
 * Recuento elementos
 * Contador de vocales
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {

        // Pedimos al usuario la ruta del archivo por teclado
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la ruta del archivo: ");
        String ruta = sc.nextLine();
        sc.close();

        // Inicializamos contadores de cada vocal
        int a = 0, e = 0, i = 0, o = 0, u = 0;

        // Creamos el objeto File con la ruta introducida
        File f = new File(ruta);

        // Verificación de existencia y validez del archivo
        if (!f.exists()) {
            System.out.println("El archivo no existe");
            System.exit(1);
        }
        if (f.isDirectory()) {
            System.out.println("La ruta indicada es un directorio");
            System.exit(1);
        }

        // Lectura del archivo carácter a carácter
        try {
            FileReader fr = null;
            fr = new FileReader(f);
            int c;
            while ((c = fr.read()) != -1) {
                char letra = (char) c;
                if (letra == 'a' || letra == 'A') {
                    a++;
                }
                if (letra == 'e' || letra == 'E') {
                    e++;
                }
                if (letra == 'i' || letra == 'I') {
                    i++;
                }
                if (letra == 'o' || letra == 'O') {
                    o++;
                }
                if (letra == 'u' || letra == 'U') {
                    u++;
                }
            }

            // Mostramos resultados
            System.out.println("El número de a es " + a);
            System.out.println("El número de e es " + e);
            System.out.println("El número de i es " + i);
            System.out.println("El número de o es " + o);
            System.out.println("El número de u es " + u);

        } catch (IOException ex) {
            System.err.println("Error de entrada/salida");
        }
    }
}
