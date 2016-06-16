(ns predictor.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[predictor started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[predictor has shut down successfully]=-"))
   :middleware identity})
