(ns words.data
  (:gen-class))

(defn file-count
  "counts the number of data files"
  []
  (int 12))

(defn construct-file-path
  "constructs a data file name"
  [file-index]
  (str "data/words-" file-index ".clj"))

(defn open-file
  "Opens a data file by file index"
  [file-index]
  (read-string (slurp (construct-file-path file-index))))

(defn get-word
  "selects a word from random file"
  []
  (let [file-index (+ 1 (rand-int (file-count)))
        words (open-file file-index)
        word-count (count words)
        word-index (rand-int word-count)]
    (get words word-index)))

(defn get-words
  "returns a list of word-count number of words, plus word"
  [word word-count]
  (let [word-group (for [index (range word-count)] (get-word))]
    (into [] (conj word-group word))))

(defn get-randomized-words
  "Randomizes list of word-count number of words, plus word"
  [word word-count]
  (shuffle (get-words word word-count)))

;tests

(defn test-get-word
  "tests get-word"
  []
  (let [file-index 13
        words (open-file file-index)
        word-count (count words)
        word-index (rand-int word-count)]
    (nth words word-index)))

(defn data-test []
  (let [file-index 13
        file-content (open-file file-index)]
    (println (file-count)
      (construct-file-path file-index)
      (nth ["pikers","ulitis","godown","gulags"] 0)
      file-content
      (get file-content 1)
      (get-word)
      (get-words "madeup" 5))))
