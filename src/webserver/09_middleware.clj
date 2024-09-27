(ns webserver.09-middleware
  (:require [webserver.helpers :refer [restart!]]
            [clojure.string :as str])
  (:import (java.time LocalDate)))

;; Thats not super exciting yet!
;; What if we want to wish you a happy Thursday?
;;
;; In Ring, we can define middleware to add context to a request.
;; Middleware in Ring is like a decorator in Python.
;; Its a function that "wraps" around a function.

;; So here is that middleware, its a function, `wrap-now`
(defn wrap-now [handler]
  ;; That returns a function accepting a request
  (fn [request]
    (let [with-now (assoc request :today (LocalDate/now))]
      ;; That calls the next function with that request amended with today
      (handler with-now))))





;; Once again, we can define a handler using that :today value
(defn handler [request]
  {:status 200
   :body (str "Hello, what a great "
              (str/capitalize (.getDayOfWeek (:today request)))
              "!")})






;; So, to compose that, we just call `wrap-now` with `handler`
(def app
  (wrap-now handler))

;; We can start it again, works in the browser
(restart! app)





;; We can still call our handler, but need to provide a :today value
;; Bonus points, it can timetravel now!
(handler {:today (LocalDate/of 1995 3 12)})

;; Or we can call our entire app, its just a function
(app {})

;; And we can even test our middleware, see if it does what we expect
((wrap-now identity) {})
