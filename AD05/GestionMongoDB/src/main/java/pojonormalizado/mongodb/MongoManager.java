package pojonormalizado.mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoManager {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Conexión a la BD (Criterio: Establecer conexión)
    public static void conectar() {
        try {

            // POJO codec
            CodecRegistry pojoCodecRegistry = fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            // Cadena de conexión estándar (localhost por defecto)
            String usuario = "mongodaw";
            String password = "mongodaw";
            String uri = "mongodb+srv://" + usuario + ":" + password +
                    "@cluster0.uyprv4n.mongodb.net/?retryWrites=true&w=majority";
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("institutoDB").withCodecRegistry(pojoCodecRegistry);;

            System.out.println(">> Conexión establecida con éxito.");
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }

    // Cierre de recursos (Criterio: Cierre de conexiones)
    public static void cerrar() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println(">> Conexión cerrada.");
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
}
