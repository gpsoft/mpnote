(ns mpnote.utils
  (:require [clojure.edn :as edn]
            [clojure.string :as s]
            ))

;; Debugging
(def debug? ^boolean goog/DEBUG)
(def d (if debug? (fn [e] (prn e) e) identity))

(defn dom
  [selector]
  (.querySelector js/document selector))

(defn base64encode
  [s]
  ;; Not sure it's correct.
  (-> s
      (js/encodeURIComponent)
      (js/unescape)
      (js/btoa)))

;; LocalStorage
(defn ls-load-raw
  [ls-key]
  (.getItem js/localStorage ls-key))

(defn ls-store-raw
  [ls-key s]
  (.setItem js/localStorage ls-key s))

(defn ls-remove
  [ls-key]
  (.removeItem js/localStorage ls-key))

(defn ls-load
  ([ls-key] (ls-load ls-key nil))
  ([ls-key default]
   (if-let [s (ls-load-raw ls-key)]
     (edn/read-string s)
     default)))

(defn ls-store
  [ls-key d]
  (->> d
       prn-str
       (ls-store-raw ls-key)))

;; File
(defn upload!
  [selector cont]
  (let [inp (dom selector)
        fil (aget inp "files" 0)]
    (if fil
      (let [fr (js/FileReader.)]
        (aset fr "onload" #(cont (aget % "target" "result")))
        (.readAsText fr fil))
      (cont nil))
    (aset inp "value" "")))
(defn uploading-filename
  [selector]
  (let [inp (dom selector)
        fil (aget inp "files" 0)]
    (when fil (.-name fil))))

(defn download!
  [selector data mime fname]
  (let [data (base64encode data)
        href (str "data:" mime ";charset=utf-8;base64," data)
        a (.createElement js/document "a")]
    (.setAttribute a "download" fname)
    (.setAttribute a "href" href)
    (.appendChild (dom selector) a)
    (.click a)
    (.remove a)))

(comment
  (upload! "input[name=from-fs]" (fn [s] (-> (edn/read-string s) :title prn)))
  (uploading-filename "input[name=from-fs]")
  )
