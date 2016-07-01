(ns predictor.devcards.main
  (:require [carry.core :as carry]
            [taoensso.timbre :as log]
            [carry-atom-sync.core :as carry-atom-sync]
            [rum.core :as rum :include-macros true]
            [predictor.view-models.app :as view-model]
            [predictor.reconcilers.app :as reconciler]
            [predictor.controls.app :as control]
            [predictor.models.app :as model]
            [predictor.views.app :as view]
            [predictor.carry-rum :as carry-rum]
            [carry-logging.core :as logging])
  (:require-macros
    [devcards.core :as dc :refer [defcard deftest defcard-doc]]
    [cljs.test :refer [testing is]]))

(rum/defc mounter <
  {:did-mount    (fn [{[_ dispatch] :rum/args :as state}]
                   (log/debug "On-start")
                   (dispatch :on-start)
                   state)
   :will-unmount (fn [{[_ dispatch] :rum/args :as state}]
                   (log/debug "On-stop")
                   (dispatch :on-stop)
                   state)}
  [child dispatch]
  child)

(defn new-spec []
  {:initial-model (model/new-model)
   :control       (control/-new-control)
   :reconcile     reconciler/-reconcile})

(defn prepare-card [component view-model]
  ; Create app instance.
  (fn [data-atom _]
    (let [app (carry/app (-> (new-spec)
                             (assoc :initial-model @data-atom)
                             (carry-atom-sync/add data-atom)
                             (logging/add "[counter-with-history] ")))
          [_ app-view] (carry-rum/connect app view-model component)]
      ; Render app view.
      (mounter app-view (:dispatch-signal app)))))

(defcard Application
         "Main application"
         (prepare-card view/view view-model/view-model)
         (atom {:val 10})
         {:inspect-data true
          :watch-atom   false
          :history      true})

