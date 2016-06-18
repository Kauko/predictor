(ns predictor.reconcilers.app
  (:require
    #?(:cljs [cljs.core.match :refer-macros [match]])
    #?(:clj [clojure.core.match :refer [match]])))


(defn -reconcile
  [model action]
  (match action
         :increment (update model :val inc)
         :decrement (update model :val dec)))
