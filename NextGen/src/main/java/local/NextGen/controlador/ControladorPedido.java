package local.NextGen.controlador;

import local.NextGen.modelo.DAO.PedidoDAO;
import local.NextGen.modelo.DAO.DetallePedidoDAO;
import local.NextGen.modelo.factory.Datos;
import local.NextGen.modelo.entidades.Pedido;
import local.NextGen.modelo.entidades.DetallePedido;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar operaciones relacionadas con los pedidos.
 * Maneja las transacciones y sesiones de Hibernate para asegurar la coherencia y la integridad de los datos.
 */
public class ControladorPedido {
    private final PedidoDAO pedidoDAO;
    private final DetallePedidoDAO detallePedidoDAO;

    /**
     * Constructor para inicializar el controlador con las instancias DAO necesarias.
     */
    public ControladorPedido() {
        this.pedidoDAO = Datos.getPedidoDAO();
        this.detallePedidoDAO = Datos.getDetallePedidoDAO();
    }

    /**
     * Actualiza los estados de los pedidos según la hora actual
     */
    public void actualizarEstadoPedidos() {
        Transaction tx = null;
        try (Session session = Datos.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            pedidoDAO.actualizarEstadoPedido(session);
            tx.commit();
            session.close();
        }
    }

    /**
     * Lista todos los pedidos existentes.
     *
     * @return Lista de pedidos.
     */
    public List<Pedido> listarPedidos() {
        actualizarEstadoPedidos();
        try (Session session = Datos.getSessionFactory().openSession()) {
            return pedidoDAO.listarTodos(session);
        }
    }

    /**
     * Obtiene los detalles de un pedido específico.
     *
     * @param numeroPedido El número del pedido.
     * @return Un conjunto de detalles del pedido.
     */
    public Set<DetallePedido> obtenerDetallesDePedido(int numeroPedido) {
        actualizarEstadoPedidos();
        try (Session session = Datos.getSessionFactory().openSession()) {
            List<DetallePedido> detallesList = detallePedidoDAO.listarPorPedido(numeroPedido, session);
            return new HashSet<>(detallesList);
        }
    }

    /**
     * Obtiene todos los detalles de todos los pedidos en la lista.
     *
     * @return Un conjunto de detalles de todos los pedidos.
     */
    public Set<DetallePedido> obtenerDetallesDeTodosLosPedidos() {
        actualizarEstadoPedidos();
        try (Session session = Datos.getSessionFactory().openSession()) {
            List<Pedido> pedidos = pedidoDAO.listarTodos(session);
            Set<DetallePedido> detallesDeTodosLosPedidos = new HashSet<>();

            for (Pedido pedido : pedidos) {
                detallesDeTodosLosPedidos.addAll(detallePedidoDAO.listarPorPedido(pedido.getNumeroPedido(), session));
            }

            return detallesDeTodosLosPedidos;
        }
    }

    /**
     * Agrega un nuevo pedido y sus detalles a la base de datos.
     *
     * @param pedido         El pedido a agregar.
     * @param detallesPedido Los detalles asociados con el pedido.
     * @return El pedido agregado o null en caso de fallo.
     */
    public Pedido agregarPedido(Pedido pedido, Set<DetallePedido> detallesPedido) {
        actualizarEstadoPedidos();
        Transaction tx = null;
        try (Session session = Datos.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int numeroPedidoGenerado = pedidoDAO.insertar(pedido, session);
            if (numeroPedidoGenerado > 0) {
                pedido.setNumeroPedido(numeroPedidoGenerado);
                for (DetallePedido detalle : detallesPedido) {
                    detalle.getId().setNumeroPedido(numeroPedidoGenerado);
                    detalle.setPedido(pedido);
                    detallePedidoDAO.agregarDetalle(detalle, session);
                }
                tx.commit();
                return pedido;
            } else {
                tx.rollback();
                return null;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina un pedido y todos sus detalles de la base de datos.
     *
     * @param numeroPedido El número del pedido a eliminar.
     * @return true si el pedido y sus detalles se eliminaron con éxito, de lo contrario false.
     */
    public boolean eliminarPedido(int numeroPedido) {
        actualizarEstadoPedidos();
        Transaction tx = null;
        try (Session session = Datos.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            boolean detallesEliminados;
            if (!detallePedidoDAO.listarPorPedido(numeroPedido, session).isEmpty()) {
                detallesEliminados = detallePedidoDAO.eliminarPorPedido(numeroPedido, session);
            } else {
                detallesEliminados = true;
            }
            boolean pedidoEliminado = pedidoDAO.eliminar(numeroPedido, session);
            if (detallesEliminados && pedidoEliminado) {
                tx.commit();
                return true;
            } else {
                tx.rollback();
                return false;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todos los pedidos que están pendientes.
     *
     * @return Lista de pedidos pendientes.
     */
    public List<Pedido> listarPedidosPendientes() {
        actualizarEstadoPedidos();
        try (Session session = Datos.getSessionFactory().openSession()) {
            return pedidoDAO.listarTodos(session).stream()
                    .filter(pedido -> pedido.getEstadoPedido() == Pedido.EstadoPedido.Pendiente)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Lista todos los pedidos que han sido enviados.
     *
     * @return Lista de pedidos enviados.
     */
    public List<Pedido> listarPedidosEnviados() {
        actualizarEstadoPedidos();
        try (Session session = Datos.getSessionFactory().openSession()) {
            return pedidoDAO.listarTodos(session).stream()
                    .filter(pedido -> pedido.getEstadoPedido() == Pedido.EstadoPedido.Enviado)
                    .collect(Collectors.toList());
        }
    }
}
