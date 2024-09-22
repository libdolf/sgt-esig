package br.com.libdolf.sgtesig.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {
    private final EntityManagerFactory factory;

    public EntityManagerProducer() {
        this.factory = createEntityManagerFactory();
    }

    protected EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("sgt-PU");
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return this.factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        em.close();
    }
}