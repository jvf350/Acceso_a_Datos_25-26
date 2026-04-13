package pojocollations.modelo;

import org.bson.types.ObjectId;

import java.util.List;

public class Estudiante {

    private ObjectId id;
    private String nombre;
    private Curso curso;
    private List<String> asignaturas;
    private Integer edad; // Integer (no int) → puede no existir

    public Estudiante() {} // obligatorio

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<String> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<String> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}

