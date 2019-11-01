(ns wee.web
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [clojure.string :as str]
            [wee.client]))

(defn ui []
  [:center
   [:h2 "Click count"]
   [:h3 @(rf/subscribe [:cnt])]
   [:input {:type "button" :value "Click"
            :on-click #(rf/dispatch [:send-click])}]])

(defn render
  {:dev/after-load true}
  []
  (r/render [ui] (.getElementById js/document "app")))

(defn main []
  (rf/dispatch-sync [:initialize])
  (render))
