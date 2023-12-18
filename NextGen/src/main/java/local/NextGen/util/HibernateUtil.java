package local.NextGen.util;

import local.NextGen.modelo.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the Configuration from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Articulo.class);
            configuration.addAnnotatedClass(Cliente.class);
            configuration.addAnnotatedClass(ClienteEstandard.class);
            configuration.addAnnotatedClass(ClientePremium.class);
            configuration.addAnnotatedClass(Pedido.class);
            configuration.addAnnotatedClass(DetallePedido.class);

            // Build a StandardServiceRegistry
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            // Build the SessionFactory
            sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}