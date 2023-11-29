
import local.NextGen.modelo.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {

    public static void main(String[] args) {
        Connection conexion = null;
        try {
            conexion = ConexionBD.obtenerConexion();
            if (conexion != null) {
                System.out.println("Conexión establecida con éxito.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la base de datos.");
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
