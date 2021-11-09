(ns learn-clj.app-1)


(defn mais-caro-que-100?
  [valor]
  (> valor 100))

(defn valor-com-desconto
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa (/ 10 100)
          desconto (* valor-bruto taxa)]
      (- valor-bruto desconto))
    valor-bruto))


