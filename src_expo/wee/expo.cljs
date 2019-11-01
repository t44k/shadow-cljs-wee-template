(ns wee.expo
  (:require
    ["expo" :as ex]
    ["react-native" :as rn]
    ["react" :as react]
    [reagent.core :as r]
    [shadow.expo :as expo]
    [re-frame.core :as re-frame]
    [wee.client]))

(def styles
  ^js (-> {:container
           {:flex 1
            :backgroundColor "#fff"
            :alignItems "center"
            :justifyContent "center"}
           :title
           {:fontWeight "bold"
            :fontSize 35
            :marginBottom 25}}
          (clj->js)
          (rn/StyleSheet.create)))

(defn root []
  [:> rn/View {:style (.-container styles)}
   [:> rn/Text {:style (.-title styles)} "Click count"]
   [:> rn/Text {:style (.-title styles)} @(re-frame/subscribe [:cnt])]
   [:> rn/Button {:title "Click" :on-press #(re-frame/dispatch [:send-click])}]])

(defn main
  {:dev/after-load true}
  []
  (re-frame/dispatch-sync [:initialize])
  (expo/render-root (r/as-element [root])))
