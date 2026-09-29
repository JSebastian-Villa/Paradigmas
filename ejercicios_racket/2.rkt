#lang racket

; EJERCICIO 2 - SUMA DE DOS NÚMEROS

(display "ingrese el primer numero:")
(define numero1 (string->number (read-line)))

(display "ingrese el segundo numero:")
(define numero2 (string->number (read-line)))

(define suma (+ numero1 numero2))

(printf "la suma de ~a +~a es: ~a\n"
        numero1
        numero2
        suma)