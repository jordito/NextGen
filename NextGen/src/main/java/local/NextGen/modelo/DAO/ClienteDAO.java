package local.NextGen.modelo.DAO;

import local.NextGen.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para la gestión de clientes en la base de datos.
 * Esta clase se encarga de realizar operaciones CRUD para clientes.
 */
public class ClienteDAO {
    private Connection conexion;

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
     * @return Una lista de clientes.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public List<Cliente> obtenerTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.id_cliente, c.NIF, c.nombre, c.email, c.domicilio, ce.id_cliente AS estandard, cp.id_cliente AS premium, cp.cuota_anual, cp.descuento_envio " +
                "FROM Clientes c " +
                "LEFT JOIN ClientesEstandard ce ON c.id_cliente = ce.id_cliente " +
                "LEFT JOIN ClientesPremium cp ON c.id_cliente = cp.id_cliente";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = rs.getInt("estandard") > 0 ?
                        new ClienteEstandard(
                                rs.getInt("id_cliente"),
                                rs.getString("NIF"),
                                rs.getString("nombre"),
                                rs.getString("email"),
                                rs.getString("domicilio")) :
                        new ClientePremium(
                                rs.getInt("id_cliente"),
                                rs.getString("NIF"),
                                rs.getString("nombre"),
                                rs.getString("email"),
                                rs.getString("domicilio"),
                                rs.getDouble("cuota_anual"),
                                rs.getDouble("descuento_envio"));
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
    public Cliente obtenerPorId(int idCliente) throws SQLException {
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
                                rs.getString("NIF"),
                                rs.getString("nombre"),
                                rs.getString("email"),
                                rs.getString("domicilio")) :
                        new ClientePremium(
                                rs.getInt("id_cliente"),
                                rs.getString("NIF"),
                                rs.getString("nombre"),
                                rs.getString("email"),
                                rs.getString("domicilio"),
                                rs.getDouble("cuota_anual"),
                                rs.getDouble("descuento_envio"));
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
    public boolean insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (NIF, nombre, email, domicilio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNif());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getDireccion());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     *
     * @param cliente El cliente a actualizar.
     * @return true si la actualización es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public boolean actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE Clientes SET nombre = ?, email = ?, domicilio = ? WHERE NIF = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getDireccion());
            stmt.setString(4, cliente.getNif());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un cliente de la base de datos utilizando su NIF.
     *
     * @param nif El NIF del cliente a eliminar.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public boolean eliminarPorNIF(String nif) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE NIF = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nif);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
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
        }
        return false;
    }
}
