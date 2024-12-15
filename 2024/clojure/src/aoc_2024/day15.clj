(ns aoc-scratch.day15
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [clojure.set :as set]))
(def input-ex-2
  (let [[map-str moves] (-> "resources/day15ex-2.txt"
                           slurp
                           (s/split #"\n\n"))]
    {:deposit (->> map-str
                s/split-lines
                (map vec)
                (into []))
      :moves (->> moves s/split-lines (s/join "") vec)}))

(def input
  (let [[map-str moves] (-> "resources/day15.txt"
                           slurp
                           (s/split #"\n\n"))]
    {:deposit (->> map-str
                s/split-lines
                (map vec)
                (into []))
      :moves (->> moves s/split-lines (s/join "") vec)}))

(def deltas
  {\^ [-1 0]
   \v [1 0]
   \> [0 1]
   \< [0 -1]})

(defn find-next-free-spot [deposit pos move]
  (let [delta (deltas move)]
    (loop [p (mapv + pos delta)]
      (case (get-in deposit p)
        \# :none
        \. p
        \O (recur (mapv + p delta))))))

(defn shift-boxes-and-robot [deposit robot-pos next-robot-pos move]
  (let [next-free-spot (find-next-free-spot deposit next-robot-pos move)]
    (if (= :none next-free-spot)
      [robot-pos deposit] ;; no-op, no free space
      [next-robot-pos
       (-> deposit
           (assoc-in next-free-spot \O)
           (assoc-in next-robot-pos \.))])))

(defn move-robot [pos deposit move]
  (let [next-robot-pos (mapv + pos (deltas move))]
    (case (get-in deposit next-robot-pos)
      \# [pos deposit] ;; no op
      \. [next-robot-pos deposit] ;; move robot
      \O (shift-boxes-and-robot deposit pos next-robot-pos move))))

(defn get-start-pos [deposit]
  (reduce #(reduced %2)  nil (for [i (range (count deposit)) j (range (count (first deposit)))
                 :when (= \@ (get-in deposit [i j]))]
           [i j])))

(defn arrange [{:keys [deposit moves]}]
  (loop [pos (get-start-pos deposit)
         dep (assoc-in deposit pos \.)
         [m & ms] moves]
    (if (nil? m)
      dep
      (let [[p d] (move-robot pos dep m)]
        (recur p d ms)))))

(defn part-1 [input]
  (let [arranged (arrange input)]
    (->> arranged
         (map-indexed (fn [i row]
                        (map-indexed #(if (= \O %2) (+ (* i 100) %1) 0) row)))
         (apply concat)
         (reduce + 0))))

(part-1 input) ;=> 1514333
