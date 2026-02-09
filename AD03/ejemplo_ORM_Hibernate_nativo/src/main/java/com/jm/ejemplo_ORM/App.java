package com.jm.ejemplo_ORM;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.io.File;

/**
 * Primer proyecto ORM con Hibernate
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    			//Inicialización del SessionFactory
    			StandardServiceRegistry sr = new StandardServiceRegistryBuilder()
    					.configure()
    					.build();
    			SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
    			   			
    			// Creamos el SessionFactory desde el archivo estándar de configuración (hibernate.cfg.xml)
    			/*SessionFactory sf = new Configuration()
                    .configure(new File("hibernate.cfg.xml")).buildSessionFactory();*/    
    			
    			/* DEPRECATED !!!
    			//Codigo "legacy"
    			SessionFactory sf = new Configuration()
    					.configure()
    					.buildSessionFactory();
    			*/
    			
    			//Apertura de una sesión (e inicio de una transacción)
    			Session session = sf.openSession();

    			User user1 = new User();
    			user1.setId(1);
    			user1.setUserName("Pepe");
    			user1.setUserMessage("Hello world from Pepe");

    			User user2 = new User();
    			user2.setId(2);
    			user2.setUserName("Juan");
    			user2.setUserMessage("Hello world from Juan");
    			session.beginTransaction();
    			
    			//Almacenamos los objetos
    			session.save(user1);
    			session.save(user2);
    			
    			//Commit de la transacción
    			session.getTransaction().commit();
    			
    			//Cierre de la sesión
    			session.close();
    			sf.close();
    }
}
