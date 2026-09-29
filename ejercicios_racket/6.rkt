#lang racket

; EJERCICIO 6 - DESCUENTO

(define (calcular-descuento compra)
  (cond
    [(< compra 100000) 0]
    [(< compra 300000) 0.05]
    [(< compra 500000) 0.10]
    [else 0.15]))

(display "Ingrese el valor de la compra: $")
(define compra (string->number (read-line)))

(define porcentaje (calcular-descuento compra))
(define descuento (* compra porcentaje))
(define total (- compra descuento))

(printf "\nValor compra: $~a\n" compra)
(printf "Descuento: $~a\n" descuento)
(printf "Total a pagar: $~a\n" total)