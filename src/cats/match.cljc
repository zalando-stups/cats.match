(ns cats.match
  (:require
    [cats.core :as c]
    #?(:clj  [cats.monad.exception]
       :cljs [cats.monad.exception :refer [Success Failure]])
    #?(:clj  [cats.monad.maybe]
       :cljs [cats.monad.maybe :refer [Just Nothing]])
    #?(:clj  [cats.monad.either]
       :cljs [cats.monad.either :refer [Left Right]])
    #?(:clj
    [clojure.core.match.protocols :refer [IMatchLookup]])
    [clojure.core.match])
  #?(:clj
     (:import (cats.monad.exception Success)
              (cats.monad.exception Failure)
              (cats.monad.maybe Just)
              (cats.monad.maybe Nothing)
              (cats.monad.either Left)
              (cats.monad.either Right))))

(defn- handle-success [this k not-found]
  (case k
    :success @this
    not-found))

(extend-type Success
  #?@(:clj
      [IMatchLookup
       (val-at [this k not-found]
         (handle-success this k not-found))]
      :cljs
      [ILookup
       (-lookup
         ([this k]
           (handle-success this k nil))
         ([this k not-found]
           (handle-success this k not-found)))]))

(defn- handle-failure [this k not-found]
  (case k
    :failure (c/extract this)
    not-found))

(extend-type Failure
  #?@(:clj  [IMatchLookup
             (val-at [this k not-found]
               (handle-failure this k not-found))]
      :cljs [ILookup
             (-lookup
               ([this k]
                 (handle-failure this k nil))
               ([this k not-found]
                 (handle-failure this k not-found)))]))

(defn- handle-just [this k not-found]
  (case k
    :just @this
    not-found))

(extend-type Just
  #?@(:clj  [IMatchLookup
             (val-at [this k not-found]
               (handle-just this k not-found))]
      :cljs [ILookup
             (-lookup
               ([this k]
                 (handle-just this k nil))
               ([this k not-found]
                 (handle-just this k not-found)))]))

(defn- handle-nothing [k not-found]
  (case k
    :nothing :nothing
    not-found))

(extend-type Nothing
  #?@(:clj  [IMatchLookup
             (val-at [_ k not-found]
               (handle-nothing k not-found))]
      :cljs [ILookup
             (-lookup
               ([this k]
                 (handle-nothing k nil))
               ([this k not-found]
                 (handle-nothing k not-found)))]))

(defn- handle-left [this k not-found]
  (case k
    :left @this
    :failure @this
    not-found))

(extend-type Left
  #?@(:clj  [IMatchLookup
             (val-at [this k not-found]
               (handle-left this k not-found))]
      :cljs [ILookup
             (-lookup
               ([this k]
                 (handle-left this k nil))
               ([this k not-found]
                 (handle-left this k not-found)))]))

(defn- handle-right [this k not-found]
  (case k
    :right @this
    :success @this
    not-found))

(extend-type Right
  #?@(:clj  [IMatchLookup
             (val-at [this k not-found]
               (handle-right this k not-found))]
      :cljs [ILookup
             (-lookup
               ([this k]
                 (handle-right this k nil))
               ([this k not-found]
                 (handle-right this k not-found)))]))
