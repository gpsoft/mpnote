(ns mpnote.views
  (:require
   [re-frame.core :as re-frame]
   [mpnote.styles :as styles]
   [mpnote.subs :as subs]
   ))

(defn key-1 [black-white no]
  [:div.key-1
   {:class (str (name black-white) "-key")
    :data-note-no no}])

(defn keys-3 [no]
  [:div.octave.keys-3
   (key-1 :white (+ no 0))
   (key-1 :black (+ no 1))
   (key-1 :white (+ no 2))
   ]
  )

(defn keys-1 [no]
  [:div.octave.keys-1
   (key-1 :white (+ no 0))
   ]
  )

(defn keys-12 [no]
  [:div.octave.keys-12
   (key-1 :white (+ no 0))
   (key-1 :black (+ no 1))
   (key-1 :white (+ no 2))
   (key-1 :black (+ no 3))
   (key-1 :white (+ no 4))
   (key-1 :white (+ no 5))
   (key-1 :black (+ no 6))
   (key-1 :white (+ no 7))
   (key-1 :black (+ no 8))
   (key-1 :white (+ no 9))
   (key-1 :black (+ no 10))
   (key-1 :white (+ no 11))
   ])
(defn keys-88 []
  [:div.keys-88
   (keys-3 21)
   (map #(keys-12 (+ (* % 12) 24)) (range 7))
   (keys-1 108)
   ])

(defn timeline []
  [:div.timeline
   (keys-3 21)
   (map #(keys-12 (+ (* % 12) 24)) (range 7))
   (keys-1 108)
   ])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.app
     [:header
      [:h1
       {:class (styles/brand)}
       "MPのおと"]]
     [:div.main-container
      [:div.indicator-col]
      [:div.main-col
       (keys-88)
       (timeline)]
      [:div.annotation-col]]
     #_[:h1 {:class (styles/level1)} "Hello from " @name]
     ]))
