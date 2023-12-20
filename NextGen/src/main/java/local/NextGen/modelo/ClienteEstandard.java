package local.NextGen.modelo;

import javax.persistence.*;
import java.util.Map;

/**
 * Clase que representa un cliente estándar. Hereda de la clase abstracta Cliente.
 */

@Entity
@Table(name = "clientesestandard")
@PrimaryKeyJoinColumn(name = "id_cliente")
@DiscriminatorValue("Estandard")
public class ClienteEstandard extends Cliente {

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    public ClienteEstandard() {
    }

    public ClienteEstandard(int idCliente, String nombre, String direccion, String nif, String email) {
        super(idCliente, nombre, direccion, nif, email);
        this.tipoCliente = "Estandard";
    }

    @Override
    public String tipoCliente() {
        return "Estandard";
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
        return 0;
    }

    @Override
    public float descuentoEnv() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + " \u001B[33m(Estandard)\u001B[0m";
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }
}
