(defproject predictor "0.1.0-SNAPSHOT"

  :description "Predict match outcomes"
  :url "https://github.com/Kauko/predictor"

  :dependencies [[luminus-log4j "0.1.3"]
                 [metosin/compojure-api "1.1.2"]
                 [cljs-ajax "0.5.5"]
                 [secretary "1.2.3"]
                 [org.clojure/clojurescript "1.9.36" :scope "provided"]
                 [org.clojure/clojure "1.8.0"]
                 [markdown-clj "0.9.89"]
                 [ring-middleware-format "0.7.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [org.webjars/bootstrap "4.0.0-alpha.2"]
                 [org.webjars/font-awesome "4.6.3"]
                 [org.webjars.bower/tether "1.3.2"]
                 [org.clojure/tools.logging "0.3.1"]
                 [compojure "1.5.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.2.1"]
                 [mount "0.1.10"]
                 [cprop "0.1.8"]
                 [org.clojure/tools.cli "0.3.5"]
                 [luminus-nrepl "0.1.4"]
                 [buddy "0.13.0"]
                 [com.datomic/datomic-free "0.9.5359" :exclusions [org.slf4j/log4j-over-slf4j org.slf4j/slf4j-nop]]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [luminus-immutant "0.2.0"]

                 [rum "0.9.1"]
                 [carry "0.4.0"]
                 [carry-atom-sync "0.2.0"]
                 [carry-history "0.3.0"]
                 [carry-persistence "0.3.0"]
                 [hodgepodge "0.1.3"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [com.taoensso/timbre "4.4.0"]
                 [com.rpl/specter "0.11.2"]
                 [clj-fakes "0.4.0"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"
  :main predictor.core

  :plugins [[lein-cprop "1.0.1"]
            [lein-cljsbuild "1.1.3"]
            [lein-immutant "2.1.0"]
            [lein-sassc "0.10.4"]
            [lein-auto "0.1.2"]]
  :sassc
  [{:src         "resources/scss/screen.scss"
    :output-to   "resources/public/css/screen.css"
    :style       "nested"
    :import-path "resources/scss"}]

  :auto
  {"sassc" {:file-pattern #"\.(scss|sass)$" :paths ["resources/scss"]}}

  :hooks [leiningen.sassc]
  :clean-targets ^{:protect false}
[:target-path [:cljsbuild :builds :app :compiler :output-dir] [:cljsbuild :builds :app :compiler :output-to]]

  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src/cljc" "src/cljs" "env/dev/cljs"]
     :figwheel     true
     :compiler
                   {:main                 "predictor.app"
                    :asset-path           "/js/out"
                    :output-to            "target/cljsbuild/public/js/app.js"
                    :output-dir           "target/cljsbuild/public/js/out"
                    :optimizations        :none
                    :recompile-dependents false
                    :source-map           true
                    :pretty-print         true}}
    :devcards
    {:source-paths ["devcards/cljs" "src/cljc" "src/cljs"]
     :figwheel { :devcards true }
     :compiler {:main "predictor.devcards.main"
                :asset-path "js/devcards_out"
                :output-to  "target/cljsbuild/public/js/predictor_devcards.js"
                :output-dir "target/cljsbuild/public/js/devcards_out"
                :source-map-timestamp true
                :optimizations :none}}
    :test
    {:source-paths ["src/cljc" "src/cljs" "test/cljs" "test/cljc"]
     :compiler
                   {:output-to     "target/test.js"
                    :main          "predictor.test.doo-runner"
                    :optimizations :none
                    :pretty-print  true}}
    :min
    {:source-paths ["src/cljc" "src/cljs" "env/prod/cljs"]
     :compiler
                   {:output-to     "target/cljsbuild/public/js/app.js"
                    :output-dir    "target/uberjar"
                    :externs       ["react/externs/react.js"]
                    :optimizations :advanced
                    :pretty-print  false
                    :closure-warnings
                                   {:externs-validation :off :non-standard-jsdoc :off}}}}}

  :figwheel
  {:http-server-root "public"
   :nrepl-port       7002
   :reload-clj-files false
   :css-dirs         ["resources/public/css"]
   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}


  :profiles
  {:uberjar       {:omit-source    true

                   :prep-tasks     ["compile" ["cljsbuild" "once" "min"]]
                   :aot            :all
                   :uberjar-name   "predictor.jar"
                   :source-paths   ["env/prod/clj"]
                   :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]

   :project/dev   {:dependencies   [[prone "1.1.1"]
                                    [ring/ring-mock "0.3.0"]
                                    [ring/ring-devel "1.5.0"]
                                    [pjstadig/humane-test-output "0.8.0"]
                                    [doo "0.1.6"]
                                    [binaryage/devtools "0.7.0"]
                                    [figwheel-sidecar "0.5.4-3"]
                                    [com.cemerick/piggieback "0.2.2-SNAPSHOT"]

                                    [carry-debugger "0.5.0"]
                                    [carry-logging "0.1.0"]
                                    [prismatic/schema "1.1.2"]
                                    [carry-schema "0.3.0"]
                                    [cljsjs/jquery-ui "1.11.4-0"]
                                    [cljsjs/filesaverjs "1.1.20151003-0"]
                                    [devcards "0.2.1-7"]]
                   :plugins        [[com.jakemccrary/lein-test-refresh "0.14.0"]
                                    [lein-doo "0.1.6"]
                                    [lein-figwheel "0.5.4-3"]
                                    [org.clojure/clojurescript "1.9.36"]]

                   :doo            {:build "test"}
                   :source-paths   ["env/dev/clj" "test/clj" "test/cljc"]
                   :resource-paths ["env/dev/resources"]
                   :repl-options   {:init-ns user
                                    :init    (predictor.core/-main)}
                   :injections     [(require 'pjstadig.humane-test-output)
                                    (pjstadig.humane-test-output/activate!)]}
   :project/test  {:resource-paths ["env/dev/resources" "env/test/resources"]}
   :profiles/dev  {}
   :profiles/test {}})
