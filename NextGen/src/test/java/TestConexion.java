import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestConexion {

    public static void main(String[] args) {
        // Configurar la sesión de Hibernate
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml") // Nombre del archivo de configuración de Hibernate
                .buildSessionFactory();

        Session session = null;
        try {
            // Obtener una sesión de Hibernate
            session = sessionFactory.openSession();

            if (session != null) {
                System.out.println("Conexión establecida con éxito.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la base de datos.");
        } finally {
            if (session != null) {
                session.close(); // Cerrar la sesión de Hibernate
                System.out.println("Conexión cerrada.");
            }
        }

        // Cerrar la fábrica de sesiones de Hibernate al finalizar
        sessionFactory.close();
    }
}
