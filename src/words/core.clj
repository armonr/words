(ns words.core
  (:require [words.lib :as lib])
  (:require [words.api :as api])
  (:gen-class))

(defn main-menu
  "The main menu for the program"
  []
  (str
    "\nWhat do you like to do?\n"
    "0 => Print this menu\n"
    "1 => Guess the word!\n"
    "2 => Look up a word in dictionary\n"
    "quit => Exit!\n"))

(defn guess-game
  "Guess game"
  []
  (let [more-words-count 3
        word (str (lib/get-word))
        words (lib/get-randomized-words word more-words-count)]
    (println (lib/present-question word words))
    (do
      (def input (read-line))
      (when-not (= input "quit")
        (do
          (def word-index (inc (.indexOf words word)))
          (if (= word-index (Integer/parseInt input))
            "Correct!"
            (str
              "Sorry, the correct answer was ... "
              word-index)))))))

(defn dict-game
  "Dictionary"
  []
  (do
    (println "\nEnter the word to look up:")
    (api/get-def (read-line))))

(defn -main
  "Entry point"
  []
  (do
    (println (main-menu))
    (loop [input (read-line)]
      (when-not (= input "quit")
        (do
          (cond
            (= input "1") (println (guess-game))
            (= input "2") (println "------\n" (dict-game)))
          (println "\n" (main-menu)))
        (recur (read-line))))
    (str "Bye!")))
