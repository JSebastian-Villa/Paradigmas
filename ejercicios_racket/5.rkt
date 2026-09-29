#lang racket

; EJERCICIO 5 - ACCESO CON AND

(display "Nombre del empleado: ")
(define nombre (read-line))

(display "Edad: ")
(define edad (string->number (read-line)))

(display "¿Tiene identificación de la clase virtual empresarial? (si/no): ")
(define identificacion (read-line))

(if (and (>= edad 18)
         (string-ci=? identificacion "si"))
    (printf "Acceso autorizado para ~a.\n" nombre)
    (printf "Acceso denegado para ~a.\n" nombre))