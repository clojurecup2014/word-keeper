(ns word-keeper.frontend
  (:require [clostache.parser :refer [render-resource]]
            [word-keeper.auth :refer :all]
            [word-keeper.db :refer :all]))

(defn action-index [req]
  (let [session (:session req)
        notice (:notice session)
        show_notice (:show_notice session)]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :session (assoc session :notice nil :show_notice nil)
     :body (render-resource
              "views/index.html.mustache"
              {:notice notice
               :show_notice show_notice})}))

(defn action-vocabulary [req]
  (let [uid (:uid (:session req))
        screen_name (:screen_name req)]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (render-resource
              "views/vocabulary.html.mustache"
              {:screen_name screen_name
               :uid uid})}))

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
        {:status 301
         :headers {"location" "/vocabulary"}
         :session (assoc (:session req) :uid (:uid (find-twitter-user (Integer/parseInt user_id))))})
      {:status 301
       :headers {"location" "/"}
       :session (assoc (:session req)
                  :notice "Something went wrong. Please try again."
                  :show_notice true)})))

(defn action-signout [req]
  (let [session (assoc (:session req)
                  :request-token nil
                  :uid nil
                  :notice "Signed out"
                  :show_notice true)]
    {:status 301
     :session session
     :headers {"location" "/"}}))
