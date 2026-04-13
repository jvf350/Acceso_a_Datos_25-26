package pojonormalizado.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import pojonormalizado.utilidades.TextoUtils;
import pojonormalizado.modelo.Curso;
import pojonormalizado.mongodb.MongoManager;

import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private final MongoCollection<Curso> collection;

    public CursoDAO() {
        this.collection = MongoManager.getDatabase()
                .getCollection("cursosnormalizado", Curso.class);
    }

    public void insertar(Curso curso) {
        curso.setCodigoNormalizado(
                TextoUtils.normalizar(curso.getCodigo())
        );
        collection.insertOne(curso);
    }

    public List<Curso> listar() {
        return collection.find().into(new ArrayList<>());
    }

    public Curso buscarPorCodigo(String codigo) {
        return collection.find(
                Filters.eq("codigoNormalizado",
                        TextoUtils.normalizar(codigo))
        ).first();
    }

    public void eliminarPorCodigo(String codigo) {
        collection.deleteOne(Filters.regex("codigo", codigo,"i")
        );
    }
}

