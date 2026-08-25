import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Producto {
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;

    public Producto(String nombre, double precio, int stock, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}

public class Tienda {

    public static void main(String[] args) throws Exception {

        List<Producto> productos = new ArrayList<>(Arrays.asList(
                new Producto("Laptop", 3500.00, 8, "Tecnologia"),
                new Producto("Mouse", 80.0, 25, "Tecnologia"),
                new Producto("Teclado", 150.0, 10, "Tecnologia"),
                new Producto("Silla",600.0, 4, "Hogar"),
                new Producto("Escritorio", 1200.0, 3, "Hogar")
        ));

        // Supplier<T>: genera un producto nuevo
        java.util.function.Supplier<Producto> proveedorProducto =
                () -> new Producto("monitor", 900.0, 6, "Tecnologia");
                
        Producto nuevoProducto = proveedorProducto.get();
        productos.add(nuevoProducto);

        //Predicate<T>: filtra productos caros
        java.util.function.Predicate<Producto> esCaro = p -> p.getPrecio() > 500;

        List<Producto> productoCaros = productos.stream()
                .filter(esCaro)
                .collect(Collectors.toList());
        
        // Function<T,R>: obtener nombre del producto
        java.util.function.Function<Producto, String> obtenerNombre = Producto::getNombre;

        List<String> nombres = productos.stream()
                .map(obtenerNombre)
                .collect(Collectors.toList());
        
        // Consumer<T>: mostrar productos
        java.util.function.Consumer<Producto> mostrarProducto = p -> System.out.println("Producto: " + p);

        System.out.println("=== Lista de productos ===");
        productos.forEach(mostrarProducto);

        // BiFunction <T,U,R>: calcular precio total con descuento
        java.util.function.BiFunction<Double, Double, Double> aplicarDescuento = (precio, descuento) -> precio - (precio * descuento / 100);

        double precioFinalLaptop = aplicarDescuento.apply(3500.0, 10.0);
        System.out.println("\nPrecio final de la Laptop con 10% de descuento: " + precioFinalLaptop);
    }
}