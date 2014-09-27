(ns word-keeper.frontend
  (:require [clostache.parser :refer [render-resource]]
            [word-keeper.auth :refer :all]
            [word-keeper.db :refer :all]))

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
    (if (contains? user-data :user_id)
      (do
        (if (nil? (find-twitter-user (:user_id user-data)))
          (create-twitter-user! (:user_id user-data) (:screen_name user-data)))
        {:status 200
         :headers {"Content-Type" "text/plain"}
         :body (str "Signed in as " (:screen_name user-data))})
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body ("Something went wrong")})))
