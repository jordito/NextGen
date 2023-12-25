package local.NextGen.modelo.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa un detalle específico de un pedido en el sistema.
 * Mapea a la tabla "DetallePedido" en la base de datos y contiene información de cada línea de pedido.
 */
@Entity
@Table(name = "DetallePedido")
public class DetallePedido {
    @EmbeddedId
    private DetallePedidoId id;

    @ManyToOne
    @MapsId("numeroPedido")
    @JoinColumn(name = "numero_pedido")
    private Pedido pedido;

    @ManyToOne
    @MapsId("codigoArticulo")
    @JoinColumn(name = "codigo_articulo")
    private Articulo articulo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_venta", nullable = false)
    private BigDecimal precioVenta;

    /**
     * Constructor por defecto necesario para Hibernate.
     */
    public DetallePedido() {
    }

    /**
     * Constructor para crear un detalle de pedido con todos sus atributos.
     *
     * @param id          El identificador compuesto del detalle del pedido.
     * @param pedido      El pedido asociado a este detalle.
     * @param articulo    El artículo asociado a este detalle.
     * @param cantidad    La cantidad del artículo.
     * @param precioVenta El precio de venta del artículo.
     */
    public DetallePedido(DetallePedidoId id, Pedido pedido, Articulo articulo, Integer cantidad, BigDecimal precioVenta) {
        this.id = id;
        this.pedido = pedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    // Getters y setters

    public DetallePedidoId getId() {
        return id;
    }

    public void setId(DetallePedidoId id) {
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", pedido=" + (pedido != null ? pedido.getNumeroPedido() : null) +
                ", articulo=" + (articulo != null ? articulo.getCodigo() : null) +
                ", cantidad=" + cantidad +
                ", precioVenta=" + precioVenta +
                '}';
    }

    /**
     * Clase para representar la clave primaria compuesta de DetallePedido.
     */
    @Embeddable
    public static class DetallePedidoId implements Serializable {
        @Column(name = "numero_pedido")
        private Integer numeroPedido;

        @Column(name = "codigo_articulo")
        private String codigoArticulo;

        public DetallePedidoId() {
        }

        public DetallePedidoId(Integer numeroPedido, String codigoArticulo) {
            this.numeroPedido = numeroPedido;
            this.codigoArticulo = codigoArticulo;
        }

        // Getters

        public Integer getNumeroPedido() {
            return numeroPedido;
        }

        public String getCodigoArticulo() {
            return codigoArticulo;
        }

        // Setters

        public void setNumeroPedido(Integer numeroPedido) {
            this.numeroPedido = numeroPedido;
        }

        public void setCodigoArticulo(String codigoArticulo) {
            this.codigoArticulo = codigoArticulo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DetallePedidoId that = (DetallePedidoId) o;
            return Objects.equals(numeroPedido, that.numeroPedido) &&
                    Objects.equals(codigoArticulo, that.codigoArticulo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroPedido, codigoArticulo);
        }

        @Override
        public String toString() {
            return "DetallePedidoId{" +
                    "numeroPedido=" + numeroPedido +
                    ", codigoArticulo='" + codigoArticulo + '\'' +
                    '}';
        }
    }
}