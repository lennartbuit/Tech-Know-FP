(ns fib.05-memoized
  (:require [fib.helpers :refer [time-map]]))

;; So the problem is that we only memoize the "outer" Fibonacci call.
(defn fib-memoized-2
  [n]
  (let [;; We need a trick: The recursive call must refer to the memoized fib.

        ;; Define a function, step, taking a function, next, as argument.
        ;; Crucially, we can pass memoized step as this next argument!
        step (fn [next n]
                 (case n
                   0 0
                   1 1
                   ;; And for the recursive step we call `next`.
                   (+ (next next (- n 1))
                      (next next (- n 2)))))
        ;; Now, we memoize step
        memoized-step (memoize step)]
    ;; Call memoized-step with itself, making all references to fib memoized!
    (memoized-step memoized-step n)))






(time-map (fib-memoized-2 40))
