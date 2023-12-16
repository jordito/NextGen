package local.NextGen.modelo.DAO;

import local.NextGen.modelo.Articulo;
import local.NextGen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {

    public List<Articulo> obtenerTodos() {

        Transaction tx = null;
        List<Articulo> articulos = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            Query<Articulo> query = session.createQuery("FROM Articulo", Articulo.class);
            articulos = query.list();
            session.close();
            // Cerramos el session factory
            HibernateUtil.shutdown();


        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return articulos;
    }






    public Articulo obtenerPorCodigo(String codigo) {
        // Obtenemos la sesión de hibernate a partir de la configuración
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Abrimos la sesión
        Session session = sessionFactory.openSession();

        Articulo articulo = null;

        System.out.println("Código ingresado: " + codigo);

        try {
            articulo = session.get(Articulo.class, codigo);
        } catch (Exception e) {
            // Handle exceptions or log the error
            throw new DAOException("Error al buscar el usuario con ID " + codigo, e);
        } finally {
            // Cerrar la sesión
            session.close();
        }

        // Cerramos el session factory
        HibernateUtil.shutdown();

        return articulo;
    }


    public boolean insertar(Articulo articulo) {
        // Obtenemos la sesión de hibernate a partir de la configuración
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Abrimos la sesión
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(articulo);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar la sesión
            session.close();
        }

        // Cerramos el session factory
        HibernateUtil.shutdown();
        return true;
    }


    public boolean actualizar(Articulo articulo) {
        // Obtenemos la sesión de hibernate a partir de la configuración
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Abrimos la sesión
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Articulo articuloDel = session.get(Articulo.class, articulo);
            session.remove(articuloDel);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar la sesión
            session.close();
        }

        // Cerramos el session factory
        HibernateUtil.shutdown();
        return true;
    }


    public boolean eliminar(String codigo){
        // Obtenemos la sesión de hibernate a partir de la configuración
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Abrimos la sesión
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Articulo articuloDel = session.get(Articulo.class, codigo);
            session.remove(articuloDel);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar la sesión
            session.close();
        }

        // Cerramos el session factory
        HibernateUtil.shutdown();
        return true;

    }

}
