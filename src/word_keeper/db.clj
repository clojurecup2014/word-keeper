(ns word-keeper.db
  (:require [yesql.core :refer [defquery]]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/wordkeeper"
              :user "cloudsigma"
              :password "wordkeeper"})

(defquery select-twitter-user "sql/select-twitter-user.sql")
(defn find-twitter-user [twitter-id] (first (select-twitter-user db-spec twitter-id)))

(defquery select-twitter-user-by-uid "sql/select-twitter-user-by-uid.sql")
(defn find-twitter-user-by-uid [uid]
  (first
   (select-twitter-user-by-uid db-spec uid)))

(defquery insert-user<! "sql/insert-user.sql")
(defn create-user! [] (insert-user<! db-spec))

(defquery insert-twitter-user! "sql/insert-twitter-user.sql")
(defn create-twitter-user!
  ([twitter-id twitter-name]
     (let [uid (-> (create-user!) :id)]
       (create-twitter-user! twitter-id twitter-name uid)))
  ([twitter-id twitter-name uid]
     (insert-twitter-user! db-spec twitter-id twitter-name uid)))
