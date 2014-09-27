(ns word-keeper.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET]]
            [compojure.handler :refer [site]]
            [compojure.route :refer [files not-found]]
            [clostache.parser :refer [render-resource]]
            [word-keeper.backend  :refer :all]
            [word-keeper.frontend :refer :all]))

(def consumer-key "5KvZggyamEy8yHD0oACgAkLxH")
(def consumer-secret "3DeEHXQ6LVh7LxSdApivzAOiwBAcGdvRorheKzheCchbPPQF6h")

(defroutes routes
  (GET "/" [] action-index)
  (GET "/signin" [] action-signin)
  (files "/public/")
  (not-found "<h1>404. Not found</h1>"))


(defn -main [& args]
  (run-server (site #'routes) {:port 8080}))
