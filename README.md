# cats.match

<p align="center"><img width="200" alt="cats.match" src="https://rawgithub.com/zalando/cats.match/master/cats.match.jpg"></p>

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.danpersa/cats.match.svg)](https://clojars.org/org.clojars.danpersa/cats.match)
[![Build Status](https://travis-ci.org/zalando/cats.match.svg?branch=master)](https://travis-ci.org/zalando/cats.match)

Having monads without pattern matching is neither fun nor cool!

cats.match provides pattern matching for the monads in the clojure [cats](http://funcool.github.io/cats/latest/) library.

It uses the [core.match](https://github.com/clojure/core.match) library to do the pattern matching part.

There is no similar project providing pattern matching for [cats](http://funcool.github.io/cats/latest/), so when developing [instaskip](https://github.com/zalando-incubator/instaskip) I felt the need to create one.
The initial version of cats.match was embedded into instaskip and now I took some time to extract it.

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

    [org.clojars.danpersa/cats.match "0.1.0"]

## How to contribute

Open an issue in the github project and/or do a pull request!

## Contributors

- [Dan Persa](https://twitter.com/danpersa)

## Publishing

     lein deploy clojars
     
## License

Copyright 2016 Zalando SE

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
