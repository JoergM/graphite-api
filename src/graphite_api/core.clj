(ns graphite-api.core)

(defmacro urlencode [param] `(java.net.URLEncoder/encode (str ~param)))

(defn build-query-string [parameter-map]
  (apply str "?"
         (interpose
           "&"
           (map #(str (urlencode (name (key %)))
                      "="
                      (urlencode (val %)))
                parameter-map))))

