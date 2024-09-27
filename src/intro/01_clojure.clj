(ns intro.01-clojure
  (:import (java.time LocalDate)))

;; Clojure has literals just like any other language:
1
"Hello World"
true
:keyword
'symbol
;; and a bunch more




;; In Clojure, we tend to use kebab-case for names
:my-awesome-keyword
'such-a-symbol





;; Like many languages, Clojure also has lists
'(1, 2, 3)
'(1 2 3) ;; The comma's are optional. This is the same!





;; What makes Clojure a Lisp is that it treats code as data.
;; A function call is a list starting with an operation followed by arguments.
;;
;; + here is a function that takes arguments and adds them together.
(+ 1 2 3)
;; That is also the reason that I had to quote the first list:
;;   Clojure would complain that 1 is not a function.





;; Clojure has no infix operators, it only has function calls.
;; In JS, we would write (4 + 5) * (20 + 3), that doesn't exist in Clojure.
;; If instead, we had times and plus functions in JS, we would write:
;; times(plus(4, 5), plus(20, 3))

;; We do the same in Clojure:
(* (+ 4 5) (+ 20 3))
;; That coincidentally removes all ambiguity (PEMDAS anyone?)





;; Then there are the other data structures
;; Clojure has vectors (of numbers)
[1 2 3]

;; And sets (of keywords)
#{:spam :bacon :eggs}

;; And maps (of keywords to strings)
{:first-name "Lennart" :last-name "Buit"}





;; Thats almost all, but there is one elephant in the room: macros.
;; Technically there is another elephants in the room, special forms -- ignored.

;; let is one of these constructs, it creates lexical bindings.
(let [;; Define num-elephants to be 12
      num-elephants 12
      ;; Define weight-elephant be 4000
      weight-elephant 4000]

  ;; Calculate the weight of my problem.
  (* num-elephants weight-elephant))
;; The let bindings no longer exist.





;; There is also destructuring syntax:
(let [;; take the num-elephants and weight-elephant values from a map
      {:keys [num-elephants weight-elephant]} {:num-elephants 12
                                               :weight-elephant 4000}]

  ;; Calculate the weight of my problem.
  (* num-elephants weight-elephant))





;; def defines something in a namespace ("module" you could say in JS language)
(def excitement-for-clojure "Very")
;; You can refer to that var later:
(str "How exciting are we for clojure? " excitement-for-clojure "!")





;; fn creates a new function
(fn [num-elephants weight-elephant]
  (* num-elephants weight-elephant))

;; And you can use it together with def to define it in a namespace
(def weight-of-my-problem
  (fn [num-elephants weight-elephant] (* num-elephants weight-elephant)))

;; Now we can just call it
(weight-of-my-problem 12 4000)





;; This is obviously so common that we have a macro for that: defn.
;; Looks a bit like a mashup of both:
(defn weight-of-my-problem
  [num-elephants weight-elephant]
  (* num-elephants weight-elephant))





;; And then there is if
(if (> 5 4)
  "Much bigger"
  "Such smaller")

;; With its short form when if you only need the positive branch
(when (> 5 4)
  "Much bigger")

;; Intermezzo: if and when aren't functions, any idea why?





;; Finally: interop with Java. You'll see it today so I'll show the syntax.
;; Call the static method now of LocalDate:
(LocalDate/now)

;; Call the instance method plusDays of LocalDate
(.plusDays (LocalDate/now) 5)





;; Basically thats it.

;; One thing to realise, macros aren't exclusive to the compiler.
;; Clojure code is data, you can generate data and splice that in as code.
;; So, I cannot possibly reference all syntax! But you have a good overview.
