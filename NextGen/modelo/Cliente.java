package NextGen.modelo;

public abstract class Cliente {
    private String email;
    private String nombre;
    private String direccion;
    private String nif;  // Atributo NIF agregado

    public Cliente(String email, String nombre, String direccion, String nif) {
        this.email = email;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nif = nif;
    }

    public abstract String tipoCliente();
    public abstract float calcAnual();
    public abstract float descuentoEnv();

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Nuevo getter para el NIF
    public String getNif() {
        return nif;
    }

    // Nuevo setter para el NIF
    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", nif='" + nif + '\'' +
                '}';
    }
}

