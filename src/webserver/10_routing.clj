(ns webserver.10-routing
  (:require [webserver.helpers :refer [restart!]]
            [bidi.bidi :as bidi]
            [bidi.ring :refer [make-handler]]
            [ring.middleware.json :refer [wrap-json-response]]))

;; Alright, but how do you do routing then?
;;
;; Routing is nothing more than calling the right handler, wouldn't you say?
;; Bidi is such a library.

;; First, little bit of mock data
(def cats
  [{:id 1 :name "Snoopy" :playful false}
   {:id 2 :name "Bounty" :playful true}])





;; Second, a few handlers
(defn cat-index
  "Returns a list of all cats Lennarts household has."
  [_request]
  {:status 200
   ;; Simulating that the index probably doesn't return all data
   :body  (map (fn [cat] (select-keys cat [:id :name]))
               cats)})

(defn cat-show
  "Returns details about a specific cat Lennarts household has."
  [{{id-param :id} :route-params}]
  (let [id  (parse-long id-param)
        cat (first (filter (fn [cat] (= id (:id cat)))
                           cats))]
    (if cat
      {:status 200 :body cat}
      {:status 404})))

(defn not-found
  [_request]
  {:status 404})





;; Alright, routes.
;; This is bidi specific routing syntax (written as Clojure data!).
(def routes
  ["/" {"cats"   {:get {""        cat-index
                        ["/" :id] cat-show}}

        true     not-found}])





;; Does that work like we expect?
(bidi/match-route routes "/cats" :request-method :get)

(bidi/match-route routes "/cats/12" :request-method :get)

(bidi/match-route routes "/aliens" :request-method :get)

(bidi/match-route routes "/cats" :request-method :post)





(def handler
  ;; bidi has a nice method to make this "dispatching handler"
  (make-handler routes))

;; Alright building an app
(def app
  (wrap-json-response handler))





;; We can once again start the app
(restart! app)

;; But we can still evaluate at any point in the chain
(handler {:request-method :get
          :uri            "/cats/1"})
