package local.NextGen.modelo.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entidad que representa un pedido en el sistema.
 * Mapea a la tabla "Pedidos" en la base de datos.
 * Contiene información como el número del pedido, el cliente asociado, la fecha y hora del pedido,
 * el estado del pedido y los detalles del pedido.
 */
@Entity
@Table(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_pedido")
    private Integer numeroPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_hora_pedido", nullable = false)
    private LocalDateTime fechaHoraPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false)
    private EstadoPedido estadoPedido = EstadoPedido.Pendiente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetallePedido> detallePedidos;

    /**
     * Constructor por defecto.
     * Inicializa el estado del pedido como 'Pendiente'.
     */
    public Pedido() {
        this.fechaHoraPedido = LocalDateTime.now(); // Establece la fecha y hora actuales. El estadoPedido se inicializa por defecto a Pendiente.
    }

    /**
     * Constructor para crear un pedido con todos sus atributos.
     *
     * @param numeroPedido    El número único del pedido.
     * @param cliente         El cliente asociado al pedido.
     * @param fechaHoraPedido La fecha y hora en que se realizó el pedido.
     * @param estadoPedido    El estado actual del pedido.
     */
    public Pedido(Integer numeroPedido, Cliente cliente, LocalDateTime fechaHoraPedido, EstadoPedido estadoPedido) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.fechaHoraPedido = fechaHoraPedido;
        this.estadoPedido = estadoPedido;
    }

    // Getters y setters

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaHoraPedido() {
        return fechaHoraPedido;
    }

    public void setFechaHoraPedido(LocalDateTime fechaHoraPedido) {
        this.fechaHoraPedido = fechaHoraPedido;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Set<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(Set<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", cliente=" + (cliente != null ? cliente.getIdCliente() : null) +
                ", fechaHoraPedido=" + fechaHoraPedido +
                ", estadoPedido=" + estadoPedido.toString() +
                '}';
    }

    /**
     * Enumeración de los posibles estados de un pedido.
     */
    public enum EstadoPedido {
        Pendiente("Pendiente"),
        Enviado("Enviado");
        // Otros estados pueden ser añadidos en el futuro.

        private final String nombre;

        private EstadoPedido(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }


}
