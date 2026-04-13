package operaciones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

public class MongoManager {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;


    // Conexión a la BD (Criterio: Establecer conexión)
    public void connect() {
        try {
            // Cadena de conexión estándar (localhost por defecto)
            String usuario = "mongodam";
            String password = "mongodam";
            String uri = "mongodb+srv://" + usuario + ":" + password +
                    "@cluster0.gqrrxyi.mongodb.net/?appName=Cluster0";
            this.mongoClient = MongoClients.create(uri);this.database = mongoClient.getDatabase("institutoDB");

            // Seleccionamos una colección por defecto para empezar
            this.collection = database.getCollection("alumnos");

            System.out.println(">> Conexión establecida con éxito.");
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }

    // Cierre de recursos (Criterio: Cierre de conexiones)
    public void close() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
            System.out.println(">> Conexión cerrada.");
        }
    }

    public void crearColleccion(String nombre)
    {
        database.createCollection(nombre.toLowerCase());
        System.out.println("Colección " + nombre + " creada correctamente.");
    }

    public void borrarColeccion(String nombre)
    {
        database.getCollection(nombre.toLowerCase()).drop();
        System.out.println("Colección " + nombre + " borrada correctamente.");
    }

    public void listarColecciones()
    {
        for (String nombre : database.listCollectionNames()) {
            System.out.println("- " + nombre);
        }
    }

    public void buscarColeccion(String nombre)
    {
        List<String> colecciones = database.listCollectionNames().into(new ArrayList<>());

        if (colecciones.contains(nombre.toLowerCase())) {
            System.out.println("La colección " + nombre + " existe.");
        }
        else {
            System.out.println("La colección " + nombre + " no existe.");
        }
    }

    // 1. Insertar Documento (Criterio: Añadir documentos)
    public void insertarEstudiante(String nombre, int edad, String curso) {

        List<String> asignaturas = Arrays.asList("Java", "SQL", "Python");

        Document doc = new Document("nombre", nombre)
                .append("edad", edad)
                .append("curso", curso)
                .append("asignaturas", asignaturas);

        collection.insertOne(doc);
        System.out.println(">> Alumno insertado correctamente.");
    }

    // 2. Listar Todos (Criterio: Realizar consultas)
    public void listarAlumnos() {
        System.out.println("--- LISTA DE ALUMNOS ---");
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }

        System.out.println("--- LISTA DE ALUMNOS VERSIÓN 2");
        List<Document> listaAlumnos=collection.find().into(new ArrayList<>());
        System.out.println("Total documentos: " + listaAlumnos.size());
        for(Document doc:listaAlumnos)
        {
            System.out.println("Nombre: " + doc.getString("nombre"));

            // Comprobar si existe el campo edad
            if (doc.containsKey("edad")) {
                System.out.println("Edad: " + doc.getInteger("edad"));
            }

//            Integer edad = doc.getInteger("edad");
//            if (edad != null) {
//                System.out.println("Edad: " + edad);
//            }

//            Integer edad = doc.getInteger("edad");
//            System.out.println("Edad: " + (edad != null ? edad : "No especificada"));

            System.out.println("pojo.modelo.Curso: " + doc.getString("curso"));

            // Leer array asignaturas
            List<String> asignaturas = doc.getList("asignaturas", String.class);

            System.out.println("Asignaturas:");
            if (asignaturas != null) {
                for (String asignatura : asignaturas) {
                    System.out.println(" - " + asignatura);
                }
            }

            System.out.println("-----------------------------");
        }

    }

    // 7. Buscar por Nombre (Criterio: Consultas con filtro)
    public void buscaAlumnoPorNombre(String nombre) {

//        Document doc = collection.find(Filters.eq("nombre", nombre)).first();
        Document doc = collection.find(Filters.regex("nombre", nombre,"i")).first();
        if (doc != null) {
            System.out.println("Encontrado: " + doc.toJson());
        } else {
            System.out.println("No se encontró ningún alumno con ese nombre.");
        }

    }

    // 8. Buscar por Edad (Criterio: Consultas con filtro)
    public void buscaAlumnoPorEdad(int edad) {

        Document doc = collection.find(Filters.gte("edad", edad)).first();
        if (doc != null) {
            System.out.println("Encontrado: " + doc.toJson());
        } else {
            System.out.println("No se encontró ningún alumno con una edad mayor o igual a " + edad);
        }

    }

    // 9. Buscar por ciclo (Criterio: Consultas con filtro)
    public void buscaAlumnoPorCiclo() {

        Document doc = collection.find(Filters.or(Filters.eq("curso", "2DAW"),
                Filters.eq("curso", "2DAM"))).first();

        if (doc != null) {
            System.out.println("Encontrado: " + doc.toJson());
        } else {
            System.out.println("No se encontró ningún alumno pertenecientes a ciclos de desarrollo." );
        }

    }

    // 10. Buscar por asignaturas (Criterio: Consultas con filtro)
    public void buscaAlumnoPorAsignaturas() {
        Document doc = collection.find(Filters.all("asignaturas",Arrays.asList("Java","SQL"))).first();

        if (doc != null) {
            System.out.println("Encontrado: " + doc.toJson());
        } else {
            System.out.println("No se encontró ningún alumno pertenecientes a ciclos de desarrollo." );
        }
    }

    //11. Buscar por asignaturas (Criterio: Consultas con filtro)
    public void buscaAlumnoConAsignaturas(int numeroAsignaturas) {
        Document doc = collection.find(Filters.size("asignaturas",numeroAsignaturas)).first();

        if (doc != null) {
            System.out.println("Encontrado: " + doc.toJson());
        } else {
            System.out.println("No se encontró ningún alumno pertenecientes a ciclos de desarrollo." );
        }
    }

    //12. Buscar por alumnos con campo edad (Criterio: Consultas con filtro)
    public void buscaAlumnoConCampoEdad()
    {
        List<Document> listaAlumnos = collection
                .find(Filters.exists("edad"))
                .into(new ArrayList<>());

        for (Document doc : listaAlumnos) {
            System.out.println("Nombre: " + doc.getString("nombre"));
            System.out.println("Edad: " + doc.getInteger("edad"));
            System.out.println("pojo.modelo.Curso: " + doc.getString("curso"));
            System.out.println("---------------------------");
        }
    }


    // 13. Eliminar Alumno (Criterio: Eliminar documentos)
    public void borrarEstudiante(String nombre) {
        var result = collection.deleteOne(Filters.eq("nombre", nombre));
        System.out.println(">> Documentos eliminados: " + result.getDeletedCount());
    }

    // 14. Actualizar Edad (Criterio: Modificar documentos)
    public void actualizarEdadAlumno(String nombre, int nuevaEdad) {
        var result = collection.updateOne(
                Filters.eq("nombre", nombre),
                set("edad", nuevaEdad)
        );

        System.out.println(">> Documentos modificados: " + result.getModifiedCount());
    }

    // 15. Incrementar edad
    public void incrementarEdadAlumno(String nombre, int incrementoEdad)
    {
        var result = collection.updateOne(
                Filters.eq("nombre", nombre),
                inc("edad", incrementoEdad)
        );

        System.out.println(">> Documentos modificados: " + result.getModifiedCount());
    }

    //16. Añade asignatura
    public void anadeAsignaturaAlumno(String nombreAActualizar, String nuevaAsignatura)
    {
       var result=collection.updateOne(
               Filters.eq("nombre", nombreAActualizar),
               addToSet("asignaturas", nuevaAsignatura));

        System.out.println(">> Documentos modificados: " + result.getModifiedCount());
    }

    //17. Borra asignatura
    public void borraAsignaturaAlumno(String nombreAActualizar, String asignatura)
    {
        var result=collection.updateOne(
                Filters.eq("nombre", nombreAActualizar),
                pull("asignaturas", asignatura));

        System.out.println(">> Documentos modificados: " + result.getModifiedCount());
    }

    //18. Actualiza edad si supera a la edad actual
    public void actualizaEdadSiSuperaEdadActualAlumno(String nombreAActualizar, int edad)
    {
        var result=collection.updateOne(
                Filters.eq("nombre", nombreAActualizar),
                max("edad", edad));

        System.out.println(">> Documentos modificados: " + result.getModifiedCount());
    }

    //19. Listar todas las bases de datos existentes
    public void listarBasesDatos()
    {
        System.out.println("Listado de bases de datos: ");
        for (String nombre : mongoClient.listDatabaseNames()) {
            System.out.println(" * " + nombre);
        }
    }

    // 20. Proyección (Criterio: Consultas optimizadas)
    // Muestra SOLO nombre y curso, ocultando el _id y la edad.
    public void listarAlumnosProyeccion()
    {

        System.out.println("--- LISTADO DE NOMBRES Y CURSOS (PROYECCIÓN) ---");

        List<Document> listaAlumnos=collection.find()
                .projection(fields(
                        include("nombre", "curso"),
                        excludeId()))
                .into(new ArrayList<>());

        for(Document doc: listaAlumnos)
        {
            System.out.println(doc.toJson());
        }

    }

    // 21. Proyección (Criterio: Consultas optimizadas)
    // Excluye edad.
    public void listarAlumnosSinEdad()
    {

        System.out.println("--- LISTADO DE ALUMNOS SIN LA EDAD (PROYECCIÓN) ---");

        List<Document> listaAlumnos=collection.find()
                //.projection(fields(exclude("edad"),excludeId()))
                .projection(exclude("edad"))
                .into(new ArrayList<>());

        for(Document doc: listaAlumnos)
        {
            System.out.println(doc.toJson());
        }

    }

    // 22. Proyección por número de elementos
    public void listarAlumnosYAlgunasAsignaturas(int numAsignaturas)
    {

        System.out.println("--- LISTADO DE ALUMNOS Y SUS ASIGNATURAS (PROYECCIÓN) ---");

        List<Document> listaAlumnos=collection.find()
                .projection(fields(
                        include("nombre"),
                        slice("asignaturas",numAsignaturas)))
                .into(new ArrayList<>());

        for(Document doc: listaAlumnos)
        {
            System.out.println(doc.toJson());
        }

    }

    //23. Proyección de arrays desde posición y con número de elementos
    public void listarAlumnosAsignaturasDesdePosicion(int posicion, int numAsignaturas)
    {
        System.out.println("--- LISTADO DE ALUMNOS Y SUS ASIGNATURAS (PROYECCIÓN) ---");

        List<Document> listaAlumnos=collection.find()
                .projection(fields(
                        include("nombre"),
                        slice("asignaturas",posicion,numAsignaturas)))
                .into(new ArrayList<>());

        for(Document doc: listaAlumnos)
        {
            System.out.println(doc.toJson());
        }
    }

    //24. Exportar Colección a Fichero
    public void exportCollectionToFile(String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("[");
            boolean first = true;

            for (Document doc : collection.find()) {

                if (!first) {
                    writer.write(",");
                }
                first = false;

                writer.write(doc.toJson());
            }

            writer.write("]");

            System.out.println(">> Exportación correcta a: " + filePath);

        } catch (IOException e) {
            System.out.println("Error exportando: " + e.getMessage());
        }
    }


    //25. Importar opción 1
    public void importarColeccionDesdeFicheroOpcion1(String coleccion, String nombrefich) {

        try {
            // Leer JSON desde archivo
            String json = Files.readString(Paths.get(nombrefich));
            ObjectMapper mapper = new ObjectMapper();

            // lee el JSON (como String) y lo convierte en una lista de mapas,
            // donde cada mapa representa un objeto con sus claves y valores
            List<Map<String, Object>> nuevosdatos = mapper.readValue(json, List.class);

            // Convierte cada objeto JSON leído como Map<String, Object> en un
            // documento BSON de MongoDB y lo añade a una lista de documentos.
            List<Document> documentos = new ArrayList<>();
            for (Map<String, Object> documento : nuevosdatos) {
                documentos.add(new Document(documento));
            }
            // insertar la lista en la colección
            // Se carga la colección
            MongoCollection<Document> nuevaColeccion = database.getCollection(coleccion);
            nuevaColeccion.insertMany(documentos);
            System.out.println("Datos insertados.");

            // Mostrar los documentos
            List<Document> coleccionImportada = nuevaColeccion.find().into(new ArrayList<>());
            System.out.println("** Número de documentos: " + coleccionImportada.size());
            for (Document documento : coleccionImportada) {
                // Leer array asignaturas
                List<String> asignaturas = documento.getList("asignaturas", String.class);
                String listaAsignaturas=". Asignaturas: ";

                if (asignaturas != null) {
                    for (String asignatura : asignaturas) {
                        listaAsignaturas+=" - " + asignatura;
                    }
                }

                System.out.println("Nombre: " + documento.getString("nombre") + ". pojo.modelo.Curso: " + documento.getString("curso")
                        + ". Edad: " + documento.get("edad")
                        + listaAsignaturas);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //25. Importar opción 2
    public void importarColeccionDesdeFicheroOpcion2(String nombreColeccion, String filePath) {

        try {
            String json = Files.readString(Paths.get(filePath));

            MongoCollection<Document> coleccionImportar=database.getCollection(nombreColeccion);

            // Parsear el array JSON
            List<Document> docs = Document.parse("{ \"data\": " + json + " }")
                    .getList("data", Document.class);

            if (!docs.isEmpty()) {
                coleccionImportar.insertMany(docs);
            }

            System.out.println(">> Importación correcta desde: " + filePath);

        } catch (IOException e) {
            System.out.println("Error leyendo fichero: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error importando: " + e.getMessage());
        }
    }


    public void leerObjetosEmbebidos() {

        MongoCollection<Document> collection = database.getCollection("estudiantes");

        List<Document> listaEstudiantes = collection.find().into(new ArrayList<>());

        for (Document doc : listaEstudiantes) {

            // Campos simples
            System.out.println("Nombre: " + doc.getString("nombre"));
            System.out.println("Edad: " + doc.getInteger("edad"));

            // Objeto embebido: curso
            Document curso = doc.get("curso", Document.class);
            if (curso != null) {
                System.out.println("pojo.modelo.Curso:");
                System.out.println("  Código: " + curso.getString("codigo"));
                System.out.println("  Horas: " + curso.getInteger("horas"));
                System.out.println("  Ciclo: " + curso.getString("ciclo"));
            }

            // Array: asignaturas
            List<String> asignaturas = doc.getList("asignaturas", String.class);
            if (asignaturas != null && !asignaturas.isEmpty()) {
                System.out.println("Asignaturas:");
                for (String asignatura : asignaturas) {
                    System.out.println("  - " + asignatura);
                }
            }

            System.out.println("----------------------------------");
        }
    }

    public void filtradoPorCamposObjetosEmbebidos(String codigo)
    {

        List<Document> listaAlumnos=database.getCollection("estudiantes").find(Filters.eq("curso.codigo", codigo))
                .projection(fields(include("nombre", "curso.codigo"),excludeId()))
                .into(new ArrayList<>());

        if (listaAlumnos.isEmpty())
            System.out.println("No hay ningún estudiante que cumpla la condición establecida.");
        else
        {
            for(Document doc:listaAlumnos)
            {
                System.out.println(doc.toJson());
            }
        }


    }



}
