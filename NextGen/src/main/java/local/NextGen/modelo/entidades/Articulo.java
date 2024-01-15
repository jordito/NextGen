package local.NextGen.modelo.entidades;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Clase Articulo que representa la entidad "Articulos" en la base de datos.
 * Esta clase está mapeada a la tabla "Articulos" y contiene detalles sobre los artículos.
 */
@Entity
@Table(name = "Articulos")
public class Articulo {
    @Id
    private String codigo;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "precio_venta", nullable = false)
    private BigDecimal precioVenta;

    @Column(name = "gastos_envio", nullable = false)
    private BigDecimal gastosEnvio;

    @Column(name = "tiempo_preparacion", nullable = false)
    private Integer tiempoPreparacion;

    /**
     * Constructor por defecto de Articulo.
     */
    public Articulo() {
    }

    /**
     * Constructor de Articulo con todos los campos.
     *
     * @param codigo           El código único del artículo.
     * @param descripcion      La descripción del artículo.
     * @param precioVenta      El precio de venta del artículo.
     * @param gastosEnvio      Los gastos de envío asociados con el artículo.
     * @param tiempoPreparacion El tiempo de preparación necesario para el artículo.
     */
    public Articulo(String codigo, String descripcion, BigDecimal precioVenta, BigDecimal gastosEnvio, Integer tiempoPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    // Getters y setters con comentarios descriptivos para cada campo.

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(BigDecimal gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public Integer getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(Integer tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    /**
     * Representación en cadena de la entidad Articulo.
     *
     * @return Una cadena que representa los detalles del artículo.
     */
    @Override
    public String toString() {
        return "Articulo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioVenta=" + precioVenta +
                ", gastosEnvio=" + gastosEnvio +
                ", tiempoPreparacion=" + tiempoPreparacion +
                '}';
    }
}
