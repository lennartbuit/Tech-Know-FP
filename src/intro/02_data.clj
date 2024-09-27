(ns intro.02-data)

;; Did you notice I haven't talked about classes?
;; While they do exist in Clojure, we don't use them often. We have data.
;; That sounds cheesy. I'll show you.




;; If we have a list, we can conj(oin) another element
(conj '(1 2 3) 4)
;; ... which put it at the start. Interesting!

;; What if I do that on a vector?
(conj [1 2 3] 4)
;; ... now its at the end. Huh!





;; We learned two things here:
;; 1) Many operations work on different types, such as conj
;; 2) The conj operation inserts elements in their natural place:
;;    - For lists, because they are linked list, that is at the start.
;;    - For vectors, because they are arrays thats at the end.





;; assoc(iate) is the operation we use to add a key/value pair to a map
(assoc {:first-name "Lennart"} :learning "Clojure")
;; dissoc(iate) is the opposite
(dissoc {:first-name "Lennart"} :first-name)





;; And the funny thing is. You could think that assoc is a form of conj
;; You are adding a key-value tuple to a map, after all.
;; Clojure says, why not?
(conj {:first-name "Lennart"} [:learning "Clojure"])

;; And similarly, you can see conj as a form of assoc
;; Clojure says, why not?
(assoc [:a :b :c] 3 :d)





;; Then the staples are there.
;; map is used to map values using a function.
;; For example, doubling a list of numbers:
(map (fn [x] (* 2 x)) [1 2 3])





;; filter is used to keep values satisfying some predicate:
(filter even? [5 2 8 7 4])





;; reduce, sometimes called fold in functional programming, combines values.
(reduce + [5 2 8 7 4])
;; Intuitively it:
;; 1) combines ("using plus") 5 and 2 to 7
;; 2) carries this 7, combines it with 8 to 15
;; 3) etc.





;; One other thing thats really cool, many values are also functions
;; invoked keywords take a map as argument and lookup values for that key:
(:name {:name "Lennart"})

;; invoked maps take a keyword as argument and lookup values for that keyword:
({:name "Lennart"} :name)
;; Look, thats the inverse!





;; Thats how deep I want to go in Clojure today.
;; That said there are some constructs shown that you did not see.
;; If the name isn't obvious to you, just ask.
