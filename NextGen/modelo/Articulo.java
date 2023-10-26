package NextGen.modelo;

public class Articulo {
    private String codigo;
    private String descripcion;
    private double precio;
    private double gastosEnvio;
    private int preparacionEnMin;

    public Articulo(String codigo, String descripcion, double precio, double gastosEnvio, int preparacionEnMin) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.gastosEnvio = gastosEnvio;
        this.preparacionEnMin = preparacionEnMin;
    }

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getGastosEnvio() { return gastosEnvio;     }

    public void setGastosEnvio(double gastosEnvio) { this.gastosEnvio = gastosEnvio;  }

    public int getPreparacionEnMin() { return preparacionEnMin;}

    public void setPreparacionEnMin(int preparacionEnMin) {this.preparacionEnMin = preparacionEnMin; }

    @Override
    public String toString() {
        return  "\nCodigo Articulo:      " + codigo +
                "\nDescripción Articulo: " + descripcion +
                "\nPrecio Articulo:      " + precio +
                "\nGastos de Envio:      " + gastosEnvio +
                "\nPreparacion en Min:   " + preparacionEnMin +
                "\n";
    }
}
