package pojocollations.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.DeleteOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import pojocollations.modelo.Estudiante;
import pojocollations.mongodb.MongoManager;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.set;

public class EstudianteDAO {

    private final MongoCollection<Estudiante> collection;

    public EstudianteDAO() {
        this.collection = MongoManager.getDatabase()
                .getCollection("estudiantes", Estudiante.class);
    }

    public void insertar(Estudiante estudiante) {
        collection.insertOne(estudiante);
    }

    public List<Estudiante> listar() {
        return collection.find().into(new ArrayList<>());
    }

    public Estudiante buscarPorNombre(String nombre) {
        return collection.find(
                        Filters.eq("nombre", nombre)
                ).collation(MongoManager.getCollationEs())
                .first();
    }

    public void actualizarEdad(String nombre, int edad) {
        collection.updateOne(
                Filters.eq("nombre", nombre),
                set("edad", edad),
                new UpdateOptions().collation(MongoManager.getCollationEs())
        );
    }

    public void eliminar(String nombre) {
        collection.deleteOne(
                Filters.eq("nombre", nombre),
                new DeleteOptions().collation(MongoManager.getCollationEs())
        );
    }
}
