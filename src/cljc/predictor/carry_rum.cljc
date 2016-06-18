(ns predictor.carry-rum
  (:require [rum.core :as rum]))

(defn connect
  [{:keys [model dispatch-signal] :as _app} view-model view]
  {:pre [model (fn? dispatch-signal) (fn? view-model) (fn? view)]}
  (let [app-view-model (view-model (rum/cursor model []))
        app-view (view app-view-model dispatch-signal)]
    [app-view-model app-view]))

(defn render-html [component view-model-atom]
  (rum/render-html (component view-model-atom (fn fake-dispatch [& _]))))