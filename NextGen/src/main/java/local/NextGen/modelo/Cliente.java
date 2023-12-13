package local.NextGen.modelo;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Clase abstracta que representa un cliente con sus atributos básicos.
 */
public abstract class Cliente {
    private int idCliente;
    private String nif;
    private String nombre;
    private String email;
    private String direccion;

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

    public abstract String tipoCliente();
    public abstract float calcAnual();
    public abstract float descuentoEnv();

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return String.format("ID Cliente: %-5s| Nombre: %-10s| Domicilio: %-15s| NIF: %-12s| Email: %-15s| Cuota Anual: %-5s| Descuento Envío: %-5s",
                     idCliente, nombre, direccion, nif, email, df.format(calcAnual()) + "€", df.format((descuentoEnv() * 100)) + "%");
    }
    public abstract Map<String, Object> toMap();
}
