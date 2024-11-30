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

(defn rank-players
  "A function that ranks players based on predicted points"
  [players]
  (sort-by (fn [player] (- (calculate-players-predicted-points player))) players))

(defn suggest-best-captain
"A function that selects the best captain based on predicted points."
[team]
  (first (rank-players team)))

(defn suggest-best-transfer
  "A function that returns best replacement for the selected players."
  [all-players & selected-player])
