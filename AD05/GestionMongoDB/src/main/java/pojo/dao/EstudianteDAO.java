package pojo.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import pojo.modelo.Estudiante;
import pojo.mongodb.MongoManager;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class EstudianteDAO {

    private final MongoCollection<Estudiante> collection;

    public EstudianteDAO() {
        collection = MongoManager.getDatabase()
                .getCollection("estudiantes", Estudiante.class);
    }

    public void insertar(Estudiante e) {
        collection.insertOne(e);
    }

    public List<Estudiante> listar() {
        return collection.find().into(new ArrayList<>());
    }

    public Estudiante buscarPorNombre(String nombre) {
        return collection.find(Filters.regex("nombre", nombre,"i")).first();
    }

    public void actualizarEdad(String nombre, int nuevaEdad) {
        collection.updateOne(
                Filters.regex("nombre", nombre,"i"),
                set("edad", nuevaEdad)
        );
    }

    public void eliminar(String nombre) {
        collection.deleteOne(Filters.regex("nombre", nombre,"i"));
    }
}
