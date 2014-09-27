(defproject word-keeper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [http-kit "2.1.16"]
                 [compojure "1.1.9"]
                 [org.clojure/clojurescript "0.0-2342"]
                 [om "0.7.3"]
                 [yesql "0.4.0"]
                 [org.postgresql/postgresql "9.3-1102-jdbc41"]
                 [ring "1.2.0"]
                 [cheshire "5.3.1"]
                 [de.ubercode.clostache/clostache "1.4.0"]]
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {:builds [{:source-paths ["src-cljs"]
                        :compiler {:output-to "public/js/frontend.js"
                                   :output-dir "public/js/"
                                   :optimizations :none
                                   :pretty-print true}}]})
