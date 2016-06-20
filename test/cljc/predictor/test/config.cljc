(ns predictor.test.config
  (:require [predictor.models.app :as model]
            [predictor.controls.app :as control]
            [predictor.reconcilers.app :as reconciler]))

(def spec (-> {:initial-model (model/new-model)
               :control       (control/-new-control)
               :reconcile     reconciler/-reconcile}))