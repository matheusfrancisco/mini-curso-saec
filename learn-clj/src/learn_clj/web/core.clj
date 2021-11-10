(ns learn-clj.web.core
  (:require [com.stuartsierra.component :as component]
            [learn-clj.web.components :as web]
            [learn-clj.web.app :as app]
            [io.pedestal.service-tools.dev :as dev]))

;;https://clojuredocs.org/clojure.core/defrecord
;;atom
;;component
;;testing
;;type-sistem

(def system (atom nil))

(defn- build-system-map []
  (component/system-map
    :config (web/new-config web/config-map)
    :db (web/create-new-db)
    :routes  (web/new-routes #'app/routes)
    :http-server (component/using (web/new-webserver) [:config :routes :db])))

(defn -main
  "The entry-point for 'lein run-dev'"
  [& args]
  (-> (build-system-map)
      (app/start-system! system)))

(defn run-dev []
  (dev/watch) ;; auto-reload namespaces only in run-dev / repl-start
  (-main))

(run-dev)
