(ns ^:figwheel-no-load predictor.app
  (:require [predictor.core :as core]
            [devtools.core :as devtools]
            [figwheel.client :as figwheel :include-macros true]
            [carry-debugger.core :as debugger]
            [carry-history.core :as history]
            [carry-logging.core :as logging]
            [carry-persistence.core :as persistence]
            [hodgepodge.core :as hp]
            [taoensso.timbre :as log]
            [predictor.reconcilers.app :as reconciler]
            [predictor.controls.app :as control]
            [predictor.models.app :as model]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :on-jsload core/on-jsload
  :before-jsload core/before-jsload)

(devtools/install!)

(def logging-config
  {:level :debug  ; e/o #{:trace :debug :info :warn :error :fatal :report}
   :ns-whitelist  [] #_["my-app.foo-ns"]
   :ns-blacklist  [] #_["taoensso.*"]
   :middleware [] ; (fns [data]) -> ?data, applied left->right
   })

#_(log/merge-config! logging-config)

(def spec (-> {:initial-model (model/new-model)
               :control       (control/-new-control)
               :reconcile     reconciler/-reconcile}
              (logging/add "[CARRY] ")))

#_(debugger/add hp/local-storage :carry-debugger-model)
#_(history/add history/new-hash-history)
#_(persistence/add hp/local-storage :carry-persistence-model)

(core/init! spec)
