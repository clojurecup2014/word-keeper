(ns word-keeper.frontend
  (:require [clostache.parser :refer [render-resource]]
            [word-keeper.auth :refer [authorize twitter-auth-uri]]))

(defn action-index [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-resource
            "views/index.html.mustache"
            {:twitter-auth-uri twitter-auth-uri})})

(defn action-signin [req]
  (let [user-data (authorize (-> req :params :verifier))]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (str "Signed in as " (:screen_name user-data))}))
