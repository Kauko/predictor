(ns predictor.test.app-test
  (:require
    [predictor.test.config :as config]
    #?@(:clj  [[clojure.test :refer :all]
               [clj-fakes.core :as f]]
        :cljs [[cljs.test :refer-macros [is are deftest testing use-fixtures]]
               [clj-fakes.core :as f :include-macros true]])))

;; CONTROLLER

(deftest test-tests
  (is (= 2 2)))

(deftest
  increment-is-dispatched
  (f/with-fakes
    (let [{:keys [control]} config/spec
          dispatch-action (f/recorded-fake)]
      ; act
      (control :_model :on-increment :signal dispatch-action)

      ; assert
      (is (f/was-called-once dispatch-action [:increment])))))

;; RECONCILER

(deftest
  increases-value
  (let [{:keys [initial-model reconcile]} config/spec]
    (is (= (+ 1 (:val initial-model)) (:val (reconcile initial-model :increment))))))

;; VIEW-MODEL


