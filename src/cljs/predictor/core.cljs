(ns predictor.core
  (:require [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [predictor.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [predictor.views.app :as app]
            [rum.core :as rum]
            [predictor.view-models.app :as view-model]
            [predictor.carry-rum :as carry-rum]
            [carry.core :as carry]
            [predictor.reconcilers.app :as reconciler]
            [predictor.controls.app :as control]
            [predictor.models.app :as model])
  (:import goog.History))


;; -------------------------
;; Routes
#_(secretary/set-config! :prefix "#")

#_(secretary/defroute "/" []
                      (session/put! :page :home))

#_(secretary/defroute "/about" []
                      (session/put! :page :about))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defonce app nil)

(def spec {:initial-model model/-initial-model
           :control       (control/-new-control)
           :reconcile     reconciler/-reconcile})

(defn mount! [component]
  (rum/mount component (js/document.getElementById "app")))

(defn main []
  (let [app (carry/app spec)
        [app-view-model app-view] (carry-rum/connect app view-model/view-model app/view)]
    (mount! app-view)
    ((:dispatch-signal app) :on-start)
    (assoc app :view-model app-view-model)))

(defn init! []
  (js/console.log "Init!")
  (load-interceptors!)
  #_(hook-browser-navigation!)
  (set! app (main)))

;;;;;;;;;;;;;;;;;;;;;;;; Figwheel stuff
(defn before-jsload []
  ((:dispatch-signal app) :on-stop))

(defn on-jsload []
  (mount! (app/view (view-model/view-model (:model app)) (:dispatch-signal app)))
  #_(. js/console clear))
