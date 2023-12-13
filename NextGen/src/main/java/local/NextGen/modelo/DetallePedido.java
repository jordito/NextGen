package local.NextGen.modelo;

import java.text.DecimalFormat;

/**
 * Clase que representa un detalle de un pedido, incluyendo información del artículo, cantidad y precio.
 */
public class DetallePedido {
    private int numeroPedido;
    private Articulo articulo;
    private int cantidad;

    /**
     * Constructor para crear un detalle de pedido.
     *
     * @param numeroPedido El número del pedido al que pertenece este detalle.
     * @param articulo     El objeto Articulo en este detalle de pedido.
     * @param cantidad     La cantidad del artículo en este detalle.
     */
    public DetallePedido(int numeroPedido, Articulo articulo, int cantidad) {
        this.numeroPedido = numeroPedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
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

    /**
     * Obtener el precio de venta del artículo en este detalle.
     *
     * @return El precio de venta del artículo.
     */
    public double getPrecioVenta() {
        if (articulo != null) {
            return articulo.getPrecio();
        } else {
            return 0.0;
        }
    }

    /**
     * Representación en cadena de un detalle de pedido.
     *
     * @return Una cadena que representa este detalle de pedido.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return String.format("Código Artículo: %-5s| Descripción Articulo: %-15s| Cantidad: %-5s| Precio Venta: %-5s",
                (articulo != null ? articulo.getCodigo() : ""), articulo.getDescripcion(),  cantidad, df.format(getPrecioVenta()) + "€");
    }
}
