package ficheros;

import java.io.*;

public class EscribirFicheroAleatorio {

    public static void main(String[] args) throws IOException{

        File fichero = new File("src/main/java/ficheros/AleatorioEmple.dat");
        //declaramos el fichero de acceso aleatorio y modo acceso lectura/escritura
        RandomAccessFile file = new RandomAccessFile(fichero, "rw");

        //arrays con los datos - apellidos
        String apellido[]= {"FERNANDEZ","GIL","LOPEZ","RAMOS","SEVILLA","CASTILLA","REY"}; //apellidos
        int dep[]= {10,20,10,10,30,30,20}; //departamentos
        Double salario[]= {1000.45,2400.60,3000.0,1500.56,2200.0,2435.87,2000.0}; //salarios
        StringBuffer buffer = null; //buffer para almacenar apellido

        int n=apellido.length; //número de elementos del array

        for(int i=0; i<n; i++) {

            file.writeInt(i+1);
            buffer = new StringBuffer(apellido[i]); //i+1 es el identificador del empleado (4bytes)
            buffer.setLength(10); //10 caracteres para el apellido
            file.writeChars(buffer.toString()); //insertar apellido (20bytes)
            file.writeInt(dep[i]); //inserta departamento (4bytes)
            file.writeDouble(salario[i]); //inserta salario (8bytes)

        }
        file.close(); //cerrar el fichero
    }

}
