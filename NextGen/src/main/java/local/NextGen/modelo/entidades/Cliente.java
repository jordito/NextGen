package local.NextGen.modelo.entidades;

import javax.persistence.*;

/**
 * Clase Cliente que representa la entidad "Clientes" en la base de datos.
 * Esta clase está mapeada a la tabla "Clientes" y contiene detalles sobre los clientes.
 * Utiliza la estrategia de herencia JOINED para posibles extensiones de esta clase.
 */
@Entity
@Table(name = "Clientes")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String domicilio;

    @Column(nullable = false, unique = true)
    private String nif;

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Constructor por defecto de Cliente.
     */
    public Cliente() {
    }

    /**
     * Constructor de Cliente con todos los campos.
     *
     * @param idCliente El identificador único del cliente.
     * @param nombre    El nombre del cliente.
     * @param domicilio El domicilio del cliente.
     * @param nif       El NIF (Número de Identificación Fiscal) del cliente.
     * @param email     El email del cliente.
     */
    public Cliente(Integer idCliente, String nombre, String domicilio, String nif, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }

    // Getters y setters con comentarios descriptivos para cada campo.

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Representación en cadena de la entidad Cliente.
     *
     * @return Una cadena que representa los detalles del cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
