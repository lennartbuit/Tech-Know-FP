(ns webserver.08-simple
  (:require [webserver.helpers :refer [restart!]]))

;; -------------------------------------------------------------------------- ;;
;; Third example: Webserver                                                   ;;
;; Goal: Learning the benefit of functions as units of composition.           ;;
;; -------------------------------------------------------------------------- ;;


(defn handler [_request]
  {:status 200
   :body "Hello, World!"})





;; We can start a webserver
(restart! handler)






;; But, we can also just call our handler
(handler {})
