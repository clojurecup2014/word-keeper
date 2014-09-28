(ns word-keeper.api
  (:require [word-keeper.db :refer :all]
            [cheshire.core :refer [generate-string]]))

(defn user-translations [uid]
  (let [db-data (find-translations uid :en :ru)
        group-fn (fn [m] (select-keys m [:wid :word]))
        dissoc-fn (fn [m] (apply dissoc m [:wid :word]))
        grouped-ts (->> db-data
                (group-by group-fn))
        result (for [[ {:keys [wid word]} ts] grouped-ts] {:wid wid
                                                           :word word
                                                           :translations ts})]
    (generate-string result)))
