(ns word-keeper.api
  (:require [word-keeper.db :refer :all]
            [cheshire.core :refer [generate-string]]))

(defn user-translations [req uid]
  (let [db-data (find-translations (Integer/parseInt uid) :en :ru)
        group-fn (fn [m] (select-keys m [:wid :word]))
        dissoc-fn (fn [m] (apply dissoc m [:wid :word]))
        grouped-ts (->> db-data
                (group-by group-fn))
        result (for [[ {:keys [wid word]} ts] grouped-ts] {:wid wid
                                                           :word word
                                                           :translations ts})]
    {:status 200
     :headers {"Content-Type" "application/json; charset=utf-8"}
     :body (generate-string result)}))
