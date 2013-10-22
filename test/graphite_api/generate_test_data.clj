(ns graphite-api.generate-test-data
  (:import (java.net Socket)
           (java.util Date)))

;; This is just to fill an example graphite server with testdata


(def server "localhost")
(def port 2003)

(defn make-message [^String key
                    ^Number value
                    ^Long timestamp]
  (str key " " value " " (long (/ timestamp 1000)) "\n"))

(defn send-message [message]
  (spit (Socket. server port) message))

(def h24 (* 1000 60 60 24))

(defn one-day []
  (take (* 60 24)
        (iterate (partial + 60000) (- (.getTime (Date.)) h24))))

(defn send-one-day [key] (doseq [timestamp (one-day)]
                           (send-message
                            (make-message key
                                          (rand-int 100)
                                          timestamp))))
