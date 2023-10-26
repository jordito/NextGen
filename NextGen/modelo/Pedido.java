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
        return "Pedido: " +
                "Numero Pedido=" + numeroPedido +
                ", Fecha y Hora=" + fechaHora +
                ", NIF Cliente=" + cliente.getNif() +
                ", Nombre Cliente=" + cliente.getNombre() +
                ", Codigo Articulo=" + articulo.getCodigo() +
                ", Descripcion Articulo=" + articulo.getDescripcion() +
                ", Cantidad=" + cantidad +
                ", Precio Articulo=" + articulo.getPrecio() +
                ", Coste Envio=" + precioEnvio() +
                ", Precio Total=" + (articulo.getPrecio() * cantidad + precioEnvio()) +
                ", Enviado: " + pedidoEnviado() +
                '}';
    }
}
