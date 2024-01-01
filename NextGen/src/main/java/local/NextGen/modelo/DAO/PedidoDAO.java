package local.NextGen.modelo.DAO;

import local.NextGen.modelo.entidades.Pedido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase DAO para la entidad Pedido.
 * Proporciona métodos para realizar operaciones de base de datos sobre pedidos,
 * como insertar, eliminar, listar y obtener pedidos.
 */
public class PedidoDAO {
    private SessionFactory sessionFactory;

    /**
     * Constructor de la clase PedidoDAO.
     *
     * @param sessionFactory La fábrica de sesiones de Hibernate.
     */
    public PedidoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserta un pedido en la base de datos utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión, ni de manejar la transacción.
     *
     * @param pedido  El pedido a insertar.
     * @param session La sesión de Hibernate activa.
     * @return El número de pedido generado, o -1 si ocurrió un error.
     */
    public int insertar(Pedido pedido, Session session) {
        try {
            session.save(pedido);
            return pedido.getNumeroPedido();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Elimina un pedido de la base de datos utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión, ni de manejar la transacción.
     *
     * @param numeroPedido El número de pedido a eliminar.
     * @param session      La sesión de Hibernate activa.
     * @return True si el pedido se eliminó con éxito, False en caso contrario.
     */
    public boolean eliminar(int numeroPedido, Session session) {
        try {
            Pedido pedido = session.get(Pedido.class, numeroPedido);
            if (pedido != null) {
                session.delete(pedido);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los pedidos en la base de datos utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión.
     *
     * @param session La sesión de Hibernate activa.
     * @return Una lista de pedidos.
     */
    public List<Pedido> listarTodos(Session session) {
        return session.createQuery("from Pedido", Pedido.class).list();
    }

    /**
     * Actualiza los estados de los pedidos según el tiempo de preparación de cada artículo
     * y lo compara con la hora actual
     *
     * @param session La sesión de Hibernate activa.
     */
    public void actualizarEstadoPedido(Session session) {

        Query<Integer> subquery = session.createQuery(
                "SELECT dp.pedido.numeroPedido " +
                        "FROM DetallePedido dp " +
                        "JOIN dp.articulo a " +
                        "JOIN dp.pedido p " +
                        "WHERE p.estadoPedido = 'Pendiente' AND " +
                        "FUNCTION('TIMESTAMPADD', MINUTE, a.tiempoPreparacion, p.fechaHoraPedido) <= CURRENT_TIMESTAMP", Integer.class);

        List<Integer> numeroPedidos = subquery.getResultList();

        if (!numeroPedidos.isEmpty()) {
            Query<?> updateQuery = session.createQuery(
                    "UPDATE Pedido p " +
                            "SET p.estadoPedido = 'Enviado' " +
                            "WHERE p.numeroPedido IN :numeroPedidos"
            );

            updateQuery.setParameter("numeroPedidos", numeroPedidos);
            updateQuery.executeUpdate();
        }
    }

    /**
     * Obtiene un pedido por su número de pedido utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión.
     *
     * @param numeroPedido El número de pedido a buscar.
     * @param session      La sesión de Hibernate activa.
     * @return El pedido encontrado, o null si no se encontró un pedido con ese número.
     */
    public Pedido obtenerPorNumero(int numeroPedido, Session session) {
        return session.get(Pedido.class, numeroPedido);
    }
}
