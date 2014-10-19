(ns word-keeper.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET POST ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :refer [files not-found]]
            [clostache.parser :refer [render-resource]]
            [word-keeper.backend  :refer :all]
            [word-keeper.frontend :refer :all]
            [word-keeper.db :refer [find-twitter-user-by-uid]]
            [word-keeper.api :refer :all]
            [word-keeper.resources :refer [language]]))

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
    (if-let [uid (-> req :session :uid)]
      (let [user (find-twitter-user-by-uid uid)]
        (handler (assoc req :screen_name (:twitter_name user))))
        {:status 301
         :headers {"location" "/"}
         :session (assoc (:session req)
                    :notice "Please, sign in as twitter user"
                    :show_notice true)})))

(defroutes new-routes
  (ANY ["/language/:id"] [id] (language (Integer/parseInt id))))

(defn -main [& args]
  (let [port (Integer/parseInt (get (System/getenv) "OPENSHIFT_CLOJURE_HTTP_PORT" "8080"))
        ip (get (System/getenv) "OPENSHIFT_CLOJURE_HTTP_IP" "0.0.0.0")]
    (run-server (site #'new-routes) {:ip ip :port port})
    (println "Application run " ip ":" port)))
