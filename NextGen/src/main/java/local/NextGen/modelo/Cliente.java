package local.NextGen.modelo;

import javax.persistence.*;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Clase abstracta que representa un cliente con sus atributos básicos.
 */

@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
public abstract class Cliente implements Serializable {
    @Id
    @Column(name = "id_cliente")
    private int idCliente;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "domicilio")
    private String direccion;
    @Column(name = "NIF", length = 9)
    private String nif;
    @Column(name = "email")
    private String email;
    @Column(name = "tipo_cliente")
    private String tipoCliente;

    @OneToOne(mappedBy = "cliente")
    private Pedido pedido;

    public Cliente() {
    }

    /**
     * Constructor para crear un objeto Cliente con los detalles especificados.
     * @param idCliente El ID del cliente.
     * @param nif El NIF del cliente.
     * @param nombre El nombre del cliente.
     * @param email El correo electrónico del cliente.
     * @param direccion La dirección de envío del cliente.
     */
    public Cliente(int idCliente, String nombre, String direccion, String nif, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nif = nif;
        this.email = email;
        this.tipoCliente = "Estandard";
    }

    public Cliente(int idCliente, String nombre, String direccion, String nif, String email, String tipoCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nif = nif;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }

    // Getters y setters

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public abstract String tipoCliente();
    public abstract float calcAnual();
    public abstract float descuentoEnv();

    public static Cliente createCliente(String tipoCliente) {
        if ("Estandard".equals(tipoCliente)) {
            return new ClienteEstandard();
        } else if ("Premium".equals(tipoCliente)) {
            return new ClientePremium();
        }
        return null;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return String.format("ID Cliente: %-5s| Nombre: %-10s| Domicilio: %-15s| NIF: %-12s| Email: %-15s| Cuota Anual: %-5s| Descuento Envío: %-5s",
                     idCliente, nombre, direccion, nif, email, df.format(calcAnual()) + "€", df.format((descuentoEnv() * 100)) + "%");
    }
    public abstract Map<String, Object> toMap();
}
