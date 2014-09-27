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
(def consumer (oauth/make-consumer consumer-key
                                   consumer-secret
                                   "https://api.twitter.com/oauth/request_token"
                                   "https://api.twitter.com/oauth/access_token"
                                   "https://api.twitter.com/oauth/authorize"
                                   :hmac-sha1))

(def request-token (oauth/request-token consumer "http://word-keeper.clojurecup.com/signin"))

(defroutes routes
  (GET "/" [] action-index))

(defn -main [& args]
  (run-server (site #'routes) {:port 8080}))
