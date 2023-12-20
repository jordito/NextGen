package local.NextGen.modelo;

import javax.persistence.*;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Clase que representa un detalle de un pedido, incluyendo información del artículo, cantidad y precio.
 */
@Entity
@Table(name = "detallepedido")
public class DetallePedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "numero_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "codigo_articulo")
    private Articulo articulo;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio_venta")
    private double precioVenta;

    public DetallePedido() {
    }

    /**
     * Constructor para crear un detalle de pedido.
     *
     * @param id           El ID del pedido al que pertenece este detalle.
     * @param pedido       El pedido al que pertenece este detalle.
     * @param articulo     El objeto Articulo en este detalle de pedido.
     * @param cantidad     La cantidad del artículo en este detalle.
     */
    public DetallePedido(int id, Pedido pedido, Articulo articulo, int cantidad) {
        this.id = id;
        this.pedido = pedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precioVenta = articulo.getPrecio();
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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

    public void setPrecioVenta(double precioVenta) {
        if (articulo != null) {
            this.precioVenta = precioVenta;
        } else {
            this.precioVenta = 0.0;
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
