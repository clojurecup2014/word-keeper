(ns word-keeper.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET]]
            [compojure.handler :refer [site]]))

(defn hello [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, world!"})

(defroutes routes
  (GET "/" [] hello))

(defn -main [& args]
  (run-server (site #'routes) {:port 8080}))
