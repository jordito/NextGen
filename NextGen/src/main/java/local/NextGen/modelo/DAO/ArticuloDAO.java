package local.NextGen.modelo.DAO;

import local.NextGen.exceptions.CustomException;
import local.NextGen.modelo.Articulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar los artículos en la base de datos.
 */
public class ArticuloDAO {
    private static Connection conexion;

    /**
     * Constructor de ArticuloDAO.
     * @param conexion La conexión a la base de datos.
     */
    public ArticuloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene todos los artículos de la base de datos.
     */
    public static List<Articulo> obtenerTodos() {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM Articulos";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }
    /**
     * Obtiene un artículo específico de la base de datos por su código.
     *
     * @param codigo El código del artículo a buscar.
     * @return El objeto Articulo si se encuentra, o null si no existe.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public static Articulo obtenerPorCodigo(String codigo) throws SQLException {
        System.out.println("Código ingresado: " + codigo);

        String sql = "SELECT * FROM Articulos WHERE LOWER(codigo) = LOWER(?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion"));
            }
        }
        return null;
    }



    /**
     * Inserta un nuevo artículo en la base de datos.
     *
     * @param articulo El objeto Articulo a insertar.
     */
    public static boolean insertar(Articulo articulo) throws CustomException {
        String sql = "INSERT INTO Articulos (codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, articulo.getCodigo());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setDouble(3, articulo.getPrecio());
            stmt.setDouble(4, articulo.getGastosEnvio());
            stmt.setInt(5, articulo.getTiempoPreparacion());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        articulo.setCodigo(generatedKeys.getString(1));
                    }
                }
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Actualiza los datos de un artículo en la base de datos.
     * @param articulo El objeto Articulo con los datos a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean actualizar(Articulo articulo) {
        String sql = "UPDATE Articulos SET descripcion = ?, precio_venta = ?, gastos_envio = ?, tiempo_preparacion = ? WHERE codigo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, articulo.getDescripcion());
            stmt.setDouble(2, articulo.getPrecio());
            stmt.setDouble(3, articulo.getGastosEnvio());
            stmt.setInt(4, articulo.getTiempoPreparacion());
            stmt.setString(5, articulo.getCodigo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un artículo de la base de datos utilizando su código.
     *
     * @param codigo El código del artículo a eliminar.
     * @return
     */
    public static boolean eliminar(String codigo) {
        String sql = "DELETE FROM Articulos WHERE codigo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

