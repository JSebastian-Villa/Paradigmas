import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Realizar un programa en Java con programación funcional:

// 1. crear empleados,
// 2. filtrar por salario, área o antigüedad,
// 3. calcular bonificaciones,
// 4. validar si un empleado aplica a un beneficio,
// 5. actualizar salarios,
// 6. acumular masa salarial,
// 7. ejecutar reportes.


class Empleado {
    private String nombre;
    private double salario;
    private String area;
    private int antiguedad;

        public Empleado(String nombre, double salario, String area, int antiguedad) {
        this.nombre = nombre;
        this.salario = salario;
        this.area = area;
        this.antiguedad = antiguedad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalario() {
        return salario;
    }

    public String getArea() {
        return area;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", salario=" + salario +
                ", area='" + area + '\'' +
                ", antiguedad=" + antiguedad +
                '}';
    }
}

public class ejercicio_bonificacion {

    public static void main(String[] args) throws Exception {

        List<Empleado> empleados = new ArrayList<>(Arrays.asList(
                new Empleado("Juan", 3500.00, "Tecnologia", 8),
                new Empleado("Maria", 8000.0, "Gerencia", 25),
                new Empleado("Pedro", 1500.0, "Ventas", 10),
                new Empleado("Ana", 6000.0, "Hogar", 4),
                new Empleado("Luis", 12000.0, "Recuros humanos", 3)
        ));

        // Supplier<T>: genera un empleado nuevo
        java.util.function.Supplier<Empleado> proveedorEmpleado =
                () -> new Empleado("Miguel Angel", 2000.00, "Recursos humanos", 5);

        Empleado nuevoEmpleado = proveedorEmpleado.get();
        empleados.add(nuevoEmpleado);

        // Filtrar empleados con salario mayor a 3000
        java.util.function.Predicate<Empleado> esSalarioAlto =
                e -> e.getSalario() > 3000;

        List<Empleado> empleadosSalarioAlto = empleados.stream()
                .filter(esSalarioAlto)
                .collect(Collectors.toList());

        // Filtrar empleados de Tecnologia
        java.util.function.Predicate<Empleado> esTecnologia =
                e -> e.getArea().equals("Tecnologia");

        List<Empleado> empleadosTecnologia = empleados.stream()
                .filter(esTecnologia)
                .collect(Collectors.toList());

        // Filtrar empleados con 5 años o más
        java.util.function.Predicate<Empleado> tieneAntiguedad =
                e -> e.getAntiguedad() >= 5;

        List<Empleado> empleadosAntiguos = empleados.stream()
                .filter(tieneAntiguedad)
                .collect(Collectors.toList());

        // Function<T,R>: obtener nombre del empleado
        // java.util.function.Function<Empleado, String> obtenerNombre = Empleado::getNombre;

        // List<String> nombres = empleados.stream()
        //         .map(obtenerNombre)
        //         .collect(Collectors.toList());

        // Consumer<T>: mostrar empleados
        // java.util.function.Consumer<Empleado> mostrarEmpleados = e -> System.out.println("Empleado: " + e);

        // System.out.println("=== Lista de empleados ===");
        // empleados.forEach(mostrarEmpleados);

        // BiFunction <T,U,R>: calcular bonificación

        java.util.function.BiFunction<Double, Double, Double> calcularBonificacion =
                (salario, porcentaje) -> salario * porcentaje / 100;

        double bonificacionPedro = calcularBonificacion.apply(1500.0, 10.0);
        System.out.println("\nBonificacion de Pedro con el 10%: " + bonificacionPedro);

        // BiPredicate <T,U>: validar beneficio

        // BiPredicate<T,U>: validar si un empleado aplica a un beneficio
        // El beneficio aplica si tiene 5 años o más de antigüedad
        java.util.function.BiPredicate<Empleado, Integer> aplicaBeneficio =
                (empleado, antiguedadMinima) ->
                        empleado.getAntiguedad() >= antiguedadMinima;

        System.out.println(
                "\n¿Ana aplica al beneficio? "
                        + aplicaBeneficio.test(empleados.get(1), 5)
        );

        // UnaryOperator<T>: actualizar salario
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

        System.out.println("\n=== Empleados con salario actualizado ===");
        empleadosActualizados.forEach(System.out::println);

        // BinaryOperator<T>: acumular masa salarial
        java.util.function.BinaryOperator<Double> sumarSalarios =
                Double::sum;

        double masaSalarial = empleadosActualizados.stream()
                .map(Empleado::getSalario)
                .reduce(0.0, sumarSalarios);

        System.out.println(
                "\nMasa salarial total: "
                        + masaSalarial
        );

        // Runnable: ejecutar reporte
        Runnable tareaReporte =
                () -> {
                    System.out.println("\n=== REPORTE GENERAL ===");

                    System.out.println(
                            "Cantidad de empleados: "
                                    + empleados.size()
                    );

                    System.out.println(
                            "Empleados con salario mayor a 3000: "
                                    + empleadosSalarioAlto.size()
                    );

                    System.out.println(
                            "Empleados de Tecnologia: "
                                    + empleadosTecnologia.size()
                    );

                    System.out.println(
                            "Empleados con 5 años o más: "
                                    + empleadosAntiguos.size()
                    );

                    System.out.println(
                            "Masa salarial total: "
                                    + masaSalarial
                    );
                };

        tareaReporte.run();

        }
}
