
// Imperativo
// Encontrar el número max de una lista

import java.util.*;


public class introduccion_java {
        public static void main(String[] args) {
            List<Integer> numeros = Arrays.asList(0, 4, 5, 6, 7);
            int max= numeros.get(0); // Inicializa el primer número
        
        for (Integer n: numeros) {
            if (n > max) {
                    max = n;
            }
        }
        System.out.println("El número máximo es: " + max);
    }
}

