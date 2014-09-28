(ns word-keeper.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET]]
            [compojure.handler :refer [site]]
            [compojure.route :refer [files not-found]]
            [clostache.parser :refer [render-resource]]
            [word-keeper.backend  :refer :all]
            [word-keeper.frontend :refer :all]
            [word-keeper.db :refer [find-twitter-user-by-uid]]
            [word-keeper.api :refer :all]))

(def consumer-key "5KvZggyamEy8yHD0oACgAkLxH")
(def consumer-secret "3DeEHXQ6LVh7LxSdApivzAOiwBAcGdvRorheKzheCchbPPQF6h")

(defn user-middleware [handler]
  (fn [req]
    (if-let [uid (-> req :session :uid)]
      (let [user (find-twitter-user-by-uid uid)]
        (handler (assoc req :screen_name (:twitter_name user))))
      (handler req))))

(defn auth-middleware [handler]
  (fn [req]
    (if-let [screen_name (:screen_name req)]
      (handler req)
      {:status 301
       :headers {"location" "/"}
       :session (assoc (:session req)
                  :notice "Please, sign is as twitter user"
                  :show_notice true)})))

(defroutes routes
  (GET "/" [] action-index)
  (GET "/api/translations/:uid" [uid] (-> user-translations
                                          user-middleware
                                          auth-middleware))
  (GET "/vocabulary" [] (-> action-vocabulary user-middleware auth-middleware))
  (GET "/signin" [] action-signin)
  (GET "/signout" [] action-signout)
  (GET "/twitter-auth" [] action-twitter-auth)
  (files "/public/")
  (not-found "<h1>404. Not found</h1>"))

(defn -main [& args]
  (run-server (site #'routes) {:port 8080}))
