(ns mpnote.views
  (:require
   [re-frame.core :as re-frame]
   [mpnote.styles :as styles]
   [mpnote.subs :as subs]
   ))

(defn top-in-tl
  [step-ix]
  (+ (styles/step-top step-ix) 20))

(defn vnote [{:keys [tick-no hand finger-no]}]
  (let [dummy? (nil? finger-no)
        top (top-in-tl (dec tick-no))
        ;; FIX: use step instead of tick
        klass (str (name hand) "-note")]
    [:div
     {:class (str (if dummy? "dummy-note" "note") " " klass)
      :style {:top top}
      :key tick-no
      }
     (when
       (and (not dummy?) (pos? finger-no))
       finger-no)]))

(defn key-1-for-tl
  [no]
  (let [notes (re-frame/subscribe [::subs/notes no])]
    (when (not-empty @notes)
      (doall (map vnote @notes)))))

(defn key-1
  ([black-white no tl?]
   [:div.key-1
    {:class (str (name black-white) "-key")
     :key no
     :data-note-no no}
    (when tl? (key-1-for-tl no))]))

(defn keys-3
  [no tl?]
  [:div.octave.keys-3
   (key-1 :white (+ no 0) tl?)
   (key-1 :black (+ no 1) tl?)
   (key-1 :white (+ no 2) tl?)
   ])

(defn keys-1 [no tl?]
  [:div.octave.keys-1
   (key-1 :white (+ no 0) tl?)
   ]
  )

(defn keys-12 [no tl?]
  [:div.octave.keys-12
   {:key no}
   (key-1 :white (+ no 0) tl?)
   (key-1 :black (+ no 1) tl?)
   (key-1 :white (+ no 2) tl?)
   (key-1 :black (+ no 3) tl?)
   (key-1 :white (+ no 4) tl?)
   (key-1 :white (+ no 5) tl?)
   (key-1 :black (+ no 6) tl?)
   (key-1 :white (+ no 7) tl?)
   (key-1 :black (+ no 8) tl?)
   (key-1 :white (+ no 9) tl?)
   (key-1 :black (+ no 10) tl?)
   (key-1 :white (+ no 11) tl?)
   ])

(defn cur-step []
  (let [cur-step-ix (re-frame/subscribe [::subs/cur-step])
        top (top-in-tl @cur-step-ix)]
    [:div.cur-step
      {:style {:top top}}]))

(defn vbar-top
  [step-ix]
  (let [top (top-in-tl step-ix)]
    [:div.bar-top
     {:style {:top top}
      :key step-ix}]))

(defn bar-tops []
  (let [tops (re-frame/subscribe [::subs/bar-tops])]
    (map vbar-top @tops)))

(defn keys-88
  ([] (keys-88 false))
  ([tl?]
   [:div
    {:class (if tl? :timeline :keys-88)}
    (keys-3 21 tl?)
    (doall (map #(keys-12 (+ (* % 12) 24) tl?) (range 7)))
    (keys-1 108 tl?)
    (when tl? (cur-step))
    (when tl? (bar-tops))
    ]))
(defn timeline [] (keys-88 true))

(defn vpedal
  [[step-ix on?]]
  (let [top (top-in-tl step-ix)
        klass (if on? :pedal-on :pedal-off)]
    [:div.pedal
     {:class klass
      :style {:top top}
      :key step-ix}]))

(defn indicator []
  (let [pedals (re-frame/subscribe [::subs/pedals])]
    [:div.indicator-col
     (map vpedal @pedals)]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.app
     [:header
      [:h1
       {:class (styles/brand)}
       "MPのおと"]]
     [:div.main-container
      (indicator)
      [:div.main-col
       (keys-88)
       (timeline)]
      [:div.annotation-col]]
     #_[:h1 {:class (styles/level1)} "Hello from " @name]
     ]))
