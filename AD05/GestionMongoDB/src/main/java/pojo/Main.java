package pojo;

import org.iesalandalus.programacion.utilidades.Entrada;
import pojo.dao.CursoDAO;
import pojo.dao.EstudianteDAO;
import pojo.modelo.Curso;
import pojo.modelo.Estudiante;
import pojo.mongodb.MongoManager;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MongoManager.conectar();
        CursoDAO cursoDAO = new CursoDAO();
        EstudianteDAO estudianteDAO = new EstudianteDAO();


        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ DE OPERACIONES ===");
            System.out.println("1. Insertar curso");
            System.out.println("2. Listar cursos");
            System.out.println("3. Insertar estudiante");
            System.out.println("4. Listar estudiantes");
            System.out.println("5. Buscar estudiante por nombre");
            System.out.println("6. Actualizar edad de estudiante");
            System.out.println("7. Eliminar estudiante");
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");

            int opcion = Entrada.entero();

            switch (opcion) {
                case 1 -> insertarCurso(cursoDAO);
                case 2 -> listarCursos(cursoDAO);
                case 3 -> insertarEstudiante(estudianteDAO, cursoDAO);
                case 4 -> listarEstudiantes(estudianteDAO);
                case 5 -> buscarEstudiante(estudianteDAO);
                case 6 -> actualizarEdad(estudianteDAO);
                case 7 -> eliminarEstudiante(estudianteDAO);
                case 8 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }

        System.out.println("¡Programa finalizado!");
    }

    // ===== Métodos auxiliares =====

    private static void insertarCurso(CursoDAO cursoDAO) {
        Curso c = new Curso();
        System.out.print("Código del curso: ");
        c.setCodigo(Entrada.cadena());
        System.out.print("Horas: ");
        c.setHoras(Entrada.entero());
        System.out.print("Ciclo: ");
        c.setCiclo(Entrada.cadena());

        cursoDAO.insertar(c);
        System.out.println("Curso insertado correctamente.");
    }

    private static void listarCursos(CursoDAO cursoDAO) {
        List<Curso> cursos = cursoDAO.listar();
        System.out.println("=== LISTA DE CURSOS ===");
        for (Curso c : cursos) {
            System.out.println(c.getCodigo() + " - " + c.getCiclo() + " - " + c.getHoras() + "h");
        }
    }

    private static void insertarEstudiante(EstudianteDAO estudianteDAO, CursoDAO cursoDAO) {
        Estudiante e = new Estudiante();

        System.out.print("Nombre del estudiante: ");
        e.setNombre(Entrada.cadena());

        System.out.print("Edad: ");
        e.setEdad(Entrada.entero());

        System.out.print("Código del curso: ");
        String codigoCurso = Entrada.cadena();
        Curso curso = cursoDAO.buscarPorCodigo(codigoCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado, primero inserta el curso.");
            return;
        }
        e.setCurso(curso);

        System.out.print("Asignaturas (separadas por coma): ");
        String line = Entrada.cadena();
        e.setAsignaturas(Arrays.asList(line.split(",")));

        estudianteDAO.insertar(e);
        System.out.println("Estudiante insertado correctamente.");
    }

    private static void listarEstudiantes(EstudianteDAO estudianteDAO) {
        List<Estudiante> estudiantes = estudianteDAO.listar();
        System.out.println("=== LISTA DE ESTUDIANTES ===");
        for (Estudiante e : estudiantes) {
            System.out.println("Nombre: " + e.getNombre() +
                    " | Edad: " + e.getEdad() +
                    " | Curso: " + (e.getCurso() != null ? e.getCurso().getCodigo() : "N/A") +
                    " | Asignaturas: " + e.getAsignaturas());
        }
    }

    private static void buscarEstudiante(EstudianteDAO estudianteDAO) {
        System.out.print("Nombre del estudiante a buscar: ");
        String nombre = Entrada.cadena();
        Estudiante e = estudianteDAO.buscarPorNombre(nombre);
        if (e != null) {
            System.out.println("Nombre: " + e.getNombre());
            System.out.println("Edad: " + e.getEdad());
            if (e.getCurso() != null) {
                System.out.println("Curso: " + e.getCurso().getCodigo() + " - " + e.getCurso().getCiclo());
            }
            System.out.println("Asignaturas: " + e.getAsignaturas());
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    private static void actualizarEdad(EstudianteDAO estudianteDAO) {
        System.out.print("Nombre del estudiante: ");
        String nombre = Entrada.cadena();
        System.out.print("Nueva edad: ");
        int edad = Entrada.entero();

        estudianteDAO.actualizarEdad(nombre, edad);
        System.out.println("Edad actualizada.");
    }

    private static void eliminarEstudiante(EstudianteDAO estudianteDAO) {
        System.out.print("Nombre del estudiante a eliminar: ");
        String nombre = Entrada.cadena();
        estudianteDAO.eliminar(nombre);
        System.out.println("Estudiante eliminado.");
    }
}
