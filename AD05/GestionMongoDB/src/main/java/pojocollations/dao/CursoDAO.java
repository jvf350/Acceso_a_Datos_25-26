package pojocollations.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import pojocollations.modelo.Curso;
import pojocollations.mongodb.MongoManager;

import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private final MongoCollection<Curso> collection;

    public CursoDAO() {
        this.collection = MongoManager.getDatabase()
                .getCollection("cursos", Curso.class);
    }

    public void insertar(Curso curso) {
        collection.insertOne(curso);
    }

    public List<Curso> listar() {
        return collection.find().into(new ArrayList<>());
    }

    public Curso buscarPorCodigo(String codigo) {
        return collection.find(
                        Filters.eq("codigo", codigo)
                ).collation(MongoManager.getCollationEs())
                .first();
    }

    public void eliminarPorCodigo(String codigo) {
        collection.deleteOne(
                Filters.eq("codigo", codigo),
                new com.mongodb.client.model.DeleteOptions()
                        .collation(MongoManager.getCollationEs())
        );
    }
}
