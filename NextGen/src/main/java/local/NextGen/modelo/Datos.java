package local.NextGen.modelo;

import local.NextGen.modelo.DAO.*;

/**
 * Clase Datos que actúa como fábrica para la creación de objetos DAO.
 * Proporciona métodos estáticos para obtener instancias de diferentes DAOs.
 */
public class Datos {

    /**
     * Obtiene una instancia de ClienteDAO.
     * @return una instancia de ClienteDAO.
     */
    public static ClienteDAO getClienteDAO() {
        return new ClienteDAO();
    }

    /**
     * Obtiene una instancia de ArticuloDAO.
     * @return una instancia de ArticuloDAO.
     */
    public static ArticuloDAO getArticuloDAO() {
        return new ArticuloDAO();
    }

    /**
     * Obtiene una instancia de PedidoDAO.
     * @return una instancia de PedidoDAO.
     */
    public static PedidoDAO getPedidoDAO() {
        return new PedidoDAO();
    }

    /**
     * Obtiene una instancia de DetallePedidoDAO.
     * @return una instancia de DetallePedidoDAO.
     */
    public static DetallePedidoDAO getDetallePedidoDAO() {
        return new DetallePedidoDAO();
    }
}
