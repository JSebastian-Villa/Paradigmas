
#lang racket

(struct servicio (codigo cliente horas valor-hora estado)
  #:transparent)

(define (leer-dato mensaje tipo)
  (display mensaje)
  (if (eq? tipo 'numero)
      (string->number (read-line))
      (read-line)))

(define (registrar-servicios cantidad)
  (if (= cantidad 0)
      '()
      (let* ([codigo (leer-dato "Código del servicio: " 'numero)]
             [cliente (leer-dato "Nombre del cliente: " 'texto)]
             [horas (leer-dato "Horas trabajadas: " 'numero)]
             [valor-hora (leer-dato "Valor de la hora: " 'numero)]
             [estado (leer-dato "Estado (Abierto/Cerrado): " 'texto)])
        (cons
         (servicio codigo cliente horas valor-hora estado)
         (registrar-servicios (- cantidad 1))))))

(define (servicio-cerrado? s)
  (string-ci=? (servicio-estado s) "Cerrado"))

(define (subtotal-servicio s)
  (* (servicio-horas s)
     (servicio-valor-hora s)))

(define (descuento-servicio s)
  (let ([subtotal (subtotal-servicio s)])
    (* subtotal
       (if (>= subtotal 300000)
           0.08
           0))))

(define (total-servicio s)
  (- (subtotal-servicio s)
     (descuento-servicio s)))

(define (total-facturado servicios)
  (foldl
   +
   0
   (map total-servicio
        (filter servicio-cerrado? servicios))))

(define (ordenar-por-total servicios)
  (sort servicios
        (lambda (a b)
          (> (total-servicio a)
             (total-servicio b)))))

(define (mostrar-servicio s)
  (printf "\nCódigo: ~a\n" (servicio-codigo s))
  (printf "Cliente: ~a\n" (servicio-cliente s))
  (printf "Horas trabajadas: ~a\n" (servicio-horas s))
  (printf "Subtotal: $~a\n" (subtotal-servicio s))
  (printf "Descuento: $~a\n" (descuento-servicio s))
  (printf "Total: $~a\n" (total-servicio s)))

(display "Cantidad de servicios a registrar: ")
(define cantidad (string->number (read-line)))

(define servicios (registrar-servicios cantidad))
(define cerrados (filter servicio-cerrado? servicios))
(define ordenados (ordenar-por-total cerrados))

(displayln "\nSERVICIOS CERRADOS")
(for-each mostrar-servicio ordenados)

(printf "\nTotal general facturado: $~a\n"
        (total-facturado servicios))

(if (null? ordenados)
    (displayln "No existen servicios cerrados.")
    (begin
      (displayln "\nSERVICIO DE MAYOR VALOR")
      (mostrar-servicio (first ordenados))))
