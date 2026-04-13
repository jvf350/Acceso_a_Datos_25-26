package pojo.dao;

import com.mongodb.client.MongoCollection;
import pojo.modelo.Curso;
import pojo.mongodb.MongoManager;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private final MongoCollection<Curso> collection;

    public CursoDAO() {
        collection = MongoManager.getDatabase().getCollection("cursos", Curso.class);
    }

    public void insertar(Curso curso) {
        collection.insertOne(curso);
    }

    public List<Curso> listar() {
        return collection.find().into(new ArrayList<>());
    }

    public Curso buscarPorCodigo(String codigo) {
        return collection.find(Filters.regex("codigo", codigo,"i")
        ).first();
    }

    public void eliminarPorCodigo(String codigo) {
        collection.deleteOne(Filters.regex("codigo", codigo,"i")
        );
    }
}

