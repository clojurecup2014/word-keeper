(ns word-keeper.cljs.frontend
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(def app-state
  (atom {:text "Hello Om!"}))

(defn widget [data]
  (om/component
    (dom/h1 nil (:text data))))

(om/root
  widget
  app-state
  {:target (. js/document (getElementById "omable"))})
