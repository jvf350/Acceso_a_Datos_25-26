package ficheros;

import java.io.*;

public class EscribirFicheroBinario {

    public static void main(String[] args) throws IOException {

        File fichero = new File ("src/main/java/ficheros/FicheroBinario.dat");
        FileOutputStream fileout = new FileOutputStream (fichero);

        DataOutputStream dataOS = new DataOutputStream(fileout);

        String nombres[]= {"Ana","Luis Miguel","Alicia","Pedro",
                "Manuel","Andrés","Julio", "Antonio", "María Jesús"};

        int edades[] = {24,25,23,25,26,22,26,24,23};

        for(int i=0; i<edades.length;i++) {
            dataOS.writeUTF(nombres[i]); //inserta nombre
            dataOS.writeInt(edades[i]); //inserta edad

        }

        dataOS.close();
    }

}
