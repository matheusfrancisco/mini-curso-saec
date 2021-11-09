(ns learn-clj.atom-protocol-records)

;;imutabilidade


;;https://clojuredocs.org/clojure.core/atom
;;; mutabilidade
(def my-atom (atom 0))
(swap! my-atom inc)
(swap! my-atom (fn [n] (* (+ n n) 2)))

(reset! my-atom 0)








