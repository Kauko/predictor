(ns predictor.env
  (:require [taoensso.timbre :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[predictor started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[predictor has shut down successfully]=-"))
   :middleware identity})
