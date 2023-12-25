package local.NextGen.modelo.entidades;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Clase ClientePremium que extiende de la clase Cliente.
 * Representa una especialización de Cliente, enfocada en clientes premium.
 * Está mapeada a la tabla "ClientesPremium" en la base de datos.
 * Incluye características adicionales como cuota anual y descuento en envío.
 */
@Entity
@Table(name = "ClientesPremium")
public class ClientePremium extends Cliente {
    @Column(name = "cuota_anual", nullable = false)
    private BigDecimal cuotaAnual;

    @Column(name = "descuento_envio", nullable = false)
    private BigDecimal descuentoEnvio;

    /**
     * Constructor por defecto de ClientePremium.
     * Invoca al constructor por defecto de la superclase Cliente.
     */
    public ClientePremium() {
        super();
    }

    /**
     * Constructor de ClientePremium con todos los campos.
     * Incluye los campos específicos de la clase y los de la superclase Cliente.
     *
     * @param idCliente      El identificador único del cliente.
     * @param nombre         El nombre del cliente.
     * @param domicilio      El domicilio del cliente.
     * @param nif            El NIF (Número de Identificación Fiscal) del cliente.
     * @param email          El email del cliente.
     * @param cuotaAnual     La cuota anual para clientes premium.
     * @param descuentoEnvio El descuento en envío para clientes premium.
     */
    public ClientePremium(Integer idCliente, String nombre, String domicilio, String nif, String email, BigDecimal cuotaAnual, BigDecimal descuentoEnvio) {
        super(idCliente, nombre, domicilio, nif, email);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
    }

    // Getters y setters con comentarios descriptivos para los campos adicionales.

    public BigDecimal getCuotaAnual() {
        return cuotaAnual;
    }

    public void setCuotaAnual(BigDecimal cuotaAnual) {
        this.cuotaAnual = cuotaAnual;
    }

    public BigDecimal getDescuentoEnvio() {
        return descuentoEnvio;
    }

    public void setDescuentoEnvio(BigDecimal descuentoEnvio) {
        this.descuentoEnvio = descuentoEnvio;
    }

    /**
     * Representación en cadena de la entidad ClientePremium.
     *
     * @return Una cadena que representa los detalles del cliente premium.
     */
    @Override
    public String toString() {
        return "ClientePremium{" +
                "idCliente=" + getIdCliente() +
                ", nombre='" + getNombre() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", nif='" + getNif() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cuotaAnual=" + cuotaAnual +
                ", descuentoEnvio=" + descuentoEnvio +
                '}';
    }
}
