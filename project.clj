(defproject cats.match "1.0.0"
  :description "cats.match provides pattern matching for the monads in the clojure cats library."
  :url "https://github.com/zalando/cats.match"
  :license {:name "MIT License"
            :url  "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure       "1.8.0"        :scope "provided"]
                 [org.clojure/clojurescript "1.9.293"      :scope "provided"]
                 [funcool/cats              "2.0.0"        :scope "provided"]
                 [org.clojure/core.match    "0.3.0-alpha4" :scope "provided"]]
  :plugins [[lein-cljsbuild                    "1.1.4"]
            [lein-doo                          "0.1.7"]
            [lein-npm                          "0.6.2"]]
  :doo {:paths {:node    "node"}}
  :profiles {:uberjar {:aot       :all
                       :jvm-opts ^:replace ["-Dclojure.compiler.direct-linking=true"]}
             :dev     {:test-paths   ["test"]
                       :plugins      [[lein-ancient                "0.6.10"]]
                       :dependencies [[lein-doo                    "0.1.7"]]}}
  :cljsbuild {:builds [{:id           "node-test"
                        :source-paths ["src" "test"]
                        :compiler     {:main           runners.doo-node
                                       :optimizations  :simple
                                       :asset-path     "cljs/node-tests/out"
                                       :output-dir     "target/cljs/node-tests/out"
                                       :output-to      "target/cljs/node-tests/all-tests.js"
                                       :cache-analysis true
                                       :target         :nodejs}}]})
