(ns learn-clj.functions
  (:require [clojure.string :as clj.str]))

;;chamada de funcao
(+ 1 2)
(- 2 3)

(+ 1 2 3 4 5 5)
(- 1 2 3 4 5 5)

(first [1 2 3])
(second [1 2 3])
(last [1 2 3])

(first '(1 2 3))
(second '(1 2 3))
(last '(1 2 3))

(and 1 3)
(and false 1)
(or 1 2)

(seq {:a 1 :b 2})
(seq {:a 1 :b {:c 1}})

#_(defn nomes-com-espaco?
    []
    ;;logica, exprs...
    retorno-da-funcao)
;;

(defn nomes-com-espaco?
  [nome]
  (clj.str/includes? nome " "))

(def nomes ["Jaozim Silva" "Chico" "Caio com K" "marcos Costa"])
(def nomesComEspaco (filter nomes-com-espaco? nomes))


(println nomesComEspaco)


;;definir funcoes
(defn one-arity [one]
  (str "My one arity function " one))

;;body
(defn sum [a b]
  (+ a b))

(sum 1 2)

;;body and statment
(defn print-if-bigger-than-5
  [x]
  (if (> x 5)
    "é maior"
    "nao é maior"))

(print-if-bigger-than-5 6)

#_(defn print-when-bigger-than-5
    [x]
    (or (when (> x 5)
          "é maior")
        "nao é maior"))

#_(print-when-bigger-than-5 3)

(defn soma-bigger-6
  [x]
  (if (> x 6)
    (+ 1 x)
    x))

(soma-bigger-6 7)

;;arity overload
;;one way to provide default value
(defn multi-arity
  ([] (str "No arguments"))
  ([x] (str "One arguments " x))
  ([x y] (str "Two arguments " x " " y)))

(defn soma
  ([] (soma 1 1))
  ([x] (soma 1 x))
  ([x y] (+ x y)))

;; destructuring with vector
(defn my-first
  [[first-thing]]
  first-thing)

#_(defn my-first
    [first-thing]
    first-thing)

(my-first ["saec" "blockchain" "bitcoin"])

(defn select-things
  [[first-thing second-thing & unimportant-choices]]
  (println first-thing)
  (println second-thing)
  (println unimportant-choices))

(select-things [["saec"] "clojure" "php" "another" "things"])

;;maps destructuring
(defn location
  [{lat :lat lng :lng}]
  (println lat)
  (println lng))

(location {:lat 199 :lng 1223})



(defn location-keywords-ds
  [{:keys [lat lng]}]
  (println lat)
  (println lng))


(defn location-keywords-ds
  [{:keys [lat lng] :as data}]
  (println lat)
  (println lng)
  (println data))

(location-keywords-ds {:lat 199 :lng 1223 :address "fake address"})

;;funcoes anonymous
(fn [params]
  (println params))
(def anonymous (fn [params]
                 (println params)))
(anonymous "my")



(map (fn [nome] (str "Hi " nome))
     ["Matheus F" "Jaozim Silva"])

(def  say-hello (fn [nome] (str "Hi " nome)))

(map say-hello ["Matheus F" "Jaozim Silva"])


#_(#(str "Olá " %) "Matheus")

(map #(str "Olá " %)
     ["Matheus F" "Jaozim Silva"])

(#(str "Ola " %1 " " %2) "Matheus" "F")


;;returning functions
(defn inc-maker
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))
(inc3 2)


#_(def nomesComEspaco (->> nomes
                           (filter #(clojure.string/includes? % " "))))



;;funct and let
;; let
(def x 0)
(prn x)

(let [x 1] x)
(prn x)

(let [x (inc x)] x)
(prn x)


;;map

(map inc [1 2 3])
(reduce + [1 2 3 4])
(filter even? [1 2 3 4 5 6 7 8 9 10])                       ;;pares

(filter odd? [1 2 3 4 5 6 7 8 9 10])                       ;;im


(def users
  [{:name "Matheus"}
   {:name "Clojure"}
   {:name "Manao"}
   {:name "Bruce"}])

(map :name users)

;;assoc
;;conj
;;assoc-in


;;reduce

(reduce (fn [new-obj [key value]]
          (assoc new-obj key (inc value)))
        {}
        {:max 30 :min 10})


;;take
(take 3 [1 2 3 4 5 6])
;;drop
(drop 3 [1 2 3 4 5])
;;take-while
(def months
  [{:month 1}
   {:month 2}
   {:month 3}
   {:month 4}])

;;take while recebe um predicate
(take-while #(< (:month %) 3) months)

;; drop-while
(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2)
                        months))

(def meses-menores-que-dois? #(< (:month %) 2))
(def meses-menores-que-quatro? #(< (:month %) 4))

(->> months
     (drop-while meses-menores-que-dois?)
     (take-while meses-menores-que-quatro?))

(defn menor-que [x]
  #(< (:month %) x))

(def menor-que-2 (menor-que 2))
(def menor-que-4 (menor-que 4))

(->> months
     (drop-while menor-que-2)
     (take-while menor-que-4))

(filter menor-que-2 months)
(filter menor-que-4 months)

;;n outras funcoes
;some
;;sort
;;sort-by

(sort-by count ["aaa" "c" "bb" "ssssss"])


(->> ["aaa" "c" "bb" "ssssss"]
     (sort-by count)
     (reverse ))

(concat [1 2] [1])


;;apply
;;max
(apply max [1 2 3])

;;partial

(def add10 (partial + 10))
(add10 1)


(let [my-vector [1 2 3 4]
      my-map {:fred "ethel"}
      my-list (list 4 3 2 1)]
  (list
    (conj my-vector 5)
    (assoc my-map :ricky "lucy")
    (conj my-list 5)
    ;the originals are intact
    my-vector
    my-map
    my-list))
