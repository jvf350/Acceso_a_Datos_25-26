package org.mapeo.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    private LocalDate fechaDevolucion;

    public Prestamo() {}

    public Prestamo(Socio socio, Libro libro, LocalDate fechaPrestamo) {
        this.socio = socio;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
    }

    public Long getId() { return id; }
    public Socio getSocio() { return socio; }
    public Libro getLibro() { return libro; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public boolean isActivo() {
        return fechaDevolucion == null;
    }

    @Override
    public String toString() {
        return "Prestamo{id=" + id +
                ", socio=" + (socio != null ? socio.getNombre() : "null") +
                ", libro=" + (libro != null ? libro.getTitulo() : "null") +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion + "}";
    }
}
