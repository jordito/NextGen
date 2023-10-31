package NextGen.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;

/**
 * Clase que representa un pedido realizado por un cliente.
 */
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

    public int getNumeroPedido() { return numeroPedido; }

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

    public float precioTotal() {
        return (float) (cantidad * articulo.getPrecio() + precioEnvio());
    }

    public boolean pedidoEnviado() {
        LocalDateTime horaPreparacion = LocalDateTime.now().plusMinutes(articulo.getPreparacionEnMin());
        LocalDateTime horaActual = LocalDateTime.now();
        return horaActual.isAfter(horaPreparacion);
    }

    public float precioEnvio() {
        float descuento = cliente.descuentoEnv();
        return (float) (articulo.getGastosEnvio() * (1 - descuento));
    }

    @Override
    public String toString() {
        String separator = " | ";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return String.format("%-17s" + separator + "%-22s" + separator + "%-11s" + separator + "%-23s" + separator + "%-16s" + separator + "%-22s" + separator + "%-8s" + separator + "%-15s" + separator + "%-13s" + separator + "%-7s",
                numeroPedido, dateFormat.format(fechaHora), cliente.getNif(), cliente.getNombre(), articulo.getCodigo(), articulo.getDescripcion(), cantidad, articulo.getPrecio() + "€", precioEnvio() + "€", precioTotal() + "€", enviado);
    }
}
