package org.mapeo.biblioteca.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("bibliotecaPU");

    private JPAUtil() {}

    public static EntityManager em() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        emf.close();
    }
}

