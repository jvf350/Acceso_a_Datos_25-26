package operaciones;

import org.iesalandalus.programacion.utilidades.Entrada;

public class Main {

    private static MongoManager mongoManager = new MongoManager();

    public static void main(String[] args) {
        mongoManager.connect();

        int opcion = 0;

        do {
            System.out.println("\n=== MENÚ GESTIÓN MONGO DB AVANZADO ===");
            System.out.println("1. Crear Colección");
            System.out.println("2. Borrar Colección");
            System.out.println("3. Listar colecciones");
            System.out.println("4. Buscar colección");
            System.out.println("5. Insertar Alumno");
            System.out.println("6. Listar todos los alumnos (Documento completo)");
            System.out.println("7. Buscar Alumnos por nombre");
            System.out.println("8. Buscar Alumnos por edad");
            System.out.println("9. Buscar Alumnos en cursos de Desarrollo (DAM o DAW)");
            System.out.println("10. Buscar Alumnos por asignaturas");
            System.out.println("11. Buscar Alumnos con un número de asignaturas");
            System.out.println("12. Buscar Alumnos que tengan campo edad");
            System.out.println("13. Eliminar Alumno");
            System.out.println("14. Modificar edad de Alumno");
            System.out.println("15. Incrementar edad de Alumno");
            System.out.println("16. Añadir asignatura a Alumno");
            System.out.println("17. Borrar asignatura a Alumno");
            System.out.println("18. Actualiza edad si supera edad actual Alumno");
            System.out.println("19. Listar bases de datos");
            System.out.println("20. Proyección: Listar alumnos");
            System.out.println("21. Proyección: Listar alumnos sin mostrar la edad");
            System.out.println("22. Proyección: Listar alumnos y sus primeras asignaturas");
            System.out.println("23. Proyección: Listar alumnos y asignaturas");
            System.out.println("24. Exportar datos a fichero JSON");
            System.out.println("25. Importar datos desde fichero JSON - Opción 1");
            System.out.println("26. Importar datos desde fichero JSON - Opción 2");
            System.out.println("27. Leer Objetos embebidos");
            System.out.println("28. Filtrar por objetos embebidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Entrada.entero();
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Introduce el nombre de la colección a crear: ");
                    String nombreColeccion=Entrada.cadena();
                    mongoManager.crearColleccion(nombreColeccion);
                    break;
                case 2:
                    System.out.print("Introduce el nombre de la colección a borrar: ");
                    nombreColeccion=Entrada.cadena();
                    mongoManager.borrarColeccion(nombreColeccion);
                    break;
                case 3:
                    System.out.println("Litado de colecciones existentes: ");
                    mongoManager.listarColecciones();
                    break;
                case 4:
                    System.out.print("Introduce el nombre de la colección a buscar en la base de datos: ");
                    nombreColeccion=Entrada.cadena();
                    mongoManager.buscarColeccion(nombreColeccion);
                    break;
                case 5:
                    System.out.print("Nombre: ");
                    String nombre = Entrada.cadena();
                    System.out.print("Edad: ");
                    int edad = Entrada.entero();
                    System.out.print("pojo.modelo.Curso: ");
                    String curso = Entrada.cadena();
                    mongoManager.insertarEstudiante(nombre, edad, curso);
                    break;
                case 6:
                    mongoManager.listarAlumnos();
                    break;
                case 7:
                    System.out.print("Nombre a buscar: ");
                    mongoManager.buscaAlumnoPorNombre(Entrada.cadena());
                    break;
                case 8:
                    System.out.println("Introduce la edad a usar como filtro: ");
                    mongoManager.buscaAlumnoPorEdad(Entrada.entero());
                    break;
                case 9:
                    mongoManager.buscaAlumnoPorCiclo();
                    break;
                case 10:
                    mongoManager.buscaAlumnoPorAsignaturas();
                    break;
                case 11:
                    System.out.print("Introduce el número de asignaturas de los alumnos a buscar: ");
                    mongoManager.buscaAlumnoConAsignaturas(Entrada.entero());
                    break;
                case 12:
                    mongoManager.buscaAlumnoConCampoEdad();
                    break;
                case 13:
                    System.out.print("Nombre a eliminar: ");
                    mongoManager.borrarEstudiante(Entrada.cadena());
                    break;
                case 14:
                    System.out.print("Nombre a actualizar: ");
                    String nombreAActualizar = Entrada.cadena();
                    System.out.print("Nueva edad: ");
                    int nuevaEdad = Entrada.entero();
                    mongoManager.actualizarEdadAlumno(nombreAActualizar, nuevaEdad);
                    break;
                case 15:
                    System.out.print("Nombre a actualizar: ");
                    nombreAActualizar = Entrada.cadena();
                    System.out.print("Introduce la cantidad de edad a incrementar: ");
                    int incrementoEdad = Entrada.entero();
                    mongoManager.incrementarEdadAlumno(nombreAActualizar, incrementoEdad);
                    break;
                case 16:
                    System.out.print("Nombre a actualizar: ");
                    nombreAActualizar = Entrada.cadena();
                    System.out.print("Introduce la nueva asignatura a añadir: ");
                    String nuevaAsignatura = Entrada.cadena();
                    mongoManager.anadeAsignaturaAlumno(nombreAActualizar, nuevaAsignatura);
                    break;
                case 17:
                    System.out.print("Nombre a actualizar: ");
                    nombreAActualizar = Entrada.cadena();
                    System.out.print("Introduce la asignatura a eliminar: ");
                    String asignaturaAEliminar = Entrada.cadena();
                    mongoManager.borraAsignaturaAlumno(nombreAActualizar, asignaturaAEliminar);
                    break;
                case 18:
                    System.out.print("Nombre a actualizar: ");
                    nombreAActualizar = Entrada.cadena();
                    System.out.print("Introduce la nueva edad para actualizar si supera a la edad actual: ");
                    nuevaEdad = Entrada.entero();
                    mongoManager.actualizaEdadSiSuperaEdadActualAlumno(nombreAActualizar, nuevaEdad);
                    break;

                case 19:
                    mongoManager.listarBasesDatos();
                    break;

                case 20: // PROYECCIÓN
                    mongoManager.listarAlumnosProyeccion();
                    break;
                case 21: //Proyección sin edad
                    mongoManager.listarAlumnosSinEdad();
                    break;
                case 22: //Proyección de arrays
                    System.out.print("Introduce el número de asignaturas a mostrar: ");
                    int numAsignaturas = Entrada.entero();
                    mongoManager.listarAlumnosYAlgunasAsignaturas(numAsignaturas);
                    break;
                case 23: //Proyección de arrays por posición
                    System.out.print("Introduce el número de asignaturas a mostrar: ");
                    numAsignaturas = Entrada.entero();
                    System.out.print("Introduce la posición en la que comenzar: ");
                    int posicion=Entrada.entero();
                    mongoManager.listarAlumnosAsignaturasDesdePosicion(posicion,numAsignaturas);
                    break;
                case 24: // EXPORTAR
                    System.out.print("Nombre del fichero destino (ej: backup.json): ");
                    String path=Entrada.cadena();
                    mongoManager.exportCollectionToFile(path);
                    break;
                case 25: //Importar
                    System.out.print("Introduce el nombre del fichero origen (ej: datos.json): ");
                    path=Entrada.cadena();
                    System.out.print("Introduce el nombre de la colección a la que importar: ");
                    String coleccion=Entrada.cadena();
                    mongoManager.importarColeccionDesdeFicheroOpcion1(coleccion, path);
                    break;
                case 26: // IMPORTAR
                    System.out.print("Introduce el nombre del fichero origen (ej: datos.json): ");
                    path=Entrada.cadena();
                    System.out.print("Introduce el nombre de la colección a la que importar: ");
                    coleccion=Entrada.cadena();
                    mongoManager.importarColeccionDesdeFicheroOpcion2(coleccion,path);
                    break;
                case 27:
                    mongoManager.leerObjetosEmbebidos();
                    break;
                case 28:
                    System.out.print("Introduce el código del curso por el que filtrar: ");
                    mongoManager.filtradoPorCamposObjetosEmbebidos(Entrada.cadena());
                    break;
                /*case 26: // Agregación
                    manager.aggregateStudentsByCourse();
                    break;
                */
                case 0:
                    System.out.println("Cerrando aplicación...");
                    mongoManager.close();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);


    }

}
