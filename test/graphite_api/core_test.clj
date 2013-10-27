(ns graphite-api.core-test
  (:require [clojure.test :refer :all]
            [graphite-api.core :refer :all]))

;; simple usage of the api

(deftest basic-api-call
  (with-redefs [get-server-response (fn [url] "Null")]
    (is (= (get-server-response "foo") "Null"))))

(run-tests)
