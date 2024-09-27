(ns fib.04-naive-memoized
  (:require [fib.03-naive :refer [fib]]
            [fib.helpers :refer [time-map]]))

;; So the problem is that we do duplicate work.
;; We know for a fact that (fib n) is always the same for a given n

;; The trick is memoization:
;;   Create a function that remembers which arguments caused what result.
(def fib-memoized-1 (memoize fib))





;; Still not particularly fast?
(time-map (fib-memoized-1 40))

;; But evaluating it again is fast. This is where the memoization kicked in!
(time-map (fib-memoized-1 40))





;; Didn't we expect the first invocation to be fast too? What makes it slow?
