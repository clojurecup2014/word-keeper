(ns word-keeper.db
  (:require [yesql.core :refer [defquery]]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/wordkeeper"
              :user "cloudsigma"})

(defquery select-english-russian "sql/select-english-russian.sql")
(defquery select-russian-english "sql/select-russian-english.sql")

(defn table-name [from to]
  (let [langs {:en "english"
               :ru "russian"}]
    (str (from langs) "-" (to langs))))

(defn find-translations [uid from to]
  (if (= from :en)
    (select-english-russian db-spec uid)
    (select-russian-english db-spec uid)))
