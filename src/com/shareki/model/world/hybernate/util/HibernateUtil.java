package com.shareki.model.world.hybernate.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    /*private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
   */
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            //configuration.configure();
            configuration.configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).buildServiceRegistry();
            return configuration.buildSessionFactory(serviceRegistry);
            
            //ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().configure().buildServiceRegistry();
            //MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            //SessionFactory factory = metadataSources.buildMetadata().buildSessionFactory();
            
            
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        
    }

    public static SessionFactory getSessionFactory() {
       // if(sessionFactory==null)
        //	sessionFactory=buildSessionFactory();
        
    	return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}