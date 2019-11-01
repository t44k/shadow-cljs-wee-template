(ns wee.server
  (:require [org.httpkit.server :as server]))

(defonce instance (atom nil))
(defonce cnt (atom 0))
(defonce channels (atom #{}))

(defn send-count [channel c]
  (server/send! channel (pr-str {:click-cnt c})))

(defn increment []
  (let [c (swap! cnt inc)]
    (doseq [channel @channels]
      (send-count channel c))))

(defn app [req]
  (server/with-channel req channel
    (swap! channels conj channel)
    (send-count channel @cnt)
    (server/on-close channel (fn [_] (swap! channels disj channel)))
    (server/on-receive channel (fn [_] (increment))))) 

(defn start []
  (reset! instance (server/run-server app {:port 9499})))

(defn stop []
  (when-let [close-fn @instance]
    (close-fn :timeout 100)
    (reset! instance nil)))

(defn -main [& args]
  (start)
  (println "Server is started."))
