package NextGen.modelo;

public class ClientePremium extends Cliente {
    //Atributos
    private double cuota;
    private double descuento;
    public ClientePremium(String nif, String nombre, String email, String direccion) {
        super(nif, nombre, email, direccion);
        this.cuota = calcAnual();
        this.descuento = descuentoEnv();
    }
    //Getters and Setters
    public double getCuota() { return cuota;}
    public void setCuota(double cuota) { this.cuota = cuota;}
    public double getDescuento() {return descuento; }
    public void setDescuento(double descuento) {this.descuento = descuento;}

    //Metodos abstractos
    public String tipoCliente() {
        return "Premium";
    }
    public float calcAnual() { return 30; }
    public float descuentoEnv() { return 0.2; }

    @Override
    public String toString() {
        return super.toString() +
                "\nSu cuota anual es de" + calcAnual() + "€" +
                "\nSe le aplica un descuento de " + (descuentoEnv() * 100) + "%";
    }
}
