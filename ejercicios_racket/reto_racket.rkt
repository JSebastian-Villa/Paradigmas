#lang racket

(define (calcular-tarifa-base vehiculo)
	(cond
		[(string=? vehiculo "bicicleta") 4000]
		[(string=? vehiculo "moto") 6000]
		[else 10000]))

(define (calcular-kilometros-adicionales distancia)
	(if (> distancia 5)
			(- distancia 5)
			0))

(define (calcular-valor-kilometro vehiculo)
	(cond
		[(string=? vehiculo "bicicleta") 800]
		[(string=? vehiculo "moto") 1200]
		[else 2000]))

(define (calcular-costo-adicional vehiculo distancia)
	(* (calcular-kilometros-adicionales distancia)
		 (calcular-valor-kilometro vehiculo)))

(define (calcular-porcentaje-recargo hora)
	(cond
		[(or (>= hora 22) (< hora 6)) 0.20]
		[(>= hora 20) 0.10]
		[else 0]))

(define (calcular-recargo vehiculo distancia hora)
	(* (+ (calcular-tarifa-base vehiculo)
				(calcular-costo-adicional vehiculo distancia))
		 (calcular-porcentaje-recargo hora)))

(define (calcular-descuento vehiculo distancia hora domicilios)
	(if (>= domicilios 10)
			(* (+ (calcular-tarifa-base vehiculo)
						(calcular-costo-adicional vehiculo distancia)
						(calcular-recargo vehiculo distancia hora))
				 0.05)
			0))

(define (calcular-total vehiculo distancia hora domicilios)
	(- (+ (calcular-tarifa-base vehiculo)
				(calcular-costo-adicional vehiculo distancia)
				(calcular-recargo vehiculo distancia hora))
		 (calcular-descuento vehiculo distancia hora domicilios)))

(display "Nombre del cliente: ")
(define cliente (read-line))

(display "Vehiculo (bicicleta, moto o carro): ")
(define vehiculo (read-line))

(display "Distancia recorrida en kilometros: ")
(define distancia (string->number (read-line)))

(display "Hora del servicio (0 a 23): ")
(define hora (string->number (read-line)))

(display "Domicilios realizados durante el mes: ")
(define domicilios (string->number (read-line)))

(define tarifa-base (calcular-tarifa-base vehiculo))
(define kilometros-adicionales (calcular-kilometros-adicionales distancia))
(define costo-adicional (calcular-costo-adicional vehiculo distancia))
(define recargo (calcular-recargo vehiculo distancia hora))
(define descuento (calcular-descuento vehiculo distancia hora domicilios))
(define total (calcular-total vehiculo distancia hora domicilios))

(printf "\nLiquidacion del domicilio\n")
(printf "Cliente: ~a\n" cliente)
(printf "Vehiculo: ~a\n" vehiculo)
(printf "Distancia recorrida: ~a km\n" distancia)
(printf "Tarifa base: $~a\n" tarifa-base)
(printf "Kilometros adicionales: ~a\n" kilometros-adicionales)
(printf "Costo por kilometros adicionales: $~a\n" costo-adicional)
(printf "Recargo por horario: $~a\n" recargo)
(printf "Descuento: $~a\n" descuento)
(printf "Total a pagar: $~a\n" total)

