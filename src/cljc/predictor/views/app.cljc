(ns predictor.views.app
  (:require [rum.core :as rum]
            [predictor.models.app :as model]))

(rum/defc app < rum/reactive [atom]
          [:div "It's working!"
           [:span (rum/react atom)]])

#?(:cljs
   (defn mount! [mount-el]
     (rum/mount (app model/model) mount-el)))