package local.NextGen.modelo.DAO;

import local.NextGen.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase DAO para la gestión de clientes en la base de datos.
 * Esta clase se encarga de realizar operaciones CRUD para clientes.
 */
public class ClienteDAO {
    private static Connection conexion;

    /**
     * Constructor que establece la conexión con la base de datos.
     *
     * @param conexion La conexión a la base de datos.
     */
    public ClienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene una lista de todos los clientes de la base de datos.
     *
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public static List<Cliente> obtenerTodos(String tipoCliente) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.id_cliente, c.nombre, c.domicilio, c.NIF, c.email, ce.id_cliente AS estandard, cp.id_cliente AS premium, cp.cuota_anual, cp.descuento_envio " +
                "FROM Clientes c " +
                "LEFT JOIN ClientesEstandard ce ON c.id_cliente = ce.id_cliente " +
                "LEFT JOIN ClientesPremium cp ON c.id_cliente = cp.id_cliente";

        if ("estandard".equalsIgnoreCase(tipoCliente)) {
            sql += " WHERE ce.id_cliente IS NOT NULL AND cp.id_cliente IS NULL";
        } else if ("premium".equalsIgnoreCase(tipoCliente)) {
            sql += " WHERE cp.id_cliente IS NOT NULL AND ce.id_cliente IS NULL";
        }

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = rs.getInt("estandard") > 0 ?
                        new ClienteEstandard(
                                rs.getInt("id_cliente"),
                                rs.getString("nombre"),
                                rs.getString("domicilio"),
                                rs.getString("NIF"),
                                rs.getString("email")) {
                            @Override
                            public Map<String, Object> toMap() {
                                return null;
                            }
                        } :
                        new ClientePremium(
                                rs.getInt("id_cliente"),
                                rs.getString("nombre"),
                                rs.getString("domicilio"),
                                rs.getString("NIF"),
                                rs.getString("email"),
                                rs.getDouble("cuota_anual"),
                                rs.getDouble("descuento_envio")) {
                            @Override
                            public Map<String, Object> toMap() {
                                return null;
                            }
                        };
                clientes.add(cliente);
            }
        }
        return clientes;
    }
    /**
     * Obtiene un cliente específico de la base de datos por su ID.
     *
     * @param idCliente El ID del cliente a buscar.
     * @return Un objeto Cliente si se encuentra, o null si no existe.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public static Cliente obtenerPorId(int idCliente) throws SQLException {
        String sql = "SELECT c.id_cliente, c.NIF, c.nombre, c.email, c.domicilio, " +
                "ce.id_cliente AS estandard, cp.id_cliente AS premium, cp.cuota_anual, cp.descuento_envio " +
                "FROM Clientes c " +
                "LEFT JOIN ClientesEstandard ce ON c.id_cliente = ce.id_cliente " +
                "LEFT JOIN ClientesPremium cp ON c.id_cliente = cp.id_cliente " +
                "WHERE c.id_cliente = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("estandard") > 0 ?
                        new ClienteEstandard(
                                rs.getInt("id_cliente"),
                                rs.getString("nombre"),
                                rs.getString("domicilio"),
                                rs.getString("NIF"),
                                rs.getString("email")) {
                            @Override
                            public Map<String, Object> toMap() {
                                return null;
                            }
                        } :
                        new ClientePremium(
                                rs.getInt("id_cliente"),
                                rs.getString("nombre"),
                                rs.getString("domicilio"),
                                rs.getString("NIF"),
                                rs.getString("email"),
                                rs.getDouble("cuota_anual"),
                                rs.getDouble("descuento_envio")) {
                            @Override
                            public Map<String, Object> toMap() {
                                return null;
                            }
                        };
            }
        }
        return null; // El cliente no fue encontrado
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param cliente El cliente a insertar.
     * @return true si la inserción es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public static boolean insertar(Cliente cliente) throws SQLException {
        String sqlCliente = "INSERT INTO Clientes (nombre, domicilio, NIF, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmtCliente = conexion.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS)) {
            stmtCliente.setString(1, cliente.getNombre());
            stmtCliente.setString(2, cliente.getDireccion());
            stmtCliente.setString(3, cliente.getNif());
            stmtCliente.setString(4, cliente.getEmail());


            int affectedRowsCliente = stmtCliente.executeUpdate();

            if (affectedRowsCliente > 0) {
                ResultSet generatedKeys = stmtCliente.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idCliente = generatedKeys.getInt(1);
                    if (cliente instanceof ClienteEstandard) {
                        return insertarClienteEstandard(idCliente);
                    } else if (cliente instanceof ClientePremium) {
                        return insertarClientePremium(idCliente, (ClientePremium) cliente);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Inserta un nuevo registro en la tabla ClientesEstandard asociado a un cliente existente.
     *
     * @param idCliente El ID del cliente al que se asociará el registro en la tabla ClientesEstandard.
     * @return true si la inserción es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    private static boolean insertarClienteEstandard(int idCliente) throws SQLException {
        String sqlEstandard = "INSERT INTO ClientesEstandard (id_cliente) VALUES (?)";

        try (PreparedStatement stmtEstandard = conexion.prepareStatement(sqlEstandard)) {
            stmtEstandard.setInt(1, idCliente);

            int affectedRowsEstandard = stmtEstandard.executeUpdate();
            return affectedRowsEstandard > 0;
        }
    }

    /**
     * Inserta un nuevo registro en la tabla ClientesPremium asociado a un cliente existente.
     *
     * @param idCliente      El ID del cliente al que se asociará el registro en la tabla ClientesPremium.
     * @param clientePremium El objeto ClientePremium con la información adicional a insertar.
     * @return true si la inserción es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    private static boolean insertarClientePremium(int idCliente, ClientePremium clientePremium) throws SQLException {
        String sqlPremium = "INSERT INTO ClientesPremium (id_cliente, cuota_anual, descuento_envio) VALUES (?, ?, ?)";

        try (PreparedStatement stmtPremium = conexion.prepareStatement(sqlPremium)) {
            stmtPremium.setInt(1, idCliente);
            stmtPremium.setDouble(2, clientePremium.getCuotaAnual());
            stmtPremium.setDouble(3, clientePremium.getDescuentoEnvio());

            int affectedRowsPremium = stmtPremium.executeUpdate();
            return affectedRowsPremium > 0;
        }
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     *
     * @param cliente El cliente a actualizar.
     * @return true si la actualización es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public static boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Clientes SET nombre = ?, email = ?, domicilio = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getDireccion());
            stmt.setInt(4, cliente.getIdCliente());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos utilizando su NIF.
     *
     * @param nif El NIF del cliente a eliminar.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public static boolean eliminarPorNIF(String nif) throws SQLException {
        int idCliente = obtenerIdClientePorNIF(nif);
        if (idCliente == -1) {
            return false;
        }
        eliminarClienteEstandard(idCliente);
        eliminarClientePremium(idCliente);
        String sqlEliminarCliente = "DELETE FROM Clientes WHERE NIF = ?";
        try (PreparedStatement stmtEliminarCliente = conexion.prepareStatement(sqlEliminarCliente)) {
            stmtEliminarCliente.setString(1, nif);
            int affectedRowsCliente = stmtEliminarCliente.executeUpdate();

            return affectedRowsCliente > 0;
        }
    }
    /**
     * Elimina un cliente de la tabla ClientesEstandard asociado a un cliente existente.
     *
     * @param idCliente El ID del cliente cuyo registro en la tabla ClientesEstandard se eliminará.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    private static void eliminarClienteEstandard(int idCliente) throws SQLException {
        String sql = "DELETE FROM ClientesEstandard WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }
    }
    /**
     * Elimina un cliente de la tabla ClientesPremium asociado a un cliente existente.
     *
     * @param idCliente El ID del cliente cuyo registro en la tabla ClientesPremium se eliminará.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    private static void eliminarClientePremium(int idCliente) throws SQLException {
        String sql = "DELETE FROM ClientesPremium WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene el ID de un cliente basado en su NIF.
     *
     * @param nif El NIF del cliente cuyo ID se desea obtener.
     * @return El ID del cliente si se encuentra, o -1 si no se encuentra ningún cliente con ese NIF.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    private static int obtenerIdClientePorNIF(String nif) throws SQLException {
        String sql = "SELECT id_cliente FROM Clientes WHERE NIF = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nif);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id_cliente") : -1;
        }
    }

    /**
     * Verifica si existe un cliente con un determinado NIF en la base de datos.
     *
     * @param nif El NIF del cliente a verificar.
     * @return true si el cliente existe, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public boolean existeNIF(String nif) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Clientes WHERE NIF = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nif);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
        e.printStackTrace(); // Manejo de excepciones
    }
        return false;
    }
}
