package local.NextGen.modelo.DAO;

import local.NextGen.modelo.Articulo;
import local.NextGen.modelo.DetallePedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static local.NextGen.modelo.ConexionBD.obtenerConexion;

public class DetallePedidoDAO {

    private static Connection conexion;

    public DetallePedidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Recupera los detalles de un pedido específico.
     *
     * @param numeroPedido El número del pedido cuyos detalles se quieren recuperar.
     * @return Una lista de objetos DetallePedido asociados con el número de pedido.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public List<DetallePedido> listarPorPedido(int numeroPedido) throws SQLException {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT * FROM DetallePedido WHERE numero_pedido = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numeroPedido);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DetallePedido detalle = new DetallePedido(
                        rs.getInt("numero_pedido"),
                        obtenerArticuloPorCodigo(rs.getString("codigo_articulo")),
                        rs.getInt("cantidad"));
                detalles.add(detalle);
            }
        }
        return detalles;
    }

    /**
     * Agrega un detalle de pedido a la base de datos.
     */
    public static void agregarDetalle(Connection conn, DetallePedido detalle) {
        String sql = "INSERT INTO DetallePedido (numero_pedido, codigo_articulo, cantidad, precio_venta) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (!existeDetalle(conn, detalle)) {
                pstmt.setInt(1, detalle.getNumeroPedido());
                pstmt.setString(2, detalle.getArticulo().getCodigo());
                pstmt.setInt(3, detalle.getCantidad());
                pstmt.setDouble(4, detalle.getPrecioVenta());

                pstmt.executeUpdate();
            } else {
                System.out.println("El detalle ya existe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean existeDetalle(Connection conn, DetallePedido detalle) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DetallePedido WHERE numero_pedido = ? AND codigo_articulo = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, detalle.getNumeroPedido());
            pstmt.setString(2, detalle.getArticulo().getCodigo());

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Elimina un detalle de pedido de la base de datos.
     */
    public boolean eliminarPorPedido(int numeroPedido) {
        String sql = "DELETE FROM DetallePedido WHERE numero_pedido = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numeroPedido);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Método auxiliar para obtener un objeto Articulo por su código
    private Articulo obtenerArticuloPorCodigo(String codigoArticulo) throws SQLException {
        String sql = "SELECT * FROM Articulos WHERE codigo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, codigoArticulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion")
                );
            } else {
                return null; // Puedes manejar el caso en que el artículo no se encuentre
            }
        }
    }
}
