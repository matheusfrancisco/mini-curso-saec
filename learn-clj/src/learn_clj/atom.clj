(ns learn-clj.atom
  (:use [clojure pprint]))

;;imutabilidade


;;https://clojuredocs.org/clojure.core/atom
;;; mutabilidade
(def my-atom (atom 0))
(swap! my-atom inc)
(swap! my-atom (fn [n] (* (+ n n) 2)))

(reset! my-atom 0)




;;; concurrently world


(defn testa-atomao []
  (let [data (atom {:recurso 1})]
    (print data)
    (print "-------------------")
    (print (deref data))
    (print "-------------------")

    (print @data)
    (print "-------------------")

    (print (assoc @data :recurso inc))
    ))

#_(testa-atomao)

(defn cabe-na-fila? [hospital departamento]
  (-> hospital
    (get departamento)
    count
    (< 5)))

(defn chega-em-pausado-logando
  [hospital departamento pessoa]
  (println "Tentando adicionar a pessoa" pessoa)
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do
      ; (Thread/sleep (* (rand) 2000))
      (println "Dando o update" pessoa)
      (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital chega-em-pausado-logando :espera pessoa)
  (println "apos inserir" pessoa))


(def fila-vazia clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera       fila-vazia
   :laboratorio1 fila-vazia
   :laboratorio2 fila-vazia
   :laboratorio3 fila-vazia})


(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo)



(defn atualizando-memoria!
  [memoria chave valor]
  (println "Tentando adicionar a o valor" valor)
  (Thread/sleep (* (rand) 2000))
  (do
    ; (Thread/sleep (* (rand) 2000))
    (println "Dando o update" valor)
    (update memoria chave conj valor)))


(defn process! [memoria valor]
  (swap! memoria atualizando-memoria! :recurso valor)
  (println "apos inserir" valor)
  (println memoria))


(defn simula-coisas-em-paralelo
  []
  (let [memoria (atom {:recurso []})]
    (.start (Thread. (fn [] (process! memoria 1))))
    (.start (Thread. (fn [] (process! memoria 2))))
    (.start (Thread. (fn [] (process! memoria 3))))
    (.start (Thread. (fn [] (process! memoria 4))))
    (.start (Thread. (fn [] (process! memoria 5))))
    (.start (Thread. (fn [] (process! memoria 6))))
    (.start (Thread. (fn [] (process! memoria 7))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint memoria))))))

;(simula-coisas-em-paralelo)

;{:recurso [2 3 5 4 6 7 1]}
;{:recurso [2 7 1 3 6 4 5]}
