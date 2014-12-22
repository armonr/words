(ns words.lib
  (:require [words.api :as api])
  (:require [words.data :as data])
  (:gen-class))

(defn repr-micro-sec
  "Representation of microseconds as a string"
  [t]
  (str t " microseconds"))

(defn micro-time
  "returns timestamp"
  []
  (quot (System/nanoTime) 1000))

(defn get-word
  "Wrapps data/get-word"
  []
  (data/get-word))

(defn get-randomized-words
  "Wrapps data/get-randomized-words"
  [word more-words-count]
  (data/get-randomized-words word more-words-count))

(defn stringify-words
  "Formats given word list"
  [word-list]
  (let [word-count (count word-list)]
    (apply str (for [index (range 0 word-count)]
                 (str "\n"
                   (str (inc index)) ". "
                   (get word-list index))))))

(defn present-question
  "Presents the challenge"
  [word words]
  (str
    "\n"
    "Match the definition:\n"
    "\n"
    (api/get-def word)
    "\n"
    (stringify-words words)
    "\n"))



(defn full-test
  "For testing ..."
  [& args]
  (let [start-time (micro-time)]
    (println (data/get-word))
    (println (repr-micro-sec (- (micro-time) start-time)))
    (println (str "urls: " (api/dictionary-url "test") ", " (api/thesaurus-url "test")))
    (println (str "keys: " (api/api-key-dictionary) ", " (api/api-key-thesaurus)))))
