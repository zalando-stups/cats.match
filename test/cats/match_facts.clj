(ns cats.match-facts
  (:require [midje.sweet :refer :all]
            [cats.match]
            [clojure.core.match :as m]
            [cats.monad.exception :as exc]
            [cats.monad.maybe :as may]
            [cats.monad.either :as eth]))

(facts "Pattern match for monad.exception"
       (fact "matches success"
             (m/matchm (exc/success "success")
                       {:success "some"} "r0"
                       {:success "success"} "r1"
                       {:failure "failure"}) => "r1")
       (fact "matches and extracts success"
             (m/matchm (exc/success "success")
                       {:success s} s
                       {:failure f} "failure") => "success")
       (fact "matches and extracts failure"
             (m/matchm (exc/failure (ex-info "Some Info" {}))
                       {:success s} "success"
                       {:failure f} (.getMessage f)) => "Some Info"))

(facts "Pattern match for monad.maybe"
       (fact "matches just"
             (m/matchm (may/just "hello")
                       {:just "hello"} "r1"
                       :nothing "nothing") => "r1")
       (fact "matches and extracts just"
             (m/matchm (may/just "hello")
                       {:just just} just
                       {:nothing _} "nothing") => "hello")
       (fact "matches nothing"
             (m/matchm (may/nothing)
                       {:just just} just
                       {:nothing _} "nothing") => "nothing"))

(facts "Pattern match for monad.either"
       (fact "matches left and right"
             (fact "matches left"
                   (m/matchm (eth/left "left")
                             {:left "nomatch"} "r0"
                             {:left "left"} "r1"
                             {:right "right"} "r2") => "r1")
             (fact "matches and extracts left"
                   (m/matchm (eth/left "left")
                             {:left l} l
                             {:right r} "success") => "left")
             (fact "matches right"
                   (m/matchm (eth/right "right")
                             {:left "left"} "r1"
                             {:right "nomatch"} "no"
                             {:right "right"} "r2") => "r2")
             (fact "matches and extracts right"
                   (m/matchm (eth/right "right")
                             {:left l} l
                             {:right r} r) => "right"))
       (fact "matches success and failure"
             (fact "matches left"
                   (m/matchm (eth/left "left")
                             {:failure "nomatch"} "r0"
                             {:failure "left"} "r1"
                             {:success "right"} "r2") => "r1")
             (fact "matches and extracts left"
                   (m/matchm (eth/left "left")
                             {:failure l} l
                             {:success r} "success") => "left")
             (fact "matches right"
                   (m/matchm (eth/right "right")
                             {:failure "left"} "r1"
                             {:success "nomatch"} "no"
                             {:success "right"} "r2") => "r2")
             (fact "matches and extracts right"
                   (m/matchm (eth/right "right")
                             {:failure l} l
                             {:success r} r) => "right")))
