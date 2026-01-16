package org.mapeo.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false)
    private int anioPublicacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(nullable = false)
    private boolean disponible = true;

    public Libro() {}

    public Libro(String titulo, int anioPublicacion) {
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "Libro{id=" + id + ", titulo='" + titulo + "', anio=" + anioPublicacion +
                ", autor=" + (autor != null ? autor.getNombre() : "null") +
                ", disponible=" + disponible + "}";
    }
}
