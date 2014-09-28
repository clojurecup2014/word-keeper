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

(defquery insert-russian "sql/insert-russian.sql")
(defn create-russian! [word] (insert-russian db-spec word))

(defquery insert-english "sql/insert-english.sql")
(defn create-english! [word] (insert-english db-spec word))

(defquery insert-english-russian<! "sql/insert-english-russian.sql")
(defn create-english-russian! [uid wid translation]
  (insert-english-russian<! db-spec uid wid translation))
