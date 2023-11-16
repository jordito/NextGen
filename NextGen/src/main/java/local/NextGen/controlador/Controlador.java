package local.NextGen.controlador;

import local.NextGen.modelo.*;
import local.NextGen.modelo.DAO.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors ;

public class Controlador {
    private ArticuloDAO articuloDAO;
    private ClienteDAO clienteDAO;
    private PedidoDAO pedidoDAO;

    public Controlador() throws SQLException {
        // Inicialización de DAOs. Por ejemplo, utilizando un método para obtener la conexión.
        this.articuloDAO = new ArticuloDAO(ConexionBD.obtenerConexion());
        this.clienteDAO = new ClienteDAO(ConexionBD.obtenerConexion());
        this.pedidoDAO = new PedidoDAO(ConexionBD.obtenerConexion());
    }

    public List<Articulo> listarArticulos() throws SQLException {
        return articuloDAO.obtenerTodos();
    }

    public void agregarArticulo(Articulo articulo) throws SQLException {
        boolean exito = articuloDAO.insertar(articulo);
        if (!exito) {

        }
    }

    public void eliminarArticulo(String codigo) throws SQLException {
        boolean exito = articuloDAO.eliminar(codigo);
        if (!exito) {

        }
    }

    /**
     * Lista todos los clientes en la base de datos.
     *
     * @return Una lista de objetos Cliente.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public List<Cliente> listarClientes() throws SQLException {
        return clienteDAO.obtenerTodos();
    }

    /**
     * Agrega un nuevo cliente a la base de datos.
     *
     * @param cliente El objeto Cliente a agregar.
     * @return true si el cliente se agregó con éxito, false si el cliente ya existe.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public boolean agregarCliente(Cliente cliente) throws SQLException {
        if (!clienteDAO.existeNIF(cliente.getNif())) {
            return clienteDAO.insertar(cliente);
        }
        return false;
    }

    /**
     * Elimina un cliente de la base de datos usando su NIF.
     *
     * @param nif El NIF del cliente a eliminar.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public boolean eliminarCliente(String nif) throws SQLException {
        return clienteDAO.eliminarPorNIF(nif);
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param cliente El objeto Cliente con la información actualizada.
     * @return true si la actualización es exitosa, false si el cliente no existe.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    /**
     * Lista todos los pedidos existentes en la base de datos.
     *
     * @return Una lista de objetos Pedido.
     * @throws SQLException Si ocurre un error de SQL durante la consulta.
     */
    public List<Pedido> listarPedidos() throws SQLException {
        return pedidoDAO.listarTodos();
    }

    /**
     * Agrega un nuevo pedido a la base de datos.
     *
     * @param pedido El pedido a agregar.
     * @throws SQLException Si ocurre un error de SQL durante la inserción.
     */
    public void agregarPedido(Pedido pedido) throws SQLException {
        // Aquí implementarías la lógica para agregar un pedido y sus detalles.
        // Esto puede incluir insertar en las tablas Pedidos y DetallePedido.
    }

    /**
     * Elimina un pedido existente de la base de datos.
     *
     * @param numeroPedido El número del pedido a eliminar.
     * @throws SQLException Si ocurre un error de SQL durante la eliminación.
     */
    public void eliminarPedido(int numeroPedido) throws SQLException {
        // Implementar lógica para eliminar un pedido.
        // Esto puede incluir eliminar registros en la tabla DetallePedido y luego en Pedidos.
    }

    /**
     * Lista todos los pedidos con estado 'Pendiente'.
     *
     * @return Una lista de pedidos pendientes.
     * @throws SQLException Si ocurre un error de SQL durante la consulta.
     */
    public List<Pedido> listarPedidosPendientes() throws SQLException {
        return pedidoDAO.listarTodos().stream()
                .filter(pedido -> !pedido.isEnviado())
                .collect(Collectors.toList());
    }

    /**
     * Lista todos los pedidos con estado 'Enviado'.
     *
     * @return Una lista de pedidos enviados.
     * @throws SQLException Si ocurre un error de SQL durante la consulta.
     */
    public List<Pedido> listarPedidosEnviados() throws SQLException {
        return pedidoDAO.listarTodos().stream()
                .filter(Pedido::isEnviado)
                .collect(Collectors.toList());
    }
}