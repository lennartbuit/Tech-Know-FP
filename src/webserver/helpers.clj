(ns webserver.helpers
  (:require [ring.adapter.jetty :refer [run-jetty]]))

(def server (atom nil))

(defn restart!
  "Start a webserver, if one is already running, stop it"
  [app]
  (swap! server
         (fn [server]
           (when server
             (.stop server))
           (run-jetty app {:port 3000 :join? false}))))
