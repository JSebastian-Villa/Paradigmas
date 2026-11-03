# Guía de estudio y sustentación — Soporte360 (Versión Scala)


## Índice

1. [Análisis comparativo: Scala vs Python](#1-análisis-comparativo-scala-vs-python)
2. [Herramientas y conceptos nativos de Scala utilizados](#2-herramientas-y-conceptos-nativos-de-scala-utilizados)
3. [Explicación de las 59 funciones personalizadas (Módulo por Módulo)](#3-explicación-de-las-59-funciones-personalizadas-módulo-por-módulo)

---

## 1. Análisis comparativo: Scala vs Python

Este análisis compara dos implementaciones idénticas de la misma solución (Soporte360), construidas bajo las mismas reglas estrictas: sin ciclos (`for`/`while`), sin variables mutables y sin excepciones (`try`/`catch`).

### 1.1 Escenarios analizados y resultados

| # | Escenario | Scala | Python | Ganador | Por qué |
|---|---|---|---|---|---|
| E1 | **Normal** | Correcto | Correcto | Empate | Salida idéntica en datos variados. |
| E2 | **Vacío** (N = 0) | Sin errores | Idéntico | Empate | Manejo seguro con `Option` / `None`, sin dividir por cero. |
| E3 | **Datos inválidos** | Rechaza y repite | Idéntico | Empate* | Ambos validan formatos, textos vacíos, negativos y duplicados. |
| E4 | **Empates** | Consistente | Idéntico | Empate | Mismo orden de ranking (-valor, nombre/id). |
| E5 | **Errores repetidos** | Soporta infinito | **Se cae** a los ~990 | **Scala** | `@tailrec` optimiza la recursión en un salto; Python satura la pila (`RecursionError`). |
| E6 | **Volumen alto** | Lineal (1 pasada) | Cuadrático | **Scala** | `groupBy` de Scala es nativo y eficiente; Python copia el diccionario en cada paso para no mutar. |
| E7 | **Reglas estrictas** | El compilador frena | Se permite | **Scala** | Reasignar un `val` o mutar un objeto no compila en Scala; en Python depende de la disciplina. |
| E8 | **Cambios en caliente** | Requiere compilar | Inmediato | Depende | Python prueba al instante; Scala valida tipos antes de ejecutar. |
| E9 | **Prototipo corto** | Requiere JVM | Más ágil | **Python** | Sintaxis más corta y arranque directo sin máquina virtual de Java. |
| E10 | **Librerías de IA/Datos** | No aplica | Dominante | **Python** | Ecosistema de pandas/NumPy (prohibido en las reglas de esta práctica). |

*\*Nota en E3:* La única diferencia de todo el proyecto ocurrió con el texto `"NaN"`. Scala necesitó una regla extra (`isNaN`) porque `toDoubleOption` lo convierte en un número especial; la expresión regular de Python lo rechaza de inmediato. Ambos rechazan el dato.

---

### 1.2 ¿Cuál es más rápido?

* **Con alto volumen de datos (E6):** **Scala es más rápido y eficiente**. Su función nativa `groupBy` procesa los datos en una sola pasada (crecimiento lineal). En cambio, la versión inmutable de Python debe copiar el diccionario completo en cada paso para no mutar los datos, haciendo que su tiempo crezca de forma cuadrática (ej. 20,000 solicitudes toman 2.26 segundos en Python y el tiempo se multiplica por $\times 3.5$ al duplicar la carga).
* **Para arrancar, prototipar o hacer scripts cortos (E9):** **Python es más rápido**. No requiere un proceso de compilación previo ni arrancar la Máquina Virtual de Java (JVM), lo que permite ejecutar y probar cambios de inmediato con una sintaxis más corta.
* **Ante entradas erróneas masivas (E5):** **Scala** maneja mejor la estabilidad y velocidad sin límites gracias a la recursión de cola optimizada (`@tailrec`), mientras que Python se colapsa con un `RecursionError` al superar los ~990 errores consecutivos.

### 1.3 Veredicto para Soporte360

**Scala es el mejor lenguaje para esta práctica** por tres razones medibles:
1. **Detecta antes los errores:** Reasignar un `val`, modificar una `case class` o usar un `Option`/`Either` como si fuera un valor plano **no compila**. En Python, las mismas reglas se cumplen solo por disciplina del programador y los errores de tipo aparecen al ejecutar.
2. **Trae las herramientas funcionales listas:** Python necesitó **8 funciones más** (67 contra 59) para construir a mano lo que Scala ya incluye por defecto: `encadenar`, `agrupar_por`, `primero_o_nada`, `a_entero`, `a_decimal`...
3. **Resiste los escenarios extremos sin violar las reglas:** Ante errores repetidos o miles de registros, la solución natural en Python requeriría usar un `while` o mutar diccionarios, lo cual está prohibido por el enunciado. Scala los supera de forma nativa sin romper el paradigma funcional.

*(Calidad del código analizada con SonarQube en ambas versiones: **0 bugs, 0 vulnerabilidades, 0 code smells y calificación A**. La diferencia no es la calidad de escritura, sino lo que cada lenguaje **garantiza**).*

---

## 2. Herramientas y conceptos nativos de Scala utilizados

### 2.1 Estructuras Funcionales y Control de Flujo

| Función / Concepto | ¿Qué es? | ¿Por qué se usa en el proyecto? |
|---|---|---|
| **`case class`** | Estructura para crear tipos de datos inmutables de forma automática. | Define el objeto `Solicitud`. Proporciona comparación por valor y métodos predeterminados sin requerir constructores clásicos (`new`). |
| **`Either[E, A]`** | Tipo de dato que representa Error (`Left`) o Éxito (`Right`). | Reemplaza el uso de excepciones (`try/catch`). Devuelve el dato validado o el texto del error. |
| **`Option[A]`** | Contenedor que indica si un valor existe (`Some`) o está ausente (`None`). | Evita divisiones entre cero y maneja búsquedas sin resultados (ej. promedio vacío). |
| **`@tailrec`** | Anotación para verificar la recursión de cola. | Sustituye los ciclos `while`, permitiendo repeticiones infinitas al usuario sin agotar la memoria. |
| **`match`** | Evalúa la forma/patrón de un valor para decidir qué acción tomar. | Reemplaza estructuras complejas de `if/else`, reaccionando ante resultados `Left/Right` o `Some/None`. |
| **`case ... if`** | Condición extra (Guarda) dentro de un patrón de `match`. | Filtra valores especiales directamente en la decisión (ej. rechazar números negativos). |
| **`.copy`** | Método de las `case class` para crear un clon exacto cambiando atributos. | Mantiene la inmutabilidad absoluta; en lugar de mutar, creas una copia con el nuevo estado. |

### 2.2 Transformación y Colecciones

| Función | ¿Qué es? | ¿Por qué se usa en el proyecto? |
|---|---|---|
| **`foldLeft`** | Recorre una colección acumulando un resultado paso a paso. | Se usa para pedir y construir la lista de N solicitudes desde cero, permitiendo revisar repeticiones de IDs. |
| **`map`** | Transforma cada elemento de una colección en otro valor. | Pasa de una lista de solicitudes a una lista puramente de sus valores económicos. |
| **`filter`** | Crea una nueva colección con elementos que cumplen una condición. | Separa estados específicos (ej. "Pendientes"). |
| **`groupBy`** | Agrupa elementos creando un Mapa/Diccionario basado en un atributo. | Agrupa la información (por técnico o cliente) en un solo paso. |
| **`sortBy`** | Ordena una colección utilizando un criterio. | Organiza rankings utilizando tuplas paramétricas para desempatar (-valor, alfabético). |
| **`flatMap`** | Encadena operaciones: si la primera falla, no ejecuta la segunda. | Encadena validaciones (formato de ID, luego verificar repetición). |
| **`reduceOption`** | Combina elementos de una colección; devuelve `None` si está vacía. | Suma valores para calcular promedios evitando fallos si no hay datos. |
| **`partition`** | Divide una colección en una tupla de dos listas (cumplen y no cumplen). | Separa rápidamente las solicitudes finalizadas de las no finalizadas. |
| **`headOption`** | Obtiene el primer elemento de la lista como `Option`. | Encuentra al "primero" del top tras ordenar, sin fallar si la colección original estaba vacía. |
| **`take(n)`** | Devuelve los primeros "n" elementos. | Ideal para los reportes de "Top 3". |
| **`find`** | Busca el primer elemento que cumpla una condición y devuelve un `Option`. | Valida que la categoría ingresada exista dentro de las listas permitidas. |
| **`exists`** | Evalúa si algún elemento cumple una condición (`true`/`false`). | Revisa si el ID ingresado ya existe en las listas de registrados. |
| **`zip`** | Une dos listas por posición creando tuplas. | Enumera elementos ("1.", "2.") en los reportes. |
| **`foreach`** | Aplica una acción a cada elemento. | Se usa exclusivamente para aplicar efectos (imprimir en pantalla). |
| **`:+` (Append)** | Añade un elemento al final devolviendo una **nueva lista**. | Acumula solicitudes preservando la inmutabilidad de la lista original. |
| **`List.empty`** | Crea una lista vacía de un tipo específico. | Sirve como el "valor inicial" del acumulador en el registro de datos. |

### 2.3 Utilidades y Tipos Base

| Método / Tipo | ¿Qué es y qué indica en el código? |
|---|---|
| **`toIntOption` / `toDoubleOption`** | Intenta convertir texto a número sin lanzar error (excepción), devolviendo `Option`. |
| **`StdIn.readLine()`** | Método estándar para leer entrada de texto del teclado. |
| **`getOrElse`** | Extrae el valor de un `Option`; si es `None`, devuelve un valor por defecto. |
| **`trim`** | Elimina espacios al inicio y al final de un texto. |
| **`isEmpty`** | Verifica si una colección, texto u `Option` está vacío (`true`/`false`). |
| **`equalsIgnoreCase`** | Compara textos ignorando mayúsculas/minúsculas. |
| **`mkString`** | Une los elementos de una colección en texto, separados por un carácter. |
| **`isNaN`/`isInfinite`** | Verifica si un valor numérico es inválido ("Not a Number" o infinito). |
| **`size`** | Devuelve la cantidad de elementos de una colección. |
| **`sum`** | Suma todos los elementos numéricos (devuelve 0.0 si está vacía). |
| **`toList`** | Convierte otra estructura de datos (como un `Map` agrupado) en una Lista. |
| **`s""` y `f""`** | Interpoladores para inyectar variables en textos o aplicar formatos numéricos. |
| **`1 to cantidad`** | Crea un rango secuencial para reemplazar el contador de un ciclo `for`. |
| **`Unit`** | Equivalente a `void`. Indica que la función aplica un efecto (imprime) sin devolver un valor. |
| **`Some` / `None`** | Variaciones del tipo `Option`. `Some(x)` = éxito, `None` = vacío/ausencia. |
| **`Left` / `Right`** | Variaciones del tipo `Either`. `Left(x)` = error, `Right(x)` = éxito validado. |
| Tipos Primitivos | `Boolean` (V/F), `String` (Textos), `Int` (Enteros), `Double` (Decimales). |

---

## 3. Explicación de las 59 funciones personalizadas (Módulo por Módulo)

### Módulo 1: Modelo (Estructura de Datos)
1. **`valorEconomico`**: Multiplica las `horas` por el `costoPorHora` de una solicitud específica.
2. **`estaFinalizada`**: Comprueba si el estado de una solicitud es igual a la constante "Finalizado" y devuelve verdadero o falso.

### Módulo 2: Validación (RF13)
3. **`validarTextoNoVacio`**: Quita espacios iniciales y finales y verifica que campos como cliente o técnico no queden en blanco.
4. **`validarOpcion`**: Busca el texto escrito por el usuario dentro de las listas válidas y, si coincide, devuelve el valor oficial.
5. **`validarNumeroNoNegativo`**: Intenta convertir el texto a decimal, verificando que no sea negativo y rechazando textos extraños como "NaN".
6. **`validarCantidad`**: Comprueba que la cantidad de solicitudes "N" que el usuario desea ingresar sea un número entero y no negativo.
7. **`validarEnteroPositivo`**: Valida que un identificador (ID) sea estrictamente mayor que cero.
8. **`validarIdNoRepetido`**: Revisa si el ID recién escrito ya se encuentra en la lista de IDs registrados para evitar duplicados.
9. **`validarId`**: Encadena las dos funciones anteriores usando `flatMap` para verificar primero el formato del ID y luego su no repetición.

### Módulo 3: Entrada (Interacción con el usuario)
10. **`leerDatoValido`**: Función recursiva base que hace una pregunta, recibe el texto, aplica una validación y, si hay un error, vuelve a preguntar sin consumir memoria extra.
11. **`leerCantidad`**: Utiliza `leerDatoValido` para preguntar "¿Cuántas solicitudes desea registrar?".
12. **`leerSolicitud`**: Llama a `leerDatoValido` ocho veces (una para cada campo de la solicitud) para construir y devolver un objeto `Solicitud` válido.
13. **`registrarSolicitudes`**: Repite `leerSolicitud` "N" veces usando `foldLeft`, acumulando todas las solicitudes en una lista final.
14. **`registrar`**: Orquesta el módulo; primero lee la cantidad "N" y luego registra esa cantidad de solicitudes.

### Módulo 4: Análisis (Cálculos de los reportes)
15. **`promedio`**: Suma los números de una lista y los divide por la cantidad, asegurando que no se divida entre cero si la lista está vacía.
16. **`contarSegun`**: Función de apoyo genérica que recibe una lista (ej. categorías) y cuenta cuántas solicitudes pertenecen a cada valor, dejando en cero las que no tengan datos.
17. **`solicitudesPendientes`**: Filtra las solicitudes cuyo estado sea "Pendiente" (RF03).
18. **`solicitudesEnProceso`**: Filtra las solicitudes cuyo estado sea "En proceso" (RF03).
19. **`separarPorFinalizacion`**: Divide todas las solicitudes en dos grupos: finalizadas y sin finalizar (RF14).
20. **`contarPorCategoria`**: Cuenta cuántas solicitudes hay por categoría (RF04).
21. **`categoriaMayorDemanda`**: Ordena el conteo anterior de mayor a menor y extrae la categoría principal (RF04).
22. **`valorTotal`**: Suma el valor económico de todas las solicitudes ingresadas (RF05).
23. **`valorPromedio`**: Utiliza la función `promedio` sobre los valores económicos de las solicitudes (RF05).
24. **`tresMasCostosas`**: Ordena todas las solicitudes desde la más cara y extrae el "Top 3" (RF06).
25. **`resumenPorTecnico`**: Agrupa solicitudes por técnico y calcula tres datos: número de servicios, horas acumuladas y finalizados (RF07).
26. **`tecnicoMayorCarga`**: Toma el resumen por técnico, lo ordena por más horas acumuladas y extrae al primero (RF07).
27. **`clientesFrecuentes`**: Agrupa por cliente, cuenta cuántas solicitudes tiene cada uno y deja solo a los que tienen más de una (RF08).
28. **`valorPorCliente`**: Agrupa por cliente y suma todo el dinero que ha generado (RF09).
29. **`tresClientesMayorValor`**: Ordena a los clientes por valor generado y extrae el "Top 3" (RF09).
30. **`clienteMayorValor`**: Extrae al cliente número 1 en valor para colocarlo en el resumen (RF14).
31. **`solicitudesCriticas`**: Filtra las que sean prioridad Alta y NO estén finalizadas (RF10).
32. **`promedioHoras`**: Calcula el tiempo promedio que toman todas las solicitudes juntas (RF11).
33. **`solicitudesConHorasElevadas`**: Filtra solo las solicitudes que tardan más que el promedio (RF11).
34. **`contarPorPrioridad`**: Cuenta cuántas solicitudes tienen prioridad Alta, Media y Baja (RF12).
35. **`porcentajePrioridadAlta`**: Divide las de prioridad alta entre el total y multiplica por 100.0 para calcular el porcentaje (RF12).

### Módulo 5: Reportes (Presentación)
*Utilidades y formatos:*
36. **`formatoDinero`**: Añade el signo "$", separador de miles y dos decimales a un número.
37. **`formatoHoras`**: Añade "h" y dos decimales a un tiempo.
38. **`formatoPorcentaje`**: Añade el símbolo "%" y dos decimales a una cifra.
39. **`textoSolicitudes`**: Asegura que el texto diga "1 solicitud" (singular) o "N solicitudes" (plural).
40. **`textoOpcional`**: Si un dato existe, lo formatea; si es un `None` (vacío), imprime "Sin datos".
41. **`titulo`**: Imprime el texto de encabezado decorado con `======`.
42. **`mostrarLista`**: Recibe una lista e imprime uno a uno sus elementos; si la lista está vacía, imprime el mensaje "No hay solicitudes...".
43. **`numerar`**: Añade las posiciones "1.", "2.", "3." al lado de un ranking.
44. **`describirSolicitud`**: Concatena los 8 datos de una solicitud para que se lean de forma organizada.
45. **`describirTecnico`**: Formatea el resumen visual de un técnico.

*Vistas de los requisitos funcionales:*
46. **`mostrarSolicitudes`**: Imprime el listado general de todo lo registrado (RF02).
47. **`mostrarSinFinalizar`**: Imprime dos listas separadas, las pendientes y las en proceso (RF03).
48. **`mostrarCategorias`**: Imprime la cantidad de cada categoría y menciona cuál tuvo mayor demanda (RF04).
49. **`mostrarAnalisisEconomico`**: Imprime el valor de cada solicitud, y luego el total y el promedio del dinero (RF05).
50. **`mostrarMasCostosas`**: Imprime las posiciones del "Top 3" de las solicitudes (RF06).
51. **`mostrarTecnicos`**: Imprime a todos los técnicos y menciona abajo cuál tuvo la mayor carga (RF07).
52. **`mostrarClientesFrecuentes`**: Imprime la lista de clientes con más de un servicio (RF08).
53. **`mostrarClientesMayorValor`**: Imprime el ranking del "Top 3" de mejores clientes (RF09).
54. **`mostrarCriticas`**: Imprime los datos específicos (cliente, categoría, técnico y horas) de los casos urgentes (RF10).
55. **`mostrarHorasElevadas`**: Menciona el tiempo promedio e imprime las solicitudes que se excedieron de ese tiempo (RF11).
56. **`mostrarPrioridades`**: Imprime el total por prioridades y abajo el porcentaje de prioridad Alta (RF12).
57. **`mostrarResumenEjecutivo`**: Llama y desempaqueta todas las funciones anteriores para mostrar de golpe 8 indicadores globales tipo Dashboard (RF14).
58. **`mostrarTodosLosReportes`**: Coordina llamar en orden consecutivo todos los reportes desde el RF02 hasta el RF12.

### Módulo 6: Main
59. **`main`**: El método donde inicia el programa; invoca el módulo de Entrada para pedir los datos y luego el módulo de Reportes para mostrarlos.