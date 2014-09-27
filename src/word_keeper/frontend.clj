(ns word-keeper.frontend
  (:require [clostache.parser :refer [render-resource]]
            [word-keeper.auth :refer [twitter-auth-uri]]))

(defn action-index [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-resource
            "views/index.html.mustache"
            {:twitter-auth-uri twitter-auth-uri})})
