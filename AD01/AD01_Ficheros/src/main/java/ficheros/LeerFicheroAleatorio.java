package ficheros;

import java.io.*;

public class LeerFicheroAleatorio {

    public static void main(String[] args) throws IOException {
        File fichero = new File ("src/main/java/ficheros/AleatorioEmple.dat");
        //declara el fichero de acceso aleatorio
        RandomAccessFile file = new RandomAccessFile(fichero, "r");
        int id, dep, posicion;
        double salario;
        char apellido[] = new char[10], aux;

        posicion=0; //para situarnos al principio

        file.seek(posicion); //con while apuntador fichero

        while(file.getFilePointer()<file.length()) {
            for(;;) {
                file.seek(posicion); //apuntador de fichero
                id=file.readInt(); //obtener id empleado
                for(int i=0; i<apellido.length; i++) {
                    aux=file.readChar(); //recorremos uno a uno los caracteres
                    apellido[i]=aux; //los vamos guardando en el array
                }

                String apellidos = new String (apellido); //convierto a String el array
                dep=file.readInt(); //obtengo departamento
                salario=file.readDouble(); //obtengo salario

                System.out.println("ID: "+id+", Apellido: "+apellidos+
                        ", Departamento "+dep+", Salario: "+salario);

                posicion+=36; //cada empleado ocupa 36 bytes (4+20+4+8)

                if(file.getFilePointer() == file.length()) break; //sale del bucle
                file.seek(posicion);
            }


        }
        file.close(); //cerrar el fichero
    }

}
