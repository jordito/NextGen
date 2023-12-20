package local.NextGen.modelo.DAO;

import local.NextGen.modelo.Cliente;
import local.NextGen.modelo.ClienteEstandard;
import local.NextGen.modelo.ClientePremium;
import local.NextGen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase DAO para la gestión de clientes en la base de datos.
 * Esta clase se encarga de realizar operaciones CRUD para clientes.
 */
public class ClienteDAO {

    /**
     * Obtiene una lista de todos los clientes de la base de datos.
     *
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public static List<Cliente> obtenerTodos(String tipoCliente) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {

                String hql = "SELECT c.idCliente, c.nombre, c.direccion, c.nif, c.email, ce.idCliente AS estandard, cp.idCliente AS premium, cp.cuotaAnual, cp.descuentoEnvio FROM Cliente c" +
                        " LEFT JOIN ClienteEstandard ce ON c.idCliente = ce.idCliente" +
                        " LEFT JOIN ClientePremium cp ON c.idCliente = cp.idCliente";


                if ("estandard".equalsIgnoreCase(tipoCliente)) {
                    hql += " WHERE ce.idCliente IS NOT NULL AND cp.idCliente IS NULL";
                } else if ("premium".equalsIgnoreCase(tipoCliente)) {
                    hql += " WHERE cp.idCliente IS NOT NULL AND ce.idCliente IS NULL";

                }


                Query<Object[]> query = session.createQuery(hql, Object[].class);
                List<Object[]> results = query.getResultList();

                int idCliente = 0;
                String nombre = "";
                String direccion = "";
                String NIF = "";
                String email = "";
                int estandard = 0;
                int premium = 0;
                double cuotaAnual = 0;
                double descuentoEnv = 0;
                for (Object[] result : results) {
                    idCliente = (Integer) result[0];
                    nombre = (String) result[1];
                    direccion = (String) result[2];
                    NIF = (String) result[3];
                    email = (String) result[4];
                    if (result[5] != null) {
                        estandard = (Integer) result[5];
                    } else {
                        estandard = 0;
                    }

                    if (result[6] != null) {
                        premium = (Integer) result[6];
                    } else {
                        premium = 0;
                    }

                    if (result[7] != null) {
                        cuotaAnual = (double) result[7];
                    }

                    if (result[8] != null) {
                        descuentoEnv = (double) result[8];
                    }

                    Cliente cliente = estandard > 0 ? new ClienteEstandard(
                            idCliente, nombre, direccion, NIF, email) {
                        @Override
                        public Map<String, Object> toMap() {
                            return null;
                        }
                    }
                            : new ClientePremium(
                            idCliente, nombre, direccion, NIF, email, cuotaAnual, descuentoEnv) {
                        @Override
                        public Map<String, Object> toMap() {
                            return null;
                        }
                    };
                    clientes.add(cliente);
                }

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

        return clientes;
    }

    /**
     * Obtiene un cliente específico de la base de datos por su ID.
     *
     * @param idCliente El ID del cliente a buscar.
     * @return Un objeto Cliente si se encuentra, o null si no existe.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public Cliente obtenerPorId(int idCliente) throws SQLException {
        Cliente cliente = null;
        List<Cliente> clientes = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {

                //String hql = "SELECT c FROM Cliente c WHERE idCliente = :idCliente";

                // Quizás la forma más sucia de hacer esta query, pero de cualquier otra manera daba errores por el polimorfismo
                String hql = "SELECT c.idCliente, c.nombre, c.direccion, c.nif, c.email, ce.idCliente AS estandard, cp.idCliente AS premium, cp.cuotaAnual, cp.descuentoEnvio FROM Cliente c" +
                        " LEFT JOIN ClienteEstandard ce ON c.idCliente = ce.idCliente" +
                        " LEFT JOIN ClientePremium cp ON c.idCliente = cp.idCliente" +
                        " WHERE c.idCliente = :idCliente";

                Query<Object[]> query = session.createQuery(hql, Object[].class).setParameter("idCliente", idCliente);
                Object[] result = !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;

                if (result != null) {

                    String nombre = (String) result[1];
                    String direccion = (String) result[2];
                    String NIF = (String) result[3];
                    String email = (String) result[4];

                    if (result[5] != null) {
                        cliente = new ClienteEstandard(
                                idCliente, nombre, direccion, NIF, email) {
                            @Override
                            public Map<String, Object> toMap() {
                                return null;
                            }
                        };
                    }

                    if (result[6] != null) {
                        double cuotaAnual = 0;
                        double descuentoEnv = 0;
                        cliente = new ClientePremium(
                                idCliente, nombre, direccion, NIF, email, cuotaAnual, descuentoEnv) {
                            @Override
                            public Map<String, Object> toMap() {
                                return null;
                            }
                        };
                    }
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al buscar el usuario", e);
            } finally {
                // Cerrar la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }

        return cliente;
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param cliente El cliente a insertar.
     * @return true si la inserción es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public static boolean insertar(Cliente cliente) throws SQLException {
        boolean transactionSuccessful = false;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {

                tx = session.beginTransaction();
                String sql = "INSERT INTO clientes (id_cliente, nombre, domicilio, NIF, email, tipo_cliente) VALUES (:idCliente, :nombre, :direccion, :nif, :email, :tipoCliente)";
                NativeQuery<?> query = session.createNativeQuery(sql);

                query.setParameter("idCliente", cliente.getIdCliente());
                query.setParameter("nombre", cliente.getNombre());
                query.setParameter("direccion", cliente.getDireccion());
                query.setParameter("nif", cliente.getNif());
                query.setParameter("email", cliente.getEmail());
                query.setParameter("tipoCliente", cliente.getTipoCliente());

                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    transactionSuccessful = true;

                    if (cliente instanceof ClienteEstandard) {
                        return insertarClienteEstandard((BigInteger) session.createNativeQuery("SELECT LAST_INSERT_ID()").uniqueResult());
                    } else if (cliente instanceof ClientePremium) {
                        return insertarClientePremium((BigInteger) session.createNativeQuery("SELECT LAST_INSERT_ID()").uniqueResult(), (ClientePremium) cliente);
                    }
                    session.close();
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al guardar el usuario", e);
            } finally {
                // Cerrar la sesión

            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }
        return transactionSuccessful;
    }

    /**
     * Inserta un nuevo registro en la tabla ClientesEstandard asociado a un cliente existente.
     *
     * @param idCliente El ID del cliente al que se asociará el registro en la tabla ClientesEstandard.
     * @return true si la inserción es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    private static boolean insertarClienteEstandard(BigInteger idCliente) throws SQLException {

        boolean transactionSuccessful = false;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                String sql = "INSERT INTO clientesestandard (id_cliente) VALUES (:idCliente)";
                NativeQuery<?> query = session.createNativeQuery(sql);
                query.setParameter("idCliente", idCliente);


                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    transactionSuccessful = true;
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al guardar el usuario", e);
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

    /**
     * Inserta un nuevo registro en la tabla ClientesPremium asociado a un cliente existente.
     *
     * @param idCliente      El ID del cliente al que se asociará el registro en la tabla ClientesPremium.
     * @param clientePremium El objeto ClientePremium con la información adicional a insertar.
     * @return true si la inserción es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    private static boolean insertarClientePremium(BigInteger idCliente, ClientePremium clientePremium) throws SQLException {
        String sqlPremium = "INSERT INTO ClientesPremium (id_cliente, cuota_anual, descuento_envio) VALUES (?, ?, ?)";

        boolean transactionSuccessful = false;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                String sql = "INSERT INTO clientespremium (id_cliente, cuota_anual, descuento_envio) VALUES (:idCliente, :cuotaAnual, :descuentoEnvio)";
                NativeQuery<?> query = session.createNativeQuery(sql);
                query.setParameter("idCliente", idCliente);
                query.setParameter("cuotaAnual", clientePremium.getCuotaAnual());
                query.setParameter("descuentoEnvio", clientePremium.getDescuentoEnvio());

                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    transactionSuccessful = true;
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al guardar el usuario", e);
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

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     *
     * @param cliente El cliente a actualizar.
     * @return true si la actualización es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public static boolean actualizar(Cliente cliente) {
        boolean transactionSuccessful = false;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                String sql = "UPDATE clientes SET nombre = :nombre, email = :email, domicilio = :domicilio WHERE id_cliente = :idCliente";

                NativeQuery<?> query = session.createNativeQuery(sql);
                query.setParameter("nombre", cliente.getNombre());
                query.setParameter("email", cliente.getEmail());
                query.setParameter("domicilio", cliente.getDireccion());
                query.setParameter("idCliente", cliente.getIdCliente());

                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    transactionSuccessful = true;
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al actualizar el usuario", e);
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

    /**
     * Elimina un cliente de la base de datos utilizando su NIF.
     *
     * @param nif El NIF del cliente a eliminar.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public static boolean eliminarPorNIF(String nif) throws SQLException {
        boolean transactionSuccessful = false;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            int idCliente = obtenerIdClientePorNIF(nif);
            if (idCliente == -1) {
                return false;
            }
            eliminarClienteEstandard(idCliente);
            eliminarClientePremium(idCliente);
            try {
                tx = session.beginTransaction();
                String sql = "DELETE FROM clientes WHERE nif = :nif";

                NativeQuery<?> query = session.createNativeQuery(sql);
                query.setParameter("nif", nif);

                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    transactionSuccessful = true;
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al actualizar el usuario", e);
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

    /**
     * Elimina un cliente de la tabla ClientesEstandard asociado a un cliente existente.
     *
     * @param idCliente El ID del cliente cuyo registro en la tabla ClientesEstandard se eliminará.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    private static void eliminarClienteEstandard(int idCliente) throws SQLException {
        //String hql = "DELETE FROM Cliente c WHERE id_cliente = :idCliente";
        boolean transactionSuccessful = false;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                String sql = "DELETE FROM clientesestandard WHERE id_cliente = :idCliente";

                NativeQuery<?> query = session.createNativeQuery(sql);
                query.setParameter("idCliente", idCliente);

                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    transactionSuccessful = true;
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al eliminar el usuario", e);
            } finally {
                // Cerrar la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }
    }

    /**
     * Elimina un cliente de la tabla ClientesPremium asociado a un cliente existente.
     *
     * @param idCliente El ID del cliente cuyo registro en la tabla ClientesPremium se eliminará.
     * @return true si la eliminación es exitosa, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    private static void eliminarClientePremium(int idCliente) throws SQLException {
        boolean transactionSuccessful = false;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                tx = session.beginTransaction();
                String sql = "DELETE FROM clientespremium WHERE id_cliente = :idCliente";

                NativeQuery<?> query = session.createNativeQuery(sql);
                query.setParameter("idCliente", idCliente);

                int result = query.executeUpdate();

                if (result > 0) {
                    tx.commit();
                    transactionSuccessful = true;
                }
            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al eliminar el usuario", e);
            } finally {
                // Cerrar la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }
    }

    /**
     * Obtiene el ID de un cliente basado en su NIF.
     *
     * @param nif El NIF del cliente cuyo ID se desea obtener.
     * @return El ID del cliente si se encuentra, o -1 si no se encuentra ningún cliente con ese NIF.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    private static int obtenerIdClientePorNIF(String nif) throws SQLException {
        Cliente cliente = null;
        List<Cliente> clientes = null;
        int idCliente = -1;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {

                String hql = "SELECT c.idCliente FROM Cliente c" +
                        " WHERE c.nif = :nif";

                Query<Integer> query = session.createQuery(hql, Integer.class).setParameter("nif", nif);
                int result = !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;

                if (result != -1) idCliente = result;
            } catch (Exception e) {
                // Mostramos el error
                return -1;


            } finally {
                // Cerrar la sesión
                session.close();
            }
        } catch (Exception e) {
            // Mostramos el error
            throw new DAOException("Error al conectar con la base de datos", e);
        }

        return idCliente;
    }

    /**
     * Verifica si existe un cliente con un determinado NIF en la base de datos.
     *
     * @param nif El NIF del cliente a verificar.
     * @return true si el cliente existe, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public boolean existeNIF(String nif) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Clientes WHERE NIF = ?";

        Cliente cliente = null;
        List<Cliente> clientes = null;
        long result = -1;
        boolean transactionSuccessful = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {

                //String hql = "SELECT c FROM Cliente c WHERE idCliente = :idCliente";

                // Quizás la forma más sucia de hacer esta query, pero de cualquier otra manera daba errores por el polimorfismo
                String hql = "SELECT COUNT(*) FROM Cliente c" +
                        " WHERE c.nif = :nif";

                Query<Long> query = session.createQuery(hql, Long.class).setParameter("nif", nif);
                result = query.uniqueResult();

                if (result > 0) transactionSuccessful = true;


            } catch (Exception e) {
                // Mostramos el error
                throw new DAOException("Error al buscar el usuario", e);
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

}
