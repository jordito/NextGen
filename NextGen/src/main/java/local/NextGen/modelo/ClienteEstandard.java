package local.NextGen.modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.HashMap;
import java.util.Map;
/**
 * Clase que representa un cliente estándar. Hereda de la clase abstracta Cliente.
 */

@Entity
@Table(name = "clientes")
public abstract class ClienteEstandard extends Cliente {

    public ClienteEstandard() {
    }

    public ClienteEstandard(int idCliente, String nombre, String direccion, String nif, String email) {
        super(idCliente, nombre, direccion, nif, email);
    }

    @Override
    public String tipoCliente() {
        return "Estandard";
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
}
