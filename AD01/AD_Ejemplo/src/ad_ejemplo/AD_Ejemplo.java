package ad_ejemplo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author jm
 * @version 0.1.0
 */

public class AD_Ejemplo {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    
    public static void main(String[] args) throws IOException, FileNotFoundException {

        String directorio ="dir/";
        
        File archivos=new File(directorio);
        File f=new File(directorio+"prueba.txt");
        
        FileReader fr=new FileReader(f);
        int c;
        
        archivos.delete();
        f.delete();
        
        if (!archivos.exists() && !f.exists()){

            archivos.mkdir();
            f.createNewFile();
        }
        
        System.out.println("Directorio y fichero creados");
        System.out.println(System.getProperty("user.dir"));
                
        if (f.isFile())
            System.out.println("El tamaño es de " + f.length() + " bytes");
        
        /* Lectura fichero caracter a caracter.
         (sin hacer casting)
        */

        /*try {
            while ((c = fr.read()) !=-1) {
                System.out.print(c);
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        
        /* Lectura fichero caracter a caracter.
         Como el método int read() lee un caracter y lo devuelve en un entero
         hay que hacer un casting para convertirlo en char
        */

        try {
            
            while ((c = fr.read()) != -1) {
                char letra = (char) c;
                System.out.print(letra);
            }
        } catch (IOException e){
            System.err.println("Error al leer archivo" + e.getMessage());
        
        }
    }
    
}
