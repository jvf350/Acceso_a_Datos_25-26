package org.mapeo.biblioteca.service;

import org.mapeo.biblioteca.jpa.JPAUtil;
import org.mapeo.biblioteca.model.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class BibliotecaService {

    // 1) Crear autor + libro (muestra cascada si usas autor.addLibro y persist autor)
    public Autor crearAutorConLibro(String nombreAutor, String titulo, int anio) {
        EntityManager em = JPAUtil.em();
        try {
            em.getTransaction().begin();

            Autor autor = new Autor(nombreAutor);
            Libro libro = new Libro(titulo, anio);
            autor.addLibro(libro);

            em.persist(autor); // cascade -> guarda también el libro

            em.getTransaction().commit();
            return autor;
        } finally {
            em.close();
        }
    }

    public Socio crearSocio(String nombre, String dni) {
        EntityManager em = JPAUtil.em();
        try {
            em.getTransaction().begin();
            Socio socio = new Socio(nombre, dni);
            em.persist(socio);
            em.getTransaction().commit();
            return socio;
        } finally {
            em.close();
        }
    }

    // 2) Listados (JPQL)
    public List<Libro> listarLibros() {
        EntityManager em = JPAUtil.em();
        try {
            TypedQuery<Libro> q = em.createQuery(
                    "SELECT l FROM Libro l ORDER BY l.titulo", Libro.class
            );
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Prestamo> listarPrestamosActivos() {
        EntityManager em = JPAUtil.em();
        try {
            TypedQuery<Prestamo> q = em.createQuery(
                    "SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NULL ORDER BY p.fechaPrestamo DESC",
                    Prestamo.class
            );
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // 3) Prestar libro (cambia disponible + crea préstamo)
    public void prestarLibro(long socioId, long libroId) {
        EntityManager em = JPAUtil.em();
        try {
            em.getTransaction().begin();

            Socio socio = em.find(Socio.class, socioId);
            Libro libro = em.find(Libro.class, libroId);

            if (socio == null) throw new IllegalArgumentException("Socio no existe: " + socioId);
            if (libro == null) throw new IllegalArgumentException("Libro no existe: " + libroId);
            if (!libro.isDisponible()) throw new IllegalStateException("El libro no está disponible");

            libro.setDisponible(false);

            Prestamo prestamo = new Prestamo(socio, libro, LocalDate.now());
            em.persist(prestamo);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // 4) Devolver libro
    public void devolverPrestamo(long prestamoId) {
        EntityManager em = JPAUtil.em();
        try {
            em.getTransaction().begin();

            Prestamo p = em.find(Prestamo.class, prestamoId);
            if (p == null) throw new IllegalArgumentException("Préstamo no existe: " + prestamoId);
            if (!p.isActivo()) throw new IllegalStateException("El préstamo ya está devuelto");

            p.setFechaDevolucion(LocalDate.now());
            p.getLibro().setDisponible(true);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
