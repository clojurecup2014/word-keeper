(ns word-keeper.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET]]
            [compojure.handler :refer [site]]
            [clostache.parser :refer [render-resource]]))

(defn action-index [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-resource
            "views/index.html.mustache"
            nil)})

(defroutes routes
  (GET "/" [] action-index))

(defn -main [& args]
  (run-server (site #'routes) {:port 8080}))
