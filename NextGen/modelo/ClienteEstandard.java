package NextGen.modelo;

public class ClienteEstandard extends Cliente {

    public ClienteEstandard(String nif, String nombre, String email, String direccion) {
        super(nif, nombre, email, direccion);
    }

    //Metodos Abstractos
    public String tipoCliente() {
        return "Estandard";
    }
    public float calcAnual() {return 0; }
    public float descuentoEnv() {return 0; }

    //toString
    @Override
    public String toString() {
        return super.toString();
    }
}
