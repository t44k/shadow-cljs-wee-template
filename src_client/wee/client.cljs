(ns wee.client
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :as a :refer [<! >! chan]]
            [haslett.client :as ws]
            [haslett.format :as fmt]
            [re-frame.core :as re-frame]))

(defonce in (chan))
(defonce out (chan))

(goog-define SERVER-HOST "127.0.0.1")
(goog-define SERVER-PORT "9499")

(def SERVER-URL (str "ws://" SERVER-HOST ":" SERVER-PORT))

(ws/connect SERVER-URL
            {:source in 
             :sink out})

(go
  (loop []
    (when-let [mess-str (<! in)]
      (let [mess (cljs.reader/read-string mess-str)]
        (re-frame/dispatch [:update-click-count (:click-cnt mess)]))
      (recur))))

(defn- send-click []
  (go (>! out (pr-str {:operation :click}))))

(re-frame/reg-event-db
 :initialize
 (fn [db _]
   (assoc db :click-cnt "...")))

(re-frame/reg-event-db
 :update-click-count
 (fn [db [_ cnt]]
   (assoc db :click-cnt cnt)))

(re-frame/reg-event-fx
 :send-click
 (fn [_]
   {:send-click []}))

(re-frame/reg-fx
 :send-click
 (fn [_]
   (send-click)))

(re-frame/reg-sub
 :cnt
 (fn [db _]
   (:click-cnt db)))
