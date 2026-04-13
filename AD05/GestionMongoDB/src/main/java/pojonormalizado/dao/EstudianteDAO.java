package pojonormalizado.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import pojonormalizado.modelo.Estudiante;
import pojonormalizado.mongodb.MongoManager;
import pojonormalizado.utilidades.TextoUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.set;

public class EstudianteDAO {

    private final MongoCollection<Estudiante> collection;

    public EstudianteDAO() {
        this.collection = MongoManager.getDatabase()
                .getCollection("estudiantesnormalizado", Estudiante.class);
    }

    public void insertar(Estudiante estudiante) {
        estudiante.setNombreNormalizado(
                TextoUtils.normalizar(estudiante.getNombre())
        );
        collection.insertOne(estudiante);
    }

    public List<Estudiante> listar() {
        return collection.find().into(new ArrayList<>());
    }

    public Estudiante buscarPorNombre(String nombre) {
        return collection.find(
                Filters.eq("nombreNormalizado",
                        TextoUtils.normalizar(nombre))
        ).first();
    }

    public void actualizarEdad(String nombre, int edad) {
        collection.updateOne(
                Filters.eq("nombreNormalizado",
                        TextoUtils.normalizar(nombre)),
                set("edad", edad)
        );
    }

    public void eliminar(String nombre) {
        collection.deleteOne(
                Filters.eq("nombreNormalizado",
                        TextoUtils.normalizar(nombre))
        );
    }
}

