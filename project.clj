(defproject fantasy-app "0.1.0-SNAPSHOT"
  :description "Fantasy app for predicting player points"
  :url "http://example.com/fantasy-app"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [midje "1.10.9"]
                 [clj-http "3.12.3"] 
                 [cheshire "5.10.1"]] 
  :main ^:skip-aot fantasy-app.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:dependencies [[midje "1.10.9"]]}
             :test {:plugins [[lein-midje "3.2.1"]]}})
