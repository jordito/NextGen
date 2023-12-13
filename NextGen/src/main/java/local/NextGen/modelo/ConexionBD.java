package local.NextGen.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/tienda"; // Asegúrate de que 'tienda' es el nombre correcto de tu base de datos
    private static final String USER = "jordi"; // Cambia este usuario por el tuyo
    private static final String PASSWORD = "Ll0l.123"; // Cambia esta contraseña por la tuya

    static {
        try {
            // Registrando el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una conexión a la base de datos.
     * @return Connection a la base de datos.
     * @throws SQLException si ocurre un error al conectarse.
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
