package local.NextGen.controlador;

import local.NextGen.modelo.*;
import local.NextGen.modelo.DAO.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase Controlador que maneja las operaciones de negocio.
 * Utiliza DAOs para interactuar con la base de datos y realizar operaciones CRUD.
 */
public class Controlador {
    private ArticuloDAO articuloDAO;
    private ClienteDAO clienteDAO;
    private PedidoDAO pedidoDAO;

    /**
     * Constructor de Controlador.
     * Inicializa los DAOs utilizando la fábrica de DAOs 'Datos'.
     * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
     */
    public Controlador() throws SQLException {
        Datos datos = new Datos();
        this.articuloDAO = datos.getArticuloDAO();
        this.clienteDAO = datos.getClienteDAO();
        this.pedidoDAO = datos.getPedidoDAO();
    }

    // Métodos de operaciones para Artículos

    public List<Articulo> listarArticulos() throws SQLException {
        return articuloDAO.obtenerTodos();
    }

    public void agregarArticulo(Articulo articulo) throws SQLException {
        articuloDAO.insertar(articulo);
    }

    public void eliminarArticulo(String codigo) throws SQLException {
        articuloDAO.eliminar(codigo);
    }

    // Métodos de operaciones para Clientes

    public List<Cliente> listarClientes() throws SQLException {
        return clienteDAO.obtenerTodos();
    }

    public boolean agregarCliente(Cliente cliente) throws SQLException {
        return clienteDAO.insertar(cliente);
    }

    public boolean eliminarCliente(String nif) throws SQLException {
        return clienteDAO.eliminarPorNIF(nif);
    }

    // Métodos de operaciones para Pedidos

    public List<Pedido> listarPedidos() throws SQLException {
        return pedidoDAO.listarTodos();
    }

    public void agregarPedido(Pedido pedido) throws SQLException {
        pedidoDAO.insertar(pedido);
    }

    public void eliminarPedido(int numeroPedido) throws SQLException {
        pedidoDAO.eliminar(numeroPedido);
    }

    public List<Pedido> listarPedidosPendientes() throws SQLException {
        return pedidoDAO.listarTodos().stream()
                .filter(pedido -> !pedido.isEnviado())
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPedidosEnviados() throws SQLException {
        return pedidoDAO.listarTodos().stream()
                .filter(Pedido::isEnviado)
                .collect(Collectors.toList());
    }
}
