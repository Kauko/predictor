(ns predictor.routes.home
  (:require [predictor.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [predictor.templates :as templates]
            [predictor.models.app :as model]))

(defn home-page []
  (layout/render templates/main-page {:element-opts {:data model/model}}))

(defn devcards []
  (layout/render templates/devcards))

(defroutes home-routes
           (GET "/" [] (home-page))
           (GET "/devcards" [] (devcards))
           (GET "/docs" [] (response/ok (-> "docs/docs.md" io/resource slurp))))

