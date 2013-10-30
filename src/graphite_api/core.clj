(ns graphite-api.core
  (:require [clj-http.client :as client]))

(defmacro urlencode [param] `(java.net.URLEncoder/encode (str ~param)))

(defn build-query-string [parameter-map]
  (apply str "?"
         (interpose
           "&"
           (map #(str (urlencode (name (key %)))
                      "="
                      (urlencode (val %)))
                parameter-map))))


(defn get-server-response [url]
  (:body (client/get url)))

(defn load-data
  [data-key & {:keys [server port]
               :or {server "localhost" port "2003"}}]
  (str server port data-key))
