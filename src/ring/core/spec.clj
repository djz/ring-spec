(ns ring.core.spec
  (:require [clojure.spec :as s]
            [clojure.string :as str]))

(defn- lower-case? [s]
  (= s (str/lower-case s)))

;; Request

(s/def :ring.request/server-port     (s/and nat-int? #(<= % 65535)))
(s/def :ring.request/server-name     string?)
(s/def :ring.request/remote-addr     string?)

(s/def :ring.request/uri             (s/and string? #(str/starts-with? % "/")))
(s/def :ring.request/query-string    string?)
(s/def :ring.request/scheme          #{:http :https})
(s/def :ring.request/method          (s/and keyword? (comp lower-case? name)))
(s/def :ring.request/protocol        string?)

(s/def :ring.request/ssl-client-cert #(instance? java.security.cert.X509Certificate %))

(s/def :ring.request/header-name     (s/and string? lower-case?))
(s/def :ring.request/header-value    string?)
(s/def :ring.request/headers         (s/map-of :ring.request/header-name
                                               :ring.request/header-value))

(s/def :ring.request/body            #(instance? java.io.InputStream %))

(s/def :ring.request/request-method  :ring.request/method)

(s/def :ring/request
  (s/keys :req-un [:ring.request/server-port
                   :ring.request/server-name
                   :ring.request/remote-addr
                   :ring.request/uri
                   :ring.request/scheme
                   :ring.request/protocol
                   :ring.request/headers
                   :ring.request/request-method]
          :opt-un [:ring.request/query-string
                   :ring.request/ssl-client-cert
                   :ring.request/body]))
