(ns mpnote.audio
  (:require
    [cljs-bach.synthesis :as bach]))

(defonce context (bach/audio-context))

(defn note-no->freq
  [no]
  (* 440 (.pow js/Math 1.05946 (- no 69))))

(defn note
  [no]
  (let [freq (note-no->freq no)]
    (bach/connect->
      (bach/add
        (bach/square freq)
        (bach/sawtooth freq))
      (bach/low-pass 600)
      (bach/adsr 0.001 0.4 0.2 0.3)
      (bach/gain 0.05))))

(defn notes
  [& args]
  (apply bach/add (map note args)))

(defn play-notes!
  [& args]
  (-> (apply notes args)
      (bach/connect-> bach/destination)
      (bach/run-with
        context
        (bach/current-time context) 1.0)))

(comment
  (play-notes! 60 64 67)
  (play-notes! 60 67)
  (-> (notes 60 64 67)
    #_(bach/add (note 67)
              (note 64)
              (note 60))
    (bach/connect-> bach/destination)
    (bach/run-with
      context
      (bach/current-time context) 1.0))
  )
