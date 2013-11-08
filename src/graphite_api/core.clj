(ns graphite-api.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clj-time.format :refer [formatter unparse]]
            [clj-time.core :refer [date-time]])
  (:import (java.net Socket)
           (java.util Date)
           (java.text SimpleDateFormat)))

;; loading logic
(defmacro urlencode [param] `(java.net.URLEncoder/encode (str ~param)))

(defn- build-query-string [parameter-map]
  (apply str "?"
         (interpose
          "&"
          (map #(str (urlencode (name (key %)))
                     "="
                     (urlencode (val %)))
               parameter-map))))

(defn- build-full-url [server port parameter-map]
  (str "http://" server ":" port "/render" (build-query-string parameter-map)))

(defn- get-server-response [url]
  (:body (client/get url)))

(defn- filter-nil-tuples [array]
  (filter #(first %) array))

(defn- convert-dates [array]
  (map #(vector (first %) (Date. (* 1000 (second %)))) array))

(def date-formatter (SimpleDateFormat. "HH:mm_YYMMDD"))

(defn- date-in-at-format [^Date date]
  (.format date-formatter date))

(defn load-data
  "Returns the data stored in graphite as a vector of tuples.
  Removes elements where values are nil."
  [data-key & {:keys [host port]
               :or {host "localhost" port "80"}}]
  (-> (build-full-url host port {:target data-key :format "json"})
      get-server-response
      json/read-str
      (get 0)
      (get "datapoints")
      filter-nil-tuples
      convert-dates))

;; next add parameters for date-range

;; sending logic
(defn- build-message [^String key
                      ^Number value
                      ^Long timestamp]
  (str key " " value " " (long (/ timestamp 1000)) "\n"))

(defn send-data-point [key value & {:keys [host port timestamp]
                                    :or {host "localhost"
                                         port 2003
                                         timestamp (.getTime (Date.))}}]
  (spit (Socket. host port)
        (build-message key value timestamp)))
