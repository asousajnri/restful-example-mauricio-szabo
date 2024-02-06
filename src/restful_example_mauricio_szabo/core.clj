(ns restful-example-mauricio-szabo.core
  (:gen-class)
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  {:status 200 :body "Hello, Person!"})

(def routes
  (route/expand-routes
    #{["/greet" :get respond-hello :route-name :greet]}))

(def pedestal-config
    {::http/routes (fn [] routes)
     ::http/type :jetty
     ::http/join? false
     ::http/port 3003})

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

