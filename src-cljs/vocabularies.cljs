(ns vocabularies
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [dommy.core :refer-macros [sel1]]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(def vocabulary
  (atom {:translations []}))

(defn vocabulary-lister [vocab]
    [:ul
      (for [translation (:translations @vocab)]
        [:li
          [:strong (:word translation)]
          " - "
          (:translation translation)])])

(defn fetch-vocabulary []
  (go
    (let [response
          (<! (http/get "/user/1/vocabulary/english"))]
      ;(.log js/console (prn-str (:body response)))
      (reset! vocabulary {:translations (:body response)})
      )))

(defn update-vocabulary []
  (fetch-vocabulary)
  (js/setInterval fetch-vocabulary 10000))

(defn vocabulary-component []
  [:div
   "Vocabulary"
   (vocabulary-lister vocabulary)])

(defn ^:export run []
  (reagent/render-component
    (fn []
      (with-meta
        [vocabulary-component]
        {:component-did-mount (update-vocabulary)}))
    (sel1 :#reactable)))
