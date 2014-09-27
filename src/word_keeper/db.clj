(ns word-keeper.db
  (:require [yesql.core :refer [defquery]]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/wordkeeper"
              :user "cloudsigma"
              :password "wordkeeper"})

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

(defquery select-twitter-user "sql/select-twitter-user.sql")
(defn find-twitter-user [twitter-id] (first (select-twitter-user db-spec twitter-id)))

(defquery insert-user "sql/insert-user.sql")
(defn create-user [] (insert-user db-spec))

(defquery insert-twitter-user "sql/insert-twitter-user.sql")
(defn create-twitter-user
  ([twitter-id twitter-name]
     (let [uid (-> (create-user) first :id)]
       (create-twitter-user twitter-id twitter-name uid)))
  ([twitter-id twitter-name uid]
     (insert-twitter-user db-spec twitter-id twitter-name uid)))
