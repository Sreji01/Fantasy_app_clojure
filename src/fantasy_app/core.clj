(ns fantasy-app.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def all-players
  [{:id 101 :now-cost 81 :xg 0.15 :xa 0.08 :expected-bonus 3 :element_type 2}
   {:id 202 :now-cost 65 :xg 0.30 :xa 0.12 :expected-bonus 5 :element_type 4}
   {:id 303 :now-cost 70 :xg 0.10 :xa 0.20 :expected-bonus 2 :element_type 3}
   {:id 404 :now-cost 90 :xg 0.40 :xa 0.05 :expected-bonus 4 :element_type 4}
   {:id 505 :now-cost 75 :xg 0.25 :xa 0.15 :expected-bonus 3 :element_type 3}
   {:id 606 :now-cost 60 :xg 0.20 :xa 0.10 :expected-bonus 1 :element_type 3}
   {:id 707 :now-cost 85 :xg 0.35 :xa 0.18 :expected-bonus 5 :element_type 4}
   {:id 808 :now-cost 55 :xg 0.12 :xa 0.25 :expected-bonus 2 :element_type 2}])

(defn calculate-players-predicted-points
  "A function that calculates a player's predicted points in the next gameweek."
  [player]
  (+ (* (:xg player) 4) (* (:xa player) 3) (*(:expected-bonus player) 1)))

(defn suggest-best-captain
"A function that selects the best captain based on predicted points."
[team]
  team)

