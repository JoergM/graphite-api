(ns graphite-api.generate-test-data
  (:require [graphite-api.core :as graphite])
  (:import (java.util Date)))

;; This is just to fill an example graphite server with testdata

(def h24 (* 1000 60 60 24))

(defn one-day []
  (take (* 60 24)
        (iterate (partial + 60000) (- (.getTime (Date.)) h24))))

(defn send-one-day [key]
  (doseq [timestamp (one-day)]
    (graphite/send-data-point key (rand-int 100) :timestamp timestamp)))
