package local.NextGen.controlador;

import local.NextGen.exceptions.CustomException;
import local.NextGen.modelo.*;
import local.NextGen.modelo.DAO.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static local.NextGen.modelo.ConexionBD.obtenerConexion;

/**
 * Clase Controlador que maneja las operaciones de negocio.
 * Utiliza DAOs para interactuar con la base de datos y realizar operaciones CRUD.
 */
public class Controlador {


    // private static final ArticuloDAO articuloDAO = new ArticuloDAO();
    private final ClienteDAO clienteDAO;
    private final PedidoDAO pedidoDAO;

    /**
     * Constructor de Controlador.
     * Inicializa los DAOs utilizando la fábrica de DAOs 'Datos'.
     * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
     */
    public Controlador() throws SQLException {
        //this.articuloDAO = ArticuloDAO;
        //this.articuloDAO = articuloDAO;
        this.clienteDAO = Datos.getClienteDAO();
        this.pedidoDAO = Datos.getPedidoDAO();
    }

    // Métodos de operaciones para Artículos

    public static List<Articulo> listarArticulos() {

        ArticuloDAO articuloDao = new ArticuloDAO();

        List<Articulo> articulos = articuloDao.obtenerTodos();
        if (articulos.isEmpty()) {
            System.out.println("\u001B[31mNo hay artículos en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34m\nLista de artículos:\u001B[0m");
            for (Articulo articulo : articulos) {
                System.out.println(articulo);
            }
        }
        return articulos;
    }

    public static void agregarArticulo(Articulo articulo) throws CustomException {
        ArticuloDAO ad = new ArticuloDAO();
        ad.insertar(articulo);
    }

    public static boolean eliminarArticulo(String codigo) {
        ArticuloDAO ad = new ArticuloDAO();
        return ad.eliminar(codigo);
    }
    public static boolean actualizarArticulo(Articulo articulo) {
        ArticuloDAO ad = new ArticuloDAO();
        return ad.actualizar(articulo);
    }

    // Métodos de operaciones para Clientes

    public static List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = ClienteDAO.obtenerTodos("");
        if (clientes.isEmpty()) {
            System.out.println("\u001B[31mNo hay clientes en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34mLista de clientes:\u001B[0m");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
        return clientes;
    }
    public static List<Cliente> listarClientesEstandard() throws SQLException {
        List<Cliente> clienteEstandard = ClienteDAO.obtenerTodos("estandard");
        if (clienteEstandard.isEmpty()) {
            System.out.println("\u001B[31mNo hay clientes estandard en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34mLista de clientes estandard:\u001B[0m");
            for (Cliente cliente : clienteEstandard) {
                System.out.println(cliente);
            }
        }
        return clienteEstandard;
    }

    public static List<Cliente> listarClientesPremium() throws SQLException {
        List<Cliente> clientePremium = ClienteDAO.obtenerTodos("premium");
        if (clientePremium.isEmpty()) {
            System.out.println("\u001B[31mNo hay clientes premium en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34mLista de clientes premium:\u001B[0m");
            for (Cliente cliente : clientePremium) {
                System.out.println(cliente);
            }
        }
        return clientePremium;
    }
    public static boolean agregarCliente(Cliente cliente) throws SQLException {
        return ClienteDAO.insertar(cliente);
    }

    public static boolean eliminarCliente(String nif) throws SQLException {
        return ClienteDAO.eliminarPorNIF(nif);
    }

    public static boolean actualizarCliente(Cliente cliente) throws SQLException {
        return ClienteDAO.actualizar(cliente);
    }

    // Métodos de operaciones para Pedidos

    public static List<Pedido> listarPedidos() throws SQLException {
        List<Pedido> pedidos = PedidoDAO.listarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("\u001B[31mNo hay pedidos en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34m\nLista de pedidos:\u001B[0m");
            for (Pedido pedido : pedidos) {
                System.out.println(pedido.toString());
            }
        }
        return pedidos;
    }


    public static Pedido agregarPedido(Pedido pedido) throws SQLException {
        try (Connection conn = obtenerConexion()) {
            conn.setAutoCommit(false);

            try {
                int numeroPedidoGenerado = PedidoDAO.insertar(conn, pedido);

                if (numeroPedidoGenerado > 0) {
                    for (DetallePedido detalle : pedido.getDetallesPedido()) {
                        detalle.setNumeroPedido(numeroPedidoGenerado);
                        DetallePedidoDAO.agregarDetalle(conn, detalle);
                    }

                    conn.commit();  // Confirmar transacción
                    return pedido;
                } else {
                    System.out.println("Error al agregar el pedido. No se generó un número de pedido válido.");
                    conn.rollback();
                    return null;
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return null;
            }
        }
    }

    public static boolean eliminarPedido(int numeroPedido) throws SQLException {
        return PedidoDAO.eliminar(numeroPedido);
    }

    public static List<Pedido> listarPedidosPendientes() throws SQLException {
        return PedidoDAO.listarTodos().stream()
                .filter(pedido -> !pedido.isEnviado())
                .collect(Collectors.toList());
    }

    public static List<Pedido> listarPedidosEnviados() throws SQLException {
        return PedidoDAO.listarTodos().stream()
                .filter(Pedido::isEnviado)
                .collect(Collectors.toList());
    }
}
