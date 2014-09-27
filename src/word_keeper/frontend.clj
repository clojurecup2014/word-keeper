(ns word-keeper.frontend
  (:require [clostache.parser :refer [render-resource]]))

(defn action-index [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-resource
            "views/index.html.mustache"
            nil)})
