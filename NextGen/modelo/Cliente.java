package NextGen.modelo;

public abstract class Cliente {
    private String email;
    private String nombre;
    private String direccion;

    public Cliente(String email, String nombre, String direccion) {
        this.email = email;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public abstract String tipoCliente();
    public abstract float calcAnual();
    public abstract float descuentoEnv();

    // Getters and Setters
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

    @Override
    public String toString() {
        return "Cliente{" +
                "email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
