(ns fantasy-app.core-test
  (:require [clojure.test :refer :all]
            [fantasy-app.core :refer :all]
            [midje.sweet :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(def all-players
  [{:id 101 :now-cost 81 :xg 0.15 :xa 0.08 :expected-bonus 2 :element_type 2}
   {:id 202 :now-cost 65 :xg 0.30 :xa 0.12 :expected-bonus 3 :element_type 4}
   {:id 303 :now-cost 70 :xg 0.10 :xa 0.20 :expected-bonus 1 :element_type 3}
   {:id 404 :now-cost 90 :xg 0.40 :xa 0.05 :expected-bonus 3 :element_type 4}
   {:id 505 :now-cost 75 :xg 0.25 :xa 0.15 :expected-bonus 2 :element_type 3}
   {:id 606 :now-cost 60 :xg 0.20 :xa 0.10 :expected-bonus 1 :element_type 3}
   {:id 707 :now-cost 85 :xg 0.35 :xa 0.18 :expected-bonus 3 :element_type 4}
   {:id 808 :now-cost 55 :xg 0.12 :xa 0.25 :expected-bonus 2 :element_type 2}])

(def test-player {:id 909 :now-cost 81 :xg 2 :xa 1 :expected-bonus 1 :element_type 2})

(fact "Check if there is a return value"
      (calculate-players-predicted-points (get all-players 0)) =not=> nil)

(fact "Check if goal points are calculated correctly"
      (calculate-players-predicted-points test-player)
      => 12)

(fact "Check if there is a return value"
      (suggest-best-captain all-players) =not=> nil)

(fact "Check if the returned player is the one with max predicted points"
      (suggest-best-captain all-players) => (nth all-players 6))

(fact "Check if there is a return value"
      (rank-players all-players) =not=> nil)

(fact "Check if there is a return value"
      (suggest-best-transfer all-players (get all-players 2) (get all-players 4)) =not=> nil)

(fact "Check if the number of returned players is the same as selected players"
       (count (suggest-best-transfer all-players (get all-players 3) (get all-players 4))) => 2)

(fact "Check if the returned players are not the same as selected players."
      (every? #(not (= (:id %) (:id (get all-players 6)))) (suggest-best-transfer all-players (get all-players 6))) => true)




