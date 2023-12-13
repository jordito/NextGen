package local.NextGen.modelo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que representa un pedido realizado por un cliente.
 * Incluye información sobre el pedido, como el número de pedido, la fecha y hora,
 * el cliente que realizó el pedido y los detalles del mismo.
 */
public class Pedido {
    private static int numeroPedido;
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

    public static int getNumeroPedido() {
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
    public Double precioTotal() {
        Double total = 0.0;
        for (DetallePedido detalle : detallesPedido) {
            total += detalle.getPrecioVenta() * detalle.getCantidad();
        }
        return total;
    }
    /**
     * Agrega un detalle al pedido.
     *
     * @param detalle El detalle a agregar.
     */
    public void agregarDetalle(DetallePedido detalle) {
        if (detallesPedido == null) {
            detallesPedido = new ArrayList<>();
        }
        detallesPedido.add(detalle);
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
        detalles.append("\n\u001B[33mDetalles del Pedido:\u001B[0m\n");
        for (DetallePedido detalle : detallesPedido) {
            detalles.append(detalle.toString()).append("\n");
        }

        return String.format("Pedido Número: %-5s| Fecha: %-10s | Cliente: %-10s | Estado: %-8s | Precio Total: %-8s %s",
                numeroPedido, fechaFormato, cliente.getNif(), estadoPedido, df.format(precioTotal()) + "€", detalles);
    }
}
