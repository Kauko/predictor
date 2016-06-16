(ns predictor.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [predictor.core-test]))

(doo-tests 'predictor.core-test)

