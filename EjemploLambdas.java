import java.util.Arrays;
import java.util.List;
import java.util.function.*;


public class EjemploLambdas {
    public static void main(String[] args) throws Exception {

    //Lista base
    List<Integer>numeros = Arrays.asList(1,2,3,4,5);

    //1 predicate <T>
    Predicate<Integer> esPar = n -> n % 2 == 0;
    System.out.println("Es par: " + esPar.test(4));


    //2 Function <T,R>
    Function<Integer, String> convertir = n -> "Numero: " + n;
    System.out.println("Convertir 5 ->" + convertir.apply(5));

    //3 Consumer <T>
    Consumer<Integer> imprimir = n -> System.out.println("Valor: " + n);
    numeros.forEach(imprimir);

    //4 Supplier <T>
    Supplier<Double> aleatorio = () -> Math.random();
    System.out.println("Numero aleatorio: " + aleatorio.get());

    //5



    //6 BiPredicate <T,U>
    BiPredicate<Integer, Integer> mayorQue = (a, b) -> a > b;
    System.out.println("5 es mayor que 3: " + mayorQue.test(5, 3));
    
}
