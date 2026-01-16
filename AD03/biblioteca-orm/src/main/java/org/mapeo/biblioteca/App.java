package org.mapeo.biblioteca;

import org.mapeo.biblioteca.model.Libro;
import org.mapeo.biblioteca.model.Prestamo;
import org.mapeo.biblioteca.jpa.JPAUtil;
import org.mapeo.biblioteca.service.BibliotecaService;

import java.util.List;
import java.util.Scanner;


public class App {

    public static void main(String[] args) {
        BibliotecaService service = new BibliotecaService();
        Scanner sc = new Scanner(System.in);

        // Datos de prueba
        service.crearAutorConLibro("Santiago Díaz", "Jotadé", 2025);
        service.crearAutorConLibro("Ernest Hemingway", "Fiesta", 1926);
        service.crearSocio("Laura Pérez", "12345678A");
        service.crearSocio("Marta Ruiz", "87654321B");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Biblioteca ORM ===");
            System.out.println("1) Listar libros");
            System.out.println("2) Prestar libro");
            System.out.println("3) Listar préstamos activos");
            System.out.println("4) Devolver préstamo");
            System.out.println("0) Salir");
            System.out.print("Opción: ");

            String op = sc.nextLine().trim();
            try {
                switch (op) {
                    case "1" -> {
                        List<Libro> libros = service.listarLibros();
                        libros.forEach(System.out::println);
                    }
                    case "2" -> {
                        System.out.print("ID socio: ");
                        long socioId = Long.parseLong(sc.nextLine());
                        System.out.print("ID libro: ");
                        long libroId = Long.parseLong(sc.nextLine());
                        service.prestarLibro(socioId, libroId);
                        System.out.println("Préstamo registrado.");
                    }
                    case "3" -> {
                        List<Prestamo> activos = service.listarPrestamosActivos();
                        activos.forEach(System.out::println);
                    }
                    case "4" -> {
                        System.out.print("ID préstamo: ");
                        long prestamoId = Long.parseLong(sc.nextLine());
                        service.devolverPrestamo(prestamoId);
                        System.out.println("Préstamo devuelto.");
                    }
                    case "0" -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        JPAUtil.shutdown();
        sc.close();
    }
}
