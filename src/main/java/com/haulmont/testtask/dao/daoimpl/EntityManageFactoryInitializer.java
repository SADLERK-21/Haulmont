
package com.haulmont.testtask.dao.daoimpl;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * EntityManageFactoryInitializer used for create EntityManageFactory object.
 * "PERSISTENCE_UNIT_NAME " contain name of persistence unit with project classes (resources/config/persistence.xml).
 */
public class EntityManageFactoryInitializer {
    private static final String PERSISTENCE_UNIT_NAME = "APP_UNIT";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManageFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }
}
