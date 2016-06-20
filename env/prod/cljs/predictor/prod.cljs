(ns predictor.app
  (:require [carry-history.core :as history]
            [carry-persistence.core :as persistence]
            [hodgepodge.core :as hp]
            [taoensso.timbre :as log]
            [predictor.core :as core]
            [predictor.reconcilers.app :as reconciler]
            [predictor.controls.app :as control]
            [predictor.models.app :as model]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(def logging-config
  {:level :warn  ; e/o #{:trace :debug :info :warn :error :fatal :report}
   :ns-whitelist  [] #_["my-app.foo-ns"]
   :ns-blacklist  [] #_["taoensso.*"]
   :middleware [] ; (fns [data]) -> ?data, applied left->right
   })

(log/merge-config! logging-config)

(def spec (-> {:initial-model (model/new-model)
            :control       (control/-new-control)
            :reconcile     reconciler/-reconcile}))

#_(history/add history/new-hash-history)
#_(persistence/add hp/local-storage :carry-persistence-model)

(core/init! spec)
