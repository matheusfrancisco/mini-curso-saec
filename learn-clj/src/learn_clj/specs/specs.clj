(ns learn-clj.specs.specs
  (:require [clojure.spec.alpha :as s]))

;;links
;https://practical.li/clojure/clojure-spec/
;https://clojure.org/about/spec
;https://clojure.org/guides/spec
;; Predicates
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Does a function call with the value return true or false?
;; Any existing Clojure function that takes a single argument
;; and returns a truthy value is a valid predicate spec.

(odd? 1)
(string? "1")
(int? 2.3)
(type 2.3)



;;Does a value conform to a spec
;;conform takes two argumentos
;; -spec
;; value
(s/conform even? 1)
(s/valid? even? 1)

(s/valid? nil? nil)                                         ;; true
(s/valid? string? "abc")                                    ;; true

(s/valid? #(> % 5) 10)                                      ;; true
(s/valid? #(> % 5) 0)                                       ;; false
(s/valid? (fn [value] (> value 100000)) 20000)

(import java.util.Date)
(s/valid? inst? (Date.))                                    ;;

;;sets
(s/valid? #{:club :diamond :heart :spade} :club)            ;; true
(s/valid? #{:club :diamond :heart :spade} 42)               ;; false

(s/valid? #{42} 42)                                         ;; true

;;;def spec

(s/def ::meaning-of-life
  (s/and int?
         even?
         #(= 42 %)))

(s/explain ::meaning-of-life 43)

(s/explain-data ::meaning-of-life 24)
