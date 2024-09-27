(ns banking.helpers)

(definterface Mutation
  (setBalance [new-val])
  (getBalance []))

(deftype Account [^:unsynchronized-mutable balance]
  Mutation
  (setBalance [_ new-val] (set! balance new-val))
  (getBalance [_] balance))
