package local.NextGen.modelo.DAO;

import local.NextGen.modelo.Articulo;
import local.NextGen.modelo.DetallePedido;
import local.NextGen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static local.NextGen.modelo.ConexionBD.obtenerConexion;

public class DetallePedidoDAO {

    public List<DetallePedido> listarPorPedido(int numeroPedido) {
        List<DetallePedido> detalles = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usa HQL (Hibernate Query Language) en lugar de SQL
            String hql = "FROM DetallePedido WHERE numeroPedido = :numeroPedido";
            detalles = session.createQuery(hql, DetallePedido.class)
                    .setParameter("numeroPedido", numeroPedido)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detalles;
    }

    /**
     * Agrega un detalle de pedido a la base de datos.
     */
    public void agregarDetalle(DetallePedido detalle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Usa el método save para guardar la entidad en lugar de SQL directo
            if (!existeDetalle(session, detalle)) {
                session.save(detalle);
                transaction.commit();
            } else {
                System.out.println("El detalle ya existe.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private boolean existeDetalle(Session session, DetallePedido detalle) {
        // Utiliza HQL para la verificación en lugar de SQL directo
        String hql = "SELECT COUNT(*) FROM DetallePedido WHERE pedido = :pedido AND articulo = :articulo";

        Long count = session.createQuery(hql, Long.class)
                .setParameter("pedido", detalle.getPedido())
                .setParameter("articulo", detalle.getArticulo())
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Elimina un detalle de pedido de la base de datos.
     */
    public boolean eliminarPorPedido(int numeroPedido) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Usa HQL para la eliminación en lugar de SQL directo
            String hql = "DELETE FROM DetallePedido WHERE numeroPedido = :numeroPedido";
            int result = session.createQuery(hql)
                    .setParameter("numeroPedido", numeroPedido)
                    .executeUpdate();

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

    // Método auxiliar para obtener un objeto Articulo por su código
    private Articulo obtenerArticuloPorCodigo(String codigoArticulo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Utiliza HQL para la consulta en lugar de SQL directo
            String hql = "FROM Articulo WHERE codigo = :codigoArticulo";
            return session.createQuery(hql, Articulo.class)
                    .setParameter("codigoArticulo", codigoArticulo)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
