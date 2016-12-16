# cats.match

<p align="center"><img width="200" alt="cats.match" src="https://rawgithub.com/zalando/cats.match/master/cats.match.jpg"></p>

[![Clojars Project](https://img.shields.io/clojars/v/cats.match.svg)](https://clojars.org/cats.match)
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
          {:right r} "success")
; outputs "left"

(m/matchm (eth/left "left")
          {:success success} "success"
          {:failure failure} "failure")
;outputs "failure"
```

## Installation

Download from [clojars](https://clojars.org/org.clojars.danpersa/cats.match)

For lein users

    [org.clojars.danpersa/cats.match "1.0.0"]

## How to contribute

Open an issue in the github project and/or do a pull request!

## Contributors

- [Dan Persa](https://twitter.com/danpersa)

## Publishing

     lein deploy clojars
     
## License

The MIT License (MIT)
Copyright (c) 2016 Zalando SE

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
