(ns word-keeper.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET]]
            [compojure.handler :refer [site]]
            [clostache.parser :refer [render-resource]]
            [oauth.client :as oauth]
            [word-keeper.backend  :refer :all]
            [word-keeper.frontend :refer :all]))

(def consumer-key "5KvZggyamEy8yHD0oACgAkLxH")
(def consumer-secret "3DeEHXQ6LVh7LxSdApivzAOiwBAcGdvRorheKzheCchbPPQF6h")

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
