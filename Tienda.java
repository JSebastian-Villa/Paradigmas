import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
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

        List<Producto> productosCaros = productos.stream()
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

        // BiPredicate<T,U>: validar si hay stock suficiente para una compra
        java.util.function.BiPredicate<Producto, Integer> stockSuficiente =
                (producto, cantidad) -> producto.getStock() >= cantidad;

        System.out.println(
                "¿Hay stock suficiente de Mouse para 20 unidades? "
                        + stockSuficiente.test(productos.get(1), 20)
        );

        // BiConsumer<T,U>: actualizar stock después de vender
        java.util.function.BiConsumer<Producto, Integer> venderProducto =
                (producto, cantidad) ->
                        producto.setStock(producto.getStock() - cantidad);

        if (stockSuficiente.test(productos.get(1), 5)) {
            venderProducto.accept(productos.get(1), 5);
        }

        System.out.println(
                "Stock actualizado de Mouse: "
                        + productos.get(1).getStock()
        );

        // UnaryOperator<T>: aumentar precio en 5%
        java.util.function.UnaryOperator<Producto> aumentarPrecio =
                producto -> {
                    producto.setPrecio(producto.getPrecio() * 1.05);
                    return producto;
                };

        List<Producto> productosActualizados = productos.stream()
                .map(aumentarPrecio)
                .collect(Collectors.toList());

        System.out.println("\n=== Productos con aumento del 5% ===");
        productosActualizados.forEach(mostrarProducto);

        // BinaryOperator<T>: sumar precios de dos productos
        java.util.function.BinaryOperator<Double> sumarPrecios =
                Double::sum;

        double sumaDosProductos = sumarPrecios.apply(
                productosActualizados.get(0).getPrecio(),
                productosActualizados.get(1).getPrecio()
        );

        System.out.println(
                "\nSuma de precios de los dos primeros productos: "
                        + sumaDosProductos
        );

        // Runnable: tarea sin retorno
        Runnable tareaResumen =
                () -> System.out.println("\nResumen generado correctamente.");

        tareaResumen.run();

        // Callable<V>: tarea con retorno
        Callable<Double> calcularValorInventario = () ->
                productosActualizados.stream()
                        .map(p -> p.getPrecio() * p.getStock())
                        .reduce(0.0, Double::sum);

        Double valorInventario = calcularValorInventario.call();

        System.out.println(
                "Valor total del inventario: "
                        + valorInventario
        );

        // Resultados adicionales
        System.out.println("\n=== Productos caros (> 500) ===");
        productosCaros.forEach(mostrarProducto);

        System.out.println("\n=== Nombres de productos ===");
        nombres.forEach(System.out::println);
    }
}  