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

(def request-token (oauth/request-token consumer "http://word-keeper.clojurecup.com/signin"))

(def twitter-auth-uri (oauth/user-approval-uri consumer (:oauth_token request-token)))

(defn authorize [verifier]
  (oauth/access-token consumer request-token verifier))
