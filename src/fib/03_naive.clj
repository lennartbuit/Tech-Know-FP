(ns fib.03-naive
  (:refer-clojure :exclude [time])
  (:require [fib.helpers :refer [time-map]]))

;; -------------------------------------------------------------------------- ;;
;; First example: Fibonacci sequence.                                         ;;
;; Goal: Learning about the benefit of pure functions.                        ;;
;; -------------------------------------------------------------------------- ;;


;; ❌ Not the most efficient way of writing the fibonacci sequence in Clojure.
;; If you are interested, you can ask me about it offline 😉.
(defn fib
  [n]
  (case n
    0 0
    1 1
    (+ (fib (- n 1))
       (fib (- n 2)))))





;; Not particularly fast
(time-map (fib 40))




;; What makes this slow?
