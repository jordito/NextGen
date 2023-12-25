package local.NextGen.modelo.factory;

import local.NextGen.modelo.DAO.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase Datos que actúa como fábrica para la creación de objetos DAO (Data Access Objects).
 * Proporciona métodos estáticos para obtener instancias de diferentes DAOs.
 * Inicializa y mantiene una única instancia de SessionFactory de Hibernate.
 */
public class Datos {

    private static final SessionFactory sessionFactory;

    static {
        // Configuración de la SessionFactory de Hibernate
        try {
            // La configuración de Hibernate se carga del archivo hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Registro del error y propagación de una excepción para evitar que la aplicación continúe
            System.err.println("Error al crear la SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Obtiene una instancia de ClienteDAO.
     *
     * @return Una nueva instancia de ClienteDAO.
     */
    public static ClienteDAO getClienteDAO() {
        return new ClienteDAO(sessionFactory);
    }

    /**
     * Obtiene una instancia de ArticuloDAO.
     *
     * @return Una nueva instancia de ArticuloDAO.
     */
    public static ArticuloDAO getArticuloDAO() {
        return new ArticuloDAO(sessionFactory);
    }

    /**
     * Obtiene una instancia de PedidoDAO.
     *
     * @return Una nueva instancia de PedidoDAO.
     */
    public static PedidoDAO getPedidoDAO() {
        return new PedidoDAO(sessionFactory);
    }

    /**
     * Obtiene una instancia de DetallePedidoDAO.
     *
     * @return Una nueva instancia de DetallePedidoDAO.
     */
    public static DetallePedidoDAO getDetallePedidoDAO() {
        return new DetallePedidoDAO(sessionFactory);
    }

    /**
     * Proporciona acceso a la SessionFactory de Hibernate.
     *
     * @return La instancia de SessionFactory utilizada en la aplicación.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Método para cerrar la SessionFactory de Hibernate.
     * Se debe llamar este método al finalizar la aplicación para liberar recursos.
     */
    public static void cerrarSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
