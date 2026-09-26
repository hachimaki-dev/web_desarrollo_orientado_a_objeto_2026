public class ProductoDigital extends Producto {

    private int descuento;
    private String plataforma;

    public ProductoDigital(String nombre, int precioBase, int stock, int descuento, String plataforma) {
        super(nombre, precioBase, stock);
        this.descuento = descuento;
        this.plataforma = plataforma;
    }

    public int getDescuento() {
        return this.descuento;
    }

    public String getPlataforma() {
        return this.plataforma;
    }

    @Override
    public int calcularPrecioFinal() {
        int montoDescuento = (getPrecioBase() * this.descuento) / 100;
        return getPrecioBase() - montoDescuento;
    }

    @Override
    public String mostrarInfo() {
        return "Digital [" + this.plataforma + "] | " + getNombre() +
               " | Base: $" + getPrecioBase() +
               " | Desc: " + this.descuento + "%" +
               " | Final: $" + calcularPrecioFinal() +
               " | Stock: " + getStock();
    }
}