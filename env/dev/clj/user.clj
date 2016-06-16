(ns user
  (:require [mount.core :as mount]
            [predictor.figwheel :refer [start-fw stop-fw cljs]]
            predictor.core))

(defn start []
  (mount/start-without #'predictor.core/repl-server))

(defn stop []
  (mount/stop-except #'predictor.core/repl-server))

(defn restart []
  (stop)
  (start))


