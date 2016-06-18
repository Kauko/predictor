(ns predictor.view-models.app)

(defn view-model
  [model]
  {:counter model}
  #_{:counter (atom (str "#" (:val @model)))})