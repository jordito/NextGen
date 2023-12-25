package local.NextGen.modelo.DAO;

import local.NextGen.modelo.entidades.DetallePedido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase DAO para la entidad DetallePedido.
 * Proporciona métodos para acceder y manipular los datos de detalles de pedidos en la base de datos.
 */
public class DetallePedidoDAO {
    private SessionFactory sessionFactory;

    /**
     * Constructor de la clase DetallePedidoDAO.
     *
     * @param sessionFactory La fábrica de sesiones de Hibernate.
     */
    public DetallePedidoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Agrega un nuevo detalle de pedido a la base de datos utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión, ni de manejar la transacción.
     *
     * @param detalle El detalle de pedido a agregar.
     * @param session La sesión de Hibernate activa.
     */
    public void agregarDetalle(DetallePedido detalle, Session session) {
        if (!existeDetalle(detalle, session)) {
            session.save(detalle);
        } else {
            System.out.println("El detalle ya existe.");
        }
    }


    /**
     * Verifica si un detalle de pedido ya existe en la base de datos utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión.
     *
     * @param detalle El detalle de pedido a verificar.
     * @param session La sesión de Hibernate activa.
     * @return true si el detalle ya existe, false en caso contrario.
     */
    private boolean existeDetalle(DetallePedido detalle, Session session) {
        Query<Long> query = session.createQuery("select count(*) from DetallePedido where id = :id", Long.class);
        query.setParameter("id", detalle.getId());
        return query.uniqueResult() > 0;
    }

    /**
     * Obtiene una lista de detalles de pedido por número de pedido utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión.
     *
     * @param numeroPedido El número de pedido para filtrar los detalles.
     * @param session      La sesión de Hibernate activa.
     * @return Una lista de detalles de pedido relacionados con el número de pedido especificado.
     */
    public List<DetallePedido> listarPorPedido(int numeroPedido, Session session) {
        Query<DetallePedido> query = session.createQuery("from DetallePedido where id.numeroPedido = :numeroPedido", DetallePedido.class);
        query.setParameter("numeroPedido", numeroPedido);
        return query.list();
    }


    /**
     * Elimina detalles de pedido por número de pedido utilizando una sesión existente.
     * No se encarga de abrir o cerrar la sesión, ni de manejar la transacción.
     *
     * @param numeroPedido El número de pedido para eliminar los detalles relacionados.
     * @param session      La sesión de Hibernate activa.
     * @return true si se eliminaron detalles con éxito, false en caso contrario.
     */
    public boolean eliminarPorPedido(int numeroPedido, Session session) {
        Query<?> query = session.createQuery("delete from DetallePedido where id.numeroPedido = :numeroPedido");
        query.setParameter("numeroPedido", numeroPedido);
        int result = query.executeUpdate();
        return result > 0;
    }

}
