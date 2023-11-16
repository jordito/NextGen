package local.NextGen.modelo;

/**
 * Clase que representa un cliente estándar. Hereda de la clase abstracta Cliente.
 */
public class ClienteEstandard extends Cliente {

    public ClienteEstandard(int idCliente, String nif, String nombre, String email, String direccion) {
        super(idCliente, nif, nombre, email, direccion);
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
        return super.toString() + " (Estandard)";
    }
}
