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
  [all-players money-in-bank & transfered-out]
  (let [potential-transfers (filter #(not (some (fn [player] (= (:id player) (:id %))) transfered-out)) (rank-players all-players))
        budget (+ money-in-bank (reduce + (map :now-cost transfered-out)))]
    (loop [remaining potential-transfers
           selected-players []
           total-price 0]
      (if (or (empty? remaining)
              (= (count selected-players) (count transfered-out)))
        selected-players
        (let [new-price (+ total-price (:now-cost (first remaining)))]
          (if (<= new-price budget)
            (recur (rest remaining) (conj selected-players (first remaining)) new-price)
            (recur (rest remaining) selected-players total-price)))))))

(defn create-optimal-team
  "A function that creates user's otpimal team."
  [team])

