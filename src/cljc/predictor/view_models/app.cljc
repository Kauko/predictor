(ns predictor.view-models.app
  (:require [lentes.core :as lentes]))

(def value-lens (lentes/key :val))

(defn view-model
  [model]
  {:counter (lentes/focus-atom value-lens model)})