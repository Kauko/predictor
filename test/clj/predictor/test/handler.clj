(ns predictor.test.handler
  (:require [clojure.test :refer :all]
            [clj-fakes.core :as f]
            [ring.mock.request :refer :all]
            [predictor.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response ((app) (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
      (is (= 404 (:status response))))))
