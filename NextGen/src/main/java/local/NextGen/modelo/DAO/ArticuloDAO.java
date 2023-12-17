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

        List<Articulo> articulos = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                Query<Articulo> query = session.createQuery("FROM Articulo", Articulo.class);
                articulos = query.list();
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al buscar los usuarios", e);
            } finally {
                // Cerrar la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }
        return articulos;
    }

    public Articulo obtenerPorCodigo(String codigo) {
        Articulo articulo = null;
        // Obtenemos la sesión de hibernate a partir de la configuración y abrimos la sesión
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Código ingresado: " + codigo);

            try {
                articulo = session.get(Articulo.class, codigo);
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al buscar el usuario con ID " + codigo, e);
            } finally {
                // Cerrar la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al buscar el usuario con ID " + codigo, e);
        }

        return articulo;
    }


    public boolean insertar(Articulo articulo) {
        boolean transactionSuccessful = false;
        // Inicializamos la transacción
        Transaction tx = null;
        // Obtenemos la sesión de hibernate a partir de la configuración y abrimos la sesión
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                session.persist(articulo);
                tx.commit();
                transactionSuccessful = true;
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al insertar el artículo", e);

            } finally {
                // Cerrar la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }
        return transactionSuccessful;
    }


    public boolean actualizar(Articulo articulo) {
        boolean transactionSuccessful = false;
        // Inicializamos la transacción
        Transaction tx = null;
        // Obtenemos la sesión de hibernate a partir de la configuración y abrimos la sesión
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                session.update(articulo);
                tx.commit();
                transactionSuccessful = true;
            } catch (Exception e) {
                try {
                    // Hacemos rollback al haber habido un error
                    if (tx != null) {
                        tx.rollback();
                    }
                } catch (DAOException rollbackException) {
                    // Mostramos el error
                    throw new DAOException("Error al actualizar el artículo", e);
                }
            } finally {
                // Cerramos la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }

        return transactionSuccessful;
    }

    public boolean eliminar(String codigo){
        boolean transactionSuccessful = false;
        // Inicializamos la transacción
        Transaction tx = null;
        // Obtenemos la sesión de hibernate a partir de la configuración y abrimos la sesión
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                Articulo articulo = session.get(Articulo.class, codigo);
                session.remove(articulo);
                tx.commit();
                transactionSuccessful = true;
            } catch (Exception e) {
                try {
                    // Hacemos rollback al haber habido un error
                    if (tx != null) {
                        tx.rollback();
                    }
                } catch (DAOException rollbackException) {
                    // Mostramos el error
                    throw new DAOException("El artículo con código + " +
                            codigo +
                            "artículo no existe", e);
                }
            } finally {
                // Cerramos la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }

        return transactionSuccessful;

    }

}
