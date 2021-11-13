(ns learn-clj.core)

;; println
;; define symbols

;;
;; print("meu print")
;; printf("meu print", %s);

;;(function arg1, arg2, ...agrn)
;;
(println "Meu print em clojure O famoso hello world")

(+ 1 2)
;;syntax
;; uniform structure
;;def a symbol
(def title "SEAC")
(def total 10)
(println total)

#_(if algo-que-evalua-truthly-or-falsily
  ...
  ...) ;; control flow

(if true
  (str "verdade")
  (str "falso"))

(if false
  (str "verdade")
  (str "falso"))

;; calls
(if true
  (- 1 (+ 1 2))
  ;(fn1 1 2)
  (println "false"))

;;when

(when true
  (println 1)
  #_(println 2))

(when false
  (println 1))
;;;
(def x 10)
;; good
(cond
  (= x 10) :ten
  (= x 20) :twenty
  (= x 30) :forty
  :else :dunno)

;; better
(condp = x
  10 :ten
  20 :twenty
  30 :forty
  :dunno)

;; best
(case x
  10 :ten
  20 :twenty
  30 :forty
  :dunno)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(nil? 1)
(nil? nil)

(true? 1)
(true? true)

(false? 1)
;;types
(type 1)
(type 1.0)
(type 11111111231231231312312)
(type 123132123123123123123213213.1)
(type [])
(type ())
(type nil)
(type {})
(type #{})

;; data structure

;;vetor = [1, 2, 3]

(println [1 2 3 4])
;; num
(println 1)
;; string


;; map
(def meu-map {:first-name "Matheus"
              :last-name "Chico"})

(def meu-map-2 (hash-map :name "Matheus" :title "Saec"))

(get meu-map-2 :name)
(get meu-map-2 :a)
(get meu-map-2 :a "not-found")


(def pessoa {:pessoa {:first-name "Matheus"
                       :last-name "Francisco"
                       :address {:city "Floripis"}}})

(get-in pessoa [:pessoa :address :city])

;;keywords
;; usadas primariamente como keys de maps/
:a
:run
:>
(type :a)
;;primeiro parametro
(:pessoa pessoa)
(:pessoa nil)
(:pessoa {})

(:a pessoa "not-found")

;;vectors
(get [1 2 3] 0)
(get ["a" {:a 1} "c"] 1)
([1 2 3]  0)


(vector "test" "1" :a)

(def meu-vetor [1 2 3 4])

(conj meu-vetor 4)
(println meu-vetor)

;; list
'(1 2 3 4)

(def minha-list (list 1 2 3 4))
(conj minha-list 2)
#_(def opa-que-coisa '(def meu-vetor [1 2 3 5]))


;;sets
;; nao garante ordem
#{"numero" 1 :c}
(conj #{:a :b} :c)

(set [1 1 1 2 3 4])

(contains? #{:a :b} :a)

#_(contains? {:a {:b 1}} :a)

;;get
;;(:a #{:a :b})
(conj {:a 2 :b 4} [:c 6])
(conj {:firstname "John" :lastname "Doe"} {:age 25 :nationality "Chinese"})
