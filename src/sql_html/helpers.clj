(ns sql-html.helpers
  (:require [next.jdbc :as jdbc]
            [honey.sql :as sql]))

(def create-user-table
  {:create-table [:users :if-not-exists]
   :with-columns
   [[:id :integer [:not nil] [:primary-key]]
    [:name [:varchar 32] [:not nil]]
    [:age :integer [:not nil]]
    [:gender [:varchar 32] [:not nil]]]})

(defn create-user-database
  [users]
  (let [db-spec {:dbtype "sqlite" :dbname (str "data/test-" (System/currentTimeMillis) ".db")}
        ds      (jdbc/get-datasource db-spec)]
    (jdbc/execute! ds (sql/format create-user-table))
    (jdbc/execute! ds (sql/format {:insert-into [:users]
                                   :columns     [:name :age :gender]
                                   :values      (for [{:keys [name age gender]} users]
                                                  [name age gender])}))
    ds))
