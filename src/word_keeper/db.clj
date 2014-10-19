(ns word-keeper.db
  (:require [yesql.core :refer [defquery]]))

(def db-subname
  (str
    "//"
    (get (System/getenv) "OPENSHIFT_POSTGRESQL_DB_HOST" "localhost")
    ":"
    (get (System/getenv) "OPENSHIFT_POSTGRESQL_DB_PORT" "5432")
    "/"
    (get (System/getenv) "OPENSHIFT_APP_NAME" "wordkeeper")))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname db-subname
              :user
                (get (System/getenv) "OPENSHIFT_POSTGRESQL_DB_USERNAME" "cloudsigma")
              :password
                (get (System/getenv) "OPENSHIFT_POSTGRESQL_DB_PASSWORD" "wordkeeper")})

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

(defquery select-languages "sql/select-languages.sql")
(defn find-languages []
  (select-languages db-spec))

(defquery select-language "sql/select-language.sql")
(defn find-language [id]
  (first (select-language db-spec id)))

(defquery select-words "sql/select-words.sql")
(defn find-words [lang]
  (select-words db-spec lang))

(defquery select-vocabularies "sql/select-vocabularies.sql")
(defn find-vocabs [uid lang]
  (select-vocabularies db-spec uid lang))
