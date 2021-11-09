(ns learn-clj.web.components
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.interceptor.helpers :refer [before]]
            [io.pedestal.http.route :as route]
            [io.pedestal.http :as http]))

;;;;;;;;;;;; config
(defrecord Config [config]
  component/Lifecycle
  (start [this] this)
  (stop  [this] this))

(def config-map
  {:http-port (Integer/parseInt (or (System/getenv "HTTP_PORT") "3000"))
   :http-host (or (System/getenv "HTTP_HOST") "localhost")})

(defn new-config [input-map] (map->Config {:config (or input-map config-map)}))
;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;; memory db
(defrecord inMemoryDatabase
  [db]
  component/Lifecycle
  (start [component] component)
  (stop [component] (reset! db {})
    component))

(defn create-new-db []
  (->inMemoryDatabase (atom {})))

;;;;;;;;;;;;;;;;;;;; Routes

(defrecord Routes [routes]
  component/Lifecycle
  (start [component]
    (assoc component :routes routes))
  (stop  [component] (dissoc component :routes)))

(defn new-routes [routes] (map->Routes {:routes routes}))

;;;;;;;;;;;;;;;;;;;; Routes

;;;;;;;;;;;;;;;;;;;; web service

(defn- add-system [service]
  (before (fn [context] (assoc-in context [:request :components] service))))

(defn system-interceptors
  "Extend to service's interceptors to include one to inject the components
  into the request object"
  [service service-map]
  (update-in service-map
             [::http/interceptors]
             #(vec (->> % (cons (add-system service))))))

(defn base-service [routes config ]
  {:env          :dev
   ::http/router :prefix-tree
   ::http/routes #(route/expand-routes (deref routes))
   ::http/type   :jetty
   ::http/port   (:http-port config)
   ::http/host   (:http-host config)})

(defn dev-init [service-map]
  (-> service-map
      (merge {:env                   :dev
              ;; do not block thread that starts web server
              ::http/join?           false
              ;; Content Security Policy (CSP) is mostly turned off in dev mode
              ::http/secure-headers  {:content-security-policy-settings {:object-src "none"}}
              ;; all origins are allowed in dev mode
              ::http/allowed-origins {:creds true :allowed-origins (constantly true)}})
      ;; Wire up interceptor chains
      http/default-interceptors
      http/dev-interceptors))


(defrecord WebServer [config routes db]
  component/Lifecycle
  (start [this]
    (println
      (str ";; Starting webserver on " (get-in config [:config :http-port])))
    (assoc this :service
                (->> (base-service (:routes routes) (:config config))
                     dev-init
                     (system-interceptors this)
                     http/create-server
                     http/start)))

  (stop [this]
    (println (str ";; Stopping webserver"))
    (http/stop (:service this))
    (dissoc this :service)
    this))

(defn new-webserver []
  (map->WebServer {}))
;;;;;;;;;;;;;;;;;;;; web service
