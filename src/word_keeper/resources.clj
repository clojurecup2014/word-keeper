(ns word-keeper.resources
  (:require [liberator.core :refer [defresource]]
            [cheshire.core :refer [generate-string]]))

(defonce langs (ref {1 {:lang "english"}
                     2 {:lang "russian"}}))

(defresource language [id]
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :exists? (fn [_]
             (let [e (get @langs id)]
               (if-not (nil? e)
                 {::entry e})))
  :handle-ok ::entry)

(defresource languages
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok @langs)
