package local.NextGen.modelo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un pedido realizado por un cliente.
 * Incluye información sobre el pedido, como el número de pedido, la fecha y hora,
 * el cliente que realizó el pedido y los detalles del mismo.
 */
public class Pedido {
    private int numeroPedido;
    private Date fechaHora;
    private Cliente cliente;
    private boolean enviado;
    private List<DetallePedido> detallesPedido;

    /**
     * Constructor para crear un nuevo pedido.
     * @param numeroPedido Número del pedido.
     * @param fechaHora Fecha y hora del pedido.
     * @param cliente Cliente que realiza el pedido.
     * @param detallesPedido Lista de detalles del pedido.
     */
    public Pedido(int numeroPedido, Date fechaHora, Cliente cliente, List<DetallePedido> detallesPedido) {
        this.numeroPedido = numeroPedido;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.detallesPedido = detallesPedido;
        this.enviado = false;
    }

    // Getters y setters

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

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    /**
     * Calcula el precio total del pedido sumando los precios de todos los detalles.
     * @return Precio total del pedido.
     */
    public BigDecimal precioTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedido detalle : detallesPedido) {
            total = total.add(detalle.getPrecioVenta().multiply(new BigDecimal(detalle.getCantidad())));
        }
        return total;
    }

    /**
     * Devuelve una representación en cadena del pedido, incluyendo todos los detalles.
     * @return Representación en cadena del pedido.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fechaFormato = dateFormat.format(fechaHora);
        String estadoPedido = isEnviado() ? "Enviado" : "Pendiente";

        StringBuilder detalles = new StringBuilder();
        for (DetallePedido detalle : detallesPedido) {
            detalles.append(detalle.toString()).append("\n");
        }

        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", fechaHora=" + fechaFormato +
                ", clienteNIF=" + cliente.getNif() +
                ", clienteNombre=" + cliente.getNombre() +
                ", detallesPedido=\n" + detalles +
                ", precioTotal=" + df.format(precioTotal()) + "€" +
                ", estado='" + estadoPedido + '\'' +
                '}';
    }
}
