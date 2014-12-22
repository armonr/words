(ns words.api
  (:require [clj-http.client :as client])
  (:require [clojure.data.xml :as xml])
  (:require [clojure.java.io :as io])
  (:require [words.conf :as conf])
  (:gen-class))

(defn api-key-dictionary
  "get key for dictionary api"
  []
  (str conf/mw-dictionary-api-key))

(defn api-key-thesaurus
  "get key for theasaurus api"
  []
  (str conf/mw-thesaurus-api-key))

(defn api-base-url
  "Get the base URL for dictionary API"
  []
  (str "http://www.dictionaryapi.com/api/v1/references/"))

(defn dictionary-url
  "Constructs the collegiate dictionary URL for word"
  [word]
  (let [base-url (api-base-url)
        api-key (api-key-dictionary)]
    (str base-url "collegiate/xml/" word "?key=" api-key)))

(defn thesaurus-url
  "Constructs the thesaurus URL for word"
  [word]
  (let [base-url (api-base-url)
        api-key (api-key-thesaurus)]
    (str base-url "thesaurus/xml/" word "?key=" api-key)))

(defn dictionary-word
  "Retrieves data about word from dictionary API"
  [word]
  (let
    [api-url (dictionary-url word)]
    (get (client/get api-url) :body)))

(defn parse-xml
  "Parses XML string into a tree"
  [xml-str]
  (xml/parse-str xml-str))


(defn extract-def
  "Extracts meaning part of the dictionary XML blob"
  [blob]
  (let [def-seq (for [x (xml-seq blob)
                      :when (= :dt (:tag x))]
                  (str (clojure.string/replace
                         (first (:content x))
                         ":"
                         " ")
                    ""))]
    (clojure.string/triml
      (subs (apply str def-seq) 1))))

(defn get-def
  "Get clean definition of word"
  [word]
  (extract-def (parse-xml
                 (dictionary-word word))))



(defn test-func
  "Testing function"
  [& args]
  (let [parsed (parse-xml (slurp "data/api-dictionary-ex.xml"))]
    ;    (println (dictionary-word "implication"))
    ;    (println (parse-xml (slurp "data/api-dictionary-ex.xml")))
    ;    (println (str (extract-def parsed)))
    (println (str "urls: " (dictionary-url "test") ", " (thesaurus-url "test")))
    (println (str "keys: " (api-key-dictionary) ", " (api-key-thesaurus)))))

