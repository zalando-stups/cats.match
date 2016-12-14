(ns runners.doo-node
  (:require [cljs.test]
            [doo.runner :refer-macros [doo-all-tests doo-tests]]
            [cats.match-test]
            [cljs.nodejs :as node]))


(node/enable-util-print!)

(doo-tests 'cats.match-test)