(ns fib.helpers
  (:refer-clojure :exclude [time]))

;; Special time macro that prints a little nicer
(defmacro time-map
  [exp]
  `(let [start# (System/currentTimeMillis)
         ret# ~exp
         stop# (System/currentTimeMillis)]
     {:result ret# :ms (- stop# start#)}))


(comment
  (time-map {:a 1}))
