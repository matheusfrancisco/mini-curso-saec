(ns learn-clj.functions
  (:require [clojure.string :as clj-str]))

;;chamada de funcao
(+ 1 2)
(- 2 3)

(+ 1 2 3 4 5 5)
(- 1 2 3 4 5 5)

(first [1 2 3])
(rest [1 2 3])
(second [1 2 3])
(last [1 2 3])

(first '(1 2 3))
(rest '(1 2 3))
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
;;definir funcao
(defn nome-da-funcao
  [arg1 arg2 arg3]
  ()
  ())



(defn nomes-com-espaco?
  [nome]
  (clj-str/includes? nome " "))

(def nomes ["Jaozim Silva" "Chico" "Caio com K" "marcos Costa"])
(def nomes-com-espaco (filter nomes-com-espaco? nomes))


(println nomes-com-espaco)
(conj nomes-com-espaco "novo")
(println nomes-com-espaco)


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

(print-if-bigger-than-5 5)


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


(multi-arity 1 2)

(defn soma
  ([] (soma 1 1))
  ([x] (soma 1 x))
  ([x y] (+ x y)))

;; destructuring with vector
;;https://gist.github.com/john2x/e1dca953548bfdfb9844

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


;;let
(def my-map {1 "first" 2 "second"})

(let [{a 1 b 2} my-map]
  (println a b))                                            ;=> "first second"

;;funct and let
;; let
(def x 0)
(prn x)

(let [x 1] x)
(prn x)

(let [x (inc x)] x)
(prn x)

(defn mais-caro-que-100?
  [valor]
  (> valor 100))

(defn calcula-taxa [value]
  (/ value 100))

(defn valor-com-desconto
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa (calcula-taxa 10)
          desconto (* valor-bruto taxa)]
      (- valor-bruto desconto))
    valor-bruto))

(if-let [a false]
  :true
  :false)

(if-let [nome (:name {:name "Matheus"})]
  (str nome " F")
  :false)


;;funcoes anonymous
(fn [params]
  (println params))

(def anonymous (fn [params]
                 (println params)))
(anonymous "my")



(defn soma
  ([valores acumulador]
   (if (empty? valores)
     acumulador
     (soma (rest valores) (+ (first valores) acumulador)))))

(soma [1 2 5] 0)
(time (soma [1 2 5] 0)) ; => "Elapsed time: 0.047669 msecs"
(soma (range 10000) 0) ; => StackOverflowError   clojure.lang.ChunkedCons.more

(loop [i 0]
  (when (< i 5)
    (println i)
    (recur (inc i)); loop i will take this value
    ))

;;https://clojuredocs.org/clojure.core/recur
(defn soma
  ([valores acumulador]
   (if (empty? valores)
     acumulador
     (recur (rest valores) (+ (first valores) acumulador)))))

(soma (range 10000) 0)

;;;;;;hof

(map (fn [nome] (str "Hi " nome))
     ["Matheus F" "Jaozim Silva"])

(def say-hello (fn [nome] (str "Hi " nome)))

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

;;thread-first
(first (.split (.replace (.toUpperCase "a b c d") "A" "X") " "))

(-> "a b c d"
    .toUpperCase
    (.replace "A" "X")
    (.split " ")
    first)


(def person
  {:name     "Mark Volkmann"
   :address  {:street "644 Glen Summit"
              :city   "St. Charles"
              :state  "Missouri"
              :zip    63304}
   :employer {:name    "Object Computing, Inc."
              :address {:street "12140 Woodcrest Dr."
                        :city   "Creve Coeur"
                        :state  "Missouri"
                        :zip    63141}}})
(get-in person [:employer :address :city])
(-> person :employer :address :city)
(:city (:address (:employer person)))

(def c 5)
(-> c
    (+ 3)
    (/ 2)
    (- 1))

;;comun
#_(let [algo (-> data
                 (func1)
                 (func2)
                 (func3))]
    algo)

(defn slugify
  [string]
  (clj-str/replace
    (clj-str/lower-case
      (clj-str/trim string)) #" " "-"))

(defn slugify
  [string]
  (-> string
      (clj-str/trim)
      (clj-str/lower-case)
      (clj-str/replace #" " "-")))

(slugify " I will be a url slug   ")
;;https://clojuredocs.org/clojure.core/-%3E



;;map

(map inc [1 2 3])
(reduce + [1 2 3 4])
(filter even? [1 2 3 4 5 6 7 8 9 10])                       ;;pares

(filter odd? [1 2 3 4 5 6 7 8 9 10])                        ;;im


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



;;thread last

(def entries [{:month 1 :val 12}
              {:month 2 :val 3}
              {:month 3 :val 32}
              {:month 4 :val 18}
              {:month 5 :val 32}
              {:month 6 :val 62}
              {:month 7 :val 12}
              {:month 8 :val 142}
              {:month 9 :val 52}
              {:month 10 :val 18}
              {:month 11 :val 23}
              {:month 12 :val 56}])

(defn get-result
  [coll m]
  (->> coll
       (take-while
         #(<= (:month %) m))))

(defn get-total
  [coll m]
  (->>
    (get-result coll m)
    (map #(:val %))
    (reduce +)))



(def shopping-cart
  [{:product-title "Product 1" :amount 10},
   {:product-title "Product 2" :amount 30},
   {:product-title "Product 3" :amount 20},
   {:product-title "Product 4" :amount 60}])

(defn get-amount
  [{:keys [amount]}]
  amount)

(defn get-total-amount
  [shopping-cart]
  (reduce + (map get-amount shopping-cart)))


(defn get-total-amount
  [shopping-cart]
  (->> shopping-cart
       (map get-amount)
       (reduce +)))


(get-total-amount shopping-cart)                            ;; 120



;;n outras funcoes
;some
;;sort
;;sort-by

(sort-by count ["aaa" "c" "bb" "ssssss"])


(->> ["aaa" "c" "bb" "ssssss"]
     (sort-by count)
     (reverse))

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


;; é melhor ter 100 funcoes operando em uma estrutura de dados do que 10 funcoes em 10 estrutura de dados
