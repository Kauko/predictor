(ns predictor.core
  (:require [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [predictor.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [predictor.views.app :as app]
            [rum.core :as rum :include-macros true]
            [predictor.view-models.app :as view-model]
            [predictor.carry-rum :as carry-rum]
            [carry.core :as carry]
            [predictor.reconcilers.app :as reconciler]
            [predictor.controls.app :as control]
            [predictor.models.app :as model]
            [taoensso.timbre :as log]
            [predictor.carry-rum-debugger :as debugger])
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

(rum/defc root [application debugger]
          [:div
           application
           debugger])

(defn mount!
  [app debugger]
  (let [el (js/document.getElementById "app")]
    (if debugger
     (rum/mount (root app debugger) el)
     (rum/mount app el))))

(defn start-app [app]
  (let [[app-view-model app-view] (carry-rum/connect app view-model/view-model app/view)
        [_ debugger-view] (when (debugger/debugger-added? app) (debugger/connect app))]
    (mount! app-view debugger-view)
    ((:dispatch-signal app) :on-start)
    (assoc app :view-model app-view-model)))

(defn build-app [spec]
  (carry/app spec))

(defn init! [spec]
  (load-interceptors!)
  #_(hook-browser-navigation!)
  (set! app (start-app (build-app spec))))

;;;;;;;;;;;;;;;;;;;;;;;; Figwheel stuff
(defn before-jsload []
  ((:dispatch-signal app) :on-stop))

(defn on-jsload []
  (let [])
  (start-app app)
  (. js/console clear)
  (log/info "Reloaded!"))
