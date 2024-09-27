(ns sql-html.07-html
  (:require [hiccup2.core :as h]))

;; There is also such a "data DSL" for HTML called hiccup.

;; Lets say we want to make a (HTML) definition list, with name, age, gender
;; only if they are present
(defn definition-list-for-person
  [{:keys [name age gender]}]
  (into [:dl]
        ;; Thats cond-> again!
        ;; Using the same tools to create HTML that we used to create SQL!
        (cond-> []
          (some? name)   (conj [:dt "Name"]   [:dd name])
          (some? age)    (conj [:dt "Age"]    [:dd age])
          (some? gender) (conj [:dt "Gender"] [:dd gender]))))





;; We can get the HTML as data
(definition-list-for-person {:name "Lennart" :age 27 :gender "male"})

;; Or as actual HTML
(str (h/html (definition-list-for-person {:name   "Lennart"
                                          :age    27
                                          :gender "male"})))
