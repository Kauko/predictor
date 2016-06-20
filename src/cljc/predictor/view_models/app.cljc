(ns predictor.view-models.app
  (:require [rum.core :as rum]))

(defn view-model
  [model]
  {:counter (rum/derived-atom [model] ::counter :val)})