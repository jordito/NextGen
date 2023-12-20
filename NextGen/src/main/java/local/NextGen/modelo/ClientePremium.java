package local.NextGen.modelo;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "clientespremium")
@PrimaryKeyJoinColumn(name = "id_cliente")
@DiscriminatorValue("Premium")
/**
 * Clase que representa un cliente premium. Hereda de la clase abstracta Cliente.
 */
public class ClientePremium extends Cliente {

    @Column(name = "cuota_anual")
    private double cuotaAnual;

    @Column(name = "descuento_envio")
    private double descuentoEnvio;

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    public ClientePremium() {
    }

    public ClientePremium(int idCliente, String nombre, String direccion, String nif, String email, double cuotaAnual, double descuentoEnvio) {
        super(idCliente, nombre, direccion, nif, email);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
        this.tipoCliente = "Premium";
    }

    @Override
    public String tipoCliente() {
        return "Premium";
    }

    @Override
    public String getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Override
    public float calcAnual() {
        return (float) cuotaAnual;
    }

    @Override
    public float descuentoEnv() {
        return (float) descuentoEnvio;
    }

    public double getCuotaAnual() {
        return cuotaAnual;
    }

    public void setCuotaAnual(double cuotaAnual) {
        this.cuotaAnual = cuotaAnual;
    }

    public double getDescuentoEnvio() {
        return descuentoEnvio;
    }

    public void setDescuentoEnvio(double descuentoEnvio) {
        this.descuentoEnvio = descuentoEnvio;
    }

    @Override
    public String toString() {
        return super.toString() + " \u001B[34m(Premium)\u001B[0m";
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }
}
