# graphite-api

A Library to access Graphite data from Clojure.
Not yet usable.

## Usage

First simple usage:

Examples:

    (require '[graphite-api.core :as graphite])

Load data for one key from localhost on port 80:

    (graphite/load-data "key.foo.bar")

Load data from a remote server on port 8080:

    (graphite/load-data "key.foo.bar" :host "remote.server.com" :port 8080)


## License

Copyright © 2013 Joerg Mueller

Distributed under the Eclipse Public License, the same as Clojure.
