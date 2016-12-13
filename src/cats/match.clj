(ns cats.match
  (:require [cats.monad.exception]
            [cats.monad.maybe]
            [cats.monad.either]
            [clojure.core.match.protocols]
            [clojure.core.match])
  (:import (cats.monad.exception Success)
           (cats.monad.exception Failure)
           (cats.monad.maybe Just)
           (cats.monad.maybe Nothing)
           (cats.monad.either Left)
           (cats.monad.either Right)))

(extend-type Success
  clojure.core.match.protocols/IMatchLookup
  (val-at [this k not-found]
    (case k
      :success (.v this)
      not-found)))

(extend-type Failure
  clojure.core.match.protocols/IMatchLookup
  (val-at [this k not-found]
    (case k
      :failure (.e this)
      not-found)))

(extend-type Just
  clojure.core.match.protocols/IMatchLookup
  (val-at [this k not-found]
    (case k
      :just (.v this)
      not-found)))

(extend-type Nothing
  clojure.core.match.protocols/IMatchLookup
  (val-at [this k not-found]
    (case k
      :nothing :nothing
      not-found)))

(extend-type Left
  clojure.core.match.protocols/IMatchLookup
  (val-at [this k not-found]
    (case k
      :left (.v this)
      :failure (.v this)
      not-found)))

(extend-type Right
  clojure.core.match.protocols/IMatchLookup
  (val-at [this k not-found]
    (case k
      :right (.v this)
      :success (.v this)
      not-found)))
