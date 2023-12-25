package local.NextGen.modelo.DAO;

import local.NextGen.modelo.entidades.Articulo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Clase ArticuloDAO para la gestión de operaciones de base de datos relacionadas con entidades Articulo.
 * Utiliza Hibernate para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar).
 */
public class ArticuloDAO {
    private SessionFactory sessionFactory;

    /**
     * Constructor de ArticuloDAO.
     *
     * @param sessionFactory Instancia de SessionFactory para la conexión con la base de datos.
     */
    public ArticuloDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene todos los artículos disponibles en la base de datos.
     *
     * @return Lista de objetos Articulo.
     */
    public List<Articulo> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Articulo", Articulo.class).list();
        }
    }

    /**
     * Obtiene un artículo por su código único.
     *
     * @param codigo El código del artículo a buscar.
     * @return El artículo si existe, null en caso contrario.
     */
    public Articulo obtenerPorCodigo(String codigo) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Articulo.class, codigo);
        }
    }

    /**
     * Inserta un nuevo artículo en la base de datos.
     *
     * @param articulo El artículo a insertar.
     */
    public void insertar(Articulo articulo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(articulo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /**
     * Actualiza un artículo existente en la base de datos.
     *
     * @param articulo El artículo con la información actualizada.
     * @return Verdadero si la actualización es exitosa, falso en caso contrario.
     */
    public boolean actualizar(Articulo articulo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(articulo);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    /**
     * Elimina un artículo de la base de datos basándose en su código.
     *
     * @param codigo El código del artículo a eliminar.
     * @return Verdadero si la eliminación es exitosa, falso en caso contrario.
     */
    public boolean eliminar(String codigo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Articulo articulo = session.get(Articulo.class, codigo);
            if (articulo != null) {
                session.delete(articulo);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }
}

