# Ejercicio de Programación Funcional en Java — Gestión de Empleados

## Descripción

Este programa implementa un sistema sencillo de gestión de empleados utilizando **programación funcional en Java**. El objetivo es resolver diferentes necesidades del reto mediante interfaces funcionales como `Supplier`, `Predicate`, `Function`, `Consumer`, `BiFunction`, `BiPredicate`, `UnaryOperator`, `BinaryOperator` y `Runnable`.

## ¿Qué resuelve el programa?

El programa permite:

1. Crear empleados.
2. Filtrar empleados por salario, área y antigüedad.
3. Calcular bonificaciones.
4. Validar si un empleado aplica a un beneficio.
5. Actualizar salarios.
6. Acumular la masa salarial.
7. Generar un reporte general.

---

## Interfaces funcionales utilizadas

### 1. `Supplier<Empleado>` — Crear empleados

```java
java.util.function.Supplier<Empleado> proveedorEmpleado =
        () -> new Empleado("Miguel Angel", 2000.00, "Recursos humanos", 5);

Empleado nuevoEmpleado = proveedorEmpleado.get();
empleados.add(nuevoEmpleado);
```

**¿Qué hace?**

`Supplier<T>` genera o proporciona un objeto sin recibir parámetros.

En este caso, crea un nuevo objeto `Empleado`.

**¿Para qué sirve en el reto?**

Resuelve la necesidad de **crear nuevos empleados**. La función encapsula la forma de construir un empleado y se ejecuta mediante `get()`.

**Idea clave:**

`Supplier` → **genera un empleado**.

---

### 2. `Predicate<Empleado>` — Filtrar por salario

```java
java.util.function.Predicate<Empleado> esSalarioAlto =
        e -> e.getSalario() > 3000;

List<Empleado> empleadosSalarioAlto = empleados.stream()
        .filter(esSalarioAlto)
        .collect(Collectors.toList());
```

**¿Qué hace?**

`Predicate<T>` recibe un objeto y comprueba una condición. El resultado siempre es `true` o `false`.

Aquí verifica si el salario es mayor a `3000`.

**¿Para qué sirve en el reto?**

Permite **filtrar empleados según su salario**.

**Idea clave:**

`Predicate` → **comprueba una condición**.

---

### 3. `Predicate<Empleado>` — Filtrar por área

```java
java.util.function.Predicate<Empleado> esTecnologia =
        e -> e.getArea().equals("Tecnologia");

List<Empleado> empleadosTecnologia = empleados.stream()
        .filter(esTecnologia)
        .collect(Collectors.toList());
```

**¿Qué hace?**

Comprueba si el empleado pertenece al área de Tecnología.

**¿Para qué sirve en el reto?**

Permite **filtrar empleados por área**.

**Idea clave:**

`Predicate` → **determina si un empleado cumple una condición**.

---

### 4. `Predicate<Empleado>` — Filtrar por antigüedad

```java
java.util.function.Predicate<Empleado> tieneAntiguedad =
        e -> e.getAntiguedad() >= 5;

List<Empleado> empleadosAntiguos = empleados.stream()
        .filter(tieneAntiguedad)
        .collect(Collectors.toList());
```

**¿Qué hace?**

Comprueba si el empleado tiene cinco años o más de antigüedad.

**¿Para qué sirve en el reto?**

Permite **filtrar empleados según su antigüedad** y también puede servir como criterio para beneficios.

---

### 5. `Function<Empleado, String>` — Obtener nombres

```java
java.util.function.Function<Empleado, String> obtenerNombre =
        Empleado::getNombre;

List<String> nombres = empleados.stream()
        .map(obtenerNombre)
        .collect(Collectors.toList());
```

**¿Qué hace?**

`Function<T,R>` recibe un dato de un tipo y devuelve otro dato, posiblemente de otro tipo.

Aquí transforma:

`Empleado → String (nombre)`

`Empleado::getNombre` obtiene únicamente el nombre del empleado.

**¿Para qué sirve en el reto?**

Sirve para **extraer o transformar información** de los empleados, por ejemplo, para generar una lista de nombres o construir reportes.

**Idea clave:**

`Function` → **transforma un dato en otro**.

---

### 6. `Consumer<Empleado>` — Mostrar empleados

```java
java.util.function.Consumer<Empleado> mostrarEmpleados =
        e -> System.out.println("Empleado: " + e);

empleados.forEach(mostrarEmpleados);
```

**¿Qué hace?**

`Consumer<T>` recibe un objeto y realiza una acción sobre él, pero no devuelve ningún resultado.

En este caso, imprime la información del empleado.

**¿Para qué sirve en el reto?**

Permite **mostrar empleados o ejecutar acciones sobre ellos**, por ejemplo, imprimir información para un reporte.

**Idea clave:**

`Consumer` → **recibe un dato y realiza una acción**.

---

### 7. `BiFunction<Double, Double, Double>` — Calcular bonificaciones

```java
java.util.function.BiFunction<Double, Double, Double> calcularBonificacion =
        (salario, porcentaje) -> salario * porcentaje / 100;

double bonificacionPedro = calcularBonificacion.apply(1500.0, 10.0);
```

**¿Qué hace?**

`BiFunction<T,U,R>` recibe dos valores y devuelve un resultado.

Aquí recibe:

- salario
- porcentaje de bonificación

Y devuelve el valor de la bonificación.

Ejemplo:

`1500 × 10 / 100 = 150`

**¿Para qué sirve en el reto?**

Resuelve la necesidad de **calcular bonificaciones de los empleados**.

**Idea clave:**

`BiFunction` → **recibe dos valores y calcula un resultado**.

---

### 8. `BiPredicate<Empleado, Integer>` — Validar beneficios

```java
java.util.function.BiPredicate<Empleado, Integer> aplicaBeneficio =
        (empleado, antiguedadMinima) ->
                empleado.getAntiguedad() >= antiguedadMinima;

System.out.println(
        "¿Ana aplica al beneficio? "
                + aplicaBeneficio.test(empleados.get(1), 5)
);
```

**¿Qué hace?**

`BiPredicate<T,U>` funciona como `Predicate`, pero recibe **dos datos** y devuelve `true` o `false`.

En este caso compara:

- la antigüedad del empleado;
- la antigüedad mínima requerida.

**¿Para qué sirve en el reto?**

Permite **validar si un empleado cumple las condiciones para recibir un beneficio**.

Ejemplo:

`10 años >= 5 años → true`

**Idea clave:**

`BiPredicate` → **comprueba una condición usando dos valores**.

> **Nota:** En el código original, el mensaje dice “¿Ana aplica...?”, pero `empleados.get(1)` corresponde a **María**. Para consultar a Ana se debe utilizar `empleados.get(3)` o buscarla por nombre.

---

### 9. `UnaryOperator<Empleado>` — Actualizar salarios

```java
java.util.function.UnaryOperator<Empleado> actualizarSalario =
        empleado -> {
            empleado.setSalario(
                    empleado.getSalario() + 200
            );
            return empleado;
        };

List<Empleado> empleadosActualizados = empleados.stream()
        .map(actualizarSalario)
        .collect(Collectors.toList());
```

**¿Qué hace?**

`UnaryOperator<T>` recibe un objeto y devuelve otro del **mismo tipo**.

Aquí:

`Empleado → Empleado`

La función aumenta el salario en `200`.

**¿Para qué sirve en el reto?**

Resuelve la necesidad de **actualizar los salarios de los empleados**.

**Idea clave:**

`UnaryOperator` → **modifica un valor y devuelve el mismo tipo**.

---

### 10. `BinaryOperator<Double>` — Acumular masa salarial

```java
java.util.function.BinaryOperator<Double> sumarSalarios =
        Double::sum;

double masaSalarial = empleadosActualizados.stream()
        .map(Empleado::getSalario)
        .reduce(0.0, sumarSalarios);
```

**¿Qué hace?**

`BinaryOperator<T>` recibe dos valores del mismo tipo y devuelve un valor de ese mismo tipo.

Aquí combina dos salarios mediante una suma.

`reduce()` repite esa operación para acumular todos los salarios y obtener el total.

**¿Para qué sirve en el reto?**

Permite **calcular la masa salarial total de la empresa**.

**Idea clave:**

`BinaryOperator` → **combina dos valores del mismo tipo**.

---

### 11. `Runnable` — Ejecutar el reporte

```java
Runnable tareaReporte =
        () -> {
            System.out.println("\n=== REPORTE GENERAL ===");
            // Información del reporte...
        };

tareaReporte.run();
```

**¿Qué hace?**

`Runnable` representa una tarea que puede ejecutarse. No recibe parámetros y no devuelve ningún resultado.

Aquí la tarea consiste en imprimir el reporte general.

**¿Para qué sirve en el reto?**

Resuelve la necesidad de **ejecutar reportes** sobre los empleados.

**Idea clave:**

`Runnable` → **ejecuta una tarea**.

---

## Resumen general

| Interfaz funcional | Función en el programa | Parte del reto |
|---|---|---|
| `Supplier` | Crear un empleado | Crear empleados |
| `Predicate` | Comprobar condiciones | Filtrar por salario, área y antigüedad |
| `Function` | Transformar / extraer datos | Obtener información para reportes |
| `Consumer` | Realizar una acción | Mostrar empleados |
| `BiFunction` | Calcular usando dos valores | Bonificaciones |
| `BiPredicate` | Validar usando dos valores | Beneficios |
| `UnaryOperator` | Modificar un objeto | Actualizar salarios |
| `BinaryOperator` | Combinar valores | Masa salarial |
| `Runnable` | Ejecutar una tarea | Reporte general |

## Flujo del programa

```text
Crear empleados
      ↓
Filtrar empleados
      ↓
Extraer información
      ↓
Calcular bonificaciones
      ↓
Validar beneficios
      ↓
Actualizar salarios
      ↓
Acumular masa salarial
      ↓
Generar reporte
```

## ¿Por qué esto es programación funcional?

El programa utiliza funciones como valores que se pueden asignar a variables y pasar a operaciones como `filter()`, `map()` y `reduce()`. Esto permite describir **qué operación se quiere realizar** sobre los datos sin depender únicamente de ciclos y lógica repetitiva.

## Tecnologías

- **Java:** lenguaje utilizado para implementar el programa.
- **Streams:** permiten procesar colecciones de manera funcional.
- **Interfaces funcionales (`java.util.function`):** permiten representar operaciones como filtros, transformaciones, cálculos y acciones.

## Ejecución

El archivo principal contiene la clase:

```text
Empleado
```

y la clase ejecutable:

```text
ejercicio_bonificacion
```

Para compilar y ejecutar desde una terminal:

```bash
javac ejercicio_bonificacion.java
java ejercicio_bonificacion
```

