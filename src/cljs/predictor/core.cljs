(ns predictor.core
  (:require [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [predictor.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [predictor.views.app :as app])
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

(defn mount-components []
  (app/mount! (js/document.getElementById "app")))

(defn init! []
  (load-interceptors!)
  #_(hook-browser-navigation!)
  (mount-components))
