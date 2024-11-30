(ns fantasy-app.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn calculate-players-predicted-points
  "A function that calculates a player's predicted points in the next gameweek."
  [player]
  (+ (* (:xg player) 4) (* (:xa player) 3) (*(:expected-bonus player) 1)))

(defn suggest-best-captain
"A function that selects the best captain based on predicted points."
[team]
  (first (sort-by (fn [player] (- (calculate-players-predicted-points player))) team)))


