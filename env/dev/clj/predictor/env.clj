(ns predictor.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [predictor.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[predictor started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[predictor has shut down successfully]=-"))
   :middleware wrap-dev})
