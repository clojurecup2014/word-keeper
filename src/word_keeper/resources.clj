(ns word-keeper.resources
  (:require [liberator.core :refer [defresource]]
            [cheshire.core :refer [generate-string]]
            [word-keeper.db :refer :all]))

(defonce langs (ref {1 {:lang "english"}
                     2 {:lang "russian"}}))

(defresource language [id]
  :allowed-methods [:get]
  :available-media-types ["application/json; charset=utf-8"]
  :exists? (fn [_]
             (let [e (find-language id)]
               (if-not (nil? e)
                 {::entry e})))
  :handle-ok ::entry)

(defresource languages
  :allowed-methods [:get]
  :available-media-types ["application/json; charset=utf-8"]
  :handle-ok (find-languages))

(defresource words [lang]
  :allowed-methods [:get]
  :available-media-types ["application/json; charset=utf-8"]
  :handle-ok (fn [_] (generate-string (find-words lang))))
