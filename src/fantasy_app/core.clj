(ns fantasy-app.core
  (:gen-class)
  (:require [clj-http.client :as client]))

(def players (atom []))

(defn fetch-player-metrics []
  (let [url "https://fantasy.premierleague.com/api/bootstrap-static/"
        response (client/get url {:as :json})]
    (if (= 200 (:status response))
      (let [data (:body response)
            players (:elements data)
            player-statistics (map (fn [player]
                                     {:id (:id player)
                                      :first-name (:first_name player)
                                      :second-name (:second_name player)
                                      :team (:team player)
                                      :now-cost (/ (:now_cost player) 10.0)
                                      :total-points (:total_points player)
                                      :form (:form player)
                                      :expected-points (:ep_next player)})
                                   players)]
        (reset! players player-statistics))
      (println "Failed to retrieve data from the API."))))

(defn scheduled-task []
  (log/info "Fetching player metrics...")
  (fetch-player-metrics))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

      
(defn calculate-players-predicted-points
  "A function that calculates a player's predicted points in the next gameweek."
  [player]
  (let [ep (Double/parseDouble (or (get player :expected-points "0") "0"))]
    ep))

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
           total-price 0
           transfered-out-positions (map :element_type transfered-out)]
      (if (or (empty? remaining)
              (= (count selected-players) (count transfered-out)))
        selected-players
        (let [new-price (+ total-price (:now-cost (first remaining)))
              new-position (:element_type (first remaining))]
          (if (and (<= new-price budget)
                   (some #(= % new-position) transfered-out-positions))
            (recur (rest remaining) (conj selected-players (first remaining)) new-price (remove #(= % new-position) transfered-out-positions))
            (recur (rest remaining) selected-players total-price transfered-out-positions)))))))

(defn optimal-team
  "A function that generates best 15 players for a ceratin gameweek based on predicted points"
  [all-players]
  (let [ranked-players (rank-players all-players)]
    (loop [remaining ranked-players
           selected-players []
           total-price 0]
      (if (or (empty? remaining) (= (count selected-players) 15))
        selected-players
        (let [new-price (:now-cost (first remaining))
              new-position (:element_type (first remaining))]
          (if (> (+ total-price new-price) 1000)
            (recur (rest remaining) selected-players total-price)
            (recur (rest remaining) (conj selected-players (first remaining)) (+ total-price new-price))))))))

