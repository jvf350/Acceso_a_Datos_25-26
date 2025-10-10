package ficheros;

import java.io.*;

public class LeerFicheroBinario {

    public static void main(String[] args) throws IOException{

        File fichero = new File("src/main/java/ficheros/FicheroBinario.dat");
        FileInputStream filein = new FileInputStream(fichero);

        DataInputStream dataIS = new DataInputStream(filein);
        String n;
        int e;

        try {
            while(true) {
                n=dataIS.readUTF();
                e=dataIS.readInt();


                System.out.println("Nombre: "+ n +", edad: " + e);
            }
        }catch(EOFException eo) {

        }
        dataIS.close(); //cierra el flujo
    }

}