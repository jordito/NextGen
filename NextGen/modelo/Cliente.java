package NextGen.modelo;

public abstract class Cliente {

    private string nif;
    private String nombre;
    private String email;
    private String direccion;

    public Cliente(String nif, String nombre, String email, String direccion) {
        this.nif = nif;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
    }

    // Getters and Setters
    public string getNif() { return nif; }
    public void setNif(string nif) { this.nif = nif;}
    public String getNombre() { return nombre;}
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email; }
    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public abstract String tipoCliente();
    public abstract float calcAnual();
    public abstract float descuentoEnv();
    @Override

    public String toString() {
        return  "\nCliente:               " + nif +
                "\nNombre Cliente:        " + nombre +
                "\nEmail:                 " + email +
                "\nDirección de envio:    " + direccion +
                "\nTipo de Cliente:       " + tipoCliente() +
                "\nCuota anual:           " + df.format(calcAnual()) + "€" +
                "\nDescuento en envío:    " + df.format((descuentoEnv() * 100)) + "%" +
                "\n";
    }
}
