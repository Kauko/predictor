(ns predictor.controls.app
  (:require
    #?(:cljs [cljs.core.match :refer-macros [match]])
    #?(:clj [clojure.core.match :refer [match]])))

(defn -control
  [model signal _dispatch-signal dispatch-action]
  (match signal
         :on-start nil
         :on-stop nil

         :on-increment
         (dispatch-action :increment)

         :on-decrement
         (dispatch-action :decrement)))