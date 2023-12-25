package local.NextGen.modelo.entidades;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Clase ClienteEstandard que extiende de la clase Cliente.
 * Representa una especialización de Cliente, enfocada en clientes estándar.
 * Está mapeada a la tabla "ClientesEstandard" en la base de datos.
 */
@Entity
@Table(name = "ClientesEstandard")
public class ClienteEstandard extends Cliente {

    /**
     * Constructor por defecto de ClienteEstandard.
     * Invoca al constructor por defecto de la superclase Cliente.
     */
    public ClienteEstandard() {
        super();
    }

    /**
     * Constructor de ClienteEstandard que acepta parámetros para inicializar la entidad.
     * Invoca al constructor de la superclase Cliente con los mismos parámetros.
     *
     * @param idCliente El identificador único del cliente.
     * @param nombre    El nombre del cliente.
     * @param domicilio El domicilio del cliente.
     * @param nif       El NIF (Número de Identificación Fiscal) del cliente.
     * @param email     El email del cliente.
     */
    public ClienteEstandard(Integer idCliente, String nombre, String domicilio, String nif, String email) {
        super(idCliente, nombre, domicilio, nif, email);
    }

    // Getters y setters específicos si los hubiera
    // Dado que esta clase hereda de Cliente, no hay campos adicionales en este momento
    // Si se añaden campos específicos, agregar aquí sus getters y setters

    /**
     * Representación en cadena de la entidad ClienteEstandard.
     *
     * @return Una cadena que representa los detalles del cliente estándar.
     */
    @Override
    public String toString() {
        return "ClienteEstandard{" +
                "idCliente=" + getIdCliente() +
                ", nombre='" + getNombre() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", nif='" + getNif() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
