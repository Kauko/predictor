(ns predictor.env
  (:require [clojure.tools.logging :as log]
            [predictor.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[predictor started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[predictor has shut down successfully]=-"))
   :middleware wrap-dev})
