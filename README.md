# graphite-api

A Library to access and write Graphite data to/from Clojure.
Very early stage of development so expect a lot of api changes.

## Usage

### Load data

Simple usage:

    (require '[graphite-api.core :as graphite])

Load data for one key from localhost on port 80:

    (graphite/load-data "key.foo.bar")

Load data from a remote server on port 8080:

    (graphite/load-data "key.foo.bar" :host "remote.server.com" :port 8080)

### Send data to graphite

Simple usage:

    (require '[graphite-api.core :as graphite])

Send one datapoint for a key to a Graphite server on standard port and localhost.

    (graphite/send-data-point "key.foo.bar" 5)

Timestamp is default set to NOW.

Using more options:

    (graphite/send-data-point "key.foo.bar" 5 :timestamp 1383478871681 :host "your.host" :port 12345)

The timestamp is in ms since epoch. Graphite usually expects sec since epoch. This conversion will be done for you so you can use standard java.util.Date to get the timestamp.

## License

Copyright © 2013 Joerg Mueller

Distributed under the Eclipse Public License, the same as Clojure.
