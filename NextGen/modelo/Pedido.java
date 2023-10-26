package NextGen.modelo;

import java.util.Date;
import java.time.format.DateTimeFormatter;

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


    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public boolean pedidoEnviado() {
        return enviado;
    }

    public float precioTotal() {
        return cantidad * articulo.getPrecio() + precioEnvio();
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
