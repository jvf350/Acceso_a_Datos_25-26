package org.mapeo.biblioteca.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "socio")
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @OneToMany(mappedBy = "socio")
    private List<Prestamo> prestamos = new ArrayList<>();

    public Socio() {}

    public Socio(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDni(String dni) { this.dni = dni; }
    public List<Prestamo> getPrestamos() { return prestamos; }

    @Override
    public String toString() {
        return "Socio{id=" + id + ", nombre='" + nombre + "', dni='" + dni + "'}";
    }
}