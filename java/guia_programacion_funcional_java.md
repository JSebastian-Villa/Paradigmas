# Guía de exposición — Programación funcional en Java

## 1. ¿Qué hace el algoritmo en general?

El programa simula el funcionamiento de una **tienda**.

Tiene una lista de productos y realiza diferentes operaciones usando **programación funcional en Java**:

1. Crea productos.
2. Agrega un producto nuevo.
3. Busca productos caros.
4. Obtiene los nombres de los productos.
5. Muestra los productos.
6. Calcula un descuento.
7. Comprueba si hay suficiente stock.
8. Actualiza el stock después de una venta.
9. Aumenta los precios un 5%.
10. Suma precios.
11. Ejecuta una tarea.
12. Calcula el valor total del inventario.

La idea es demostrar diferentes **interfaces funcionales** de Java.

---

## 2. ¿Qué es una función aquí?

En programación funcional, una función es básicamente una operación que podemos **guardar en una variable y luego ejecutar**.

Por ejemplo:

```java
p -> p.getPrecio() > 500
```

Esto significa:

> "Recibe un producto `p` y dime si su precio es mayor a 500."

Podemos guardar esa función:

```java
Predicate<Producto> esCaro = p -> p.getPrecio() > 500;
```

Y después utilizarla.

---

# 3. Cada función utilizada

## 3.1 Supplier

```java
Supplier<Producto> proveedorProducto =
        () -> new Producto("Monitor", 900.0, 6, "Tecnología");
```

### ¿Qué hace?

**Crea y devuelve un producto.**

No recibe ningún dato, simplemente genera un producto.

Luego:

```java
Producto nuevoProducto = proveedorProducto.get();
```

ejecuta la función.

Después:

```java
productos.add(nuevoProducto);
```

lo agrega a la lista.

### Para recordar

> **Supplier = produce algo.**

Ejemplo: "Dame un producto nuevo."

---

## 3.2 Predicate

```java
Predicate<Producto> esCaro =
        p -> p.getPrecio() > 500;
```

### ¿Qué hace?

Comprueba una condición y devuelve:

- `true`
- `false`

En este caso:

> "¿El producto cuesta más de 500?"

Después:

```java
.filter(esCaro)
```

utiliza esa función para quedarse solamente con los productos caros.

Ejemplo:

```text
Laptop → 3500 → true
Mouse → 80 → false
Silla → 600 → true
```

### Para recordar

> **Predicate = pregunta algo y responde verdadero o falso.**

---

## 3.3 Function

```java
Function<Producto, String> obtenerNombre =
        Producto::getNombre;
```

### ¿Qué hace?

Recibe un `Producto` y devuelve un `String`.

En este caso:

> "Dame el nombre del producto."

Después:

```java
.map(obtenerNombre)
```

aplica esa función a todos los productos.

Por ejemplo:

```text
Producto Laptop → "Laptop"
Producto Mouse → "Mouse"
Producto Silla → "Silla"
```

### Para recordar

> **Function = recibe algo y lo transforma en otra cosa.**

---

## 3.4 Consumer

```java
Consumer<Producto> mostrarProducto =
        p -> System.out.println("Producto: " + p);
```

### ¿Qué hace?

Recibe un producto y realiza una acción.

En este caso:

> "Muestra el producto por pantalla."

Se utiliza aquí:

```java
productos.forEach(mostrarProducto);
```

Es decir:

> "Para cada producto, ejecuta `mostrarProducto`."

### Para recordar

> **Consumer = recibe algo y hace una acción, pero no devuelve nada.**

---

## 3.5 BiFunction

```java
BiFunction<Double, Double, Double> aplicarDescuento =
        (precio, descuento) ->
                precio - (precio * descuento / 100);
```

El prefijo **Bi** significa que trabaja con **dos entradas**.

Recibe:

```text
precio
descuento
```

y devuelve:

```text
precio final
```

Por ejemplo:

```java
aplicarDescuento.apply(3500.0, 10.0);
```

Hace:

```text
3500 - (3500 × 10 / 100)
= 3150
```

### Para recordar

> **BiFunction = recibe dos valores y devuelve un resultado.**

---

## 3.6 BiPredicate

```java
BiPredicate<Producto, Integer> stockSuficiente =
        (producto, cantidad) ->
                producto.getStock() >= cantidad;
```

Es parecido a `Predicate`, pero recibe **dos datos**.

Recibe:

```text
Producto
Cantidad
```

Y responde:

```text
true / false
```

Por ejemplo:

```java
stockSuficiente.test(productos.get(1), 20);
```

Pregunta:

> "¿El Mouse tiene al menos 20 unidades?"

Tiene 25 inicialmente, entonces:

```text
true
```

### Para recordar

> **BiPredicate = hace una pregunta con dos datos y responde true/false.**

---

## 3.7 BiConsumer

```java
BiConsumer<Producto, Integer> venderProducto =
        (producto, cantidad) ->
                producto.setStock(
                        producto.getStock() - cantidad
                );
```

Recibe dos datos:

```text
Producto
Cantidad
```

Y realiza una acción.

En este caso:

> "Resta del stock la cantidad vendida."

Por ejemplo:

```java
venderProducto.accept(productos.get(1), 5);
```

Mouse:

```text
25 - 5 = 20
```

Por eso después aparece:

```text
Stock actualizado de Mouse: 20
```

### Para recordar

> **BiConsumer = recibe dos datos y realiza una acción.**

---

## 3.8 UnaryOperator

```java
UnaryOperator<Producto> aumentarPrecio =
        producto -> {
            producto.setPrecio(producto.getPrecio() * 1.05);
            return producto;
        };
```

**Unary** significa que recibe **un solo dato**.

Recibe un producto y devuelve un producto.

Lo que hace es:

> "Aumentar el precio del producto un 5%."

Por ejemplo:

```text
Laptop
3500 × 1.05
= 3675
```

Después:

```java
.map(aumentarPrecio)
```

aplica el aumento a todos los productos.

### Para recordar

> **UnaryOperator = recibe un valor y devuelve otro del mismo tipo.**

---

## 3.9 BinaryOperator

```java
BinaryOperator<Double> sumarPrecios =
        Double::sum;
```

**Binary** significa que recibe **dos valores del mismo tipo** y devuelve otro del mismo tipo.

Aquí:

```text
Double + Double → Double
```

Por ejemplo:

```java
sumarPrecios.apply(
        productos.get(0).getPrecio(),
        productos.get(1).getPrecio()
);
```

Suma:

```text
Laptop = 3675
Mouse = 84

3675 + 84 = 3759
```

### Para recordar

> **BinaryOperator = recibe dos valores del mismo tipo y devuelve uno del mismo tipo.**

---

## 3.10 Runnable

```java
Runnable tareaResumen =
        () -> System.out.println(
                "\nResumen generado correctamente."
        );
```

### ¿Qué hace?

Ejecuta una tarea, pero **no recibe datos ni devuelve un resultado**.

Se ejecuta con:

```java
tareaResumen.run();
```

Simplemente imprime:

```text
Resumen generado correctamente.
```

### Para recordar

> **Runnable = ejecuta una tarea sin devolver un resultado.**

---

## 3.11 Callable

```java
Callable<Double> calcularValorInventario =
        () -> productos.stream()
                .map(p -> p.getPrecio() * p.getStock())
                .reduce(0.0, Double::sum);
```

Es parecido a `Runnable`, pero tiene una diferencia importante:

**Callable sí devuelve un resultado.**

Aquí calcula:

```text
precio × stock
```

para cada producto y después suma todos los resultados.

Por ejemplo:

```text
Laptop:     3675 × 8  = 29400
Mouse:        84 × 20 = 1680
Teclado:   157.5 × 10 = 1575
...
```

Y finalmente obtiene:

```text
46860
```

Se ejecuta con:

```java
calcularValorInventario.call();
```

### Para recordar

> **Callable = ejecuta una tarea y devuelve un resultado.**

---

# 4. Las funciones resumidas

| Función | ¿Qué hace? | Fácil de recordar |
|---|---|---|
| `Supplier` | Genera un producto | **Produce** |
| `Predicate` | Comprueba una condición | **Pregunta** |
| `Function` | Transforma un dato | **Transforma** |
| `Consumer` | Realiza una acción | **Consume/actúa** |
| `BiFunction` | Recibe 2 datos y devuelve 1 | **2 → 1** |
| `BiPredicate` | Comprueba condición con 2 datos | **Pregunta con 2** |
| `BiConsumer` | Acción usando 2 datos | **Actúa con 2** |
| `UnaryOperator` | Modifica un dato y devuelve el mismo tipo | **1 → 1** |
| `BinaryOperator` | Opera con 2 datos del mismo tipo | **2 → 1** |
| `Runnable` | Ejecuta una tarea sin resultado | **Hace** |
| `Callable` | Ejecuta una tarea y devuelve resultado | **Hace + devuelve** |

---

# 5. Las partes más importantes del paradigma funcional

Hay tres operaciones especialmente importantes cuando trabajamos con `Streams`:

## filter

```java
.filter(esCaro)
```

> **Selecciona** los productos que cumplen una condición.

## map

```java
.map(obtenerNombre)
```

> **Transforma** cada producto en otra cosa, en este caso su nombre.

## reduce

```java
.reduce(0.0, Double::sum)
```

> **Combina** muchos valores para obtener un único resultado, en este caso el valor total del inventario.

### Frase para recordar

> **Filter selecciona, map transforma y reduce combina.**

---

# 6. Guion corto para la exposición

## Introducción

> "Nuestro ejemplo representa una tienda y lo usamos para demostrar cómo funciona la programación funcional en Java. En lugar de centrarnos únicamente en objetos y estados, utilizamos funciones para realizar operaciones sobre los productos."

## Tecnologías utilizadas

> "Elegimos Java porque permite trabajar con programación orientada a objetos, pero también incorpora características de programación funcional, especialmente desde Java 8."

> "Utilizamos lambdas porque permiten escribir funciones de manera más sencilla y evitar crear métodos o clases innecesarias."

> "También usamos Streams, porque nos permiten procesar colecciones de forma funcional. Por ejemplo, podemos filtrar productos o transformar una lista sin tener que utilizar ciclos for tradicionales."

> "Utilizamos las interfaces funcionales de Java, como Predicate, Function, Consumer y Supplier, porque cada una representa un tipo diferente de operación y nos permite demostrar claramente cómo se aplica este paradigma."

> "No usamos otro lenguaje como Haskell o Lisp porque el objetivo era demostrar el paradigma funcional dentro de un lenguaje que también soporta programación orientada a objetos. Java nos permite combinar ambos enfoques y resulta más familiar para el desarrollo de aplicaciones convencionales."

## ¿Qué demuestra el código?

> "Primero tenemos Supplier, que genera un producto nuevo."

> "Después utilizamos Predicate para determinar qué productos son caros. Con filter podemos quedarnos únicamente con los productos que cumplen esa condición."

> "Con Function transformamos los productos en sus nombres utilizando map."

> "Consumer se utiliza para realizar una acción sobre cada producto, en este caso mostrarlo por pantalla."

> "También tenemos funciones que reciben dos valores, como BiFunction, que calcula un descuento, y BiPredicate, que comprueba si existe suficiente stock."

## Otras funciones

> "BiConsumer actualiza el stock después de una venta. UnaryOperator aumenta el precio de un producto y BinaryOperator permite sumar los precios de dos productos."

> "Finalmente, usamos Runnable para ejecutar una tarea que no devuelve ningún resultado y Callable para realizar una tarea que sí devuelve un resultado, en este caso calcular el valor total del inventario."

## Cierre

> "En conclusión, el ejemplo demuestra que Java permite aplicar programación funcional mediante lambdas, funciones e interfaces funcionales. Esto permite escribir código más compacto, reutilizable y orientado al procesamiento de datos, especialmente cuando trabajamos con colecciones y Streams."

---

# 7. Salida esperada del programa

```text
=== Lista de productos ===
Producto: Producto{nombre='Laptop', precio=3500.0, stock=8, categoria='Tecnología'}
Producto: Producto{nombre='Mouse', precio=80.0, stock=25, categoria='Tecnología'}
Producto: Producto{nombre='Teclado', precio=150.0, stock=10, categoria='Tecnología'}
Producto: Producto{nombre='Silla', precio=600.0, stock=4, categoria='Hogar'}
Producto: Producto{nombre='Escritorio', precio=1200.0, stock=3, categoria='Hogar'}
Producto: Producto{nombre='Monitor', precio=900.0, stock=6, categoria='Tecnología'}

Precio final Laptop con 10% descuento: 3150.0
¿Hay stock suficiente de Mouse para 20 unidades? true
Stock actualizado de Mouse: 20

=== Productos con aumento del 5% ===
Producto: Producto{nombre='Laptop', precio=3675.0, stock=8, categoria='Tecnología'}
Producto: Producto{nombre='Mouse', precio=84.0, stock=20, categoria='Tecnología'}
Producto: Producto{nombre='Teclado', precio=157.5, stock=10, categoria='Tecnología'}
Producto: Producto{nombre='Silla', precio=630.0, stock=4, categoria='Hogar'}
Producto: Producto{nombre='Escritorio', precio=1260.0, stock=3, categoria='Hogar'}
Producto: Producto{nombre='Monitor', precio=945.0, stock=6, categoria='Tecnología'}

Suma de precios de los dos primeros productos: 3759.0

Resumen generado correctamente.
Valor total del inventario: 46860.0

=== Productos caros (> 500) ===
Producto: Producto{nombre='Laptop', precio=3500.0, stock=8, categoria='Tecnología'}
Producto: Producto{nombre='Silla', precio=600.0, stock=4, categoria='Hogar'}
Producto: Producto{nombre='Escritorio', precio=1200.0, stock=3, categoria='Hogar'}
Producto: Producto{nombre='Monitor', precio=900.0, stock=6, categoria='Tecnología'}

=== Nombres de productos ===
Laptop
Mouse
Teclado
Silla
Escritorio
Monitor
```
