# cats.match

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.danpersa/cats.match.svg)](https://clojars.org/org.clojars.danpersa/cats.match)

Having monads without pattern matching is neither fun nor cool!

cats.match provides pattern matching for the monads in the clojure cats library.

## Usage
Just require `[cats.match]` and you're good to go!

Examples
```clojure
(:require [cats.match]
          [clojure.core.match :as m]
          [cats.monad.exception :as exc]
          [cats.monad.maybe :as may]
          [cats.monad.either :as eth])

; matching on a cats.monad.exception
(m/matchm (exc/success "success")
          {:success s} s
          {:failure f} "failure")
; outputs "success"

; matching on a cats.monad.maybe
(m/matchm (may/just "hello")
          {:just just} just
          {:nothing _} "nothing")
; outputs "hello"

; matching on a cats.monad.either
(m/matchm (eth/left "left")
          {:left l} l
          {:right r} "failure")
; outputs "left"
```

## Installation

Download from [clojars](https://clojars.org/org.clojars.danpersa/cats.match)

For lein users

    [org.clojars.danpersa/cats.match 0.1.0]

## How to contribute

Just do a pull request!

## Publishing

     lein deploy clojars
