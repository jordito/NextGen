
import local.NextGen.modelo.Articulo;
import local.NextGen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TestArticulos {

    public static void main(String[] args) {
        // Obtenemos la sesión de hibernate a partir de la configuración
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Abrimos la sesión
        Session session = sessionFactory.openSession();
        Transaction tx = null;


        try {
            tx = session.beginTransaction();
            Articulo articulo = new Articulo("35", "Test insert 3", 59.99, 3.45, 90);
            session.persist(articulo);
            tx.commit();
            Query<Articulo> query = session.createQuery("FROM Articulo", Articulo.class);
            List<Articulo> articulos = query.list();

            System.out.println(articulos.toString());

            tx = session.beginTransaction();
            Articulo articuloDel = (Articulo)session.get(Articulo.class, 33);
            session.remove(articuloDel);
            tx.commit();

        } catch (Exception e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
        } finally {
            // Cerrar la sesión
            session.close();
        }

        // Cerramos el session factory
        HibernateUtil.shutdown();
    }
}