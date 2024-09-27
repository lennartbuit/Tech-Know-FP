(ns banking.12-immutable)

;; We call a thing a value if it never changes. It is immutable.
;; 4 is a value. (Also true in many (if not all) other programming languages)

;; What is special is that composites are also values in Clojure:
(def account {:balance 1000})





;; Evaluate account
account

;; Produce a different value with balance increased by 500
(update account :balance (fn [current] (+ current 500)))

;; Note how update did not change the previously captured account value!
account





;; So that poses a problem right?
;; Thread 1 produces different value but thread 2 happily sees the old value.

;; What we need is a way to say, successive values belong to the same thing.
;; In Clojures jargon, the same identity.

;; Asking the state of an identity gives you a value, an immutable snapshot.
;; E.g. A screenshot of your banking app.

;; Asking the state of an identity at a later moment, gives you another value.
;; E.g. A different screenshot of your banking app.
;; But did asking for the state again change the first screenshot? No!





;; In Clojure one way to model identities is by using refs.
(def account-identity (ref {:balance 1000}))

;; We can ask for its current state and get a value
;; In Clojure jargon, we derefence it.
(def before @account-identity)
before

;; We can change the state of this identity to point to a new value
(dosync
 ;; Alter account-identities state by applying some function to its current
 ;; value producing a new value.
 (alter account-identity update :balance (fn [current] (+ current 500))))

;; And now it state points to a new value
(def after @account-identity)
after

;; But the before value did not change, they are still snapshots!
(= before after)





;; So lets do the same thing as before, but seperate identity from state.
;; Again, a function to move money between accounts. Adapted for use with refs.
(defn transfer! [from-acc to-acc amount]
  (dosync
   (alter from-acc update :balance (fn [current] (- current amount)))
   (alter to-acc update :balance (fn [current] (+ current amount)))))

;; And a function to get the total balance of all accounts.
(defn total-balance [& accounts]
  (reduce + (map (fn [acc] (:balance @acc)) accounts)))





;; And we do basically the same
(let [;; But this time, we model accounts as refs
      lennart-account     (ref {:balance 1000})
      nedap-account       (ref {:balance 1000000})
      pizza-place-account (ref {:balance 0})

      ;; The rest stays the same
      starting-balance    (total-balance lennart-account
                                         nedap-account
                                         pizza-place-account)
      nedap-paying-me     (Thread. (fn []
                                     (dotimes [_ 100]
                                       (transfer! nedap-account
                                                  lennart-account
                                                  2000))))
      me-buying-pizza     (Thread. (fn []
                                     (dotimes [_ 1000]
                                       (transfer! lennart-account
                                                  pizza-place-account
                                                  5))))]
  (.start nedap-paying-me)
  (.start me-buying-pizza)
  (.join nedap-paying-me)
  (.join me-buying-pizza)

  ;; ✅ And now the total balance stays consistent.
  {:before starting-balance
   :after  (total-balance lennart-account nedap-account pizza-place-account)})

;; So what happened here?

;; The original implementation was mixing identity and state.
;; => The Account object is both!

;; Clojure forced us to seperate these two, there is an account identity
;; that just has a succession of state values. It does not mix these concepts!

;; In using these tools (ref, dosync, alter) we managed to write a concurrent
;; program that is correct.

;; And what I like most: No headaches about locking! We really solved our
;; problem by understanding it better!
