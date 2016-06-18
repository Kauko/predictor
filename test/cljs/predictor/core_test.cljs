(ns predictor.core-test
  (:require [cljs.test :refer-macros [is are deftest testing use-fixtures]]
            [predictor.core :as rc]))

(deftest test-home
  (is (= true true)))

