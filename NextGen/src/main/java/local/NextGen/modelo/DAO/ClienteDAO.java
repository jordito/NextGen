package local.NextGen.modelo.DAO;

import local.NextGen.modelo.entidades.Cliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Clase ClienteDAO para manejar las operaciones de base de datos relacionadas con entidades Cliente.
 * Utiliza Hibernate para realizar operaciones CRUD sobre clientes.
 */
public class ClienteDAO {
    private SessionFactory sessionFactory;

    /**
     * Constructor de ClienteDAO.
     *
     * @param sessionFactory Instancia de SessionFactory para conexiones con la base de datos.
     */
    public ClienteDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene una lista de clientes, pudiendo filtrar por tipo (estándar o premium).
     *
     * @param tipoCliente Tipo de cliente para filtrar (estándar, premium), vacío para todos los clientes.
     * @return Lista de objetos Cliente.
     */
    public List<Cliente> obtenerTodos(String tipoCliente) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "from Cliente";
            if ("estandard".equalsIgnoreCase(tipoCliente)) {
                queryString = "from ClienteEstandard";
            } else if ("premium".equalsIgnoreCase(tipoCliente)) {
                queryString = "from ClientePremium";
            }
            Query<Cliente> query = session.createQuery(queryString, Cliente.class);
            return query.list();
        }
    }

    /**
     * Obtiene un cliente específico por su identificador único.
     *
     * @param idCliente El ID del cliente a buscar.
     * @return El cliente si existe, null en caso contrario.
     */
    public Cliente obtenerPorId(int idCliente) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cliente.class, idCliente);
        }
    }
    /**
     * Obtiene un cliente específico por su NIF (Número de Identificación Fiscal).
     *
     * @param nif El NIF del cliente a buscar.
     * @return El cliente si existe, null en caso contrario.
     */
    public Cliente obtenerPorNif(String nif) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = builder.createQuery(Cliente.class);
            Root<Cliente> root = criteriaQuery.from(Cliente.class);

            criteriaQuery.select(root)
                    .where(builder.equal(root.get("nif"), nif));

            Query<Cliente> query = session.createQuery(criteriaQuery);
            return query.uniqueResult();
        }
    }


    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param cliente El cliente a insertar.
     * @return Verdadero si la inserción es exitosa, falso en caso contrario.
     */
    public boolean insertar(Cliente cliente) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(cliente);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    /**
     * Actualiza un cliente existente en la base de datos.
     *
     * @param cliente El cliente con información actualizada.
     * @return Verdadero si la actualización es exitosa, falso en caso contrario.
     */
    public boolean actualizar(Cliente cliente) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(cliente);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos usando su NIF.
     *
     * @param nif El NIF del cliente a eliminar.
     * @return Verdadero si la eliminación es exitosa, falso en caso contrario.
     */
    public boolean eliminarPorNIF(String nif) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Cliente> query = session.createQuery("from Cliente where nif = :nif", Cliente.class);
            query.setParameter("nif", nif);
            List<Cliente> clientes = query.list();

            if (!clientes.isEmpty()) {
                tx = session.beginTransaction();
                for (Cliente cliente : clientes) {
                    session.delete(cliente);
                }
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
