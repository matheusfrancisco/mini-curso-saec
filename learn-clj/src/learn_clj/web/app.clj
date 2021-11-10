(ns learn-clj.web.app
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http :as http]
            [ring.util.response :as ring-resp]))

(defn start-system! [system-map system]
  (->> system-map
       component/start
       (reset! system)))

(defn stop-system! [system]
  (swap! system #(component/stop %)))


(defn get-all-users! [db]
  @db)

(defn get-in-memory-database
  [{{db :db} :components}]
  (:db db))

(defn get-all-users
  [request]
  (let [storage (get-in-memory-database request)]
    (ring-resp/response {:message (get-all-users! storage)})))


(def common-interceptors
  [(body-params/body-params) http/json-body])


(def routes
  #{["/users" :get (conj common-interceptors `get-all-users)]})
