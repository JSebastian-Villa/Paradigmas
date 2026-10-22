# GUÍA DEFINITIVA DE SUSTENTACIÓN - SISTEMA DE PEDIDOS

## PARTE 1: RESUMEN GENERAL DE FUNCIONES DEL CÓDIGO
*Aquí encontrarás para qué sirve cada función que creaste, explicada de forma directa para defenderla ante el profesor.*

### 1. Funciones de Lectura y Registro (Entrada de datos)
*   **`leer-texto`**: Muestra una pregunta en la pantalla, espera a que el usuario escriba y presione Enter, devolviendo ese texto exacto.
*   **`leer-numero`**: Utiliza la función anterior para pedir el texto y luego convierte ese texto escrito (ej. `"1500"`) en un número matemático real (`1500`).
*   **`leer-pedido`**: Despliega las 15 preguntas necesarias para armar un pedido (cédula, cantidad, precio, etc.) y las empaqueta en una lista ordenada con etiquetas.
*   **`leer-pago`**: Formulario rápido de 2 datos. Pide el número de pedido y el valor exacto en dinero que la empresa recibió.
*   **`registrar`**: Es la "máquina repetitiva". Llama al formulario tantas veces como el usuario indicó, creando una lista grande con todos los registros acumulados mediante recursión.

### 2. Funciones de Consulta y Utilidades Económicas
*   **`dato`**: Es el buscador interno. Busca dentro del pedido la etiqueta que le pidas (ej. `'cantidad`) y extrae el valor que tiene adentro.
*   **`valor-final`**: Atajo de acceso rápido que extrae el `'valor-final` sin tener que escribir la función buscadora cada vez.
*   **`sumar`**: El "totalizador". Extrae de todos los pedidos la columna numérica que le pidas, la convierte en una lista limpia de números y los suma todos de un golpe usando `apply`.
*   **`pesos`**: El "redondeador". Toma cualquier cifra con decimales (como un impuesto) y la redondea al número entero más cercano para evitar centavos.

### 3. Funciones de Validación (Control de Calidad)
*   **`valor-permitido?`**: Revisa si la opción escrita por el usuario está dentro de la lista oficial permitida de la empresa (ej. si el medio de pago es válido).
*   **`pedido-valido?`**: La auditoría estricta. Revisa que el pedido cumpla con todas las reglas obligatorias combinadas con un `and` (cantidades mayores a cero, opciones válidas, no campos vacíos).

### 4. Funciones de Duplicados (Detector de Clones)
*   **`clave`**: Reduce un pedido complejo a su identidad mínima: solo su número de pedido y su código de producto.
*   **`mismo-registro?`**: Compara las llaves de dos pedidos para responder si son clones o no.
*   **`copias-de`**: Aísla de la lista general todos los registros que sean idénticos al pedido de referencia.
*   **`sin-copias-de`**: Limpia la lista sacando a todas las copias y dejando únicamente los elementos diferentes.
*   **`registros-unicos`**: Barre la lista recursivamente conservando exclusivamente la **primera aparición** de cada pedido y eliminando sus copias.
*   **`registros-repetidos`**: Descarta las primeras apariciones y rescata únicamente las copias repetidas para poder contarlas y reportarlas.

### 5. Condiciones Comerciales
*   **`pago-aprobado?`**: Verifica si el pago del pedido se encuentra oficialmente en estado "Aprobado".
*   **`inventario-suficiente?`**: Comprueba que la bodega tenga stock mayor o igual a la cantidad solicitada (evita despachos parciales).
*   **`facturable?`**: Filtro maestro. Determina si el pedido continúa exigiendo dos cosas: pago aprobado **y** que haya inventario.
*   **`rechazado-por-inventario?`**: Identifica pedidos que SÍ fueron pagados, pero que NO tienen stock suficiente.

### 6. Liquidación de un Pedido (La Calculadora)
*   **`descuento-adicional`**: Calcula un 3% de descuento extra si el subtotal del pedido es igual o superior a $1.000.000.
*   **`costo-despacho`**: Calcula el transporte determinando la tarifa base según los kilómetros y aplicando un recargo del 40% si es Express.
*   **`tasa-impuesto`**: Define el porcentaje de impuestos (5% para alimentos y 19% general).
*   **`liquidar`**: El motor financiero. Toma un pedido, ejecuta toda la cadena de cálculos en orden secuencial usando un `let*` y devuelve un registro nuevo que adjunta los datos económicos al final.

### 7. Ordenamiento y Agrupación
*   **`ordenar-por-valor`**: Organiza la lista de pedidos de mayor a menor según su valor definitivo.
*   **`primeros`**: Extrae de forma recursiva los primeros `n` elementos de una lista (ej. el Top 5).
*   **`sin-repetir`**: Limpia una lista eliminando duplicados (ej. dejando cada ciudad una sola vez).
*   **`resumen-por`**: La máquina de reportes. Extrae valores únicos, filtra los pedidos de cada grupo y calcula cuántos pedidos hubo y cuál fue su plata acumulada.
*   **`fila-mayor`**: Analiza una tabla de resumen, identifica el número más alto y devuelve la fila ganadora.

---

## PARTE 2: TABLAS MAESTRAS PARA SUSTENTAR ESTRUCTURAS

### 1. Estructuras de Control y Lógica (El cerebro del flujo)
| Elemento | Tipo | ¿Qué hace y por qué es clave en tu código? |
| :--- | :--- | :--- |
| `cond` | Estructura condicional | Evalúa múltiples condiciones en orden de arriba hacia abajo y **se detiene en la primera verdadera**. Evita anidar muchos `if`s (usado en tarifas de despacho y conciliación). |
| `if` | Estructura condicional | El condicional básico de dos caminos. Es la clave de tus **casos base en la recursión**. |
| `and` | Operador lógico | Devuelve verdadero solo si **todas** las condiciones lo son. Tiene **cortocircuito**: se detiene en el primer `#f`. Por eso validas `number?` antes del `>` matemático. |
| `or` | Operador lógico | Devuelve verdadero si **al menos una** condición se cumple. Usado para detener lecturas si hay errores o números negativos. |
| `let*` | Estructura local | Define variables temporales **en orden secuencial**. Permite que la variable de abajo use el resultado de la de arriba (es el corazón de la función `liquidar`). |
| `begin` | Estructura de bloque | Agrupa múltiples acciones para ejecutarlas en orden dentro de un espacio que normalmente solo acepta una instrucción (usado al final para imprimir varios mensajes juntos). |
| `lambda` | Estructura funcional | Crea funciones **anónimas (sin nombre)** al vuelo. Evita tener que crear cientos de funciones externas `define` solo para hacer un filtro o mapeo rápido. |

### 2. Manipulación de Listas (Los ladrillos de Racket)
| Elemento | Tipo | ¿Qué hace y por qué es clave en tu código? |
| :--- | :--- | :--- |
| `cons` | Función nativa | Agrega un elemento al **inicio** de una lista. Es el pegamento de tus funciones recursivas. |
| `car` / `first` | Función nativa | Devuelve el **primer elemento** de una lista. Fundamental para aislar registros al buscar duplicados. |
| `cdr` / `rest` | Función nativa | Devuelve **el resto de la lista** (todo menos el primero). Se le pasa a la recursión para continuar el ciclo. |
| `append` | Función nativa | **Une dos listas** completas en una sola. Permite adjuntar datos económicos al pedido original sin destruirlo. |
| `assoc` | Función nativa | Busca un par clave-valor en una lista de pares. Es el motor de tu función buscadora `dato`. |
| `member` | Función nativa | Busca si un elemento existe en una lista. Lo usas para validar si la palabra ingresada pertenece al manual de la empresa. |

### 3. Funciones de Orden Superior (El motor de la programación funcional)
| Elemento | Tipo | ¿Qué hace y por qué es clave en tu código? |
| :--- | :--- | :--- |
| `map` | Función nativa | Recorre una lista, transforma cada elemento y devuelve una **lista nueva** (ej. extraer una lista solo de números de subtotales). |
| `filter` | Función nativa | Actúa como colador. Recorre una lista y devuelve una lista nueva **solo** con los elementos que cumplen la condición. |
| `apply` | Función nativa | Toma una lista encapsulada y le quita los paréntesis para pasarle sus elementos sueltos a una función (como `+` o `max`). |
| `for-each` | Iterador | Recorre la lista pero **no devuelve nada**. Solo ejecuta **efectos visuales** como imprimir reportes en consola. |
| `sort` | Función nativa | Reordena elementos de una lista según una regla dada por una función `lambda`. |
| `foldl` *(Comodín)* | Función nativa | **Acumulador.** Reduce una lista a un solo valor (ej. sumar) recorriéndola una sola vez de izquierda a derecha. Es la alternativa eficiente a usar `map` y `apply`. |

### 4. Tipos, Entradas y Salidas (Interactuando con el sistema)
| Elemento | Tipo | ¿Qué hace y por qué es clave en tu código? |
| :--- | :--- | :--- |
| `string=?` | Función nativa | Compara dos textos de forma **exacta**. Diferencia mayúsculas de minúsculas (ej. `"Aprobado"` vs `"aprobado"`). |
| `read-line` | Estructura I/O | Lee todo lo que el usuario digite hasta presionar Enter. Atrapa textos compuestos con espacios. |
| `printf` | Estructura I/O | Imprime texto con formato inyectando variables donde vea el marcador `~a`. |
| `exact->inexact` | Función nativa | Convierte fracciones exactas (ej. `5/100`) en decimales (`0.05`). Se usó al final para mostrar los porcentajes visualmente bonitos. |
| `zero?` | Función nativa | Pregunta si un número es exactamente 0. Sirve de escudo para evitar dividir por cero en los porcentajes. |