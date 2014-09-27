(ns word-keeper.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET]]
            [compojure.handler :refer [site]]
            [clostache.parser :refer [render-resource]]
            [word-keeper.backend  :refer :all]
            [word-keeper.frontend :refer :all]))

(defroutes routes
  (GET "/" [] action-index)
  (GET "/signin" []))

(defn -main [& args]
  (run-server (site #'routes) {:port 8080}))
