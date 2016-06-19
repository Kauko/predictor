(ns predictor.app
  (:require [carry-history.core :as history]
            [carry-persistence.core :as persistence]
            [hodgepodge.core :as hp]
            [predictor.core :as core]
            [predictor.reconcilers.app :as reconciler]
            [predictor.controls.app :as control]
            [predictor.models.app :as model]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(def spec (-> {:initial-model (model/new-model)
            :control       (control/-new-control)
            :reconcile     reconciler/-reconcile}))

#_(history/add history/new-hash-history)
#_(persistence/add hp/local-storage :carry-persistence-model)

(core/init! spec)
