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

(defn action-vocabulary [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-resource
            "views/vocabulary.html.mustache"
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
                             (-> req :params :oauth_verifier))
        user_id (:user_id user-data)
        screen_name (:screen_name user-data)]
    (if (contains? user-data :user_id)
      (do
        (if (nil? (find-twitter-user (Integer/parseInt user_id)))
          (create-twitter-user! (Integer/parseInt user_id)  screen_name))
        {:status 200
         :headers {"Content-Type" "text/plain"}
         :body (str "Signed in as " screen_name)
         :session (assoc (:session req) :uid (:uid (find-twitter-user (Integer/parseInt user_id))))})
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body ("Something went wrong")})))

(defn action-signout [req]
  (let [session (assoc
                  (:session req)
                  :request-token nil
                  :uid nil)]
    {:status 301
     :session session
     :headers {"location" "/"}}))
