
import local.NextGen.modelo.Articulo;
import local.NextGen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TestArticulos {

    public static void main(String[] args) {
        // Obtenemos la sesión de hibernate a partir de la configuración
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Abrimos la sesión
        Session session = sessionFactory.openSession();

        try {
            Query<Articulo> query = session.createQuery("FROM Articulo", Articulo.class);
            List<Articulo> articulos = query.list();

            System.out.println(articulos.toString());

        } finally {
            // Cerrar la sesión
            session.close();
        }

        // Cerramos el session factory
        HibernateUtil.shutdown();
    }
}