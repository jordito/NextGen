package local.NextGen.modelo;

import java.math.BigDecimal;

/**
 * Clase que representa un detalle de un pedido, incluyendo información del artículo, cantidad y precio.
 */
public class DetallePedido {
    private int numeroPedido;
    private String codigoArticulo;
    private int cantidad;
    private BigDecimal precioVenta;

    /**
     * Constructor para crear un detalle de pedido.
     *
     * @param numeroPedido El número del pedido al que pertenece este detalle.
     * @param codigoArticulo El código del artículo en este detalle de pedido.
     * @param cantidad La cantidad del artículo en este detalle.
     * @param precioVenta El precio de venta del artículo en este detalle.
     */
    public DetallePedido(int numeroPedido, String codigoArticulo, int cantidad, BigDecimal precioVenta) {
        this.numeroPedido = numeroPedido;
        this.codigoArticulo = codigoArticulo;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * Representación en cadena de un detalle de pedido.
     *
     * @return Una cadena que representa este detalle de pedido.
     */
    @Override
    public String toString() {
        return "DetallePedido{" +
                "numeroPedido=" + numeroPedido +
                ", codigoArticulo='" + codigoArticulo + '\'' +
                ", cantidad=" + cantidad +
                ", precioVenta=" + precioVenta +
                '}';
    }
}
