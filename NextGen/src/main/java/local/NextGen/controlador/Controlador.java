package local.NextGen.controlador;

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
    private final local.NextGen.modelo.DAO.ArticuloDAO articuloDAO;
    private final local.NextGen.modelo.DAO.ClienteDAO clienteDAO;
    private final local.NextGen.modelo.DAO.PedidoDAO pedidoDAO;

    /**
     * Constructor de Controlador.
     * Inicializa los DAOs utilizando la fábrica de DAOs 'Datos'.
     * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
     */
    public Controlador() throws SQLException {
        this.articuloDAO = local.NextGen.modelo.Datos.getArticuloDAO();
        this.clienteDAO = local.NextGen.modelo.Datos.getClienteDAO();
        this.pedidoDAO = local.NextGen.modelo.Datos.getPedidoDAO();
    }

    // Métodos de operaciones para Artículos

    public static List<local.NextGen.modelo.Articulo> listarArticulos() {
        List<local.NextGen.modelo.Articulo> articulos = local.NextGen.modelo.DAO.ArticuloDAO.obtenerTodos();
        if (articulos.isEmpty()) {
            System.out.println("\u001B[31mNo hay artículos en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34m\nLista de artículos:\u001B[0m");
            for (local.NextGen.modelo.Articulo articulo : articulos) {
                System.out.println(articulo);
            }
        }
        return articulos;
    }

    public static void agregarArticulo(local.NextGen.modelo.Articulo articulo) throws local.NextGen.exceptions.CustomException {
        local.NextGen.modelo.DAO.ArticuloDAO.insertar(articulo);
    }

    public static boolean eliminarArticulo(String codigo) {
        return local.NextGen.modelo.DAO.ArticuloDAO.eliminar(codigo);
    }
    public static boolean actualizarArticulo(local.NextGen.modelo.Articulo articulo) {
        return local.NextGen.modelo.DAO.ArticuloDAO.actualizar(articulo);
    }

    // Métodos de operaciones para Clientes

    public static List<local.NextGen.modelo.Cliente> listarClientes() throws SQLException {
        List<local.NextGen.modelo.Cliente> clientes = local.NextGen.modelo.DAO.ClienteDAO.obtenerTodos("");
        if (clientes.isEmpty()) {
            System.out.println("\u001B[31mNo hay clientes en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34mLista de clientes:\u001B[0m");
            for (local.NextGen.modelo.Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
        return clientes;
    }
    public static List<local.NextGen.modelo.Cliente> listarClientesEstandard() throws SQLException {
        List<local.NextGen.modelo.Cliente> clienteEstandard = local.NextGen.modelo.DAO.ClienteDAO.obtenerTodos("estandard");
        if (clienteEstandard.isEmpty()) {
            System.out.println("\u001B[31mNo hay clientes estandard en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34mLista de clientes estandard:\u001B[0m");
            for (local.NextGen.modelo.Cliente cliente : clienteEstandard) {
                System.out.println(cliente);
            }
        }
        return clienteEstandard;
    }

    public static List<local.NextGen.modelo.Cliente> listarClientesPremium() throws SQLException {
        List<local.NextGen.modelo.Cliente> clientePremium = local.NextGen.modelo.DAO.ClienteDAO.obtenerTodos("premium");
        if (clientePremium.isEmpty()) {
            System.out.println("\u001B[31mNo hay clientes premium en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34mLista de clientes premium:\u001B[0m");
            for (local.NextGen.modelo.Cliente cliente : clientePremium) {
                System.out.println(cliente);
            }
        }
        return clientePremium;
    }
    public static boolean agregarCliente(local.NextGen.modelo.Cliente cliente) throws SQLException {
        return local.NextGen.modelo.DAO.ClienteDAO.insertar(cliente);
    }

    public static boolean eliminarCliente(String nif) throws SQLException {
        return local.NextGen.modelo.DAO.ClienteDAO.eliminarPorNIF(nif);
    }

    public static boolean actualizarCliente(local.NextGen.modelo.Cliente cliente) throws SQLException {
        return local.NextGen.modelo.DAO.ClienteDAO.actualizar(cliente);
    }

    // Métodos de operaciones para Pedidos

    public static List<local.NextGen.modelo.Pedido> listarPedidos() throws SQLException {
        List<local.NextGen.modelo.Pedido> pedidos = local.NextGen.modelo.DAO.PedidoDAO.listarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("\u001B[31mNo hay pedidos en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34m\nLista de pedidos:\u001B[0m");
            for (local.NextGen.modelo.Pedido pedido : pedidos) {
                System.out.println(pedido.toString());
            }
        }
        return pedidos;
    }


    public static local.NextGen.modelo.Pedido agregarPedido(local.NextGen.modelo.Pedido pedido) throws SQLException {
        try (Connection conn = obtenerConexion()) {
            conn.setAutoCommit(false);

            try {
                int numeroPedidoGenerado = local.NextGen.modelo.DAO.PedidoDAO.insertar(conn, pedido);

                if (numeroPedidoGenerado > 0) {
                    for (local.NextGen.modelo.DetallePedido detalle : pedido.getDetallesPedido()) {
                        detalle.setNumeroPedido(numeroPedidoGenerado);
                        local.NextGen.modelo.DAO.DetallePedidoDAO.agregarDetalle(conn, detalle);
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
        return local.NextGen.modelo.DAO.PedidoDAO.eliminar(numeroPedido);
    }

    public static void listarPedidosPendientes() throws SQLException {
        local.NextGen.modelo.DAO.PedidoDAO.listarTodos().stream()
                .filter(pedido -> !pedido.isEnviado())
                .collect(Collectors.toList());
    }

    public static void listarPedidosEnviados() throws SQLException {
        local.NextGen.modelo.DAO.PedidoDAO.listarTodos().stream()
                .filter(local.NextGen.modelo.Pedido::isEnviado)
                .collect(Collectors.toList());
    }
}