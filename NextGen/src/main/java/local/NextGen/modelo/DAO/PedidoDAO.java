package local.NextGen.modelo.DAO;

import local.NextGen.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase DAO para la gestión de pedidos en la base de datos.
 * Esta clase se encarga de realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para los pedidos.
 */
public class PedidoDAO {
    private static Connection conn = null;
    private static DetallePedidoDAO detallePedidoDAO = null;
    private final ClienteDAO clienteDAO;
    // private final ArticuloDAO articuloDAO;

    /**
     * Constructor que establece la conexión a la base de datos y las dependencias con otras clases DAO.
     *
     * @param conn La conexión a la base de datos.
     */
    public PedidoDAO(Connection conn) {
        this.conn = conn;
        this.detallePedidoDAO = new DetallePedidoDAO(conn);
        this.clienteDAO = new ClienteDAO();
        //this.articuloDAO = new ArticuloDAO(conn);
    }

    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param pedido El objeto Pedido a insertar.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public static int insertar(Connection conn, Pedido pedido) throws SQLException {
        int numeroPedidoGenerado = -1;

        String sql = "INSERT INTO Pedidos (fecha_hora_pedido, id_cliente) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, new Timestamp(pedido.getFechaHora().getTime()));
            pstmt.setInt(2, pedido.getCliente().getIdCliente());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    numeroPedidoGenerado = generatedKeys.getInt(1);

                    for (DetallePedido detalle : pedido.getDetallesPedido()) {
                        DetallePedidoDAO.agregarDetalle(conn, new DetallePedido(numeroPedidoGenerado, detalle.getArticulo(), detalle.getCantidad()));
                    }
                }
            }
        }

        return numeroPedidoGenerado;
    }



    /**
     * Elimina un pedido de la base de datos.
     *
     * @param numeroPedido El número del pedido a eliminar.
     * @return
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public static boolean eliminar(int numeroPedido) throws SQLException {
        if (existePedido(numeroPedido)) {
            detallePedidoDAO.eliminarPorPedido(numeroPedido);

            String sql = "DELETE FROM Pedidos WHERE numero_pedido = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, numeroPedido);
                pstmt.executeUpdate();
            }
        }
        return false;
    }

    /**
     * Lista todos los pedidos existentes en la base de datos.
     *
     * @return Una lista de objetos Pedido.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public static List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Pedido pedido = crearPedidoDesdeResultSet(rs);
                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    /**
     * Obtiene un pedido específico de la base de datos por su número.
     *
     * @param numeroPedido El número del pedido a buscar.
     * @return El objeto Pedido si se encuentra, o null si no existe.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public Pedido obtenerPorNumero(int numeroPedido) throws SQLException {
        String sql = "SELECT * FROM Pedidos WHERE numero_pedido = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numeroPedido);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return crearPedidoDesdeResultSet(rs);
            }
        }
        return null;
    }

    /**
     * Crea un objeto Pedido a partir de un ResultSet.
     *
     * @param rs ResultSet de una consulta SQL.
     * @return Un objeto Pedido creado a partir de los datos del ResultSet.
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet.
     */
    private static Pedido crearPedidoDesdeResultSet(ResultSet rs) throws SQLException {
        int numeroPedido = rs.getInt("numero_pedido");
        Timestamp fechaHora = rs.getTimestamp("fecha_hora_pedido");
        Cliente cliente = ClienteDAO.obtenerPorId(rs.getInt("id_cliente"));
        List<DetallePedido> detallesPedido = detallePedidoDAO.listarPorPedido(numeroPedido);
        return new Pedido(numeroPedido, new java.util.Date(fechaHora.getTime()), cliente, detallesPedido);
    }

    /**
     * Verifica si un pedido con un determinado número ya existe en la base de datos.
     *
     * @param numeroPedido El número del pedido a verificar.
     * @return true si el pedido existe, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    private static boolean existePedido(int numeroPedido) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Pedidos WHERE numero_pedido = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numeroPedido);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}


