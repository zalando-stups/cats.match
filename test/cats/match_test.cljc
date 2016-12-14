(ns cats.match-test
  (:require
    #?(:clj [clojure.test :refer [deftest is testing]]
       :cljs [cljs.test :refer-macros [deftest is testing]])
            [cats.match]
    #?(:clj
            [clojure.core.match :refer [matchm]]
       :cljs [clojure.core.match :refer-macros [matchm]])
            [cats.monad.exception :as exc]
            [cats.monad.maybe :as may]
            [cats.monad.either :as eth]))

(deftest pattern-match-monad-exception
  (testing "matches success"
    (is (= "r1"
           (matchm (exc/success "success")
                   {:success "some"} "r0"
                   {:success "success"} "r1"
                   {:failure "failure"}))))

  (testing "matches and extracts success"
    (is (= "success"
           (matchm (exc/success "success")
                   {:success s} s
                   {:failure f} "failure"))))

  (testing "matches and extracts failure"

    (let [result (matchm (exc/failure (ex-info "Some Info" {}))
                         {:success s} "success"
                         {:failure f} #?(:clj (.getMessage f)
                                         :cljs (.-message f)))]

      (is (= "Some Info" result)))))

(deftest pattern-match-monad-maybe
  (testing "matches just"
    (is (= "r1" (matchm (may/just "hello")
                        {:just "hello"} "r1"
                        :nothing "nothing"))))

  (testing "matches and extracts just"
    (is (= "hello" (matchm (may/just "hello")
                           {:just just} just
                           {:nothing _} "nothing"))))
  (testing "matches nothing"
    (is (= "nothing" (matchm (may/nothing)
                             {:just just} just
                             {:nothing _} "nothing")))))

(deftest pattern-match-monad-either
  (testing "matches left and right"
    (testing "matches left"
      (is (= "r1" (matchm (eth/left "left")
                          {:left "nomatch"} "r0"
                          {:left "left"} "r1"
                          {:right "right"} "r2"))))

    (testing "matches and extracts left"
      (is (= "left" (matchm (eth/left "left")
                            {:left l} l
                            {:right r} "success"))))
    (testing "matches right"
      (is (= "r2" (matchm (eth/right "right")
                          {:left "left"} "r1"
                          {:right "nomatch"} "no"
                          {:right "right"} "r2"))))
    (testing "matches and extracts right"
      (is (= "right" (matchm (eth/right "right")
                             {:left l} l
                             {:right r} r)))))
  (testing "matches success and failure"
    (testing "matches left"
      (is (= "r1" (matchm (eth/left "left")
                          {:failure "nomatch"} "r0"
                          {:failure "left"} "r1"
                          {:success "right"} "r2"))))
    (testing "matches and extracts left"
      (is (= "left" (matchm (eth/left "left")
                            {:failure l} l
                            {:success r} "success"))))
    (testing "matches right"
      (is (= "r2" (matchm (eth/right "right")
                          {:failure "left"} "r1"
                          {:success "nomatch"} "no"
                          {:success "right"} "r2"))))
    (testing "matches and extracts right"
      (is (= "right" (matchm (eth/right "right")
                             {:failure l} l
                             {:success r} r))))))