package local.NextGen.modelo;

import local.NextGen.modelo.DAO.*;
import java.sql.SQLException;

/**
 * Clase Datos que actúa como fábrica para la creación de objetos DAO.
 * Proporciona métodos estáticos para obtener instancias de diferentes DAOs.
 */
public class Datos {

    /**
     * Obtiene una instancia de ClienteDAO.
     * @return una instancia de ClienteDAO.
     */
    public static ClienteDAO getClienteDAO() throws SQLException {
        return new ClienteDAO(ConexionBD.obtenerConexion());
    }

    /**
     * Obtiene una instancia de ArticuloDAO.
     * @return una instancia de ArticuloDAO.
     */
    public static ArticuloDAO getArticuloDAO() throws SQLException {
        return new ArticuloDAO(ConexionBD.obtenerConexion());
    }

    /**
     * Obtiene una instancia de PedidoDAO.
     * @return una instancia de PedidoDAO.
     */
    public static PedidoDAO getPedidoDAO() throws SQLException {
        return new PedidoDAO(ConexionBD.obtenerConexion());
    }

    /**
     * Obtiene una instancia de DetallePedidoDAO.
     * @return una instancia de DetallePedidoDAO.
     */
    public static DetallePedidoDAO getDetallePedidoDAO() throws SQLException {
        return new DetallePedidoDAO(ConexionBD.obtenerConexion());
    }
}
