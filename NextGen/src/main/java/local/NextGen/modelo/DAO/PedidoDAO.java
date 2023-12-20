package local.NextGen.modelo.DAO;

import local.NextGen.modelo.*;
import local.NextGen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase DAO para la gestión de pedidos en la base de datos.
 * Esta clase se encarga de realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para los pedidos.
 */
public class PedidoDAO {
    private DetallePedidoDAO detallePedidoDAO = null;
    private final ClienteDAO clienteDAO;

    public PedidoDAO() {
        this.detallePedidoDAO = new DetallePedidoDAO();
        this.clienteDAO = new ClienteDAO();
    }

    /**
     * Inserta un nuevo pedido en la base de datos.
     */
    public int insertar(Pedido pedido) {
        Transaction transaction = null;
        BigInteger numeroPedidoGenerado = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Usa HQL para la inserción en lugar de SQL directo
          String bfp = "before persist";
//            Pedido np = new Pedido();
//            np.setCliente((ClientePremium) pedido.getCliente());
//            np.setFechaHora(pedido.getFechaHora());
//            np.setEstadoPedido(pedido.getEstadoPedido());
//            session.persist(pedido);

            String sql = "INSERT INTO pedidos (id_cliente, fecha_hora_pedido, estado_pedido) VALUES (:idCliente, :fecha_hora_pedido, :estado_pedido)";
            NativeQuery<?> query = session.createNativeQuery(sql);

            query.setParameter("idCliente", pedido.getCliente().getIdCliente());
            query.setParameter("fecha_hora_pedido", pedido.getFechaHora());
            query.setParameter("estado_pedido", pedido.getEstadoPedido().name());

            int result = query.executeUpdate();

            if (result > 0) {

                transaction.commit();
            int test = 0;
                numeroPedidoGenerado = (BigInteger) session.createNativeQuery("SELECT LAST_INSERT_ID()").uniqueResult();
            }
            bfp = "after persist";

            bfp = "afffter persist";



        } catch (Exception e) {
            throw new DAOException("Error al crear el pedido", e);
            //e.printStackTrace();
        }

        return numeroPedidoGenerado.intValue();
    }

    /**
     * Elimina un pedido de la base de datos.
     */
    public boolean eliminar(int numeroPedido) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Usa HQL para la eliminación en lugar de SQL directo
            String hql = "DELETE FROM Pedido WHERE numeroPedido = :numeroPedido";
            int result = session.createQuery(hql)
                    .setParameter("numeroPedido", numeroPedido)
                    .executeUpdate();

            if (result > 0) {
                detallePedidoDAO.eliminarPorPedido(numeroPedido);
            }

            transaction.commit();

            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todos los pedidos existentes en la base de datos.
     */
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usa HQL en lugar de SQL directo
            String hql = "FROM Pedido";
            pedidos = session.createQuery(hql, Pedido.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    /**
     * Obtiene un pedido específico de la base de datos por su número.
     */
    public Pedido obtenerPorNumero(int numeroPedido) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usa HQL en lugar de SQL directo
            String hql = "FROM Pedido WHERE numeroPedido = :numeroPedido";
            return session.createQuery(hql, Pedido.class)
                    .setParameter("numeroPedido", numeroPedido)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si un pedido con un determinado número ya existe en la base de datos.
     */
    private boolean existePedido(int numeroPedido) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usa HQL en lugar de SQL directo
            String hql = "SELECT COUNT(*) FROM Pedido WHERE numeroPedido = :numeroPedido";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("numeroPedido", numeroPedido)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


