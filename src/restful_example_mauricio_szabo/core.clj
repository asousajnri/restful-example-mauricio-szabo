(ns restful-example-mauricio-szabo.core
  (:gen-class)
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]))

(defn respond-hello [request]
  {:status 200 :body "Hello, Person!"})

(def users (atom [{:name "Maurício" :age 10}
                  {:name "Ana" :age 12}
                  {:name "André" :age 10}]))

(defn filter-users [params users]
  (filter (fn [user] (= params (select-keys user (keys params))))
          users))

(defn get-users-handler [request]
  (-> request
      (:params {})
      (filter-users users)
      http/json-response))

(defn post-users-handler [request]
  (let [new-user (:json-params request)]
    (swap! users conj new-user)
    (-> new-user
        http/json-response
        (assoc :status 201))))

(def routes
  (route/expand-routes
    #{["/greet" :get respond-hello :route-name :greet]
      ["/users" :get get-users-handler :route-name :users]
      ["/users" :post post-users-handler :route-name :create-user]}))

(def pedestal-config
  (-> {::http/routes routes
       ::http/type :jetty
       ::http/join? false
       ::http/port 3003}
      http/default-interceptors
      (update ::http/interceptors conj (body-params/body-params))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

