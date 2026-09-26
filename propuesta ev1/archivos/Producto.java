public abstract class Producto {

    private String nombre;
    private int precioBase;
    private int stock;

    public Producto(String nombre, int precioBase, int stock) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getPrecioBase() {
        return this.precioBase;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public abstract int calcularPrecioFinal();

    public String mostrarInfo() {
        return "Producto: " + this.nombre +
               " | Base: $" + this.precioBase +
               " | Final: $" + calcularPrecioFinal() +
               " | Stock: " + this.stock;
    }
}