(ns wee.electron-web
  (:require [wee.web :as web]))

(defn main
  {:dev/after-load true}
  []
  (web/main))

