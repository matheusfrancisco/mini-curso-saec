(ns learn-clj.web-app
  (:require [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [learn-clj.web.app :as a]
            [clj-http.client :as client]
            [learn-clj.web.components :as c]))



(def system (atom nil))

(def config-map-test
  {:http-port 8080
   :http-host "localhost"})


(defn- build-system-map-test []
  (component/system-map
    :config (c/new-config config-map-test)
    :db (c/create-new-db)
    :routes  (c/new-routes #'a/routes)
    :http-server (component/using (c/new-webserver) [:config :routes :db])))


(defn start-test []
  (build-system-map-test))

(defn with-system
  [f]
  (let [current-sys (component/start (start-test))]
    (f)
    (component/stop current-sys)))

(use-fixtures :each with-system)

(deftest testing-integration-routes
  (testing "GET users with in memory database"
    (let [resp (client/get "http://localhost:8080/users")
          body (:body resp)
          expected "{\"message\":[{\"user/id\":1,\"user/name\":\"Chico\",\"user/email\":\"xico@xico.com.br\"}]}"]
      (is (= body expected)))))
