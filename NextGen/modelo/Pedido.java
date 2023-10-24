package NextGen.modelo;

import java.util.Date;

public class Pedido {
    private int numeroPedido;
    private Date fechaHora;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private boolean enviado;

    public Pedido(int numeroPedido, Date fechaHora, Cliente cliente, Articulo articulo, int cantidad) {
        this.numeroPedido = numeroPedido;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.enviado = false;
    }

    public boolean pedidoEnviado() {
        return enviado;
    }

    public float precioEnvio() {
        // Lógica para calcular el precio de envío
        return 0;  // Valor de ejemplo
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", fechaHora=" + fechaHora +
                ", NIF Cliente=" + cliente.getNif() +
                ", nombre Cliente=" + cliente.getNombre() +
                ", codigo Articulo=" + articulo.getCodigo() +
                ", descripcion Articulo=" + articulo.getDescripcion() +
                ", cantidad=" + cantidad +
                ", precio Articulo=" + articulo.getPrecio() +
                ", coste Envio=" + precioEnvio() +
                ", precio Total=" + (articulo.getPrecio() * cantidad + precioEnvio()) +
                ", enviado=" + pedidoEnviado() +
                '}';
    }
}
