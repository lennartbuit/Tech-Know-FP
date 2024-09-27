(ns banking.11-mutable
  (:require [banking.helpers :refer [->Account]]))

;; -------------------------------------------------------------------------- ;;
;; Fourth and final example: Immutability                                     ;;
;; Goal: Learning about the difference between identity and state.            ;;
;; -------------------------------------------------------------------------- ;;


;; Immutability makes for predictable programs.
;; To show you, I'll first show what could go wrong.

;; Imagine there is some Java class, Account, that is mutable.
;; It has two methods, getBalance and setBalance





;; So we can now define a function, transfer!, moving money between accounts
(defn transfer! [from-acc to-acc amount]
  (.setBalance from-acc (- (.getBalance from-acc) amount))
  (.setBalance to-acc (+ (.getBalance to-acc) amount)))

;; And a function, total-balance, that sums the total balance of many accounts
(defn total-balance [& accounts]
  (reduce + (map #(.getBalance %) accounts)))





(let [;; Initializing a bunch of accounts
      lennart-account     (->Account 1000)
      nedap-account       (->Account 1000000)
      pizza-place-account (->Account 0)

      ;; Getting the balance beforehand
      starting-balance    (total-balance lennart-account
                                         nedap-account
                                         pizza-place-account)


      ;; Nedap pays me 2000 euro 100 times
      nedap-paying-me     (Thread. (fn []
                                     (dotimes [_ 100]
                                       (transfer! nedap-account
                                                  lennart-account
                                                  2000))))

      ;; I buy pizzas for 5 euros 1000 times
      me-buying-pizza    (Thread. (fn []
                                    (dotimes [_ 1000]
                                      (transfer! lennart-account
                                                 pizza-place-account
                                                 5))))]

  ;; Nedap pays me and while I am buying pizzas
  (.start nedap-paying-me)
  (.start me-buying-pizza)

  ;; Waiting for that to be done.
  (.join nedap-paying-me)
  (.join me-buying-pizza)

  ;; ❌ HEY WE ARE LOSING MONEY HERE, different amount each time even!
  {:before starting-balance
   :after (total-balance lennart-account nedap-account pizza-place-account)})





;; Observant programmers would say that transfer! is not thread safe and would
;; suggest locking.

;; That sounds like a solution, but what is the problem?
