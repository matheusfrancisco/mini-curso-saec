(ns learn-clj.core-test
  (:require [clojure.test :refer :all]
            [learn-clj.core :refer :all]
            [learn-clj.app-1 :as app]))

(deftest learn-test-unitario
  (testing "Dado um valor bruto devo aplicar o desconto"
    (is (= (app/valor-com-desconto app/mais-caro-que-100? 100) 100))
    (is (= (app/valor-com-desconto app/mais-caro-que-100? 1000) 900))))
