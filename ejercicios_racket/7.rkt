#lang racket

; EJERCICIO 7 - VALIDAR LOGIN

(define (validar-login usuario clave)
  (and
   (string=? usuario "admin")
   (string=? clave "racket2026")))

(display "Usuario: ")
(define usuario (read-line))

(display "Contraseña: ")
(define clave (read-line))

(if (validar-login usuario clave)
    (displayln "Bienvenido acá.")
    (displayln "Usuario o contraseña incorrectos."))