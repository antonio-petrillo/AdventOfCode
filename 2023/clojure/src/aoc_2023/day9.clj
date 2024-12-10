(ns aoc.day9
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn parse-line-1 [line]
  (let [nums (reverse (re-seq #"-?\d+" line))]
    (map parse-long nums)))

(defn parse-line-2 [line]
  (let [nums (re-seq #"-?\d+" line)]
    (map parse-long nums)))

(defn input [parse-fn]
  (map parse-fn
       (s/split-lines
        (->> "resources/day9.txt"
             io/reader
             slurp))))

(defn process-line [line]
  (loop [sum 0 line line]
    (if (every? zero? line)
      sum
      (recur
       (+ sum (first line))
       (->> line
            (partition 2 1)
            (map (fn [[a b]] (- a b))))))))

(defn solution-1 []
  (->> (input parse-line-1)
       (map process-line)
       (reduce + 0)))

(solution-1) ;; => 2174807968

(defn solution-2 []
  (->> (input parse-line-2)
       (map process-line)
       (reduce + 0)))

(defn solution-2-bad []
  (->> (input parse-line-1)
       (map (comp process-line reverse))
       (reduce + 0)))

(solution-2) ;; => 1208

(time (solution-1))
;; Elapsed time: 49.525625 msecs
(time (solution-2))
;; Elapsed time: 51.554917 msecs
(time (solution-2-bad))
;; Elapsed time: 51.736875 msecs
