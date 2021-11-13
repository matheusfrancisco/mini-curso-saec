(ns learn-clj.specs.account
  (:require [clojure.spec.alpha :as s])
  (:import (java.util UUID)))

;;;https://github.com/plumatic/schema

(s/def ::account-holder
  (s/keys :req [::account-id ::first-name ::email-address]
          :opt [::last-name]))

(s/def ::account-id uuid?)
(s/def ::first-name string?)
(s/def ::last-name string?)

(s/def ::email-address
  (s/and string?
         #(re-matches #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$"
                      %)))


;; These specs are not very useful - refactor
;(s/def ::home-address string?)
;(s/def ::social-secuirty-id string?)

(s/def ::bank-account
  (s/keys :req [::bank-account-id
                ::account-balance
                ::account-status
                ::arranged-overdraft]
          :opt [::bank-account-alerts]))

;; This spec could be extended to cover different types of accounts
;; eg. mortgages, loans, savings, current, ISA (and all their variations), etc.
(s/def ::bank-account-id uuid?)
(s/def ::account-balance number?)
(s/def ::account-status #{:credit :overdrawn})
(s/def ::arranged-overdraft (s/and int? #(> 1000 %)))
(s/def ::bank-account-alerts #{:yes :warnings-only :no})


(s/valid? ::account-holder
           {::account-id    (UUID/randomUUID)
            ::first-name    "X"
            ::last-name     "Xico"
            ::email-address "x@x.spm"})



(s/explain-data ::account-holder
             {::first-name "x"
              ::last-name  "x"
              ::email      "x@x.spm"})
