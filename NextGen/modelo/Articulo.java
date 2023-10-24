package NextGen.modelo;

public class Articulo {
    // Atributos
    private String id;
    private String descripcion;
    private double precio;

    // Constructor
    public Articulo(String id, String descripcion, double precio) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters
    // ...

    // Método toString
    @Override
    public String toString() {
        return "Articulo: " + id + ", Descripcion: " + descripcion + ", Precio: " + precio;
    }
}
