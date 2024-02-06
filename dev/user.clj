(ns user
  (:require [io.pedestal.http :as http]
            [restful-example-mauricio-szabo.core :as core]))

(defonce server (atom nil))

(defn start! []
  (when (nil? @server)
    (reset! server (-> core/pedestal-config
                       (assoc ::http/routes (fn [] core/routes))
                       http/create-server
                       http/start))))

(defn stop! []
  (when  @server
    (http/stop @server)
    (reset! server nil)))

(start!)
;(stop!)