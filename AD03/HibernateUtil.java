public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    // Código estático. Sólo se ejecuta una vez, como un Singleton
    static {
        try {
            // Creamos el SessionFactory desde el archivo estándar de configuración (hibernate.cfg.xml)
            sessionFactory = new Configuration()
                .configure(new File("hibernate.cfg.xml")).buildSessionFactory();    
        } catch (HibernateException ex) {
            // Log the exception.
            System.err.println("Error en la inicialización de SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
