package NextGen.modelo;
import java.util.Date;

public class Pedido {
    // Atributos
    private int numeroPedido;
    private Date fechaHora;
    private Cliente cliente;
    private Articulo articulo;

    // Constructor
    public Pedido(int numeroPedido, Date fechaHora, Cliente cliente, Articulo articulo) {
        this.numeroPedido = numeroPedido;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.articulo = articulo;
    }

    // Métodos adicionales
    public boolean pedidoEnviado() {
        // Implementación
        return false;
    }

    public float precioEnvio() {
        // Implementación
        return 0.0f;
    }

    // Getters y Setters
    // ...

    // Método toString
    @Override
    public String toString() {
        // Implementación
        return "";
    }
}
