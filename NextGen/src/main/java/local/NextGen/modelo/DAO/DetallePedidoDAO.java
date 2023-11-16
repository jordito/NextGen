package local.NextGen.modelo.DAO;

import local.NextGen.modelo.DetallePedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAO {

    private Connection conexion;

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
                        rs.getString("codigo_articulo"),
                        rs.getInt("cantidad"),
                        rs.getBigDecimal("precio_venta")
                );
                detalles.add(detalle);
            }
        }
        return detalles;
    }

    /**
     * Agrega un detalle de pedido a la base de datos.
     */
    public boolean agregarDetalle(DetallePedido detalle) {
        String sql = "INSERT INTO DetallePedido (numero_pedido, codigo_articulo, cantidad, precio_venta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getNumeroPedido());
            stmt.setString(2, detalle.getCodigoArticulo());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setBigDecimal(4, detalle.getPrecioVenta());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Elimina un detalle de pedido de la base de datos.
     */
    public boolean eliminarDetalle(int numeroPedido, String codigoArticulo) {
        String sql = "DELETE FROM DetallePedido WHERE numero_pedido = ? AND codigo_articulo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numeroPedido);
            stmt.setString(2, codigoArticulo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
