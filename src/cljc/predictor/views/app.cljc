(ns predictor.views.app
  (:require [rum.core :as rum]
            [carry.core :as carry]
            [predictor.models.app :as model]
            [predictor.controls.app :as control]
            [predictor.reconcilers.app :as reconciler]
            [predictor.view-models.app :as view-model]
            [predictor.carry-rum :as carry-rum]))

(rum/defc view < rum/reactive
  [{:keys [counter] :as _view-model} dispatch]
  [:p
   (str "#" (:val (rum/react counter))) " "
   [:button {:on-click #(dispatch :on-increment)} "+"] " "
   [:button {:on-click #(dispatch :on-decrement)} "-"] " "])


(def spec {:initial-model model/-initial-model
           :control       control/-control
           :reconcile     reconciler/-reconcile})

#?(:cljs
   (defn main
     []
     (let [app (carry/app spec)
           [app-view-model app-view] (carry-rum/connect app view-model/view-model view)]
       (rum/mount app-view (.getElementById js/document "app"))
       ((:dispatch-signal app) :on-start)
       (assoc app :view-model app-view-model))))

#?(:cljs
   (defn mount! [mount-el]
     (main)))

;;;;;;;;;;;;;;;;;;;;;;;; Figwheel stuff
#?(:cljs
   (defn before-jsload
     []
     ;((:dispatch-signal app) :on-stop)
     )
   )

#?(:cljs(defn on-jsload
   []
   #_(. js/console clear)))