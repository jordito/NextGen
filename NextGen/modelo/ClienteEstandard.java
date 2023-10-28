package NextGen.modelo;

public class ClienteEstandard extends Cliente {

    public ClienteEstandard(String email, String nombre, String direccion, String nif) {
        super(email, nombre, direccion, nif);
    }


    @Override
    public String tipoCliente() {
        return "Estandard";
    }

    @Override
    public float calcAnual() {
        // Lógica para calcular la cuota anual
        return 0;  // Valor de ejemplo
    }

    @Override
    public float descuentoEnv() {
        // Lógica para calcular el descuento en gastos de envío
        return 0;  // Valor de ejemplo
    }
}
