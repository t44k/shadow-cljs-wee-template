(ns wee.dev
  (:require [wee.server :as server]
            [shadow.cljs.devtools.server :as shadow-server]
            [shadow.cljs.devtools.api :as shadow]))

(def modules [:web :electron-main :electron-web :expo])

(defn -main [& args]
  (println ">>> Starting development server...")
  (apply server/-main args)
  (shadow-server/start!)
  (->> modules
       (map #(future (shadow/watch %1)))
       (map deref)
       (doall))
  (println "<<< Development server started."))
