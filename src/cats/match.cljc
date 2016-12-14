(ns cats.match
  (:require
    [cats.core :as c]
    #?(:clj
    [cats.monad.exception]
       :cljs [cats.monad.exception :refer [Success Failure]])
    #?(:clj
    [cats.monad.maybe]
       :cljs [cats.monad.maybe :refer [Just Nothing]])
    #?(:clj
    [cats.monad.either]
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

(extend-type Success
  #?(:clj  IMatchLookup
     :cljs ILookup)
  #?(:clj  (val-at [this k not-found]
             (case k
               :success (.v this)
               not-found))
     :cljs (-lookup
             ([this k]
               (case k
                 :success @this))
             ([this k not-found]
               (case k
                 :success @this
                 not-found)))))

(extend-type Failure
  #?(:clj  IMatchLookup
     :cljs ILookup)
  #?(:clj  (val-at [this k not-found]
             (case k
               :failure (.e this)
               not-found))
     :cljs (-lookup
             ([this k]
               (case k
                 :failure (c/extract this)))
             ([this k not-found]
               (case k
                 :failure (c/extract this)
                 not-found)))))

(extend-type Just
  #?(:clj  IMatchLookup
     :cljs ILookup)
  #?(:clj  (val-at [this k not-found]
             (case k
               :just (.v this)
               not-found))
     :cljs (-lookup
             ([this k]
               (case k
                 :just @this))
             ([this k not-found]
               (case k
                 :just @this
                 not-found)))))

(extend-type Nothing
  #?(:clj  IMatchLookup
     :cljs ILookup)
  #?(:clj (val-at [this k not-found]
            (case k
              :nothing :nothing
              not-found))
     :cljs (-lookup
             ([this k]
               (case k
                 :nothing :nothing))
             ([this k not-found]
               (case k
                 :nothing :nothing
                 not-found)))))

(extend-type Left
  #?(:clj  IMatchLookup
     :cljs ILookup)
  #?(:clj (val-at [this k not-found]
            (case k
              :left (.v this)
              :failure (.v this)
              not-found))
     :cljs (-lookup
             ([this k]
               (case k
                 :left @this
                 :failure @this))
             ([this k not-found]
               (case k
                 :left @this
                 :failure @this
                 not-found)))))

(extend-type Right
  #?(:clj  IMatchLookup
     :cljs ILookup)
  #?(:clj (val-at [this k not-found]
            (case k
              :right (.v this)
              :success (.v this)
              not-found))
     :cljs (-lookup
             ([this k]
               (case k
                 :right @this
                 :success @this))
             ([this k not-found]
               (case k
                 :right @this
                 :success @this
                 not-found)))))
