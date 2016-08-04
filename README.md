# cats.match

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.danpersa/cats.match.svg)](https://clojars.org/org.clojars.danpersa/cats.match)
[![Build Status](https://travis-ci.org/zalando/cats.match.svg?branch=master)](https://travis-ci.org/zalando/cats.match)

Having monads without pattern matching is neither fun nor cool!

cats.match provides pattern matching for the monads in the clojure [cats](http://funcool.github.io/cats/latest/) library.

It uses the [core.match](https://github.com/clojure/core.match) library to do the pattern matching part.

The library is still in development, so use it with care.

## Usage
cats.match provides an out-of-the-box, user-friendly experience.

Just require `[cats.match]` and you're good to go!

## Examples
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

Open an issue in the github project and/or do a pull request!

## Contributors

- [Dan Persa](https://twitter.com/danpersa)

## Publishing

     lein deploy clojars
