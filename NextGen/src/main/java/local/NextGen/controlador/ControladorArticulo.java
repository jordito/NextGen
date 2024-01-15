package local.NextGen.controlador;

import local.NextGen.modelo.DAO.ArticuloDAO;
import local.NextGen.modelo.factory.Datos;
import local.NextGen.modelo.entidades.Articulo;

import java.util.List;

/**
 * Esta clase actúa como el controlador para las operaciones relacionadas con la entidad Articulo.
 * Proporciona métodos para listar artículos, agregar, eliminar, actualizar y obtener artículos por código.
 */
public class ControladorArticulo {
    private final ArticuloDAO articuloDAO;

    /**
     * Constructor de la clase ControladorArticulo.
     * Inicializa el ArticuloDAO utilizando la fábrica de Datos.
     */
    public ControladorArticulo() {
        this.articuloDAO = Datos.getArticuloDAO();
    }

    /**
     * Obtiene una lista de todos los artículos.
     *
     * @return Una lista de artículos.
     */
    public List<Articulo> listarArticulos() {
        return articuloDAO.obtenerTodos();
    }

    /**
     * Agrega un nuevo artículo.
     *
     * @param articulo El artículo a agregar.
     */
    public void agregarArticulo(Articulo articulo) {
        articuloDAO.insertar(articulo);
    }

    /**
     * Elimina un artículo por su código.
     *
     * @param codigo El código del artículo a eliminar.
     * @return true si se eliminó el artículo con éxito, false en caso contrario.
     */
    public boolean eliminarArticulo(String codigo) {
        return articuloDAO.eliminar(codigo);
    }

    /**
     * Actualiza la información de un artículo.
     *
     * @param articulo El artículo con la información actualizada.
     * @return true si se actualizó el artículo con éxito, false en caso contrario.
     */
    public boolean actualizarArticulo(Articulo articulo) {
        return articuloDAO.actualizar(articulo);
    }

    /**
     * Obtiene un artículo por su código.
     *
     * @param codigo El código del artículo a obtener.
     * @return El artículo encontrado o null si no se encontró ninguno con ese código.
     */
    public Articulo obtenerArticuloPorCodigo(String codigo) {
        return articuloDAO.obtenerPorCodigo(codigo);
    }
}
