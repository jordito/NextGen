package local.NextGen.controlador;

import local.NextGen.modelo.DAO.ClienteDAO;
import local.NextGen.modelo.factory.Datos;
import local.NextGen.modelo.entidades.Cliente;

import java.util.List;

/**
 * Esta clase actúa como el controlador para las operaciones relacionadas con la entidad Cliente.
 * Proporciona métodos para listar clientes, obtener clientes por tipo, obtener cliente por ID, agregar, eliminar y actualizar clientes.
 */
public class ControladorCliente {
    private final ClienteDAO clienteDAO;

    /**
     * Constructor de la clase ControladorCliente.
     * Inicializa el ClienteDAO utilizando la fábrica de Datos.
     */
    public ControladorCliente() {
        this.clienteDAO = Datos.getClienteDAO();
    }

    /**
     * Obtiene una lista de todos los clientes.
     *
     * @return Una lista de clientes.
     */
    public List<Cliente> listarClientes() {
        return clienteDAO.obtenerTodos("");
    }

    /**
     * Obtiene una lista de clientes estándar.
     *
     * @return Una lista de clientes estándar.
     */
    public List<Cliente> listarClientesEstandard() {
        return clienteDAO.obtenerTodos("estandard");
    }

    /**
     * Obtiene una lista de clientes premium.
     *
     * @return Una lista de clientes premium.
     */
    public List<Cliente> listarClientesPremium() {
        return clienteDAO.obtenerTodos("premium");
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param idCliente El ID del cliente a obtener.
     * @return El cliente encontrado o null si no se encontró ninguno con ese ID.
     */
    public Cliente obtenerClientePorId(int idCliente) {
        return clienteDAO.obtenerPorId(idCliente);
    }

    /**
     * Obtiene un cliente por su NIF (Número de Identificación Fiscal).
     *
     * @param nif El NIF del cliente a buscar.
     * @return El cliente encontrado o null si no se encontró ninguno con ese NIF.
     */
    public Cliente obtenerClientePorNif(String nif) {
        return clienteDAO.obtenerPorNif(nif);
    }

    /**
     * Agrega un nuevo cliente.
     *
     * @param cliente El cliente a agregar.
     * @return true si se agregó el cliente con éxito, false en caso contrario.
     */
    public boolean agregarCliente(Cliente cliente) {
        return clienteDAO.insertar(cliente);
    }

    /**
     * Elimina un cliente por su NIF.
     *
     * @param nif El NIF del cliente a eliminar.
     * @return true si se eliminó el cliente con éxito, false en caso contrario.
     */
    public boolean eliminarCliente(String nif) {
        return clienteDAO.eliminarPorNIF(nif);
    }

    /**
     * Actualiza la información de un cliente.
     *
     * @param cliente El cliente con la información actualizada.
     * @return true si se actualizó el cliente con éxito, false en caso contrario.
     */
    public boolean actualizarCliente(Cliente cliente) {
        return clienteDAO.actualizar(cliente);
    }
}
