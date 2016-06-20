(ns predictor.test.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [predictor.test.app-test]))

(doo-tests 'predictor.test.app-test)

