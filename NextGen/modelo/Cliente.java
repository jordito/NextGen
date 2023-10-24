package NextGen.modelo;

public abstract class Cliente {
    // Atributos
    private String email;
    private String nombre;
    private String direccion;

    // Constructor
    public Cliente(String email, String nombre, String direccion) {
        this.email = email;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    // Métodos abstractos
    public abstract String tipoCliente();
    public abstract float calcAnual();
    public abstract float descuentoEnv();

    // Getters y Setters
    // ...

    // Método toString
    @Override
    public String toString() {
        return "Cliente: " + email + ", Nombre: " + nombre + ", Direccion: " + direccion;
    }
}

// Clases heredadas: ClienteEstandard y ClientePremium
// ...
