(ns ^:figwheel-no-load predictor.app
  (:require [predictor.core :as core]
            [devtools.core :as devtools]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :on-jsload core/on-jsload
  :before-jsload core/before-jsload)

(devtools/install!)

(core/init!)
