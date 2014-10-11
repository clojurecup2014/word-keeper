(ns word-keeper.auth
  (:require [oauth.client :as oauth]))

(def consumer-key "5KvZggyamEy8yHD0oACgAkLxH")

(def consumer-secret "3DeEHXQ6LVh7LxSdApivzAOiwBAcGdvRorheKzheCchbPPQF6h")

(def consumer (oauth/make-consumer consumer-key
                                   consumer-secret
                                   "https://api.twitter.com/oauth/request_token"
                                   "https://api.twitter.com/oauth/access_token"
                                   "https://api.twitter.com/oauth/authorize"
                                   :hmac-sha1))

;;(defn request-token [] (oauth/request-token consumer "http://word-keeper.clojurecup.com/signin"))
(defn request-token [] (oauth/request-token consumer "http://127.0.0.1:8080/signin"))

(defn authorize [token verifier]
  (oauth/access-token consumer token verifier))
