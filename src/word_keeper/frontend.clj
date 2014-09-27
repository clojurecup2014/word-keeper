(ns word-keeper.frontend
  (:require [clostache.parser :refer [render-resource]]
            [word-keeper.auth :refer [authorize request-token]]))

(defn action-index [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-resource
            "views/index.html.mustache"
            nil)})

(defn action-twitter-auth [req]
  (let [tok (request-token)
        session (assoc (:session req) :request-token tok)
        dest-uri (str "https://api.twitter.com/oauth/authorize?oauth_token=" (:oauth_token tok))]
    {:status 301
     :session session
     :headers {"location" dest-uri}}))

(defn action-signin [req]
  (let [user-data (authorize (-> req :session :request-token)
                             (-> req :params :oauth_verifier))]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (str "Signed in as " (:screen_name user-data))}))
