
package local.NextGen.modelo;
import java.util.HashMap;
import java.util.Map;
/**
 * Clase que representa un cliente premium. Hereda de la clase abstracta Cliente.
 */
public abstract class ClientePremium extends local.NextGen.modelo.Cliente {
    private double cuotaAnual;
    private double descuentoEnvio;

    public ClientePremium(int idCliente, String nif, String nombre, String email, String direccion, double cuotaAnual, double descuentoEnvio) {
        super(idCliente, nif, nombre, email, direccion);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
    }

    @Override
    public String tipoCliente() {
        return "Premium";
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
}