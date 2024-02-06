(ns restful-example-mauricio-szabo.core
  (:gen-class)
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  {:status 200 :body "Hello, Person!"})

(def users [{:name "Maurício" :age 10}
            {:name "Ana" :age 12}
            {:name "André" :age 10}])

(defn filter-users [params users]
  (filter (fn [user] (= params (select-keys user (keys params))))
          users))

(defn get-users-handler [request]
  (-> request
      (:params {})
      (filter-users users)
      http/json-response))

(def routes
  (route/expand-routes
    #{["/greet" :get respond-hello :route-name :greet]
      ["/users" :get get-users-handler :route-name :users]}))

(def pedestal-config
    {::http/routes (fn [] routes)
     ::http/type :jetty
     ::http/join? false
     ::http/port 3003})

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

