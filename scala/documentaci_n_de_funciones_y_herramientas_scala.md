# Guía de Funciones y Herramientas Scala — Soporte360

Este documento contiene la explicación detallada de los conceptos, herramientas nativas y las 59 funciones personalizadas implementadas en el proyecto **Soporte360**, desarrollado bajo el paradigma de programación funcional estricta en Scala.

---

## 1. Herramientas y Conceptos Nativos de Scala Utilizados

El proyecto cumple con estrictas reglas funcionales (sin variables mutables, sin ciclos imperativos y sin excepciones). Para lograrlo, se utilizaron las siguientes herramientas del lenguaje:

### Estructuras FP (Programación Funcional) y Control de Flujo
| Función / Concepto | ¿Qué es? | ¿Por qué se usa en el proyecto? |
| :--- | :--- | :--- |
| **`case class`** | Estructura para crear tipos de datos inmutables de forma automática. | Define el objeto `Solicitud`. Proporciona comparación por valor y métodos predeterminados sin requerir constructores clásicos (`new`). |
| **`Either[E, A]`** | Tipo de dato que representa Error (`Left`) o Éxito (`Right`). | Reemplaza el uso de excepciones (`try/catch`). Devuelve el dato validado o el texto del error. |
| **`Option[A]`** | Contenedor que indica si un valor existe (`Some`) o está ausente (`None`). | Evita divisiones entre cero y maneja búsquedas sin resultados (ej. promedio vacío). |
| **`@tailrec`** | Anotación para verificar la recursión de cola. | Sustituye los ciclos `while`, permitiendo repeticiones infinitas al usuario sin agotar la memoria. |
| **`match`** | Evalúa la forma/patrón de un valor para decidir qué acción tomar. | Reemplaza estructuras complejas de `if/else`, reaccionando ante resultados `Left/Right` o `Some/None`. |
| **`case ... if`** | Condición extra (Guarda) dentro de un patrón de `match`. | Filtra valores especiales directamente en la decisión (ej. rechazar números negativos). |
| **`.copy`** | Método de las `case class` para crear un clon exacto cambiando atributos. | Mantiene la inmutabilidad absoluta; en lugar de mutar, creas una copia con el nuevo estado. |

### Transformación y Colecciones
| Función | ¿Qué es? | ¿Por qué se usa en el proyecto? |
| :--- | :--- | :--- |
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
| **`:+` (Append)**| Añade un elemento al final devolviendo una **nueva lista**. | Acumula solicitudes preservando la inmutabilidad de la lista original. |
| **`List.empty`** | Crea una lista vacía de un tipo específico. | Sirve como el "valor inicial" del acumulador en el registro de datos. |

### Utilidades y Tipos Base
| Método / Tipo | ¿Qué es y qué indica en el código? |
| :--- | :--- |
| **`toIntOption` / `toDoubleOption`** | Intenta convertir texto a número sin lanzar error (excepción), devolviendo `Option`. |
| **`StdIn.readLine()`** | Método estándar para leer entrada de texto del teclado. |
| **`getOrElse`** | Extrae el valor de un `Option`; si es `None`, devuelve un valor por defecto. |
| **`trim`** | Elimina espacios al inicio y al final de un texto. |
| **`isEmpty`** | Verifica si una colección, texto u `Option` está vacío (`true`/`false`). |
| **`equalsIgnoreCase`**| Compara textos ignorando mayúsculas/minúsculas. |
| **`mkString`** | Une los elementos de una colección en texto, separados por un carácter. |
| **`isNaN`/`isInfinite`**| Verifica si un valor numérico es inválido ("Not a Number" o infinito). |
| **`size`** | Devuelve la cantidad de elementos de una colección. |
| **`sum`** | Suma todos los elementos numéricos (devuelve 0.0 si está vacía). |
| **`toList`** | Convierte otra estructura de datos (como un `Map` agrupado) en una Lista. |
| **`s""` y `f""`** | Interpoladores para inyectar variables en textos o aplicar formatos numéricos. |
| **`1 to cantidad`**| Crea un rango secuencial para reemplazar el contador de un ciclo `for`. |
| **`Unit`** | Equivalente a `void`. Indica que la función aplica un efecto (imprime) sin devolver un valor. |
| **`Some` / `None`**| Variaciones del tipo `Option`. `Some(x)` = éxito, `None` = vacío/ausencia. |
| **`Left` / `Right`**| Variaciones del tipo `Either`. `Left(x)` = error, `Right(x)` = éxito validado. |
| Tipos Primitivos | `Boolean` (V/F), `String` (Textos), `Int` (Enteros), `Double` (Decimales). |

---

## 2. Resumen de las 59 Funciones Personalizadas (Por Módulo)

El sistema se dividió en 6 módulos de responsabilidad única. A continuación, el detalle de cada función:

### Módulo 1: Modelo (Estructura de Datos)
1. **`valorEconomico`**: Multiplica las `horas` por el `costoPorHora` de una solicitud específica.
2. **`estaFinalizada`**: Comprueba si el estado de una solicitud es igual a la constante "Finalizado".

### Módulo 2: Validación (RF13)
3. **`validarTextoNoVacio`**: Verifica que campos como cliente o técnico no queden en blanco (removiendo espacios).
4. **`validarOpcion`**: Busca el texto ingresado dentro de las opciones válidas y devuelve su valor normalizado oficial.
5. **`validarNumeroNoNegativo`**: Intenta convertir el texto a decimal, verificando que no sea negativo y rechazando `NaN`.
6. **`validarCantidad`**: Comprueba que la cantidad de solicitudes a ingresar sea un entero mayor o igual a cero.
7. **`validarEnteroPositivo`**: Valida que un identificador (ID) sea estrictamente numérico y mayor que cero.
8. **`validarIdNoRepetido`**: Revisa si el ID recién ingresado ya existe en la lista de IDs registrados (evita duplicados).
9. **`validarId`**: Encadena formato y repetición usando `flatMap`.

### Módulo 3: Entrada (Interacción con el usuario)
10. **`leerDatoValido`**: Función recursiva que hace una pregunta, recibe el texto, lo valida y, si falla, vuelve a preguntar sin consumir pila de memoria.
11. **`leerCantidad`**: Usa `leerDatoValido` para preguntar "¿Cuántas solicitudes desea registrar?".
12. **`leerSolicitud`**: Llama a `leerDatoValido` ocho veces para construir un objeto `Solicitud` 100% verificado.
13. **`registrarSolicitudes`**: Repite `leerSolicitud` "N" veces usando `foldLeft`, acumulando solicitudes en una lista final.
14. **`registrar`**: Coordina el módulo; pide "N" y luego invoca el registro de esas "N" solicitudes.

### Módulo 4: Análisis (Cálculos de los reportes)
15. **`promedio`**: Suma los números de una lista y los divide por la cantidad (protegido contra divisiones por cero).
16. **`contarSegun`**: Función genérica de orden superior para contar cuántas solicitudes pertenecen a una agrupación.
17. **`solicitudesPendientes`**: Filtra las solicitudes con estado "Pendiente" (RF03).
18. **`solicitudesEnProceso`**: Filtra las solicitudes con estado "En proceso" (RF03).
19. **`separarPorFinalizacion`**: Divide la colección general en finalizadas y no finalizadas (RF14).
20. **`contarPorCategoria`**: Cuenta cuántas solicitudes hay por cada categoría (RF04).
21. **`categoriaMayorDemanda`**: Ordena el conteo de categorías para encontrar la principal (RF04).
22. **`valorTotal`**: Suma el valor económico de todas las solicitudes juntas (RF05).
23. **`valorPromedio`**: Calcula el promedio económico usando la función `promedio` (RF05).
24. **`tresMasCostosas`**: Ordena las solicitudes por precio para obtener el "Top 3" (RF06).
25. **`resumenPorTecnico`**: Agrupa por técnico para calcular: número de servicios, horas totales y terminados (RF07).
26. **`tecnicoMayorCarga`**: Extrae al técnico con más horas del resumen anterior (RF07).
27. **`clientesFrecuentes`**: Filtra a los clientes que poseen estrictamente más de una solicitud (RF08).
28. **`valorPorCliente`**: Agrupa y suma el dinero generado por cada cliente (RF09).
29. **`tresClientesMayorValor`**: Ordena a los clientes económicamente para sacar el "Top 3" (RF09).
30. **`clienteMayorValor`**: Extrae al cliente #1 en facturación para el resumen (RF14).
31. **`solicitudesCriticas`**: Filtra las de prioridad Alta que no estén finalizadas (RF10).
32. **`promedioHoras`**: Calcula el tiempo promedio que consumen todas las solicitudes (RF11).
33. **`solicitudesConHorasElevadas`**: Filtra las solicitudes que tomaron más tiempo que el promedio general (RF11).
34. **`contarPorPrioridad`**: Cuenta solicitudes agrupadas por Alta, Media y Baja (RF12).
35. **`porcentajePrioridadAlta`**: Calcula matemáticamente (Alta / Total * 100.0) el porcentaje (RF12).

### Módulo 5: Reportes (Presentación)
36. **`formatoDinero`**: Aplica formato de moneda (signo $, separador de miles, dos decimales).
37. **`formatoHoras`**: Aplica sufijo "h" y dos decimales a los tiempos.
38. **`formatoPorcentaje`**: Aplica sufijo "%" y dos decimales.
39. **`textoSolicitudes`**: Asegura concordancia gramatical ("1 solicitud" vs "N solicitudes").
40. **`textoOpcional`**: Transforma un `Option` (valor presente) o imprime "Sin datos" (valor nulo).
41. **`titulo`**: Imprime los encabezados visuales (`===`).
42. **`mostrarLista`**: Función genérica de presentación de elementos iterables.
43. **`numerar`**: Añade índices decorativos a los elementos de un top/ranking ("1.", "2.").
44. **`describirSolicitud`**: Organiza y concatena en texto los 8 datos de una solicitud para su impresión.
45. **`describirTecnico`**: Formatea el texto resumen visual de un técnico.
46. **`mostrarSolicitudes`**: Imprime el listado maestro (RF02).
47. **`mostrarSinFinalizar`**: Imprime listas separadas para pendientes y en proceso (RF03).
48. **`mostrarCategorias`**: Imprime conteo de categorías y el de mayor demanda (RF04).
49. **`mostrarAnalisisEconomico`**: Imprime el costo individual, total y promedio (RF05).
50. **`mostrarMasCostosas`**: Muestra las 3 solicitudes más caras (RF06).
51. **`mostrarTecnicos`**: Muestra el rendimiento de todos los técnicos y señala al líder en horas (RF07).
52. **`mostrarClientesFrecuentes`**: Muestra clientes con más de un servicio (RF08).
53. **`mostrarClientesMayorValor`**: Muestra el Top 3 de clientes rentables (RF09).
54. **`mostrarCriticas`**: Muestra detalles precisos de casos críticos (RF10).
55. **`mostrarHorasElevadas`**: Menciona el promedio e imprime las que se excedieron (RF11).
56. **`mostrarPrioridades`**: Muestra conteos y porcentaje de Alta prioridad (RF12).
57. **`mostrarResumenEjecutivo`**: Desempaqueta y muestra de golpe 8 indicadores globales tipo dashboard (RF14).
58. **`mostrarTodosLosReportes`**: Llama consecutivamente los reportes RF02 al RF12.

### Módulo 6: Main
59. **`main`**: Método de inicio de la aplicación en la JVM. Coordina la lectura y la impresión en consola.