(ns sql-html.06-sql
  (:require [sql-html.helpers :refer [create-user-database]]
            [next.jdbc :as jdbc]
            [clojure.string :as str]
            [honey.sql :as sql]))

;; -------------------------------------------------------------------------- ;;
;; Second example: SQL and HTML generation                                    ;;
;; Goal: Learning about generic datastructures.                               ;;
;; -------------------------------------------------------------------------- ;;


;; So you heard me talk about data and we saw some operations on data.
;; But what makes it really powerful is using data as your DSL.

(def users
  [{:name "Tom"     :age 25 :gender "male"}
   {:name "Frida"   :age 40 :gender "female"}
   {:name "Charles" :age 37 :gender "male"}
   {:name "Hank"    :age 78 :gender "male"}
   {:name "Mary"    :age 66 :gender "female"}
   {:name "Anny"    :age 12 :gender "female"}])

;; First we create a database
(def ds (create-user-database users))





;; We can now query it as normal
(jdbc/execute! ds ["SELECT * FROM users LIMIT 1"])

;; And we can also find all females between 30 and 70
(jdbc/execute! ds
               ["SELECT name FROM users WHERE age >= ? AND age < ? AND gender = ?" 30 70 "female"])





;; But it becomes annoying when it becomes dynamic
(defn get-users-sql [{:keys [age-gte age-lt gender]}]
  (let [args (remove nil? [age-gte age-lt gender])
        query (str "SELECT name FROM users"
                   (when (seq args)
                     (str " WHERE "
                          (str/join " AND "
                                    (remove nil?
                                            [(when age-lt "age < ?")
                                             (when age-gte "age >= ?")
                                             (when gender "gender = ?")])))))]
    (into [query] args)))





;; We can still inspect its SQL
(get-users-sql {:age-gte 30})

;; Or execute it
(jdbc/execute! ds (get-users-sql {:age-gte 30}))

(jdbc/execute! ds (get-users-sql {:age-gte 30 :gender "female"}))

(jdbc/execute! ds (get-users-sql {}))






;; But we can also express the query as data
(defn get-users-honey-sql
  [{:keys [age-gte age-lt gender]}]
  {:select :users/name
   :from   :users
   :where  (cond-> [:and]
                   ;; cond-> threads data through conditionally:
                   ;; 1) when (some? age-lt) is true
                   ;; 2) then conj(oin) the vector [:< :users/age age-lt]
                   ;;    to the running total
                   (some? age-lt)  (conj [:< :users/age age-lt])
                   (some? age-gte) (conj [:>= :users/age age-gte])
                   (some? gender)  (conj [:= :users/gender gender]))})





;; So we can inspect the resulting map
(get-users-honey-sql {:age-lt 70 :age-gte 30})

;; And how that looks as an SQL query
(sql/format (get-users-honey-sql {:age-lt 70}))

;; Or just execute it
(jdbc/execute! ds (sql/format (get-users-honey-sql {:gender "female"})))

(jdbc/execute! ds (sql/format (get-users-honey-sql {:age-gte 30
                                                    :age-lt  70
                                                    :gender  "female"})))
